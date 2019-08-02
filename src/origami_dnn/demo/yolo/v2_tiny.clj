(ns origami-dnn.demo.yolo.v2-tiny
(:require [origami-dnn.core :refer [run-yolo]]))

(defn -main [& args]
  (run-yolo
   "resources/yolov2/yolov2-tiny.cfg" 
   "resources/yolov2/yolov2-tiny.weights"
   (or (first args) "resources/catwalk.jpg")
   (or (second args) "yolo_v2_tiny_output.jpg")))
