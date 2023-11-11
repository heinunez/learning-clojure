(ns reduce.core)

(def weather-days
  [{:max 31
    :min 27
    :description :sunny
    :date "2019-09-24"}
   {:max 28
    :min 25
    :description :cloudy
    :date "2019-09-25"}
   {:max 22
    :min 18
    :description :rainy
    :date "2019-09-26"}
   {:max 23
    :min 16
    :description :stormy
    :date "2019-09-27"}
   {:max 35
    :min 19
    :description :sunny
    :date "2019-09-28"}])

(defn main
  []
  (println (apply max (map :max weather-days)))
  ;; maximum max temp day
  (println (reduce (fn [max, item]
                     (if (> (:max item) (:max max))
                       item
                       max)) weather-days))
  ;; lowest max temp day
  (println (reduce (fn [min, item]
                     (if (< (:max item) (:max min))
                       item
                       min)) weather-days))
  )
