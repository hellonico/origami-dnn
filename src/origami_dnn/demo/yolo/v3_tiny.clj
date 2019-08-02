(ns origami-dnn.demo.yolo.v3-tiny
  (:require [origami-dnn.core :refer [run-yolo]]))

(defn -main [& args]
  (run-yolo "resources/yolov3/yolov3-tiny.cfg" 
            "resources/yolov3/yolov3-tiny.weights"
            (or (first args) "resources/catwalk.jpg")
   			(or (second args) "yolo_v3_tiny_output.jpg")))
