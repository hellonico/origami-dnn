(ns origami-dnn.demo.resnet
  (:require
   [opencv4.dnn :as dnn]
   [origami-dnn.draw :as d]
   [origami-dnn.core :as origami-dnn]
   [opencv4.core :refer [imread imwrite]]
   [origami-dnn.net.core :as c]))

(defn run-places365 [input output]
  (let [[net opts labels] (origami-dnn/read-net-from-repo "networks.caffe:resnet:1.0.0")]
    (println "Running resnet on image:" input " > " output)
    (-> input
        (imread)
        (c/classify net opts)
        (d/write-in-white labels)
        (imwrite output))))

(defn -main [& args]
  (run-places365
   (or (first args) "resources/catwalk.jpg")
   (or (second args) "resnet_output.jpg")))

; (-main)