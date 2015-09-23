# Typed-Logging

A Clojure library that provides core.typed annotations for the clojure.tools.logging library.

## Usage

Simply require the annotations into the modules that do logging.

```clojure
(ns example.logging
  (:require [clojure.tools.logging :as log]
            [typed-logging.annotations :refer :all]))

;; use the logging library as usual. The referred annotations
;; should satify the type-checker
```

All macros provided by the underlying [slf4j-api](http://www.slf4j.org/api/org/slf4j/Logger.html) via the clojure.toos.logging namespace are known to type-check.  However, the clojure.tools.logging namespace provides other macros that extend this interface and are known not to type-check with these annotations.  To use those less common macros, wrap their usage in tc-ignore.

### Installation

typed-logging is available on [Clojars](https://clojars.org/chriscnc/typed-logging)

```clojure
[chriscnc/typed-logging "0.5.0"]
```

## License

Copyright © 2015 Chris Cornelison

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
