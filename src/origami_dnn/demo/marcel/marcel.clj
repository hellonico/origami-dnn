(ns origami-dnn.demo.marcel.marcel
  (:require 
            [origami-dnn.net.mobilenet :refer [find-objects]]
            [origami-dnn.draw :as d]
            [origami-dnn.core :as origami-dnn]
            [opencv4.utils :refer [resize-by simple-cam-window]]))

(let [ [net opts labels] (origami-dnn/read-net-from-folder "networks/caffe/mobilenet")]
  
  (defn handle [buffer]
    (-> buffer
        (resize-by 0.3)
        (find-objects net opts)
        (d/blue-boxes! labels)))

  (defn -main [& args]
    (simple-cam-window
     {:frame {:width 500} :video {:device  "resources/vids/Marcel.m4v"}}
     (fn [buffer] (handle buffer)))))

; (comment
  ; (-main)
