(ns multimethods.core)

(def player {:name "Lea" :health 200 :position {:x 10 :y 10 :facing :north}})

(defmulti move (comp :facing :position))

(defmethod move :north
  [entity]
  (update-in entity [:position :y] inc))

(defmethod move :south
  [entity]
  (update-in entity [:position :y] dec))

(defmethod move :west
  [entity]
  (update-in entity [:position :x] inc))

(defmethod move :east
  [entity]
  (update-in entity [:position :x] dec))

(defmethod move :default
  [entity]
  entity)


(defn main
  []
  (println (move player))
  (println (move {:position {:x 10 :y 10 :facing :west}}))
  (println (move {:position {:x 10 :y 10 :facing :south}}))
  (println (move {:position {:x 10 :y 10 :facing :east}}))
  (println (move {:position {:x 10 :y 10 :facing :wall}})))
