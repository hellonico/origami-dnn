(ns origami-dnn.demo.age
  (:require [opencv4.core :refer [imread imwrite]]
            [origami-dnn.core :as origami-dnn]
            [opencv4.utils :as u]
            [origami-dnn.draw :as d]
            [origami-dnn.net.core :as net]))

(defn run-net [input output]
  (let [[net opts labels] (origami-dnn/read-net-from-folder "networks/caffe/convnet/age")]
    (println "Running convnet age on image:" input " > " output)
    (-> input
        (imread)
        (net/classify net opts)
        (d/guess-gender labels)
        (imwrite output))))

(defn -main [& args]
  (run-net
   (or (first args) "resources/pics/teenager2.jpg")
   (or (second args) "gender_output.jpg")))


; (-main "resources/pics/teenager.png")
