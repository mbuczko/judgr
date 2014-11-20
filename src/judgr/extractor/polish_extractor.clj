(ns judgr.extractor.polish-extractor
  (:use [judgr.extractor.base])
  (:import  [java.io StringReader]
            [org.apache.lucene.analysis.tokenattributes CharTermAttribute]
            [org.apache.lucene.analysis.pl PolishAnalyzer]
            [org.apache.lucene.util Version]))

(def analyzer (PolishAnalyzer. Version/LUCENE_43))

(defn- extractor-settings
  "Returns the settings specific for this extractor."
  [settings]
  (-> settings :extractor :polish-text))

(deftype PolishTextExtractor [settings]
  FeatureExtractor
  (extract-features [fe item]
    (let [stream (.tokenStream analyzer "text" (StringReader. item))]
      (.reset stream)
      (loop [tokens []]
        (if-not (.incrementToken stream)
          (if (:remove-duplicates? (extractor-settings settings))
            (set tokens) tokens)
          (recur (conj tokens
                       (.toString (.getAttribute stream CharTermAttribute)))))))))
