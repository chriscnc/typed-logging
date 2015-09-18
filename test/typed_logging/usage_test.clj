(ns typed-logging.usage-test
  (:require 
    [clojure.core.typed :as t]
    [clojure.test :refer :all]
    ))


(deftest type-check
  (is (t/check-ns 'typed-logging.usage)))
