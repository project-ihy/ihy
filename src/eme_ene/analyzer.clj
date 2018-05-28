(ns eme-ene.analyzer
  (:require [eme-ene.util.util :as util]
            [eme-ene.util.constants :refer :all]))

;;What do we want to look at?

;;variability - total note length traveled / cantus length
;;climax placement

(defn distance-traveled
  ([mel]
   (distance-traveled (mapv (comp :midi :pitch) mel) 0))
  ([[first-note second-note :as mel] total]
   (if second-note
     (recur (rest mel) (+ total (Math/abs (- first-note second-note))))
     total)))

(defn melody-length
  [mel pulse]
  (/ (apply + (map #(nice-names->note-values (:dur %)) mel)) (nice-names->note-values pulse)))

(defn smoothness-index
  [mel pulse]
  (float (/ (distance-traveled mel) (melody-length mel pulse))))
