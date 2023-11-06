(ns multimethods.distance_calculator)

(def walking-speed 4.0)
(def driving-speed 70.0)

(def paris {:lat 48.856483 :lon 2.352413})
(def bordeaux {:lat 44.834999 :lon -0.575490})
(def london {:lat 51.507351, :lon -0.127758})
(def manchester {:lat 53.480759, :lon -2.242631})

(defn distance [{lat1 :lat lon1 :lon} {lat2 :lat lon2 :lon}]
  (let [deglen 110.25
        x (- lat2 lat1)
        y (* (Math/cos lat2) (- lon2 lon1))]
    (* deglen (Math/sqrt (+ (* y y) (* x x))))))

(defmulti itinerary #(:transport %))

(defmethod itinerary :walking
  [{:keys [from to]}]
  (let [distance (distance from to)
        duration (/ distance walking-speed)]
    {:distance distance :duration duration :cost 0.0}))

(def vehicle-cost-fns
  {:sporche #(* % 0.12 1.5)
   :tayato #(* % 0.07 1.5)
   :sleta #(* % 0.2 0.1)})

(defmethod itinerary :driving
  [{:keys [from to vehicle]}]
  (let [distance (distance from to)
        duration (/ distance driving-speed)
        cost-fn (vehicle vehicle-cost-fns)]
    {:distance distance :duration duration :cost (cost-fn distance)}))

(defn main []
  (println (itinerary {:from paris :to bordeaux :transport :driving :vehicle :tayato}))
  (println (itinerary {:from paris :to bordeaux :transport :walking}))
  (println (itinerary {:from london :to manchester :transport :walking}))
  (println (itinerary {:from manchester :to london :transport :driving :vehicle :sleta})))