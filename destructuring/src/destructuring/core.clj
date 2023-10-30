(ns destructuring.core)

(def booking [1425, "Bob Smith", "Allergic to unsalted peanuts only",
              [[48.9615,2.4372],[37.742,-25.6976]], [[37.742,-25.6976],[48.9615,2.4372]]])

(defn print-flight
  [flight]
  (let [[departure arrival] flight
        [lat1, lon1] departure
        [lat2, lon2] arrival]
    (println (str "Flying from: Lat " lat1 " Lon " lon1 " to: Lat " lat2 " Lon " lon2))))

(defn print-booking [booking]
  (let [[_ customer-name _ & flights] booking]
    (println (str customer-name " booked " (count flights) " flights."))
    (let [[flight1, flight2, flight3] flights]
      (when flight1 (print-flight flight1))
      (when flight2 (print-flight flight2))
      (when flight3 (print-flight flight3)))))

(defn main []
  (print-booking booking))
