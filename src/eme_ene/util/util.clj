(ns eme-ene.util.util
  (:require
   ;; [fipp.ednize :refer (pprint) :rename {pprint fipp}]
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
  [& args]
  (apply pprint/pprint args))

(defn index-by
  "Returns a map, where for each x in coll, (f x) is the key
  and x is the value."
  [f coll]
  (zipmap (mapv f coll) coll))
