(ns clojure-pedestal-todo-api.AA-hello-world.hello
  (:require [io.pedestal.http :as http]
            [io.pedestal.http.route :as route]))

(defn respond-hello
  [request]
  {:status 200 :body "Hello!"})

(def routes
  (route/expand-routes
    #{["/greet",
       :get respond-hello,
       :route-name :greet]}))

(defn respond-hello-with-query-params
  [request]
  (let [user-name (get-in request [:query-params :name])]
    {:status 200 :body (str "Hello, " user-name "\n")}))

(def routes
  (route/expand-routes
    #{["/greet-with-query-params",
       :get respond-hello-with-query-params,
       :route-name :greet]}))

(def service-map
  {::http/routes routes
   ::http/type :jetty
   ::http/port 8890})

(defn start []
  (http/start (http/create-server service-map)))

(defonce server (atom nil))

(defn start-dev
  []
  (reset! server
          (http/start (http/create-server
                        (assoc service-map
                               ::http/join? false)))))

(defn stop-dev
  []
  (http/stop @server))

(defn restart
  []
  (stop-dev)
  (start-dev))