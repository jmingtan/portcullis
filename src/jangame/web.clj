(ns jangame.web
    (:require [jangame.responses :as responses]
              [compojure.route :as route]
              [compojure.core :refer (GET POST defroutes)]
              [compojure.handler :refer (site)]
              [ring.adapter.jetty :refer (run-jetty)]))

(defroutes routes
  (GET "/" {s :session f :flash} (responses/index s f))
  (GET "/new" [] {:session nil :status 302 :headers {"Location" "/"}})
  (GET "/open" {s :session} (responses/open s))
  (GET "/reject" {s :session} (responses/reject s))
  (route/files "/" {:root "resources/public"}))

(def app (site routes))

(defn -main
  ([] (-main 3000))
  ([port] (run-jetty #'app {:port (Integer. port)})))