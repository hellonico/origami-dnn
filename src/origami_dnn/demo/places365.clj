(ns origami-dnn.demo.places365
  (:require 
   [opencv4.dnn :as dnn]
   [origami-dnn.draw :as d]
   [origami-dnn.core :as origami-dnn]
   [opencv4.core :refer [imread imwrite new-scalar]]
   [origami-dnn.net.core :as c]))

(defn run-places365 [input output]
   (let [ [net opts labels] (origami-dnn/read-net-from-repo "networks.caffe:places365:1.0.0")]
    (println "Running places365 on image:" input " > " output)
    (-> input
        (imread)
        (c/classify net opts)
        (d/write-in-white labels)
        (imwrite output))))

(defn -main [& args]
  (run-places365
   (or (first args) "resources/catwalk.jpg")
   (or (second args) "places365_output.jpg")))

; (-main)