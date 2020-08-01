(ns cards.hello-world
  (:require devcards.core
            [lipase.components.example :refer [intro]])
  (:require-macros
   [devcards.core :refer [defcard defcard-rg]]))

(defcard-rg my-first-card
  "# Foobar"
  [intro])
