(ns jangame.views
  (:require [hiccup.core :refer (html)]
            [hiccup.page :as page]
            [hiccup.element :refer (link-to)]))

(defn template [& body]
  (html (page/html5
         [:head
          [:title "Portcullis"]
          [:meta {:name "viewport" :content "width=device-width, initial-scale=1.0"}]
          (map
           page/include-css
           ["//netdna.bootstrapcdn.com/twitter-bootstrap/2.2.2/css/bootstrap-combined.min.css"
            "//netdna.bootstrapcdn.com/font-awesome/3.0/css/font-awesome.css"
            "http://fonts.googleapis.com/css?family=Berkshire+Swash"
            "http://fonts.googleapis.com/css?family=Aladin"])
          (map
           page/include-js
           ["//ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"
            "//netdna.bootstrapcdn.com/twitter-bootstrap/2.2.2/js/bootstrap.min.js"])
          [:style {:type "text/css"}
           (str "h1, h3 {font-family: \"Berkshire Swash\", serif;}"
                "h1 {font-size: 45px;} h3 {font-size: 30px;}"
                "body {font-size: 24px; font-family: Aladin, sans-serif; background: url(/img/461223198.jpeg) repeat 0 0; color: #222;}"
                "p {line-height: 1.2em;}"
                ".table td, .table th {border-top: 1px solid #222;}"
                "hr {border-top: 1px solid #222; border-bottom: 0px;}"
                "#logo {margin-top: 10px;}"
                "#footer {margin-top: 50px; margin-bottom: 5px}")]]
         [:body body])))

(defn index
  [{:keys [pop gold seasons turns]} intro conversation flash]
  (template
   [:div {:class "container-fluid"}
    [:div {:class "row"}
     [:div {:class "offset1 span10"}
      [:center
       [:img {:id "logo" :src "/img/Icon.3_17.jpg" :width 150 :height 150}]
       [:h1 "Portcullis"]
       [:p (str "You are the gatekeeper for a small town. "
                "Who you choose to let in decides how the town progresses.")]
       [:p (str "Your appointment will last for " turns " more turns. ")]]]]
    [:div {:class "row"}
     [:div {:class "offset1 span10"}
      ;; [:p "There are 5 turns left for this season."]
      (if flash
        [:div {:class "alert alert-info"} flash])
      [:h3 "Town Status"]
      [:table {:class "table"}
       ;; [:tr [:th [:i {:class "icon-home"}] " Houses"] [:td houses]]
       [:tr [:th [:i {:class "icon-group"}] " Population"] [:td pop]]
       [:tr [:th [:i {:class "icon-money"}] " Gold"] [:td gold]]]
      [:h3 "Encounter"]
      [:p intro]
      ;; [:p "Your choices are:"]
      ;; (link-to {:class "btn btn-block"} "/" "Where are you from?")
      ;; (link-to {:class "btn btn-block"} "/" "Why are you here?")
      [:h3 "Your decision"]
      (link-to {:class "btn btn-block btn-success"} "/open" "Open the Portcullis!")
      (link-to {:class "btn btn-block btn-danger"} "/reject" "Keep the Gates Shut!.")]]
    [:div {:class "row"}
     [:div {:class "offset1 span10"}
      [:hr {:id "footer"}]
      [:center
       [:p "Icons by Lorc."]]]]]))

