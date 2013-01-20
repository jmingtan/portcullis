(ns jangame.data)

(def game
  {:character (map list
                   [:farmer :envoy :wizard :refugee :warrior
                    :assassin :rogue :merchant :villager])
   :agenda (map list [:steal :murder :trade :live])
   :location [[:grouping "of" :region]]
   :grouping (map list ["Principality" "Kingdom" "Monarchy" "Colony"])
   :region (map list ["Kama" "Jaytrem" "Onessi" "Greliron"])
   :event (map list [:war :famine :earthquake :pestilence])})

(def intro
  [[:multi :place]
   ["According to their leader, they hail from the"
    :location "which is currently undergoing" :event]
   ["They would like to enter the town to" :agenda]])

(def basic-enc
  {:single [["a lone" :singular "approaches"]
            ["a single" :singular "arrives at"]
            ["a solitary" :singular "walks towards"]]
   :multi [["A group of" :plural "approaches"]
           ["A number of" :plural "walk towards"]
           ["Some" :plural "arrive at"]]
   :place [["the town gate"] ["the town"] ["your post"] ["the portcullis"]]
   :steal [["seek opportunities"] ["make a living"]]
   :murder [["fulfill a contract"] ["search for a life"]]
   :trade [["fulfill a contract"] ["seek opportunities"]]
   :live [["seek opportunities"] ["make a living"] ["search for a new life"]]})

(def agenda-responses
  {:steal [["Some citizens report having their purses stolen"]]
   :murder [["Some citizens have been murdered"]]
   :trade [["A surge in trading increases the town coffers"]]
   :live [["New citizens have joined the town"]]})