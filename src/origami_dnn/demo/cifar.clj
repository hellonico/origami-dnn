(ns origami-dnn.demo.cifar
  (:gen-class)
  (:require [opencv4.core :as cv]
            [origami-dnn.draw :as d]
            [origami-dnn.net.core :as c]
            [opencv4.dnn.core :as origami-dnn]))

(defn run-cifar [input output]
  (let [[net opts labels] (origami-dnn/read-net-from-repo "networks.darknet:cifar-custom:1.0.0")]
    (println "Running cifar on image:" input " > " output)
    (-> input
        (cv/imread)
        (c/classify net opts)
        (d/write-in-white labels)
        (cv/imwrite output))))

(defn -main [& args]
  (run-cifar
   (or (first args) "resources/plane.jpg")
   (or (second args) "cifar_output.jpg")))