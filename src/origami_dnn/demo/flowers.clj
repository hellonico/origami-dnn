(ns origami-dnn.demo.flowers
  (:require [opencv4.core :refer [imread imwrite]]
            [opencv4.dnn.core :as origami-dnn]
            [clojure.java.io  :as io]
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

(defn -run-one[net opt labels input output]
(-> input
        (imread)
        (find-objects net opt)
        (d/write-in-white2 labels)
        (imwrite output)))

(defn run-net [input output]
  (let [[net opt labels] (origami-dnn/read-net-from-repo "networks.caffe:flowers:1.0.0")]
    (println "Find flowers from image:" input " > " output)
    (if (.isDirectory (io/as-file input))
        (do (.mkdir (io/as-file output)) (doseq [f (.listFiles (io/as-file input))] (-run-one net opt labels (.getAbsolutePath f) (str output "/" (.getName f)))))
        (-run-one net opt labels input output))))

(defn -main [& args]
  (run-net
   (or (first args) "resources/ajisai.jpg")
   (or (second args) "tf_output.jpg")))
