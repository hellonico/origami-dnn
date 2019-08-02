(ns origami-dnn.net.mobilenet
  (:require
   [opencv4.utils :as u]
   [opencv4.dnn :as dnn]
   [origami-dnn.net.core :refer [blob-from-image] ]
   [opencv4.core :refer [new-size new-point new-rect new-scalar]]))

(defn- to-obj [reshaped i colsi rowsi]
  {:confidence
   (int (* 100 (nth (.get reshaped i 2) 0)))
   :label
   (nth (.get reshaped i 1) 0)
   :box (new-rect
         (new-point (* colsi (nth (.get reshaped i 3) 0)) (* rowsi (nth (.get reshaped i 4) 0)))
         (new-point (* colsi (nth (.get reshaped i 5) 0)) (* rowsi (nth (.get reshaped i 6) 0))))})

; https://github.com/opencv/opencv/wiki/TensorFlow-Object-Detection-API
(defn find-objects
  [image net opts]
  (let [colsi (.cols image)
        rowsi (.rows image)
        blog (blob-from-image image opts)
        detections (do (.setInput net blog) (.forward net))
        reshaped (.reshape detections 1 (/ (.total detections) 7))
        detected-objects (filter #(> (:confidence %) 60) (map #(to-obj reshaped % colsi rowsi) (range 0 (.rows reshaped))))]
    [image detected-objects]))