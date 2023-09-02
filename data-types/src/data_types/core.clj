(ns data-types.core
  (:require [clojure.string :as str]))

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

(defn main []
  (println (encode-letter "a" 2))
  (println (encode "Hello World"))
  (println (encode "Super secret"))
  (println (encode "Super secret message"))
  (println (decode (encode "If you want to keep a secret, you must also hide it from yourself"))))
