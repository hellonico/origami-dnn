(ns origami-dnn.demo.yolo.v6
  (:require
   [origami-dnn.net.yolov6 :as yolo]
   [origami-dnn.draw :as d]
   [opencv4.core :refer [min-max-loc new-size new-scalar imread imwrite]]
   [opencv4.dnn.core :as origami-dnn]))

(defn run-yolo [input output network]
  (println "Running " network " on image:" input " > " output)
  (let [[net _ labels] (origami-dnn/read-net-from-repo network)]
    (-> input
        (imread)
        (yolo/find-objects net)
        (d/blue-boxes! labels)
        (imwrite output))))

(defn -main [& args]
  (run-yolo
   (or (first args) "resources/catwalk.jpg")
   (or (second args) "yolo_v6_output.jpg")
   (nth args 3 "networks.yolo:yolov6n:1.0")))
