(ns origami-dnn.demo.flowers
  (:require [opencv4.core :refer [imread imwrite]]
            [opencv4.dnn.core :as origami-dnn]
            [origami-dnn.net.core :refer [blob-from-image]]
            [origami-dnn.draw :as d]))

(defn- to-obj [reshaped i]
  {:confidence (first (.get reshaped 0 i)) :label i})

(defn- find-objects
  [image net opts]
  (let [blog (blob-from-image image opts)
        detections (do (.setInput net blog) (.forward net))
        detected-objects (sort-by :confidence > (map #(to-obj detections %) (range 0 (.cols detections))))]
    [image (take 3 detected-objects)]))

(defn run-net [input output]
  (let [[net opt labels] (origami-dnn/read-net-from-repo "networks.caffe:flowers:1.0.0")]
    (println "Find flowers from image:" input " > " output)
    (-> input
        (imread)
        (find-objects net opt)
        (d/write-in-white2 labels)
        (imwrite output))))

(defn -main [& args]
  (run-net
   (or (first args) "resources/ajisai.jpg")
   (or (second args) "tf_output.jpg")))
