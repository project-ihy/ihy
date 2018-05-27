(ns eme-ene.util.pitch
  (:require [overtone.music.pitch :as pitch]
            [overtone.algo.lists :as lists]
            [cmmge.util :as util]))

(def circle-of-fifths
  [:c :g :d :a :e :b :f# :db :ab :eb :bb :f])

(defn modulate-key
  [key amount]
  (let [keys-from-key (lists/rotate (.indexOf circle-of-fifths key) circle-of-fifths)]
    ;;meh... I'm not sure how to make it better
    (if (neg? amount)
      (get (apply vector (reverse keys-from-key)) (dec (Math/abs amount)))
      (get keys-from-key amount))))

(defn midi-from-pitch-class-and-octave
  [pitch-class octave]
  (pitch/note (str (name pitch-class) octave)))

(defn convert-melody-to-new-key
  [melody-pitches from-key amount]
  (let [octave (:octave (pitch/note-info (first melody-pitches)))
        to-key (modulate-key from-key amount)
        from-tonic-midi (midi-from-pitch-class-and-octave from-key octave)
        to-tonic-midi (midi-from-pitch-class-and-octave to-key octave)
        difference (- to-tonic-midi from-tonic-midi)]
    (map #(- (pitch/note %) difference) melody-pitches)))

(defn- inc-first
  "Remove the first element, increment it by n, and append to seq."
  [elems n]
  (concat (next elems) [(+ n (first elems))]))

(defn- dec-last
  "Remove the last element, decrement it by n, and prepend to seq."
  [elems n]
  (concat [(- (last elems) n)] (butlast elems)))

;;This is a working clone of `overtone.music.pitch/invert-chord`
;;that function does not properly invert chords "negatively"
(defn invert-chord
  "Move a chord voicing up or down.

    ;first inversion
    (invert-chord [60 64 67] 1) ;=> (64 67 72)

    ; second inversion
    (invert-chord [60 64 67] 2) ;=> (67 72 76)
  "
  [notes shift]
  (cond
    (pos? shift) (recur (inc-first notes 12) (dec shift))
    (neg? shift) (recur (util/spy (dec-last notes 12)) (inc shift))
    (zero? shift) notes))
