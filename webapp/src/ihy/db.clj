(ns ihy.db
  (:require [ihy.util :as util]
            [clojure.java.io :as io]))

(defn score-dir
  [score-name]
  (io/file "fake-db" score-name))

(defn new-abc-file
  [score-name]
  (io/file (score-dir score-name) (str score-name "-modified" ".abc")))

(defn orig-abc-file
  [score-name]
  (io/file (score-dir score-name) (str score-name ".abc")))

(defn score-abc
  [score-name]
  (let [new-abc-file (new-abc-file score-name)]
    (if (.exists new-abc-file)
      new-abc-file
      (io/file (score-dir score-name) (str score-name ".abc")))))
