(ns rum.examples.error-boundary
  "Ported straight from official example : https://codepen.io/gaearon/pen/wqvxGa?editors=0010"
  (:require [rum.core :as rum]))

(rum/defcs faulty-component < (rum/local 0 ::counter)
  [state]
  (let [counter (::counter state)]
    (if (= 5 @counter)
      #?(:cljs (throw (js/Error. "Something wrong happened")))
      [:div
       [:p "Counter crashes at 5: " @counter]
       [:button
        {:on-click (fn [_] (swap! counter inc))}
        "Increment"]])))

(rum/defcs error-boundary < (rum/local nil ::error)
                            {:did-catch (fn [state error info]
                                          (let [error-atom (::error state)]
                                            (reset! error-atom error)
                                            state))}
  [state child]
  (let [error @(::error state)]
    (if error
      [:div {} (str "Something went wrong, but we got your back.")]
      child)))

#?(:cljs
(defn mount! [mount-el]
  (rum/hydrate (error-boundary (faulty-component)) mount-el)))
