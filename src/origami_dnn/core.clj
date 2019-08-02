(ns origami-dnn.core
  (:require [opencv4.core :refer [min-max-loc new-size new-scalar imread imwrite]]
            [opencv4.dnn :as dnn]
            [origami-dnn.draw :as d]
            [origami-dnn.net.mobilenet :as mobilenet]
            [origami-dnn.net.yolo :as yolo]))

(defn run-yolo [cfg-file weights-file & args]
  (let [input (or (first args) "resources/catwalk.jpg")
        net (dnn/read-net-from-darknet cfg-file weights-file)
        labels (line-seq (clojure.java.io/reader "resources/yolov3/coco.names"))
        output (or (second args) "yolo_output.jpg")]
      (println "Running yolo [" cfg-file "] on image:" input " > " output)
      (-> input
          (imread)
          (yolo/find-objects net)
          (d/blue-boxes! labels)
          (imwrite output))))

(defn- find-first-file [folder substring]
  (->> folder
       (clojure.java.io/file)
       (file-seq)
       (filter #(clojure.string/includes?  (.getName %) substring))
       (first)
       (clojure.java.io/as-relative-path)))

(defn read-net-from-folder [folder]
  (let [files (file-seq (clojure.java.io/file folder))]
    [(dnn/read-net-from-caffe (find-first-file folder ".prototxt") (find-first-file folder ".caffemodel"))
     (read-string (slurp (find-first-file folder ".edn")))
     (line-seq (clojure.java.io/reader (find-first-file folder ".names")))]))

(defn -main[ & args]
  (println (slurp "README.md")))