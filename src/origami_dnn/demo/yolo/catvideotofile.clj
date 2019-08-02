(ns origami-dnn.demo.yolo.catvideotofile
  (:require [origami-dnn.net.mobilenet :refer [find-objects]]
            [origami-dnn.draw :as d]
            [opencv4.dnn :as dnn]
            [opencv4.utils :refer [resize-by cams-window]]))

(defn detect [net labels buffer]
  (-> buffer 
    (resize-by 0.25) 
    (find-objects net) 
    (d/blue-boxes! labels)))

(defn -main [& args]
  (let [net (dnn/read-net-from-caffe "networks/mobilenet/deploy.prototxt" "networks/mobilenet/mobilenet_iter_73000.caffemodel")
        labels (line-seq (clojure.java.io/reader "networks/mobilenet/MobileNetSSD.names"))]
    (cams-window
     {:frame {:hide true}
      :video {:fn identity :recording {:output "catout.mp4"}}
      :devices [{:width 384  :height 216 :device "resources/cat.mp4" :fn (partial detect net labels)}]})))