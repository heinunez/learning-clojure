(ns csvdataset.core
  (:require [clojure.data.csv :as csv]
            [clojure.java.io :as io]
            [semantic-csv.core :as sc]))

(def csv-file "resources/match_scores_1991-2016_unindexed_csv.csv")

(defn count-csv []
  (with-open [r (io/reader csv-file)]
    (count (csv/read-csv r))))

(defn get-winner []
  (with-open [r (io/reader csv-file)]
    (->> (csv/read-csv r)
         (map #(nth % 7))
         (take 6)
         doall)))

(defn first-match [csv]
  (with-open [r (io/reader csv)]
    (->> (csv/read-csv r)
         sc/mappify
         first)))

(defn five-matches [csv]
  (with-open [r (io/reader csv)]
    (->> (csv/read-csv r)
         sc/mappify
         (map #(select-keys % [:tourney_year_id
                               :winner_name
                               :loser_name
                               :winner_sets_won
                               :loser_sets_won]))
         (take 5)
         doall)))

(defn five-matches-int-sets [csv]
  (with-open [r (io/reader csv)]
    (->> (csv/read-csv r)
         sc/mappify
         (map #(select-keys % [:tourney_year_id
                               :winner_name
                               :loser_name
                               :winner_sets_won
                               :loser_sets_won]))
         (sc/cast-with {:winner_sets_won sc/->int
                        :loser_sets_won sc/->int})
         (take 5)
         doall)))

(defn main
  []
  (println (count-csv))
  (println (get-winner))
  (println (first-match csv-file))
  (println (five-matches csv-file))
  (println (five-matches-int-sets csv-file)))
