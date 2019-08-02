(ns origami-dnn.demo.bvlc
  (:require
   [origami-dnn.core :as origami-dnn] 
   [origami-dnn.draw :as d]
   [opencv4.core :refer [imread imwrite]]
   [origami-dnn.net.core :as net]))

(defn run-bvlc [input output]
 (let [ [net opts labels] (origami-dnn/read-net-from-folder "networks/caffe/bvlc_alexnet")]
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