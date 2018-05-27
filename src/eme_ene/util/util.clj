(ns eme-ene.util.util
  (:require
   [clojure.pprint :as pprint]
   [overtone.music.pitch :refer :all]))

(defn melody->numeric-intervals
  "Converts a melody given as keyword notes to intervals based off the given root."
  [root pitches]
  (map (fn [pitch] (-> pitch
                       note
                       (- (note root)))) pitches))

(defn mean [coll]
  (float (/ (apply + coll) (count coll))))

(defn percent-chance [percent]
  (< (rand) percent))

(defn spy
  [x & [f]]
  (pprint/pprint (if f (f x) x))
  x)
