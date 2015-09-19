(defproject chriscnc/typed-logging "0.5.1-SNAPSHOT"
  :description "A Clojure library that provides core.typed annotations for the clojure.tools.logging library"
  :url "https://github.com/chriscnc/typed-logging"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [
                 [org.clojure/clojure "1.6.0"]
                 [org.clojure/tools.logging "0.3.1"]
                 [org.clojure/core.typed "0.3.0"]
                 [org.slf4j/slf4j-api "1.6.2"]
                 ]
  :profiles {:dev {:dependencies [[org.slf4j/slf4j-log4j12 "1.6.2"]]}})
