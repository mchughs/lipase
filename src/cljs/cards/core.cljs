(ns cards.core
  (:require [devcards.core :as dc]
            [cards.pulmonic-consonants-table]))

(defn init []
  (dc/start-devcard-ui!))
