(ns rum.examples.fragment
  (:require [rum.core :as rum]))

(rum/defc fragment []
  (js/React.createElement js/React.Fragment nil
                          (str "Item #" 1 " term")
                          [:br]
                          (str "Item #" 1 " definition")))

(defn mount! [mount-el]
  (rum/mount (fragment) mount-el))
