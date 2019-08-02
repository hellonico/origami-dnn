(ns origami-dnn.net.core
  (:require [opencv4.dnn :as dnn]
            [opencv4.core :refer [new-size new-scalar min-max-loc]]))

;
; COMMON
;
(defn blob-from-image [image opts]
  (let [_opts (merge {:scale 1.0 :size 224 :mean [0.0 0.0 0.0] :swap-rgb false :crop false} opts)]
    (dnn/blob-from-image
     image
     (_opts :scale)
     (new-size (_opts :size) (_opts :size))
     (apply new-scalar (_opts :mean))
     (_opts :swap-rgb)
     (_opts :crop))))

;
; CLASSIFY
;
(defn- to-obj [reshaped minmax]
  {:confidence
   (.-maxVal minmax)
   :label
   (int (-> minmax (.-maxLoc) (.-x)))})

(defn classify [image net opts]
  (let [blob (blob-from-image image opts)
        detections (do (.setInput net blob) (.forward net))
        reshaped (.reshape detections 1 1) ; not needed
        minmax (min-max-loc reshaped)
        detected-objects (to-obj reshaped minmax)]
    [image detected-objects]))

;
; 
;

