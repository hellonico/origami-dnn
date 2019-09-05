(ns origami-dnn.core
  (:require [opencv4.core :refer [min-max-loc new-size new-scalar imread imwrite]]
            [opencv4.dnn :as dnn]
            [clojure.java.io :as io]
            [origami-dnn.draw :as d]
            [origami-dnn.net.mobilenet :as mobilenet]
            [origami-dnn.net.yolo :as yolo]))

(defn run-yolo [cfg-file weights-file & args]
  (let [input (or (first args) "resources/catwalk.jpg")
        net (dnn/read-net-from-darknet cfg-file weights-file)
        labels (line-seq (clojure.java.io/reader "networks/yolov3/coco.names"))
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
      (str)
      ;  (clojure.java.io/as-absolute-path)
       ))

(defn read-net-from-folder [folder]
  (let [files (file-seq (clojure.java.io/file folder))]
    [(dnn/read-net-from-caffe (find-first-file folder ".prototxt") (find-first-file folder ".caffemodel"))
     (read-string (slurp (find-first-file folder ".edn")))
     (line-seq (clojure.java.io/reader (find-first-file folder ".names")))]))

(defn get-tmp-folder [uri]
  (str 
  (or (System/getProperty "networks.local") (System/getProperty "user.dir"))
   "/" (last (clojure.string/split uri #"/"))  ))

(defn fetch [uri]
  (let [folder (get-tmp-folder uri)]
  (if (not (.exists (io/as-file folder)))
  (with-open [in (java.util.zip.ZipInputStream. (io/input-stream uri))]
    (loop [entry (.getNextEntry in)]
    (if (not (nil? entry))
    (let [
      path (str folder "/" (.getName entry))
      f (io/file path) 
      parent (.getParentFile f)
      ]
    (println path ":" (.isDirectory entry))
    (.mkdirs parent)
    (if (not (.isDirectory entry)) (io/copy in f))
    (recur (.getNextEntry in)))))))
    folder))

(defn read-net-from-uri 
  ([uri] (read-net-from-uri :caffe))
  ([uri _type]
  (let [ folder (fetch uri)]
  (read-net-from-folder folder))))

; spec is something like
; networks.caffe:mobilenet:1.0.0
(defn read-net-from-repo [ spec ]
  (let [repo (or (System/getProperty "networks.repo") "http://repository.hellonico.info/repository/hellonico/")
  [group art version] (clojure.string/split spec #":")
  type_ (second (clojure.string/split group #"\."))
  uri (str repo "networks/" type_ "/" art "/" version "/" art "-" version ".zip")
  ]
  (println "Loading network: [" type_ "]:" spec )
  (read-net-from-uri uri (keyword type_))))

(defn -main[ & args]
  (println (slurp "README.md")))