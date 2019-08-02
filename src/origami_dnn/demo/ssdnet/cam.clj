(ns origami-dnn.demo.ssdnet.cam
  (:require [origami-dnn.net.mobilenet :refer [find-objects]]
            [opencv4.dnn :as dnn]
            [origami-dnn.core :as origami-dnn]
            [origami-dnn.draw :as d]
            [opencv4.utils :refer [resize-by simple-cam-window]]))

(defn -main [& args]
  (let [ [net opts labels] (origami-dnn/read-net-from-folder "resources/caffe/mobilenet") ]
    (simple-cam-window
     (read-string (slurp "cam_config.edn"))
     (fn [buffer]
       (-> buffer 
        (find-objects net opts) 
        (d/red-boxes! labels))))))