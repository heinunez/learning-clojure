(ns csvdataset.query
  (:require [clojure.data.csv :as csv]
            [clojure.java.io :as io]
            [semantic-csv.core :as sc]))

(def csv-file "resources/match_scores_1991-2016_unindexed_csv.csv")

(defn federer-wins [csv]
  (with-open [r (io/reader csv)]
    (->> (csv/read-csv r)
         sc/mappify
         (filter #(= "Roger Federer" (:winner_name %)))
         (map #(select-keys % [:winner_name
                               :loser_name
                               :winner_sets_won
                               :loser_sets_won
                               :winner_games_won
                               :loser_games_won
                               :tourney_year_id
                               :tourney_slug]))
         doall)))

(defn match-query [csv pred]
  (with-open [r (io/reader csv)]
    (->> (csv/read-csv r)
         sc/mappify
         (sc/cast-with {:winner_sets_won sc/->int
                        :loser_sets_won sc/->int
                        :winner_games_won sc/->int
                        :loser_games_won sc/->int})
         (filter pred)
         (map #(select-keys % [:winner_name
                               :loser_name
                               :winner_sets_won
                               :loser_sets_won
                               :winner_games_won
                               :loser_games_won
                               :tourney_year_id
                               :tourney_slug]))
         doall)))

(defn main []
  (println (take 3 (federer-wins csv-file)))
  (println (count (match-query csv-file #((hash-set (:winner_name %) (:loser_name %)) "Roger Federer"))))
  (println (count (match-query csv-file #((hash-set (:winner_name %)) "Roger Federer"))))
  (println (count (match-query csv-file #(= (hash-set (:winner_name %) (:loser_name %))
                                            #{"Roger Federer" "Rafael Nadal"})))))