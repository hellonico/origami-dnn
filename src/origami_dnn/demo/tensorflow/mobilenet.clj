(ns origami-dnn.demo.tensorflow.mobilenet
  (:require [opencv4.core :refer [imread imwrite]]
            [opencv4.dnn :as dnn]
            [opencv4.utils :as u]
            [clojure.pprint]
            [origami-dnn.draw :as d]
            [origami-dnn.net.mobilenet :as mobilenet]))

(defn run-net [protofile caffe labels input output]
  (let [net (dnn/read-net-from-tensorflow protofile caffe)
        labels (line-seq (clojure.java.io/reader labels)) ]
    (println "Running tensorflow/mobilenet [" protofile "] on image:" input " > " output)
    (-> input
        (imread)
        (mobilenet/find-objects net {:size 300 :swap-rgb true})
        (d/tf-boxes! labels)
        (imwrite output))))

(defn -main [& args]
  (run-net
   "resources/tensorflow/ssd_mobilenet_v1_coco_2017_11_17.pb"
   "resources/tensorflow/ssd_mobilenet_v1_coco_2017_11_17.pbtxt"
   "resources/tensorflow/ssd_mobilenet_v1.labels"
   (or (first args) "resources/catwalk.jpg")
   (or (second args) "tf_output.jpg")))