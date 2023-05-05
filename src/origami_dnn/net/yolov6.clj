(ns origami-dnn.net.yolov6
  (:require [opencv4.core :refer [min-max-loc new-rect2d new-rect new-matofrect2d new-arraylist new-mat new-size new-scalar new-matofint new-matoffloat] :as cv]
            [opencv4.dnn :as dnn])
  (:import (org.opencv.core Mat MatOfPoint3 MatOfPoint3f)))

(defn- indexes-of-nms-boxes [locations confidences confidence-threshold nms-threshold]
  (let [locMat (new-matofrect2d) confidenceMat (new-matoffloat) indexMat (new-matofint)]
    (.fromList locMat locations)
    (.fromList confidenceMat confidences)
    (dnn/nms-boxes locMat confidenceMat (float confidence-threshold) (float nms-threshold) indexMat)
    indexMat
    (into-array (map #(nth (.get indexMat % 0) 0) (range (.total indexMat))))))

(defn- nms-filtered-objects [classes locations confidences]
  (let [indexes (indexes-of-nms-boxes locations confidences 0.5 0.5)]
    (map
     #(hash-map :confidence (nth confidences %) :label (nth classes %) :box (new-rect (.tl (nth locations %)) (.br (nth locations %))))
     indexes)))

(defn find-objects [_image net]
  (let [layers (dnn/output-layers net)
        ;outputs (new-arraylist (map (fn [_] (new-matoffloat)) (range (count layers))))
        outputs (new-arraylist)
        ; 1 / 255 = 0.00392157
        blob (dnn/blob-from-image _image 0.00392157 (new-size 640 640) (new-scalar 0 0 0) true)
        ; height 747 x width 926
        ; ratio 1.44
        tmpLocations (new-arraylist)
        tmpClasses (new-arraylist)
        tmpConfidences (new-arraylist)
        ratiow (/ (.width _image) 640)
        ratioh (/ (.height _image) 640)
        ]
    (.setInput net blob)
    (.forward net outputs layers)
    (doseq [^Mat _out outputs]
      ; for each detection
      (let [
            out (.reshape _out 1 8400)
            _h (.height out) _w (.width out)
            ]
      ;(cv/infos [["out" out]["image" _image]])

      (dotimes [j _h]

        (let [row (.row out j)
              scores (.colRange row 5 _w)
              result (min-max-loc scores)]
          ;(cv/infos [["scores" scores] ["row" row]])

          (if (> (.-maxVal result) 0.5)
            (do
              (let [center-x (* ratiow (nth (.get row 0 0) 0))
                    center-y (* ratioh (nth (.get row 0 1) 0))
                    width (* ratiow (nth (.get row 0 2) 0))
                    height (* ratioh (nth (.get row 0 3) 0))
                    x  (- center-x (/ width 2))
                    y  (- center-y (/ height 2))
                    rect (new-rect2d x y width height)]
                (.add tmpConfidences (-> result (.-maxVal) float))
                (.add tmpLocations rect)
                (.add tmpClasses (-> result (.-maxLoc) (.-x) int)))) )))))
    ;(println (nms-filtered-objects tmpClasses tmpLocations tmpConfidences))
    [_image (nms-filtered-objects tmpClasses tmpLocations tmpConfidences)]
    ))
