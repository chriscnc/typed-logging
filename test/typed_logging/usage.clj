(ns typed-logging.usage
  "Usage code that can be type-checked to make sure the annotations work."
  (:require 
    [clojure.tools.logging :as log]
    [typed-logging.annotations :refer :all]
    ))


(defn slf4j-api-interface 
  "Cover usage of the slf4j-api interface for the purpose of verifying 
  annotations."
  []
  
  (log/trace "trace")
  (log/trace (Exception.) "trace")
  (log/debug "debug")
  (log/debug (Exception.) "debug")
  (log/info "info")
  (log/info (Exception.) "info")
  (log/warn "warn")
  (log/warn (Exception.) "warn")
  (log/error "error")
  (log/error (Exception.) "error")
  (log/fatal "fatal")
  (log/fatal (Exception.) "fatal")

  (log/tracef "tracef %d" 42)
  (log/tracef (Exception.) "tracef %d" 42)
  (log/debugf "debugf %d" 42)
  (log/debugf (Exception.) "debugf %d" 42)
  (log/infof "infof %d" 42)
  (log/infof (Exception.) "infof %d" 42)
  (log/warnf "warnf %d" 42)
  (log/warnf (Exception.) "warnf %d" 42)
  (log/errorf "errorf %d" 42)
  (log/errorf (Exception.) "errorf %d" 42)
  (log/fatalf "fatalf %d" 42)
  (log/fatalf (Exception.) "fatalf %d" 42)

  (log/enabled? :info)
  (log/enabled? :info *ns*))


