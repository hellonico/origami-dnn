(ns origami-dnn.demo.fscam
  (:require [opencv4.utils :as u] ))

(defn -main [& args]
  (let [device (try (Integer/parseInt (first args)) (catch Exception e  0 )) ]
    (u/simple-cam-window 
    {:frame {:fullscreen true :width 500} :video {:device device}} identity ) ))