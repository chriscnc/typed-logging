(ns typed-logging.usage-test
  "Seem to need to put the check-ns call in a test separate from the 
  namespace I'm checking. Better way???"
  (:require 
    [clojure.core.typed :as t]
    [clojure.test :refer :all]
    ))


(deftest type-check
  (is (t/check-ns 'typed-logging.usage)))
