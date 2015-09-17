(ns typed-logging.wrappers
  "Wrapper macros to work with the tool.logging library in a core.typed workflow."
  (:require [clojure.tools.logging :as logging]
            [clojure.core.typed :refer [tc-ignore]]))


;(defmacro log
;  "Evaluates and logs a message only if the specified level is enabled. See log*
;  for more details."
;  ([level message]
;    `(log ~level nil ~message))
;  ([level throwable message]
;    `(log ~*ns* ~level ~throwable ~message))
;  ([logger-ns level throwable message]
;    `(log *logger-factory* ~logger-ns ~level ~throwable ~message))
;  ([logger-factory logger-ns level throwable message]
;    `(let [logger# (impl/get-logger ~logger-factory ~logger-ns)]
;       (if (impl/enabled? logger# ~level)
;         (log* logger# ~level ~throwable ~message)))))
;
;(defmacro logp
;  "Logs a message using print style args. Can optionally take a throwable as its
;  second arg. See level-specific macros, e.g., debug."
;  {:arglists '([level message & more] [level throwable message & more])}
;  [level x & more]
;  (if (or (instance? String x) (nil? more)) ; optimize for common case
;    `(log ~level (print-str ~x ~@more))
;    `(let [logger# (impl/get-logger *logger-factory* ~*ns*)]
;       (if (impl/enabled? logger# ~level)
;         (let [x# ~x]
;           (if (instance? Throwable x#) ; type check only when enabled
;             (log* logger# ~level x# (print-str ~@more))
;             (log* logger# ~level nil (print-str x# ~@more))))))))
;
;(defmacro logf
;  "Logs a message using a format string and args. Can optionally take a
;  throwable as its second arg. See level-specific macros, e.g., debugf."
;  {:arglists '([level fmt & fmt-args] [level throwable fmt & fmt-args])}
;  [level x & more]
;  (if (or (instance? String x) (nil? more)) ; optimize for common case
;    `(log ~level (format ~x ~@more))
;    `(let [logger# (impl/get-logger *logger-factory* ~*ns*)]
;       (if (impl/enabled? logger# ~level)
;         (let [x# ~x]
;           (if (instance? Throwable x#) ; type check only when enabled
;             (log* logger# ~level x# (format ~@more))
;             (log* logger# ~level nil (format x# ~@more))))))))


(defmacro enabled?
  "Returns true if the specific logging level is enabled.  Use of this macro
  should only be necessary if one needs to execute alternate code paths beyond
  whether the log should be written to."
  ([level]
   `(tc-ignore (logging/enabled? ~level ~*ns*)))
  ([level logger-ns]
   `(tc-ignore (logging/enabled? ~logger-ns ~level))))


;(defmacro spy
;  "Evaluates expr and may write the form and its result to the log. Returns the
;  result of expr. Defaults to :debug log level."
;  ([expr]
;   `(tc-ignore (logging/spy ~expr)))
;  ([level expr]
;   `(tc-ignore (logging/spy ~level ~expr))))

;    `(let [a# ~expr]
;       (log ~level
;         (let [s# (with-out-str
;                    (with-pprint-dispatch code-dispatch ; need a better way
;                      (pprint '~expr)
;                      (print "=> ")
;                      (pprint a#)))]
;           (trim-newline s#)))
;       a#)))
;
;(defmacro spyf
;  "Evaluates expr and may write (format fmt result) to the log. Returns the
;  result of expr. Defaults to :debug log level."
;  ([fmt expr]
;    `(spyf :debug ~fmt ~expr))
;  ([level fmt expr]
;    `(let [a# ~expr]
;       (log ~level (format ~fmt a#))
;       a#)))
;
;(defn log-stream
;  "Creates a PrintStream that will output to the log at the specified level."
;  [level logger-ns]
;  (let [logger (impl/get-logger *logger-factory* logger-ns)]
;    (java.io.PrintStream.
;      (proxy [java.io.ByteArrayOutputStream] []
;        (flush []
;          ; deal with reflection in proxy-super
;          (let [^java.io.ByteArrayOutputStream this this]
;            (proxy-super flush)
;            (let [message (.trim (.toString this))]
;              (proxy-super reset)
;              (if (> (.length message) 0)
;                (log* logger level nil message))))))
;      true)))

;(let [orig (atom nil)    ; holds original System.out and System.err
;      monitor (Object.)] ; sync monitor for calling setOut/setErr
;  (defn log-capture!
;    "Captures System.out and System.err, piping all writes of those streams to
;    the log. If unspecified, levels default to :info and :error, respectively.
;    The specified logger-ns value will be used to namespace all log entries.
;
;    Note: use with-logs to redirect output of *out* or *err*.
;
;    Warning: if the logging implementation is configured to output to System.out
;    (as is the default with java.util.logging) then using this function will
;    result in StackOverflowException when writing to the log."
;    ; Implementation Notes:
;    ; - only set orig when nil to preserve original out/err
;    ; - no enabled? check before making streams since that may change later
;    ([logger-ns]
;      (log-capture! logger-ns :info :error))
;    ([logger-ns out-level err-level]
;      (locking monitor
;        (compare-and-set! orig nil [System/out System/err])
;        (System/setOut  (log-stream out-level logger-ns))
;        (System/setErr (log-stream err-level logger-ns)))))
;  (defn log-uncapture!
;    "Restores System.out and System.err to their original values."
;    []
;    (locking monitor
;      (when-let [[out err :as v] @orig]
;        (swap! orig (constantly nil))
;        (System/setOut out)
;        (System/setErr err)))))


;;;;;;;;;;;;;;;;;;;;;;

;(defmacro with-logs
;  "Evaluates exprs in a context in which *out* and *err* write to the log. The
;  specified logger-ns value will be used to namespace all log entries.
;
;  By default *out* and *err* write to :info and :error, respectively."
;  {:arglists '([logger-ns & body]
;               [[logger-ns out-level err-level] & body])}
;  [arg & body]
;  `(tc-ignore (logging/with-logs ~arg ~@body)))

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

