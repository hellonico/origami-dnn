(ns origami-dnn.net.yolo
  (:require [opencv4.core :refer [min-max-loc new-rect2d new-rect new-matofrect2d new-arraylist new-mat new-size new-scalar new-matofrect new-matofint new-matoffloat]]
            [opencv4.dnn :as dnn]
            [opencv4.utils :as u]))

(defn- indexes-of-nms-boxes[locations confidences confidence-threshold nms-threshold]
  (let[locMat (new-matofrect2d) confidenceMat (new-matoffloat) indexMat (new-matofint)]
    (.fromList locMat locations)
    (.fromList confidenceMat confidences)
    (dnn/nms-boxes locMat confidenceMat (float confidence-threshold) (float nms-threshold) indexMat)
    indexMat
    (into-array (map #(nth (.get indexMat % 0) 0) (range (.total indexMat))))))

(defn- nms-filtered-objects[classes locations confidences ]
  (let [indexes (indexes-of-nms-boxes locations confidences 0.1 0.1)]
    (map
      #(hash-map :confidence (nth confidences %) :label (nth classes %) :box (new-rect (.tl (nth locations %)) (.br (nth locations %))) )
      indexes)))

(defn find-objects [_image net ]
  (let[layers (dnn/output-layers net)
       outputs (new-arraylist (map (fn [_] (new-mat)) (range (count layers))))
       blob (dnn/blob-from-image _image 0.00392157 (new-size 416 416) (new-scalar 0 0 0) true)
       tmpLocations (new-arraylist)
       tmpClasses (new-arraylist)
       tmpConfidences (new-arraylist)
       w (.width _image)
       h (.height _image)]
    (.setInput net blob)
    (.forward net outputs layers)

    (doseq [out outputs]
      (dotimes [j (.height out)]
    
        (let [row (.row out j) scores (.colRange row 5 (.width out)) result (min-max-loc scores)]
          
          (if (> (.-maxVal result) 0)
            (do
              (let [center-x (* w (nth (.get row 0 0) 0))
                    center-y (* h (nth (.get row 0 1) 0))
                    width (* w (nth (.get row 0 2) 0))
                    height (* h (nth (.get row 0 3) 0))
                    x  (- center-x (/ width 2))
                    y  (- center-y (/ height 2))
                    rect (new-rect2d x y width height)]
                (.add tmpConfidences (-> result (.-maxVal) float))
                (.add tmpLocations rect)
                (.add tmpClasses (-> result (.-maxLoc) (.-x) int))))))))
    [_image (nms-filtered-objects tmpClasses tmpLocations tmpConfidences)]))
