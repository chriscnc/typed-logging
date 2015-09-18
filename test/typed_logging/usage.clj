(ns typed-logging.usage
  (:require 
    [clojure.core.typed :as t]
    [clojure.test :refer :all]
    [typed-logging.wrappers :refer :all]
    ))

(defn usage []
  (info "hello"))

