(ns ihy.util
  (:require [clojure.pprint :as pprint]
            [clojure.string :refer :all]))

(defn spy
  [x]
  (pprint/pprint x)
  x)

(defn score-notes
  "Get the actual notes from an abc score. Strip repeats"
  [score]
  (replace (re-find #"\|.*" score) ":" ""))

(defn get-note-range
  [score start end]
  (as->  score $
    (score-notes $)
    (split $ #"\|")
    (rest $)
    (drop-last $)
    (into [] $)
    (subvec $ start (inc end))))
