(ns origami-dnn.demo.yolo.catvideotoscreen
  (:require [origami-dnn.net.yolo :refer [find-objects]]
            [origami-dnn.draw :as d]
            [opencv4.dnn :as dnn]
            [opencv4.utils :refer [resize-by simple-cam-window]]))

(defn -main [& args]
   (let [net (dnn/read-net-from-darknet "resources/yolov2/yolov2-tiny.cfg" "resources/yolov2/yolov2-tiny.weights")
        labels (line-seq (clojure.java.io/reader "resources/yolov3/coco.names"))]
    (simple-cam-window
     {:frame {:width 500} :video {:device (or (first args) "resources/cat.mp4") }}
     (fn [buffer]
       (-> buffer 
           (resize-by 0.25) 
           (find-objects net) 
           (d/blue-boxes! labels))))))