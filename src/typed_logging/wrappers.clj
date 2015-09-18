(ns typed-logging.wrappers
  "Wrapper macros to work with the tool.logging library in a core.typed workflow."
  (:require [clojure.tools.logging :as logging]
            [clojure.core.typed :refer [tc-ignore]]))


(defmacro enabled?
  "Returns true if the specific logging level is enabled.  Use of this macro
  should only be necessary if one needs to execute alternate code paths beyond
  whether the log should be written to."
  ([level]
   `(tc-ignore (logging/enabled? ~level ~*ns*)))
  ([level logger-ns]
   `(tc-ignore (logging/enabled? ~logger-ns ~level))))


(defmacro with-logs
  "Evaluates exprs in a context in which *out* and *err* write to the log. The
  specified logger-ns value will be used to namespace all log entries.

  By default *out* and *err* write to :info and :error, respectively."
  {:arglists '([logger-ns & body]
               [[logger-ns out-level err-level] & body])}
  [arg & body]
  `(tc-ignore (logging/with-logs ~arg ~@body)))

;; level-specific macros

(defmacro trace
  "Trace level logging using print-style args."
  {:arglists '([message & more] [throwable message & more])}
  [& args]
  `(tc-ignore (logging/trace ~@args)))


(defmacro debug
  "Debug level logging using print-style args."
  {:arglists '([message & more] [throwable message & more])}
  [& args]
  `(tc-ignore (logging/debug ~@args)))


(defmacro info
  "Info level logging using print-style args."
  {:arglists '([message & more] [throwable message & more])}
  [& args]
  `(tc-ignore (logging/info ~@args)))


(defmacro warn
  "Warn level logging using print-style args."
  {:arglists '([message & more] [throwable message & more])}
  [& args]
  `(tc-ignore (logging/warn ~@args)))


(defmacro error
  "Error level logging using print-style args."
  {:arglists '([message & more] [throwable message & more])}
  [& args]
  `(tc-ignore (logging/error ~@args)))


(defmacro fatal
  "Fatal level logging using print-style args."
  {:arglists '([message & more] [throwable message & more])}
  [& args]
  `(tc-ignore (logging/fatal ~@args)))


(defmacro tracef
  "Trace level logging using format."
  {:arglists '([fmt & fmt-args] [throwable fmt & fmt-args])}
  [& args]
  `(tc-ignore (logging/tracef ~@args)))


(defmacro debugf
  "Debug level logging using format."
  {:arglists '([fmt & fmt-args] [throwable fmt & fmt-args])}
  [& args]
  `(tc-ignore (logging/debugf ~@args)))


(defmacro infof
  "Info level logging using format."
  {:arglists '([fmt & fmt-args] [throwable fmt & fmt-args])}
  [& args]
  `(tc-ignore (logging/infof ~@args)))


(defmacro warnf
  "Warn level logging using format."
  {:arglists '([fmt & fmt-args] [throwable fmt & fmt-args])}
  [& args]
  `(tc-ignore (logging/warnf ~@args)))


(defmacro errorf
  "Error level logging using format."
  {:arglists '([fmt & fmt-args] [throwable fmt & fmt-args])}
  [& args]
  `(tc-ignore (logging/errorf ~@args)))


(defmacro fatalf
  "Fatal level logging using format."
  {:arglists '([fmt & fmt-args] [throwable fmt & fmt-args])}
  [& args]
  `(tc-ignore (logging/fatalf ~@args)))

