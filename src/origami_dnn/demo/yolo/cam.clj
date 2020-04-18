(ns origami-dnn.demo.yolo.cam
  (:gen-class)
  (:require [origami-dnn.net.yolo :as yolo]
            [opencv4.dnn :as dnn]
            [origami-dnn.draw :as d]
            [opencv4.dnn.core :as origami-dnn]
            [opencv4.utils :refer [simple-cam-window]]))

(defn -main [& args]
   (let [[net opts labels] (origami-dnn/read-net-from-repo "networks.yolo:yolov2-tiny:1.0.0")]
  (simple-cam-window
   (read-string (slurp "cam_config.edn"))
   (fn [buffer]
     (-> buffer (yolo/find-objects net) (d/blue-boxes! labels) )))))

; (-main)