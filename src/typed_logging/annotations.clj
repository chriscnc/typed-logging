(ns typed-logging.annotations
  "Type annotations for the clojure.tools.logging namespace"
  (:require 
    [clojure.core.typed :as t]
    [clojure.tools.logging :as log]
    [clojure.tools.logging.impl :as impl]
    )
  (:import [org.slf4j Logger LoggerFactory]))


(t/ann ^:no-check clojure.tools.logging.impl/enabled? [Logger t/Keyword -> t/Bool])

; the second arg is annotated as Any because I have not yet figured out its type
(t/ann ^:no-check clojure.tools.logging.impl/get-logger [LoggerFactory t/Any -> Logger])

(t/ann ^:no-check clojure.tools.logging/*logger-factory* LoggerFactory)

(t/ann ^:no-check clojure.tools.logging/log* [Logger t/Keyword (t/U Throwable nil) String -> nil])



