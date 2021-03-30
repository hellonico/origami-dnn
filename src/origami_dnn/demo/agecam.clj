(ns origami-dnn.demo.agecam
  (:require [opencv4.dnn.core :as origami-dnn]
            [opencv4.utils :as u]
            [origami-dnn.draw :as d]
            [origami-dnn.net.core :as net]))

(defn -main [& args]
   (let [[net opts labels] (origami-dnn/read-net-from-repo "networks.caffe:convnet-age:1.0.0")]
    (u/simple-cam-window
     {:frame {:width 500} :video {:device  0}}
       (fn [input] 
       (-> input
        (net/classify net opts)
        (d/guess-gender labels))))))