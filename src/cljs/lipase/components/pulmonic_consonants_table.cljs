(ns lipase.components.pulmonic-consonants-table
  (:require [re-com.core :refer [box h-box v-box gap border]]))

(def ^:private data
  {:places-of-articulation
    ["Bi-labial"
     "Labio-dental"
     "Dental"
     "Alveolar"
     "Post-alveolar"
     "Retroflex"
     "Palatal"
     "Velar"
     "Uvular"
     "Pharyngeal"
     "Glottal"]

   :manners-of-articulation
   ["Plosive"
    "Nasal"
    "Trill"
    "Tap or Flap"
    "Fricative"
    "Lateral Fricative"
    "Approximant"
    "Lateral Approximant"]

   :consonant-matrix
   [["p" nil nil nil "ɸ" :n/a nil :n/a]
    ["b" "m" "ʙ" nil "β" :n/a nil :n/a]
    [nil "ɱ" nil "ⱱ" "v" :n/a "ʋ" :n/a]
    [nil nil nil nil "f" :n/a nil :n/a]
    ["t" nil nil nil ["θ" "ð" "s"] "ɬ" nil nil] ;; wide columns
    ["d" "n" "r" "ɾ" ["z" "ʃ" "ʒ"] "ɮ" "ɹ" "l"] ;; wide columns
    ["ṭ" nil nil nil "ṣ" nil nil nil]
    ["ḍ" "ṇ" nil "ṛ" "ẓ" nil "ɻ" "ḷ"]
    ["c" nil nil nil "ç" nil nil nil]
    ["ɟ" "ɲ" nil nil "ʝ" nil "j" "ʎ"]
    ["k" nil :n/a :n/a "x" nil nil nil]
    ["g" "ŋ" :n/a :n/a "ɣ" nil "ɰ" "ʟ"]
    ["q" nil nil nil "χ" nil nil nil]
    ["ɢ" "ɴ" "ʀ" nil "ʁ" nil nil nil]
    [nil :n/a nil nil "ħ" :n/a nil :n/a]
    [:n/a :n/a nil nil "ʕ" :n/a nil :n/a]
    ["ʔ" :n/a :n/a :n/a "h" :n/a :n/a :n/a]
    [:n/a :n/a :n/a :n/a "ɦ" :n/a :n/a :n/a]]})

(def ^:private row-size (-> data :manners-of-articulation count inc str))

(defn- labels [coll]
  (map #(box :size "1"
             :justify :center
             :align :center
             :child %)
       coll))

(defn- place-of-articulation []
  (let [coll (:places-of-articulation data)]
    [h-box
     :height "50px"
     :children (labels coll)]))

(defn- manner-of-articulation []
  (let [coll (:manners-of-articulation data)]
    [v-box
     :size row-size
     :justify :center
     :align :center
     :children (labels coll)]))

(defn- consonant [char]
  (case char
    nil  [:div.unused-sound ""]
    :n/a [:div.impossbile-sound "❌"]
         [:button.consonant char]))

(defn- wide-tile [maybe-char]
  (let [child (if (vector? maybe-char)
                [h-box :width "100%" :justify :around :children (map consonant maybe-char)]
                [consonant maybe-char])]
    [box :child child
         :align :center
         :justify :center
         :height "56px"
         :size "3"]))

(defn- tile [char]
  [box :child [consonant char]
       :align :center
       :justify :center
       :height "56px"
       :size "1"])

(defn- columns [column]
  (if (#{"t" "d"} (first column))
    [v-box :size "3" :children (map wide-tile column)]
    [v-box :size "1" :children (map tile column)]))

(defn- table []
  (let [columns-coll (:consonant-matrix data)]
    [h-box
      :size (str row-size " 1 auto")
      :children (map columns columns-coll)]))

(defn component []
  [h-box
   :height "500px"
   :children
   [[v-box
     :size "none"
     :children
     [[gap :size "1"]
      [manner-of-articulation]]]
    [v-box
     :size "1 0 auto"
     :width "100%"
     :children
     [[place-of-articulation]
      [table]]]]])
