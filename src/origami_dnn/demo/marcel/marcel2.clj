(ns origami-dnn.demo.marcel.marcel2
  (:require [origami-dnn.net.mobilenet :refer [find-objects]]
            [opencv4.utils :refer [show re-show]]
            [origami-dnn.draw :as d]
            [opencv4.video :refer [CAP_PROP_FRAME_HEIGHT CAP_PROP_FRAME_WIDTH new-videowriter]]
            [opencv4.dnn.core :as origami-dnn]
            [opencv4.core :refer [ROTATE_90_CLOCKWISE rotate! new-mat resize! new-size new-videocapture imread imwrite]])
  (:import (java.util Date)))

(defn- rotated-video-size [cap]
  (new-size (.get cap CAP_PROP_FRAME_HEIGHT) (.get cap CAP_PROP_FRAME_WIDTH) ))

(defn -main [& args]
  (let [input "./resources/vids/Marcel2.m4v"
        output-file "marcel2.mpeg"
        [net opts labels] (origami-dnn/read-net-from-repo "networks.caffe:mobilenet:1.0.0")
        cap (new-videocapture input)
        stream-size (rotated-video-size cap)
        buffer (new-mat)
        w (new-videowriter)]
    (println "Rotated Stream size:\t" stream-size)
    (.open w output-file -1 24 (rotated-video-size cap))
    (while (.read cap buffer)
      (let [annon
            (-> buffer
                (rotate! ROTATE_90_CLOCKWISE)
                (find-objects net opts)
                (d/blue-boxes! labels))]
        (.write w annon)))
    (.release w)
    (.release cap)))
