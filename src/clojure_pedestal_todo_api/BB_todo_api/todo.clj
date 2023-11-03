(ns clojure-pedestal-todo-api.BB-todo-api.todo
  (:require [io.pedestal.http :as http]
            [io.pedestal.http.route :as route]
            [clojure.string :as str]))

;;IN-MEMORY DATABASE

(defonce database (atom {}))

(def db-interceptor
  {:name :database-interceptor
   :enter
   (fn [context]
     (update context :request assoc :database @database))
   :leave
   (fn [context]
     (if-let [[op & args] (:tx-data context)]
       (do
         (apply swap! database op args)
         (assoc-in context [:request :database] @database))
       context))})

;;ROUTES

(defn response
  [status body & {:as headers}]
  {:status status :body body :headers headers})
~
(def ok (partial response 200))
(def created (partial response 201))
(def accepted (partial response 202))

(def echo
  {:name :echo
   :enter
   (fn [context]
     (let [request (:request context)
           response (ok context)]
       (assoc context :response response)))})

(def routes
  (route/expand-routes
    #{["/todo" :post echo :route-name :list-create]
      ["/todo" :get echo :route-name :list-query-form]
      ["/todo/:list-id" :get echo :route-name :list-view]
      ["/todo/:list-id/:item-id" :get echo :route-name :list-item-view]
      ["/todo/:list-id/:item-id" :put echo :route-name :list-item-update]
      ["/todo/:list-id/:item-id" :delete echo :route-name :list-item-delete]}))

