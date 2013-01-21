(ns jangame.responses
  (:require [jangame.views :as views]
            [jangame.core :as core]
            [jangame.data :as data]))

(def gen-enc (partial core/generate-encounter data/game))
(def gen-intro (partial core/generate-introduction
                        data/basic-enc
                        (:event data/game)
                        data/intro))
(def next-turn (partial core/next-turn data/agenda-responses))
(def gen-loc (partial core/generate-locations data/game))

(defn new-game []
  (let [status (core/new-game)
        locations (gen-loc)
        encounters [(gen-enc locations)]
        intro (gen-intro (last encounters))]
    {:status status :encounters encounters :intro intro :locations locations}))

(defn score-msg [msg {:keys [pop gold]}]
  [:div
   [:p msg]
   [:h3 "Your Appointment is Complete"]
   [:p (format "Your final score is: %d. Thank you for playing!"
               (int (+ (* 2.0 pop) (* 1.5 gold))))]])

(defn index [session flash]
  (if (not (empty? session))
    (let [{:keys [status intro]} session]
      {:body (views/index status intro nil flash)
       :session session})
    (index (new-game) flash)))

(defn response [session action]
  (if (not (empty? session))
    (let [encounters (:encounters session)
          [status msg] (next-turn (:status session)
                                  (last encounters)
                                  action)
          end-game? (= 0 (:turns status))
          new-encounter (gen-enc (:locations session))
          msg (if end-game? (score-msg msg status) msg)
          session (if end-game?
                    {}
                    (assoc session
                      :status status
                      :encounters (conj encounters new-encounter)
                      :intro (gen-intro new-encounter)))]
      {:session session :flash msg
       :status 302 :headers {"Location" "/"}})
    {:session nil :status 302 :headers {"Location" "/"}}))

(defn open [session]
  (response session :open))

(defn reject [session]
  (response session :close))
