(defproject twitter-api/twitter-api "1.8.1"
  :description "Async interface to Twitter's REST and Streaming APIs"
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/data.json "0.2.6"]
                 [org.clojure/data.codec "0.1.0"]
                 [http.async.client "1.2.0"]
                 [clj-oauth "1.5.5"]]
  :url "https://github.com/adamwynne/twitter-api"
  :license {:name "Eclipse Public License"
            :url "https://www.eclipse.org/legal/epl-v10.html"}
  :deploy-repositories [["releases" {:url "https://clojars.org/repo"
                                     :sign-releases false}]]
  :min-lein-version "2.0.0")
