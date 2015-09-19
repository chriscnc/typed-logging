(ns typed-logging.annotations
  "Type annotations for the clojure.tools.logging namespace"
  (:require 
    [clojure.core.typed :as t :refer [ann Keyword Bool Any U]]
    [clojure.tools.logging :as log]
    [clojure.tools.logging.impl :as impl]
    )
  (:import [org.slf4j Logger LoggerFactory]))


;; functions in the clojure.tools.logging namespace that need annotations
;; as reported the core.typed.
(ann ^:no-check clojure.tools.logging.impl/enabled? [Logger Keyword -> Bool])
(ann ^:no-check clojure.tools.logging/*logger-factory* LoggerFactory)
(ann ^:no-check clojure.tools.logging/log* [Logger Keyword (U Throwable nil) String -> nil])
(ann ^:no-check clojure.tools.logging.impl/get-logger [LoggerFactory Any -> Logger])

; the second arg of get-logger is annotated as 'Any' because I have not yet figured out 
; its type.



