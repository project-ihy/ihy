(ns eme-ene.user
  (:require [clojure.repl :refer :all]
            [eme-ene.generation.melody-generation :as mel-gen]
            [eme-ene.midi :as midi]
            [eme-ene.player :as player]))

(def mel-gen-config
  {:mode :ionian
   :tonic 60
   ;;relative to the tonic, if tonic is c4 and floor is 6, no notes lower than f#3(?) will be played
   :floor 12
   :ceiling 12

   :smoothness-index 6.0

   :len-beats 4.0
   ;;The generator will only place notes as fine as the `beat-granularity` is specified.
   :beat-granularity :s
   :pulse :q})

(defn try-mel
  []
  (let [mel (mel-gen/mel-gen mel-gen-config)
        ;; mel2 (mel-gen2 config2)
        piano-track (midi/inst-track mel :piano)
        ;; bass-track (cmmge.midi/inst-track mel2 :bass)
        ]
    ;;this is just for messing around with. I don't think you'd ever want to play to melodies that were generated
    ;;separately, even with similar configs.
    ;;melodies that are meant to be played together should have had one melody be used to generate the other
    ;;similar to how "real" music is made
    (player/play-tracks [piano-track])))
