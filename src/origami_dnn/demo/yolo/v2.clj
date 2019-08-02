(ns origami-dnn.demo.yolo.v2
 (:require [origami-dnn.core :refer [run-yolo]]))

(defn -main [& args]
  (run-yolo
   "resources/yolov2/yolov2.cfg" 
   "resources/yolov2/yolov2.weights" 
   (or (first args) "resources/catwalk.jpg")
   (or (second args) "yolo_v2_output.jpg")))
  