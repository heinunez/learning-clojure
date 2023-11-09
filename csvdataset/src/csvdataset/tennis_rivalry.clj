(ns csvdataset.tennis_rivalry
  (:require [clojure.data.csv :as csv]
            [clojure.java.io :as io]
            [semantic-csv.core :as sc]))

(def csv-file "resources/match_scores_1968-1990_unindexed_csv.csv")

(defn rivalry-data [csv player-1 player-2]
  (with-open [r (io/reader csv)]
    (let [all-matches (->> (csv/read-csv r)
                           sc/mappify
                           (sc/cast-with {:winner_sets_won sc/->int
                                          :loser_sets_won sc/->int
                                          :winner_games_won sc/->int
                                          :loser_games_won sc/->int})
                           (filter #(= (hash-set (:winner_name %) (:loser_name %)) #{player-1 player-2}))
                           (map #(select-keys % [:winner_name
                                                 :loser_name
                                                 :winner_sets_won
                                                 :loser_sets_won
                                                 :winner_games_won
                                                 :loser_games_won
                                                 :tourney_year_id
                                                 :tourney_slug]))
                           doall)
          player-1-games (filter #(= (:winner_name %) player-1) all-matches)
          player-2-games (filter #(= (:winner_name %) player-2) all-matches)]
      {:first-victory-player-1 (first player-1-games)
       :first-victory-player-2 (first player-2-games)
       :total-matches (count all-matches)
       :total-victories-player-1 (count player-1-games)
       :total-victories-player-2 (count player-2-games)
       :most-competitive-matches (filter #(= 1 (- (:winner_sets_won %) (:loser_sets_won %))) all-matches)})))

(defn main []
  (println (rivalry-data csv-file "Boris Becker" "Jimmy Connors")))