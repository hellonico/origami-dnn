(ns origami-dnn.demo.ssdnet.catvideotofile
  (:require [origami-dnn.net.mobilenet :refer [find-objects]]
            [opencv4.utils :refer [show re-show]]
            [origami-dnn.draw :as d]
            [opencv4.video :refer [CAP_PROP_FRAME_HEIGHT CAP_PROP_FRAME_WIDTH new-videowriter]]
            [opencv4.dnn.core :as origami-dnn]
            [opencv4.core :refer [new-mat resize! new-size new-videocapture imread imwrite]])
  (:import (java.util Date)))

(defn- video-size [cap]
  (new-size (.get cap CAP_PROP_FRAME_WIDTH) (.get cap CAP_PROP_FRAME_HEIGHT) ))

(defn -main [& args]
    (let [
          input (or (first args) "resources/cat.mp4")
          output-file (or (second args) (str "cat_" (Date.) ".mp4"))
          [net opts labels] (origami-dnn/read-net-from-repo "networks.caffe:mobilenet:1.0.0")
          cap (new-videocapture) 
          _ (.open cap input)
          stream-size (video-size cap)
          buffer (new-mat)
          w (new-videowriter)
            ; need a fake frame before doing analysis probably due to the empty mat above
        ;   _frame (do (.read cap buffer) (resize! buffer (new-size 384 216)))
    ]
      (.open w output-file 1196444237 12 stream-size)
      (println "Stream size:\t" stream-size)
      ; (println "Frames:\t" (.get cap CAP_PROP_POS_FRAMES))
      (while (.read cap buffer)
        (let [annon 
              (-> buffer 
                ; (resize! (new-size 384 216)) 
                  (find-objects net opts)
                  (d/blue-boxes! labels)  )]
          (.write w annon)))
      (.release w)
      (.release cap)))