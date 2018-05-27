(ns eme-ene.midi
  (:require [overtone.studio.midi :as omidi]
            [overtone.music.pitch :as pitch]
            [overtone.libs.event :as event]
            [overtone.at-at :as at-at]
            [overtone.music.time :as t]
            [overtone.music.rhythm :as r]
            [eme-ene.util.constants :refer :all]))

(def receivers (omidi/midi-connected-receivers))

;;assuming `Virtual-Raw-MIDI-4.0` is hooked up to qsynth
(def qsynth (first (filter #(re-find #":3,0" (:name %)) receivers)))
(def drumkv1 (first (filter #(re-find #":3,1" (:name %)) receivers)))

(def midi->drum {:k 36
                 :s 40
                 :hh 42})

(def drum-pattern {:k [{:dur :q} {:dur :q :rest? true} {:dur :q} {:dur :q :rest? true}]
                   :s [{:dur :q :rest? true} {:dur :q} {:dur :q :rest? true} {:dur :q}]
                   :hh (repeat 8 {:dur :e})})

;;"complexity levels", at 4 (quarter notes only), there four possible "slots", half are weak, half are
;;strong
;;at 8, there are 8 possible slots, again half are weak and half are strong

;;ordering from "strongest" to "weakest" would be 1, 3, 2, 4 for 4
;;for 8 would be 1,3,2,4,1and,3and,2and,4and, or 1,5,3,7,2,4,6,8

(defn gen-drum-pattern
  [complexity-level no-beats])

;;probably don't need this map and can just def instfns when deffing tracklists, but I'll keep it around
;;anyway
(def inst-fns
  {:k #(omidi/midi-note drumkv1 (:k midi->drum) 100 10)
   :s #(omidi/midi-note drumkv1 (:s midi->drum) 100 10)
   :hh #(omidi/midi-note drumkv1 (:hh midi->drum) 100 10)
   :bass (fn [midi-note dur] (omidi/midi-note qsynth midi-note 100 dur 2))
   :piano (fn [midi-note dur] (omidi/midi-note qsynth midi-note 100 dur 0))})

(defn times
  "Loop a pattern some number of times"
  [pattern num-times]
  (apply concat (repeat num-times pattern)))

(def kick-track {:name "Kick"
                        :inst-type :percussive
                        :inst-fn (:k inst-fns)
                 :pattern (times [{:dur :q} {:dur :q :rest? true} {:dur :q} {:dur :q :rest? true}]
                                 2)})

(def snare-track {:name "Snare"
                        :inst-type :percussive
                        :inst-fn (:s inst-fns)
                        :pattern (times [{:dur :q :rest? true} {:dur :q} {:dur :q :rest? true} {:dur :q}]
                                        2)})

(def bass-track {:name "Bass"
                 :inst-type :melodic
                 :inst-fn (:bass inst-fns)
                 :pattern '({:pitch :eb2 :dur :e}
                            {:pitch :f2 :dur :e}
                            {:pitch :ab2 :dur :e}
                            {:pitch :bb2 :dur :e}
                            {:pitch :f2 :dur :e}
                            {:dur :e :rest? true}
                            {:dur :de :rest? true}
                            {:pitch :f2 :dur :s}

                            {:pitch :eb2 :dur :e}
                            {:pitch :f2 :dur :e}
                            {:pitch :ab2 :dur :e}
                            {:pitch :bb2 :dur :e}
                            {:pitch :f2 :dur :e}
                            {:dur :e :rest? true}
                            {:dur :de :rest? true})})

(def sample-tracklist [kick-track
                       snare-track
                       bass-track])

(defn inst-track
  [mel inst]
  {:inst-type :melodic
   :inst-fn (inst inst-fns)
   :pattern mel})

(defn rest-bars
  [num-bars]
  ;;assume 4/4
  (repeat num-bars {:dur :w :rest? true}))

(defn simple-build
  "Increment the number of playing tracks"
  [tracks]
  ;;assuming all the tracks' patterns are the same length in beats
  (map-indexed #(update %2 :pattern into (rest-bars (* % 1))) tracks))

(def loop-state
  {:metronome (r/metronome 120)
   :pulse-note :q
   :pulse (atom 1000)
   :playing? (atom true)})

(defn scale-pulse
  [current-pulse vel]
  (+ (* -7.48 vel) 1149.6))

(def step-skip-pct (atom 0.5))
(def up-down-pct (atom 0.5))

(event/on-event [:midi-device "ALSA (http://www.alsa-project.org)" "VirMIDI [hw:3,2,7]"
                 "VirMIDI, VirMidi, Virtual Raw MIDI" 0 :control-change]
                (fn [e]
                  (when (= (:note e) 20)
                    (let [new-val (* (:velocity e) (float (/ 1 127)))]
                      (reset! step-skip-pct new-val))))
                ::step-skip-pct-controller)

(event/on-event [:midi-device "ALSA (http://www.alsa-project.org)" "VirMIDI [hw:3,2,7]"
                 "VirMIDI, VirMidi, Virtual Raw MIDI" 0 :control-change]
                (fn [e]
                  (when (= (:note e) 23)
                    (let [new-val (* (:velocity e) (float (/ 1 127)))]
                      (reset! up-down-pct new-val))))
                ::up-down-pct-controller)

(defn play-chord
  [chord len]
  (let [notes (apply pitch/chord chord)]
    (doseq [note notes]
      (omidi/midi-note qsynth note 100 len 1))))

(def ebm-chords
  [[:eb3 :minor7]
   ;; [:f3 :diminished]
   [:gb3 :major7]
   [:ab3 :minor7]
   [:bb3 :minor7]
   [:cb3 :minor7]
   [:db3 :major7]])


;;possible enhancements, pick a pattern, and bias towards that laters,
;;so if an 3-2-5 prog comes up, include that later down the line, with or wihtout using the same absolute
;;notes
(defn play-random-chords
  [chords pause]
  (while @(:playing? loop-state )
    (let [chord (rand-nth chords)]
      (prn chord)
      (play-chord chord 500)
      (Thread/sleep pause))))

;;play a chord with just one keypress!
;; (event/on-event [:midi-device "ALSA (http://www.alsa-project.org)" "VirMIDI [hw:3,2,7]"
;;                  "VirMIDI, VirMidi, Virtual Raw MIDI" 0 :note-on]
;;                 (fn [e]
;;                   (omidi/midi-note qsynth (:note e) (:velocity e) 100)
;;                   (at-at/at (+ (at-at/now) 50)
;;                             #(omidi/midi-note qsynth (+ 4 (:note e)) (:velocity e) 120) pool)
;;                   (at-at/at (+ (at-at/now) 100)
;;                             #(omidi/midi-note qsynth (+ 7 (:note e)) (:velocity e) 140) pool)
;;                   (at-at/at (+ (at-at/now) 150)
;;                             #(omidi/midi-note qsynth (+ 11 (:note e)) (:velocity e) 150) pool)
;;                   (at-at/at (+ (at-at/now) 200)
;;                             #(omidi/midi-note qsynth (+ 14 (:note e)) (:velocity e) 150) pool)
;;                   )
;;                 ::keyboard-handler)
