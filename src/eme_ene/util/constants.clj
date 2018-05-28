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

(def pitches
[{:midi 127, :name :g9, :freq 12543.85}
 {:midi 126, :name :f#9, :freq 11839.82}
 {:midi 125, :name :f9, :freq 11175.3}
 {:midi 124, :name :e9, :freq 10548.08}
 {:midi 123, :name :d#9, :freq 9956.06}
 {:midi 122, :name :d9, :freq 9397.27}
 {:midi 121, :name :c#9, :freq 8869.84}
 {:midi 120, :name :c9, :freq 8372.02}
 {:midi 119, :name :b8, :freq 7902.13}
 {:midi 118, :name :a#8, :freq 7458.62}
 {:midi 117, :name :a8, :freq 7040.0}
 {:midi 116, :name :g#8, :freq 6644.88}
 {:midi 115, :name :g8, :freq 6271.93}
 {:midi 114, :name :f#8, :freq 5919.91}
 {:midi 113, :name :f8, :freq 5587.65}
 {:midi 112, :name :e8, :freq 5274.04}
 {:midi 111, :name :d#8, :freq 4978.03}
 {:midi 110, :name :d8, :freq 4698.64}
 {:midi 109, :name :c#8, :freq 4434.92}
 {:midi 108, :name :c8, :freq 4186.01}
 {:midi 107, :name :b7, :freq 3951.07}
 {:midi 106, :name :a#7, :freq 3729.31}
 {:midi 105, :name :a7, :freq 3520.0}
 {:midi 104, :name :g#7, :freq 3322.44}
 {:midi 103, :name :g7, :freq 3135.96}
 {:midi 102, :name :f#7, :freq 2959.96}
 {:midi 101, :name :f7, :freq 2793.83}
 {:midi 100, :name :e7, :freq 2637.02}
 {:midi 99, :name :d#7, :freq 2489.02}
 {:midi 98, :name :d7, :freq 2349.32}
 {:midi 97, :name :c#7, :freq 2217.46}
 {:midi 96, :name :c7, :freq 2093.0}
 {:midi 95, :name :b6, :freq 1975.53}
 {:midi 94, :name :a#6, :freq 1864.66}
 {:midi 93, :name :a6, :freq 1760.0}
 {:midi 92, :name :g#6, :freq 1661.22}
 {:midi 91, :name :g6, :freq 1567.98}
 {:midi 90, :name :f#6, :freq 1479.98}
 {:midi 89, :name :f6, :freq 1396.91}
 {:midi 88, :name :e6, :freq 1318.51}
 {:midi 87, :name :d#6, :freq 1244.51}
 {:midi 86, :name :d6, :freq 1174.66}
 {:midi 85, :name :c#6, :freq 1108.73}
 {:midi 84, :name :c6, :freq 1046.5}
 {:midi 83, :name :b5, :freq 987.77}
 {:midi 82, :name :a#5, :freq 932.33}
 {:midi 81, :name :a5, :freq 880.0}
 {:midi 80, :name :g#5, :freq 830.61}
 {:midi 79, :name :g5, :freq 783.99}
 {:midi 78, :name :f#5, :freq 739.99}
 {:midi 77, :name :f5, :freq 698.46}
 {:midi 76, :name :e5, :freq 659.26}
 {:midi 75, :name :d#5, :freq 622.25}
 {:midi 74, :name :d5, :freq 587.33}
 {:midi 73, :name :c#5, :freq 554.37}
 {:midi 72, :name :c5, :freq 523.25}
 {:midi 71, :name :b4, :freq 493.88}
 {:midi 70, :name :a#4, :freq 466.16}
 {:midi 69, :name :a4, :freq 440.0}
 {:midi 68, :name :g#4, :freq 415.3}
 {:midi 67, :name :g4, :freq 392.0}
 {:midi 66, :name :f#4, :freq 369.99}
 {:midi 65, :name :f4, :freq 349.23}
 {:midi 64, :name :e4, :freq 329.63}
 {:midi 63, :name :d#4, :freq 311.13}
 {:midi 62, :name :d4, :freq 293.66}
 {:midi 61, :name :c#4, :freq 277.18}
 {:midi 60, :name :c4, :freq 261.63}
 {:midi 59, :name :b3, :freq 246.94}
 {:midi 58, :name :a#3, :freq 233.08}
 {:midi 57, :name :a3, :freq 220.0}
 {:midi 56, :name :g#3, :freq 207.65}
 {:midi 55, :name :g3, :freq 196.0}
 {:midi 54, :name :f#3, :freq 185.0}
 {:midi 53, :name :f3, :freq 174.61}
 {:midi 52, :name :e3, :freq 164.81}
 {:midi 51, :name :d#3, :freq 155.56}
 {:midi 50, :name :d3, :freq 146.83}
 {:midi 49, :name :c#3, :freq 138.59}
 {:midi 48, :name :c3, :freq 130.81}
 {:midi 47, :name :b2, :freq 123.47}
 {:midi 46, :name :a#2, :freq 116.54}
 {:midi 45, :name :a2, :freq 110.0}
 {:midi 44, :name :g#2, :freq 103.83}
 {:midi 43, :name :g2, :freq 98.0}
 {:midi 42, :name :f#2, :freq 92.5}
 {:midi 41, :name :f2, :freq 87.31}
 {:midi 40, :name :e2, :freq 82.41}
 {:midi 39, :name :d#2, :freq 77.78}
 {:midi 38, :name :d2, :freq 73.42}
 {:midi 37, :name :c#2, :freq 69.3}
 {:midi 36, :name :c2, :freq 65.41}
 {:midi 35, :name :b1, :freq 61.74}
 {:midi 34, :name :a#1, :freq 58.27}
 {:midi 33, :name :a1, :freq 55.0}
 {:midi 32, :name :g#1, :freq 51.91}
 {:midi 31, :name :g1, :freq 49.0}
 {:midi 30, :name :f#1, :freq 46.25}
 {:midi 29, :name :f1, :freq 43.65}
 {:midi 28, :name :e1, :freq 41.2}
 {:midi 27, :name :d#1, :freq 38.89}
 {:midi 26, :name :d1, :freq 36.71}
 {:midi 25, :name :c#1, :freq 34.65}
 {:midi 24, :name :c1, :freq 32.7}
 {:midi 23, :name :b0, :freq 30.87}
 {:midi 22, :name :a#0, :freq 29.14}
 {:midi 21, :name :a0, :freq 27.5}
 {:midi 20, :name nil, :freq 25.96}
 {:midi 19, :name nil, :freq 24.5}
 {:midi 18, :name nil, :freq 23.12}
 {:midi 17, :name nil, :freq 21.83}
 {:midi 16, :name nil, :freq 20.6}
 {:midi 15, :name nil, :freq 19.45}
 {:midi 14, :name nil, :freq 18.35}
 {:midi 13, :name nil, :freq 17.32}
 {:midi 12, :name nil, :freq 16.35}
 {:midi 11, :name nil, :freq 15.43}
 {:midi 10, :name nil, :freq 14.57}
 {:midi 9, :name nil, :freq 13.75}
 {:midi 8, :name nil, :freq 12.98}
 {:midi 7, :name nil, :freq 12.25}
 {:midi 6, :name nil, :freq 11.56}
 {:midi 5, :name nil, :freq 10.91}
 {:midi 4, :name nil, :freq 10.3}
 {:midi 3, :name nil, :freq 9.72}
 {:midi 2, :name nil, :freq 9.18}
 {:midi 1, :name nil, :freq 8.66}
 {:midi 0, :name nil, :freq 8.18}])
