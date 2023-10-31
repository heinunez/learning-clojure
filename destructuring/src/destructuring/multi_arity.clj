(ns destructuring.multi_arity)

(def weapon-damage {:fists  10.0 :staff 35.0 :sword 100.0 :cast-iron-saucepan 150.0})
(def enemy {:name "Zulkaz" :health 250 :camp :trolls :armor 0.8})
(def ally {:name "Bryanna" :health 80 :camp :gnomes})

(defn strike
  ([{:keys [camp armor], :or {armor 0} :as target}  weapon]
   (let [points (weapon weapon-damage)]
     (if (= :gnomes camp)
       (update target :health + points)
       (let [damage (* points (- 1 armor))]
         (update target :health - damage))))))

(defn main []
  (println (strike enemy :sword))
  (println (strike ally :staff))
  (println (strike enemy :cast-iron-saucepan)))