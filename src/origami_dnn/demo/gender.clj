(ns origami-dnn.demo.gender
  (:require [opencv4.core :refer [imread imwrite]]
            [opencv4.dnn.core :as origami-dnn]
            [clojure.pprint]
            [origami-dnn.draw :as d]
            [origami-dnn.net.core :as net]))

(defn run-net [input output]
  (let [[net opts labels] (origami-dnn/read-net-from-repo "networks.caffe:convnet-gender:1.0.0")]
    (println "Running convnet gender on image:" input " > " output)
    (-> input
        (imread)
        (net/classify net opts)
        (d/guess-gender labels)
        (imwrite output))))

(defn -main [& args]
  (run-net
   (or (first args) "resources/caffe/gender/teenager2.jpg")
   (or (second args) "gender_output.jpg")))


(comment


  (run-net
   "resources/pics/teenager2.jpg"
   "gender_output.jpg")

  (run-net
   "resources/pics/jeunehomme.jpg"
   "gender_output.jpg"))