(ns opencv4.dnn.core-test
  (:require [clojure.test :refer :all]
            [opencv4.dnn.core :as origami-dnn]
            [opencv4.dnn.core :refer :all]))

(def networks [
               ;"networks.caffe:vgg-faces:1.0.0"
  ;"networks.caffe:places365:1.0.0"
  ;"networks.caffe:resnet:1.0.0"
  ;"networks.caffe:googlenet:1.0.0"
  ;"networks.caffe:convnet-gender:1.0.0"
  ;"networks.caffe:bvlc_alexnet:1.0.0"
  ;"networks.caffe:convnet-age:1.0.0"
  ;"networks.caffe:mobilenet:1.0.0"
  ;
  ;"networks.yolo:yolov3:1.0.0"
  ;"networks.yolo:yolov3-tiny:1.0.0"
  ;"networks.yolo:yolov2:1.0.0"
  ;"networks.yolo:yolov2-tiny:1.0.0"
  ;
  ;"networks.tensorflow:tf-ssdmobilenet:1.0.0"

])

(deftest test-loading-networks
  (doseq [net networks]
    (let [ [net opts labels] (origami-dnn/read-net-from-repo net) ]
    (is (not (nil? net))))))