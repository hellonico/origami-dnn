(ns origami-dnn.demo.ssdnet.catvideotoscreen
  (:require [origami-dnn.net.mobilenet :refer [find-objects]]
            [origami-dnn.draw :as d]
            [origami-dnn.core :as origami-dnn]
            [opencv4.utils :refer [resize-by simple-cam-window]]))

(defn -main [& args]
  (let [ [net opts labels] (origami-dnn/read-net-from-folder "networks/caffe/mobilenet")]
    (simple-cam-window
     {:frame {:width 500} :video {:device (or (first args) "resources/cat.mp4")}}
     (fn [buffer]
       (-> buffer 
           (resize-by 0.25) 
           (find-objects net opts)
           (d/blue-boxes! labels))))))