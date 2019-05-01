(ns ihy.server
  (:require [clojure.java.shell :as sh]
            [clojure.java.io :as io]
            [clojure.string :as s]
            [selmer.parser :as selmer]
            [clojure.walk :as walk]
            [compojure.core :as compojure :refer [GET POST]]
            [ring.middleware.params :as params]
            [compojure.route :as route]
            [hiccup.page :as h]
            [aleph.http :as http]
            [ihy.db :as db]
            [ihy.util :refer :all]))

(defn test-page
  [req]
  {:status 200
   :headers {"content-type" "text/plain"}
   :body "Hi"})

(defn success-response
  [body]
  {:status 200
   :headers nil
   :body body})

(defn upload-score
  [score-name]
  (let [temp-score-file (io/file "temp" (str score-name ".mxl"))
        target-mxl-file (io/file "fake-db/" score-name (str score-name "-original.mxl"))
        abc-string (:out (sh/sh "lib/xml2abc.py" (.getPath temp-score-file)))]
    (io/make-parents target-mxl-file)
    (io/copy temp-score-file target-mxl-file)
    (sh/sh "lib/xml2abc.py" "-o" (str "fake-db/" score-name) (.getPath temp-score-file))
    (io/delete-file temp-score-file)))

(defn score-page
  [config score-name new?]
  (let [abcsvg-config
        "%abc-2.2
         %%pagewidth 18cm
         %%bgcolor white
         %%topspace 0
         %%composerspace 0
         %%leftmargin 0.5cm
         %%rightmargin 0.5cm"
        abc (slurp
             (if new?
               (db/new-abc-file score-name)
               (db/orig-abc-file score-name)))]
    (selmer/render-file "templates/score-page.html" {:abc-string abc :score-name score-name})))

(defn generate-instrument
  [score-name req]
  (let [{:keys [start_measure end_measure syncopation complexity]} (:params req)
        ;; fix-weird (fn [measure-no-param]
        ;;             (let [measure-no (Integer/parseInt measure-no-param)]
        ;;               (str
        ;;                (if (< 7 measure-no)
        ;;                  (- measure-no 2)
        ;;                  measure-no))))
        ;; start-measure (fix-weird start_measure)
        ;; end-measure (fix-weird end_measure)
        ;; _ (prn "start: " start-measure)
        ;; _ (prn "end: " end-measure)
        orig-score-path (.getAbsolutePath (db/orig-abc-file score-name))
        new-abc-file (db/new-abc-file score-name)
        new-abc-path (.getAbsolutePath new-abc-file)
        new-xml-target (.getAbsolutePath (db/score-dir score-name))
        new-abc (:out (sh/sh "python3" "../pyfiles/common/parseabcfile.py"
                             "-o" orig-score-path
                             "-n" new-abc-path
                             "-b" start_measure "-e" end_measure
                             "-c" complexity
                             "-s" syncopation))
        redirect-query-string (str "start_measure=" start_measure
                                   "&end_measure=" end_measure
                                   "&complexity=" complexity
                                   "&syncopation=" syncopation)]
    (prn redirect-query-string)
    (sh/sh "python3" "lib/abc2xml.py" "-o" new-xml-target new-abc-path)
    (success-response (str "http://localhost:8080/new-score/" score-name "?" redirect-query-string)

;; start_measure=1&end_measure=5&complexity=2&syncopation=3
)))

(defn wrap-keywordize-params
  "Keywordize every key in the params map of the request. This should
  happen *before* a JSON or EDN body is merged in, since we may not
  those maps to have keywordized keys."
  [handler]
  (fn [req]
    (handler (update req :params walk/keywordize-keys))))

(defn make-handlers
  [config]
  (let [handler (compojure/routes
                 (GET "/test" [] test-page)
                 (GET "/score/:score-name" [score-name] (score-page config score-name false))
                 (GET "/new-score/:score-name" [score-name] (score-page config score-name true))
                 (POST "/upload-score/:score-name" [score-name] (upload-score score-name))
                 (POST "/generate-instrument/:score-name"
                      [score-name :as req] (generate-instrument score-name req))
                 (route/resources "/"))]

    (-> handler
        ;;jinkies add underscore->dash middleware
        wrap-keywordize-params
        params/wrap-params)))

(defn start-server
  [config]
  (let [{:keys [port]} config]
    (http/start-server (make-handlers config) {:port port})))
