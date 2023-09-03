(ns data-types.tempdb)

(def memory-db (atom {}))
(defn read-db [] @memory-db)
(defn write-db [new-db] (reset! memory-db new-db))

(defn create-table
  [name]
  (let [new-db (assoc (read-db) name {:data [] :indexes {}})]
    (write-db new-db)))

(defn drop-table
  [name]
  (let [new-db (dissoc (read-db) name)]
    (write-db new-db)))

(defn select-*
  [table]
  (let [db (read-db)]
    (get-in db [table :data])))

(defn select-*-where
  [table-name field field-value]
  (let [db (read-db)
        data (get-in db [table-name :data])]
    (filter (fn [m] (= (get-in m [field]) field-value)) data)))

(defn insert
  [table record id-key]
  (if (seq (select-*-where table id-key (id-key record)))
    (println "already exists")
    (let [db (read-db)
          new-db (update-in db [table :data] conj record)
          idx (- (count (get-in new-db [table :data])) 1)]
      (write-db (assoc-in new-db [table :indexes id-key idx] (get-in record [id-key]))))))

(defn main []
  (println "temp db")
  (create-table :fruits)
  (println (read-db))
  (drop-table :fruits)
  (create-table :fruits)
  (insert :fruits {:name "Pear" :stock 3} :name)
  (insert :fruits {:name "Apple" :stock 30} :name)
  (insert :fruits {:name "Grape" :stock 13} :name)
  (println (select-*-where :fruits :name "Apple"))
  (insert :fruits {:name "Pear" :stock 3} :name)
  (println (select-* :fruits)))