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

;;we should definitely track these in state and allow them to be changeable
;; https://github.com/barestides/eme-ene/issues/10
(def midi->drum {:kick 36
                 :snare 40
                 :hi-hat 42})

(def inst->qsynth-channel
  {:bass 2
   :piano 0})

;;these should be more generic, we really have just melodic and percussive, but we could have different receivers
(defn drumkv1-inst
  [inst]
  (omidi/midi-note drumkv1 (inst midi->drum) 100 10))

(defn qsynth-inst
  [inst midi-note dur]
  (omidi/midi-note qsynth midi-note 100 dur (inst inst->qsynth-channel)))

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
