(ns origami-dnn.demo.yolo.v6cam
  (:gen-class)
  (:require [origami-dnn.net.yolov6 :as yolo]
            [origami-dnn.draw :as d]
            [opencv4.dnn.core :as origami-dnn]
            [opencv4.utils :refer [simple-cam-window]]))

(defn -main [& args]
  (let [[net _ labels] (origami-dnn/read-net-from-repo "networks.yolo:yolov6:1.0.0")]
    (simple-cam-window
      {:frame {:fps true} :video {:device  (or (first args) 0)}}
      (fn [buffer]
        (-> buffer (yolo/find-objects net) (d/blue-boxes! labels) )))))
