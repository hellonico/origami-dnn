(ns origami-dnn.demo.yolo.v2-tiny
(:require [origami-dnn.core :refer [run-yolo]]))

(defn -main [& args]
  (run-yolo
   "networks/yolov2/yolov2-tiny.cfg" 
   "networks/yolov2/yolov2-tiny.weights"
   (or (first args) "resources/catwalk.jpg")
   (or (second args) "yolo_v2_tiny_output.jpg")))
