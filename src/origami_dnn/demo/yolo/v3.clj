(ns origami-dnn.demo.yolo.v3
  (:require 
    [origami-dnn.net.yolo :as yolo]
    [origami-dnn.draw :as d]
    [opencv4.core :refer [min-max-loc new-size new-scalar imread imwrite]]
    [opencv4.dnn.core :as origami-dnn]))

(defn run-yolo [ & args]
(let [input (or (first args) "resources/catwalk.jpg")
      [net opts labels] (origami-dnn/read-net-from-repo "networks.yolo:yolov3:1.0.0")
      output (or (second args) "yolo_output.jpg")]
    (println "Running yolo on image:" input " > " output)
    (-> input
        (imread)
        (yolo/find-objects net)
        (d/blue-boxes! labels)
        (imwrite output))))

(defn -main [& args]
   (run-yolo 
    (or (first args) "resources/catwalk.jpg")
    (or (second args) "yolo_v3_output.jpg")))
