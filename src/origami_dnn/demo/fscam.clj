(ns origami-dnn.demo.fscam
  (:require [opencv4.utils :as u] ))

(defn -main [& args]
    (u/simple-cam-window 
    {:frame {:fullscreen true :width 500} :video {:device  0}} identity ) )