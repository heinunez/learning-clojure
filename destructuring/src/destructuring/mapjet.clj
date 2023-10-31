(ns destructuring.mapjet)

(def mapjet-booking
  {:id 8773
   :customer-name "Alice Smith"
   :catering-notes "Vegetarian on Sundays"
   :flights [{:from {:lat 48.9615 :lon 2.4372 :name "Paris Le Bourget Airport"},
              :to {:lat 37.742 :lon -25.6976 :name "Ponta Delgada Airport"}},
             {:from {:lat 37.742 :lon -25.6976 :name "Ponta Delgada Airport"},
              :to {:lat 48.9615 :lon 2.4372 :name "Paris Le Bourget Airport"}}]})

(defn print-mapjet-flight [flight]
  (let [{{lat1 :lat lon1 :lon} :from,{lat2 :lat lon2 :lon} :to} flight]
    (println (str "flying from: lat " lat1 " lon " lon1 " to: lat " lat2 " lon " lon2))))

(defn print-mapjet-booking [booking]
  (let [{:keys [customer-name flights]} booking]
    (println (str customer-name " booked " (count flights) " flights."))
    (let [[flight1 flight2 flight3] flights]
      (when flight1 (print-mapjet-flight flight1))
      (when flight2 (print-mapjet-flight flight2))
      (when flight3 (print-mapjet-flight flight3)))))

(defn main []
  (print-mapjet-booking mapjet-booking))