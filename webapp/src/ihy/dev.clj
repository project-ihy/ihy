(ns ihy.dev
  (:require [clojure.repl :refer :all]
            [clojure.java.io :as io]
            [ihy.db :as db]
            [ihy.demo :as demo]
            [ihy.server :as server]
            [ihy.util :refer :all]))

(def dev-config
  {:port 8080})

(def aleph-server (atom nil))
(def running? (atom false))

(defn start
  []
  (if @running?
    (println "Server already running")
    (do
      (reset! aleph-server (server/start-server dev-config))
      (reset! running? true))))

(defn stop
  []
  (.close @aleph-server)
  (reset! running? false))

(defn restart
  []
  (do
    (stop)
    (start)))
