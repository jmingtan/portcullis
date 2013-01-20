(defproject jangame "0.1.0-SNAPSHOT"
  :description "A standard web application"
  :url "http://www.example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [ring "1.1.6"]
                 [hiccup "1.0.1"]
                 [compojure "1.1.3"]
                 [roul "0.2.0"]]
  :main jangame.core
  :plugins [[lein-ring "0.7.5"]]
  :ring {:handler jangame.web/app}
  :min-lein-version "2.0.0")
