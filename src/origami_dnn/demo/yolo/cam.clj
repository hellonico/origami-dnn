(ns origami-dnn.demo.yolo.cam
  (:gen-class)
  (:require [origami-dnn.net.yolo :as yolo]
            [origami-dnn.draw :as d]
            [opencv4.dnn.core :as origami-dnn]
            [opencv4.utils :refer [simple-cam-window]]))

(defn -main [& args]
  (let [[net _ labels] (origami-dnn/read-net-from-repo "networks.yolo:yolov2-tiny:1.0.0")]
    (simple-cam-window
     (if (first args) (read-string (slurp (first args))) {:frame {:fps true} :video {:device  0}})
     (fn [buffer]
       (-> buffer (yolo/find-objects net) (d/blue-boxes! labels))))))
