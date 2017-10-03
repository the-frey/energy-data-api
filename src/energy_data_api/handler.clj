(ns energy-data-api.handler
  (:require [compojure.api.sweet :refer :all]
            [ring.util.http-response :refer :all]
            [schema.core :as s]))

(s/defschema SiteDailyEnergyStream
  {:site-id s/Int
   :recorded-at s/Int
   :results [{:time s/Str
              :result s/Int}]})

(def app
  (api
    {:swagger
     {:ui "/"
      :spec "/swagger.json"
      :data {:info {:title "Energy-data-api"
                    :description "Compojure Api example"}
             :tags [{:name "api", :description "some apis"}]}}}

    (context "/api" []
             :tags ["api"]

             (context "/v1" []
                      :tags ["v1"]

                      (GET "/site-daily-stream" []
                           :return SiteDailyEnergyStream
                           :query-params [site-id :- String, recorded-at :- String]
                           :summary "Gets a stream for a site and a recorded at value."
                           (ok {:site-id (Integer/parseInt site-id)
                                :recorded-at (Integer/parseInt recorded-at)
                                :results [{:time "example1"
                                           :result 123}
                                          {:time "example2"
                                           :result 234}]}))))))
