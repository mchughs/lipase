(ns cards.pulmonic-consonants-table
  (:require [devcards.core]
            [lipase.components.pulmonic-consonants-table :as table])
  (:require-macros
   [devcards.core :refer [defcard defcard-rg]]))

(defcard-rg pulmonic-consonants-table
  "# Table of consonants."
  [table/component])
