(ns origami-dnn.demo.yolo.v3
  (:require [origami-dnn.core :refer [run-yolo]]))

(defn -main [& args]
   (run-yolo 
    "resources/yolov3/yolov3.cfg" 
    "resources/yolov3/yolov3.weights" 
    (or (first args) "resources/catwalk.jpg")
    (or (second args) "yolo_v3_output.jpg")))
