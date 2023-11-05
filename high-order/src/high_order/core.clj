(ns high-order.core)

(def weapon-fn-map
  {:fists (fn [health] (if (< health 100) (- health 10) health))
   :staff (partial + 35)
   :sword #(- % 100)
   :cast-iron-saucepan #(- % 100 (rand-int 50))
   :sweet-potato identity})

(defn strike
  "With one argument, strike a target with a default :fists `weapon`. With two
argument, strike a target with `weapon` and return the target entity"
  ([target] (strike target :fists))
  ([target weapon]
   (let [weapon-fn (weapon weapon-fn-map)]
     (update target :health weapon-fn))))

(def enemy {:name "Arnold", :health 250})

(defn mighty-strike
  "Strike a `target` with all weapons!"
  [target]
  (let [weapon-fn (apply comp (vals weapon-fn-map))]
    (update target :health weapon-fn)))

(defn main
  []
  (println ((weapon-fn-map :fists) 50))
  (println ((weapon-fn-map :staff) 150))
  (println ((weapon-fn-map :sword) 20))
  (println ((weapon-fn-map :cast-iron-saucepan) 200))
  (println ((weapon-fn-map :sweet-potato) 15))
  (println (strike enemy :sweet-potato))
  (println (strike enemy :sword))
  (println (strike enemy :cast-iron-saucepan)))
