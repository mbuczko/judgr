(ns judgr.test.extractor.polish-extractor
  (:use [judgr.extractor.polish-extractor]
        [judgr.core]
        [judgr.test.util]
        [judgr.settings]
        [clojure.test])
  (:import [judgr.extractor.polish_extractor PolishTextExtractor]))

(def-fixture remove-duplicates [b]
  (let [new-settings (update-settings settings
                                      [:extractor :type] :polish-text
                                      [:extractor :polish-text] {:remove-duplicates? b})
        extractor (extractor-from new-settings)]
    (test-body)))

(deftest ensure-polish-text-extractor
  (with-fixture remove-duplicates [true]
    (is (instance? PolishTextExtractor extractor))))

(deftest polish-extractor
  (testing "extract features"
    (testing "removing duplicates"
      (with-fixture remove-duplicates [true]
        (is (= #{"gramatyczny" "punkt" "specjalny" "sens" "widzieć"}
           (.extract-features extractor "Z gramatycznego punktu widzenia to nie ma specjalnie gramatycznego sensu")))))

    (testing "leaving duplicates"
      (with-fixture remove-duplicates [false]
        (is (= ["gramatyczny" "punkt" "widzieć" "specjalny" "gramatyczny" "sens"]
               (.extract-features extractor "Z gramatycznego punktu widzenia to nie ma specjalnie gramatycznego sensu")))))))
