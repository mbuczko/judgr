(ns judgr.settings)

(def ^:dynamic settings
  {:classes [:positive :negative]
   :unknown-class :unknown

   :database {:type :memory}

   :extractor {:type :english-text
               :english-text   {:remove-duplicates? true}
               :polish-text    {:remove-duplicates? true}
               :brazilian-text {:remove-duplicates? true}}

   :classifier {:type :default
                :default {:unbiased? false
                          :smoothing-factor 1
                          :threshold? true
                          :thresholds {:positive 1.2
                                       :negative 2.5}}}})

(defn update-settings
  "Returns an updated version of map m by applying assoc-in.
Ex: (update-settings settings [:unknown-class] :unknown)"
  [m & kvs]
  (letfn [(f [m [k v]]
            (assoc-in m k v))]
    (reduce f m (partition 2 kvs))))
