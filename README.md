# Typed-Logging

A Clojure library that provides core.typed annotations for the clojure.tools.logging library.

## Usage

Simply require the annotation into the modules that do logging.

```clojure
(ns example.logging
  (:require [clojure.tools.logging :as log]
            [type-logging.annotations :refer :all]))

;; use the logging library as usual. The referred annotations
;; should satify the type-checker
```

### Installation

typed-logging is available on [Clojars](https://clojars.org/chriscnc/typed-logging)

```clojure
[chriscnc/typed-logging "0.5.0"]
```

## License

Copyright © 2015 Chris Cornelison

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
