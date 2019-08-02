(ns origami-dnn.demo.yolo.cam
  (:require [origami-dnn.net.yolo :refer :all]
            [opencv4.dnn :as dnn]
            [origami-dnn.draw :as d]
            [opencv4.utils :refer [resize-by simple-cam-window]]))

(defn -main [& args]
   (let [net (dnn/read-net-from-darknet "networks/yolov2/yolov2-tiny.cfg" "networks/yolov2/yolov2-tiny.weights")
        labels (line-seq (clojure.java.io/reader "networks/yolov3/coco.names"))]
  (simple-cam-window
   (read-string (slurp "cam_config.edn"))
   (fn [buffer]
     (-> buffer (find-objects net) (d/blue-boxes! labels) )))))