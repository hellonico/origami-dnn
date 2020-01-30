(ns origami-dnn.demo.imagenet
  (:require
   [origami-dnn.draw :as d]
   [opencv4.dnn.core :as origami-dnn]
   [opencv4.core :refer [imread imwrite new-scalar]]
   [origami-dnn.net.core :as c]))

(defn run-googlenet [input output]
  (let [ [net opts labels] (origami-dnn/read-net-from-repo "networks.caffe:googlenet:1.0.0")]
    (println "Running googlenet on image:" input " > " output)
    (-> input
        (imread)
        (c/classify net opts)
        (d/write-in-white labels)
        (imwrite output))))

(defn -main [& args]
  (println "NOTE: this network has wrong labels ...")
  (run-googlenet
   (or (first args) "resources/catwalk.jpg")
   (or (second args) "googlenet_output.jpg")))

; (-main)