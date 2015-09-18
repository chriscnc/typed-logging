(ns typed-logging.wrappers-test
  "Tests heavily borrowed and adapted from the clojure.tools.logging library"
  (:refer-clojure :exclude [name])
  (:require 
    [clojure.core.typed :as t]
    [clojure.test :refer :all]
    [clojure.tools.logging :as untyped-logging]
    [clojure.tools.logging.impl :as impl]
    [typed-logging.wrappers :refer :all]
    ))


(def ^{:dynamic true} *entries* (atom []))

(defn test-factory [enabled-set]
  (reify impl/LoggerFactory
    (name [_] "test factory")
    (get-logger [_ log-ns]
      (reify impl/Logger
        (untyped-logging/enabled? [_ level] (contains? enabled-set level))
        (write! [_ lvl ex msg]
          (swap! *entries* conj [(str log-ns) lvl ex msg]))))))

(use-fixtures :once
  (fn [f]
    (binding [untyped-logging/*logger-factory*
              (test-factory #{:trace :debug :info :warn :error :fatal})]
      (f))))

(use-fixtures :each
  (fn [f]
    (f)
    (swap! *entries* (constantly []))))


(deftest enabled-true
  (is (= true (enabled? :fatal))))

(deftest enabled-false
  (binding [untyped-logging/*logger-factory* (test-factory #{})]
    (is (= false (enabled? :fatal)))))

(deftest with-logs-default
  (with-logs "foobar" (println "hello world"))
  (is (= ["foobar"
          :info
          nil
          "hello world"]
        (peek @*entries*))))

(deftest with-logs-level
  (with-logs ["foobar" :fatal :fatal] (println "hello world"))
  (is (= ["foobar"
          :fatal
          nil
          "hello world"]
        (peek @*entries*))))

(deftest println-style
  (are [f kw]
    (= ["typed-logging.wrappers-test"
        kw
        nil
        "hello world"]
      (do
        (f "hello" "world")
        (peek @*entries*)))
    trace :trace
    debug :debug
    info :info
    warn :warn
    error :error
    fatal :fatal))

(deftest println-style-ex
  (let [e (Exception.)]
    (are [f kw]
      (= ["typed-logging.wrappers-test"
          kw
          e
          "hello world"]
        (do
          (f e "hello" "world")
          (peek @*entries*)))
      trace :trace
      debug :debug
      info :info
      warn :warn
      error :error
      fatal :fatal)))

(deftest format-style
  (are [f kw]
    (= ["typed-logging.wrappers-test"
        kw
        nil
        "hello world"]
      (do
        (f "%s %s" "hello" "world")
        (peek @*entries*)))
    tracef :trace
    debugf :debug
    infof :info
    warnf :warn
    errorf :error
    fatalf :fatal))

(deftest format-style-ex
  (let [e (Exception.)]
    (are [f kw]
      (= ["typed-logging.wrappers-test"
          kw
          e
          "hello world"]
        (do
          (f e "%s %s" "hello" "world")
          (peek @*entries*)))
      tracef :trace
      debugf :debug
      infof :info
      warnf :warn
      errorf :error
      fatalf :fatal)))



