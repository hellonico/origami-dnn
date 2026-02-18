(ns origami-dnn.demo.yolo.v6cam
  (:gen-class)
  (:require [origami-dnn.net.yolov6 :as yolo]
            [origami-dnn.draw :as d]
            [opencv4.dnn.core :as origami-dnn]
            [opencv4.utils :refer [simple-cam-window]]))

(defn -main [& args]
  (let [network-name (or (first args) "networks.yolo:yolov6t:1.0")
        [net _ labels] (origami-dnn/read-net-from-repo network-name)]
    (simple-cam-window
     (or (second args) {:frame {:fps true} :video {:device 0}})
     (fn [buffer]
       (-> buffer
           (yolo/find-objects net)
           (d/blue-boxes! labels)
           (d/add-network-info! network-name))))))