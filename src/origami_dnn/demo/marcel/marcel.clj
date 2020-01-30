(ns origami-dnn.demo.marcel.marcel
  (:require 
            [origami-dnn.net.mobilenet :refer [find-objects]]
            [origami-dnn.draw :as d]
            [opencv4.dnn.core :as origami-dnn]
            [opencv4.utils :refer [resize-by simple-cam-window]]))

(defn handle [net opts labels buffer]
  (-> buffer
      (resize-by 0.3)
      (find-objects net opts)
      (d/blue-boxes! labels)))

(defn -main [& args]
  (let [ [net opts labels] (origami-dnn/read-net-from-repo "networks.caffe:mobilenet:1.0.0")]
  (simple-cam-window
    {:frame {:width 500} :video {:device  "resources/vids/Marcel.m4v"}}
    (fn [mat] (handle net opts labels mat)))))
