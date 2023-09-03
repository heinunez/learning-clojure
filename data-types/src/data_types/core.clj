(ns data-types.core
  (:require [clojure.string :as str]))

; (set! *print-level* 2) set print depth level

(def gemstone-db {:ruby {:name "Ruby"
                         :stock 480
                         :sales [1990 3644 6376 4918 7882 6747 7495 8573 5097 1712]
                         :properties {:dispersion 0.018
                                      :hardness 9.0
                                      :refractive-index [1.77 1.78]
                                      :color "Red"}}})

(defn encode-letter
  [s x]
  (let [code (Math/pow (+ x (int (first (char-array s)))) 2)]
    (str "#" (int code))))

(defn encode
  [s]
  (let [number-of-words (count (str/split s #" "))]
    (str/replace s #"\w" (fn [s] (encode-letter s number-of-words)))))

(defn decode-letter
  [x y]
  (let [number (Integer/parseInt (subs x 1))
        letter (char (- (Math/sqrt number) y))]
    (str letter)))

(defn decode
  [s]
  (let [number-of-words (count (str/split s #" "))]
    (str/replace s #"\#\d+" (fn [s] (decode-letter s number-of-words)))))

(defn durability
  [db gemstone]
  (get-in db [gemstone :properties :hardness]))

(defn change-color
  [db gemstone new-color]
  (assoc-in db [gemstone :properties :color] new-color))

(defn sell
  [db gemstone client-id]
  (let [clients-updated-db (update-in db [gemstone :sales] conj client-id)]
    (update-in clients-updated-db [gemstone :stock] dec)))

(defn main []
  (println (encode-letter "a" 2))
  (println (encode "Hello World"))
  (println (encode "Super secret"))
  (println (encode "Super secret message"))
  (println (decode (encode "If you want to keep a secret, you must also hide it from yourself")))
  (println (get (get (get gemstone-db :ruby) :properties) :hardness))
  (println (:hardness (:properties (:ruby gemstone-db))))
  (println (get-in gemstone-db [:ruby :properties :hardness]))
  (println (durability gemstone-db :ruby))
  (println (durability gemstone-db :pearl))
  (println (assoc (:ruby gemstone-db) :properties {:color "red"})) ; this replace the entire :properties map
  (println (update (:ruby gemstone-db) :properties into {:color "red"})) ; this add color into properties
  (println (assoc-in gemstone-db [:ruby :properties :color] "red"))
  (println (change-color gemstone-db :ruby "blue"))
  (println (update-in gemstone-db [:ruby :stock] dec))
  (println (update-in gemstone-db [:ruby :sales] conj 999))
  (println (sell gemstone-db :ruby 666))) 
