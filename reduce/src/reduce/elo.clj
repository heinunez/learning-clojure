(ns reduce.elo
  (:require [clojure.data.csv :as csv]
            [clojure.java.io :as io]
            [semantic-csv.core :as sc]
            [clojure.math.numeric-tower :as math])
  )

(defn recalculate-rating [k-factor previous-rating expected-outcome real-outcome]
        (+ previous-rating (* k-factor (- real-outcome expected-outcome))))

(defn match-probability [player-1-rating player-2-rating]
  (/ 1
     (+ 1 (math/expt 10 (/ (- player-2-rating player-1-rating) 400)))))

(defn elo-world [path k]
  (with-open [r (io/reader path)]
    (->> (csv/read-csv r)
         sc/mappify
         (sc/cast-with {:winner_sets_won sc/->int
                        :winner_games_won sc/->int
                        :loser_sets_won sc/->int
                        :loser_games_won sc/->int})
         (reduce (fn [{:keys [players] :as acc} {:keys [:winner_slug :loser_slug]}]
                   (let [winner-rating (get players winner_slug 400)
                         loser-rating (get players loser_slug 400)
                         winner-probability (match-probability winner-rating loser-rating)
                         loser-probability (- 1 winner-probability)
                         predictable-match? (not= winner-probability loser-probability)
                         prediction-correct? (> winner-rating loser-rating)
                         correct-predictions (if (and predictable-match? prediction-correct?)
                                               (inc (:correct-predictions acc))
                                               (:correct-predictions acc))
                         predictable-matches (if predictable-match?
                                               (inc (:predictable-match-count acc))
                                               (:predictable-match-count acc))]
                     (-> acc
                         (assoc :predictable-match-count predictable-matches)
                         (assoc :correct-predictions correct-predictions)
                         (assoc-in [:players winner_slug] (recalculate-rating k winner-rating winner-probability 1))
                         (assoc-in [:players loser_slug] (recalculate-rating k loser-rating loser-probability 0))
                         (update :match-count inc))))
                 {:players {} 
                  :match-count 0
                  :predictable-match-count 0 
                  :correct-predictions 0}))))

(defn main []
  (let [ratings (elo-world "resources/match_scores_1991-2016_unindexed_csv.csv" 32)]
    (comment println ratings)
    (println (get-in ratings [:players "roger-federer"]))))

