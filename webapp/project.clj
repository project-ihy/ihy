(defproject ihy "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [compojure "1.6.1"]
                 [aleph "0.4.6"]
                 [manifold "0.1.8"]
                 [hiccup "1.0.5"]
                 [selmer "1.12.12"]
                 [org.clojure/data.xml "0.0.8"]
                 [org.clojure/data.zip "0.1.3"]]
  :main ^:skip-aot ihy.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}
             :dev {:repl-options {:init-ns ihy.dev}}})
