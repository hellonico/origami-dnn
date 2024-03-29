(ns origami-dnn.demo.bvlc
  (:require
   [opencv4.dnn.core :as origami-dnn] 
   [origami-dnn.draw :as d]
   [opencv4.core :refer [imread imwrite]]
   [origami-dnn.net.core :as net]))

(defn run-bvlc [input output]
 (let [ [net opts labels] (origami-dnn/read-net-from-repo "networks.caffe:bvlc_alexnet:1.0.0")]
    (println "Running bvlc on image:" input " > " output)
    (-> input
        (imread)
        (net/classify net opts)
        (d/write-in-white labels)
        (imwrite output))))

(defn -main [& args]
  (run-bvlc
   (or (first args) "resources/catwalk.jpg")
   (or (second args) "bvlc_output.jpg")))

; (-main)