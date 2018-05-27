(ns eme-ene.util.constants)

;;is this useful?
(def QUARTER 0.25)
(def HALF 0.5)
(def WHOLE 1)

(def modes->ints
  {:ionian     #{0 2 4 5 7 9 11}
   :dorian     #{0 2 3 5 7 9 10}
   :phrygian   #{0 1 3 5 7 8 10}
   :lydian     #{0 2 4 6 7 9 11}
   :mixolydian #{0 2 4 5 7 9 10}
   :aeolian    #{0 2 3 5 7 8 10}
   :locrian    #{0 1 3 5 6 8 10}})

(def nice-names->intervals
  {:neg-u  -0
   :neg-m2 -1
   :neg-M2 -2
   :neg-m3 -3
   :neg-M3 -4
   :neg-p4 -5
   :neg-tt -6
   :neg-p5 -7
   :neg-m6 -8
   :neg-M6 -9
   :neg-m7 -10
   :neg-M7 -11
   :neg-o  -12
   :u 0
   :m2 1
   :M2 2
   :m3 3
   :M3 4
   :p4 5
   :tt 6
   :p5 7
   :m6 8
   :M6 9
   :m7 10
   :M7 11
   :o 12
   :m9 13
   :M9 14
   :m10 15
   :M10 16
   :p11 17
   :tt2 18
   :p12 19
   :m62 20
   :M62 21
   :m72 22
   :M72 23
   :o2 24})

(def nice-names->note-values
  {:w 1
   :h 0.5
   :dq 0.375
   :q 0.25
   :de 0.1875
   :e 0.125
   :et 0.04166
   :s 0.0625
   :ts 0.03125})
