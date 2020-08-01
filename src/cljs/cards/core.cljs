(ns cards.core
  (:require [devcards.core :as dc]
            cards.hello-world))

(defn init []
  (dc/start-devcard-ui!))
