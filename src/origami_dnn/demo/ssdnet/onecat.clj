(ns origami-dnn.demo.ssdnet.onecat
 (:require 
  [opencv4.core :refer [imread imwrite]]
  [origami-dnn.draw :as d]
  [origami-dnn.net.mobilenet :as mobilenet]
  [origami-dnn.core :as origami-dnn]))

(defn -main [& args]
  (let [
       input (or (first args) "resources/catwalk.jpg")
       output (or (second args) "mobilenet_output.jpg")
       [net opts labels](origami-dnn/read-net-from-repo "networks.caffe:mobilenet:1.0.0") ]
    (println "Running mobilenet on image:" input " > " output)
    (-> input
        (imread)
        (mobilenet/find-objects net opts)
        (d/blue-boxes! labels)
        (imwrite output))))

; (-main)    