(ns jangame.core
  (:require [clojure.string :refer (capitalize join upper-case)]))

(defn pluralize [word-sym]
  (-> word-sym name (str "s") keyword))

(defn generate [sym grammar]
  (letfn [(helper [sym]
            (if (or (string? sym) (nil? (sym grammar)))
              sym
              (let [elem (rand-nth (sym grammar))]
                (map helper elem))))]
    (helper sym)))

(defn generate-sentence [start-sym grammar]
  (-> (generate start-sym grammar)
      flatten
      ((partial join " "))
      capitalize
      (str ".")))

(defn generate-locations [grammar]
  (let [regions (:region grammar)
        num (count regions)
        locations (map #(->> (assoc grammar :region [%])
                             (generate :location)
                             flatten
                             (join " "))
                       regions)
        events (map #(generate % grammar) (repeat num :event))]
    (zipmap locations events)))

(defn generate-encounter [grammar locations]
  (let [location (rand-nth (keys locations))
        event (locations location)
        grammar (assoc grammar
                  :location (-> location list list)
                  :event (list event))]
    (into {} (map (fn [start-sym]
                    [start-sym (generate start-sym grammar)])
                  [:character :agenda :location :event]))))

(defn generate-introduction [grammar events paragraph encounter]
  (let [c (-> (:character encounter) first)
        a (-> (:agenda encounter) list)
        l (-> (:location encounter) list)
        e (list (let [event (:event encounter)
                      event-set (remove #(= % event) events)
                      agenda (-> a first first)
                      is-negative? (some #(= % agenda) [:steal :murder])]
                  (if is-negative?
                    (rand-nth event-set)
                    event)))
        grammar (assoc grammar
                  :location l :event e :agenda a
                  :plural [[(pluralize c)]])]
    (map (fn [start] (->> (assoc grammar :start (list start))
                          (generate :start)
                          flatten
                          (map name)
                          (join " ")
                          ((fn [e] (str e ". ")))))
         paragraph)))

(defn make-status [pop gold seasons turns]
  {:pop pop :gold gold :seasons seasons :turns turns})

(defn next-turn
  [responses
   {:keys [pop gold seasons turns]}
   {:keys [character agenda location event]}
   action]
  (let [resp-fn (fn [pop gold response]
                  [(make-status pop gold seasons (dec turns)) response])
        agenda (first agenda)
        response (generate-sentence agenda responses)]
    (condp = action
      :open (condp = agenda
              :steal (resp-fn pop (- gold 10) response)
              :murder (resp-fn (- pop 5) gold response)
              :trade (resp-fn pop (+ gold 10) response)
              :live (resp-fn (+ pop 5) gold response)
              nil)
      :close (resp-fn pop gold "Your visitors walk away.")
      nil)))

(defn new-game []
  (make-status 200 350 5 24))
