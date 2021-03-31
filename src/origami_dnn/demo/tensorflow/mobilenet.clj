(ns origami-dnn.demo.tensorflow.mobilenet
  (:require [opencv4.core :refer [imread imwrite]]
            [opencv4.dnn.core :as origami-dnn]
            [clojure.pprint]
            [origami-dnn.draw :as d]
            [origami-dnn.net.mobilenet :as mobilenet]))

(defn run-net [input output]
  (let [[net opt labels] (origami-dnn/read-net-from-repo "networks.tensorflow:tf-ssdmobilenet:1.0.0")]
    (println "Running tensorflow/mobilenet on image:" input " > " output)
    (-> input
        (imread)
        (mobilenet/find-objects net {:size 300 :swap-rgb true})
        (d/tf-boxes! labels)
        (imwrite output))))

(defn -main [& args]
  (run-net
   (or (first args) "resources/catwalk.jpg")
   (or (second args) "tf_output.jpg")))
