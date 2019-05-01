(ns ihy.demo
  (:require [clojure.java.io :as io]))

(def sample-abc "X:206
  T:Selfe,The,3voices. JGi.206
  M:6/8
  L:1/8
  Q:3/8=100
  S:Joshua Gibbons MS,1823,Tealby,Lincs.
  R:jig
  O:Tealby,Lincolnshire
  Z:VMP/R.Greig, 2009
  K:C
  [V:1]|:G|geg ceg|gfe d2g|egf efd|Bdd d2G|
  [V:2]|:G|ECEG2e|edcB3|ced cBA|GEGB2G|
  [V:3]|:z|C,3C,3|C3G,3|C,3D,3|G,3-G,2z|
  [V:1]geg ceg|gfe d2f|ege fdB|c2cc2:|
  [V:2]ECEG2e|edcB2G|c2c dBG|E2EE2:|
  [V:3]C,3C,3|C3G,3|E,3G,3|C,2C,C,2:|
  [V:1]|:g|egc' c'ba|agf e2c|egc egc|Bdd d2g|
  [V:2]|:z|e2gc2z|fedc2z|E2EE2E|DGBB2z|
  [V:3]|:z|C,2C,C,2z|F,,3C,2z|C,2C,C,2z|G,,2G,,G,,2z|
  [V:1]egc' c'ba|agfe2c|cgc fdB|c2cc3:|
  [V:2]e2gc2z|fedc2z|G3F3|E2EE3:|
  [V:3]C,2C,C,2z|F,,2F,,F,,2z|G,3G,,3|C,3-C,3:|")

(def sample2 " X:1
T:Speed the Plough
C:Trad.
L:1/8
M:4/4
I:linebreak $
K:C
 cdef gage | gage gage | f2 af e2 ge | f2 d2 d2 ed | cdef gage | gage gage | f2 af e2 ge | %7
 d2 B2 c4 | c'2 c'b c'geg | c'2 b2 a2 g2 | f2 af e2 ge | f2 d2 d2 gb |$ c'2 c'b c'2 eg | %13
 c'2 b2 a2 g2 | f2 af e2 ge | d2 B2 c4 | %16 ")

(def sample3 "
X:1
T:fa
%%score 1 2
L:1/8
M:4/4
I:linebreak $
K:C
V:1 treble nm=\"Piano\" snm=\"Pno.\"
V:2 treble nm=\"Organ\" snm=\"Org.\"
L:1/4
V:1
 AED F2 A F2 | FAA c2 A F2 | z8 | z8 | z8 | z8 | z8 | z8 | z8 | z8 | z8 | z8 |$ z8 | z8 | z8 | z8 | %16
 z8 | z8 | z8 | z8 | z8 | z8 | z8 | z8 | z8 | z8 | z8 | z8 | z8 |$ z8 | z8 | z8 |] %32
V:2
!pp!!>(! [Aceg]4!>)! |!pp!!>(! [FAc]4!>)! | z4 | z4 | z4 | z4 | z4 | z4 | z4 | z4 | z4 | z4 |$ z4 | %13
 z4 | z4 | z4 | z4 | z4 | z4 | z4 | z4 | z4 | z4 | z4 | z4 | z4 | z4 | z4 | z4 |$ z4 | z4 | z4 |] %32
")

(def newout " X:1
T:Speed the Plough
C:Trad.
L:1/8
M:4/4
I:linebreak $
V:V1
V:V2
K:C
[V:V1]  cdef gage | gage gage | f2 af e2 ge | f2 d2 d2 ed | cdef gage |$ gage gage | f2 af e2 ge | %7
[V:V2]  AEGD CGED | GDED CDDG | C2 GC E2 GE | C2 D2 A2 DC | GEAG DAGA |$ AEGA CEDD | G2 EA C2 EC |
[V:V1]  d2 B2 c4 | c'2 c'b c'geg | c'2 b2 a2 g2 | f2 af e2 ge | f2 d2 d2 gb |$ c'2 c'b c'2 eg | %13
[V:V2]  C2 C,2 C4 | G'2 C'G A'DEG | G'2 D2 E2 D2 | D2 AE D2 AG | C2 C2 G2 GE |$ C'2 A'E E'2 AC |
[V:V1]  c'2 b2 a2 g2 | f2 af e2 ge | d2 B2 c4 | %16
[V:V2]  D'2 D2 D2 E2 | G2 AA E2 EG | A2 G,2 C4 | ")

(def tee-time
  (slurp (io/file "resources/abc_files/Tee_Time.abc")))
