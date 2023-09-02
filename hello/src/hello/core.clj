(ns hello.core
  (:require [clojure.string :as str]))

(def base-co2 382)
(def base-year 2006)

(defn hello
  "I don't do a whole lot."
  []
  (println "Hello, World!"))

(defn co2-estimate
  "Returns a (conservative) year's estimate of carbon dioxide parts per million in the atmosphere"
  [year]
  (let [year-diff (- year base-year)]
    (+ base-co2 (* 2 year-diff))))

(defn meditate
  [s calmness-level]
  (cond
    (< calmness-level 5) (str (str/upper-case s) ", I TELL YA!")
    (<= 5 calmness-level 9) (str/capitalize s)
    (= calmness-level 10) (str/reverse s)))

(defn main []
  (hello)
  (println (co2-estimate 2070))
  (println (meditate "what we do now echoes in eternity" 1))
  (println (meditate "what we do now echoes in eternity" 6))
  (println (meditate "what we do now echoes in eternity" 10))
  (println (meditate "what we do now echoes in eternity" 50)))