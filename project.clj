(defproject eme-ene "0.1.0-SNAPSHOT"
  :description "Tool for algorithmically generating music."
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [overtone "0.10.1"]
                 [com.taoensso/timbre "4.10.0"] ;;logging
                 [fipp "0.6.12"]]               ;;pretty printer
  :main ^:skip-aot eme-ene.core
  :jvm-opts ^:replace []
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
