(ns origami-dnn.demo.yolo.cam
  (:require [origami-dnn.net.yolo :as yolo]
            [opencv4.dnn :as dnn]
            [origami-dnn.draw :as d]
            [origami-dnn.core :as origami-dnn]
            [opencv4.utils :refer [resize-by simple-cam-window]]))

(defn -main [& args]
   (let [[net opts labels] (origami-dnn/read-net-from-repo "networks.yolo:yolov2-tiny:1.0.0")]
  (simple-cam-window
   (read-string (slurp "cam_config.edn"))
   (fn [buffer]
     (-> buffer (yolo/find-objects net) (d/blue-boxes! labels) )))))

; (-main)