# origami-dnn

OpenCV DNN project building on [origami](https://github.com/hellonico/origami) to run Tensorflow/Caffe/Darknet networks for image/video real time analysis.

# New YoloV6

To quickly get started, run one of the following command:

```bash
# Yolo v6n
lein yolo.v6cam networks.yolo:yolov6n:1.0
# Yolo v6s
lein yolo.v6cam networks.yolo:yolov6s:1.0
# Yolo v6t
lein yolo.v6cam networks.yolo:yolov6t:1.0
# Yolo v6l
lein yolo.v6cam networks.yolo:yolov6l:1.0
```

# Run origami-dnn on a file 

To run detection on a pretrained network, read the image, and call the network detection:

```
(-> input
    (imread)
    (mobilenet/find-objects net opts)
    (d/blue-boxes! labels)
    (imwrite output))
```

# Run origami-dnn on a stream from camera

```
(ns origami-dnn.demo.ssdnet.cam
  (:require [origami-dnn.net.mobilenet :refer [find-objects]]
            [origami-dnn.core :as origami-dnn]
            [origami-dnn.draw :as d]
            [opencv4.utils :refer [resize-by simple-cam-window]]))

(defn -main [& args]
  (let [ [net opts labels] (origami-dnn/read-net-from-repo "networks.tensorflow:tf-ssdmobilenet:1.0.0") ]
    (simple-cam-window
     (read-string (slurp "cam_config.edn"))
     (fn [buffer]
       (-> buffer 
        (find-objects net opts) 
        (d/red-boxes! labels))))))
```



![doc/detected.jpg](doc/detected.jpg)


| Alias                   | Format     | Network | Network Origami ID                        | DataSet  | Type | Example                                                            |
|-------------------------| ------------------------------------------------------------ | ------------------------------------------------------------ |-------------------------------------------| ------------------------------------------------------------ | ------------------------------------------------------------ |--------------------------------------------------------------------|
| mobilenet.cam           | caffe      | mobilenet | networks.caffe:mobilenet:1.0.0            |            |            | Run mobilenet on a webcam stream                                   |
| mobilenet.videotofile   | caffe | mobilenet | networks.caffe:mobilenet:1.0.0            |    |    | Run mobilenet on a video file, and store it as a file              |
| mobilenet.videotoscreen | caffe | mobilenet | networks.caffe:mobilenet:1.0.0            |  |  | Run mobilenet on a video file, and display the file in a window    |
| mobilenet.one           | caffe      | mobilenet  | networks.caffe:mobilenet:1.0.0            |            |            | Run mobilenet on one image and save the picture as a file          |
| yolo.cam                | darknet     | Yolo         | networks.yolo:yolov2-tiny:1.0.0           |              |              | Run yolo on a webcam stream                                        |
| yolo.one                | darknet      | Yolo         | networks.yolo:yolov2-tiny:1.0.0           |              |              | Run yolo (tiny) on a picture                                       |
| yolo.v2                 | darknet       | Yolo          | networks.yolo:yolov2:1.0.0                |               |               | Run yolo v2 on a picture                                           |
| yolo.v2tiny             | darknet   | Yolo      | networks.yolo:yolov2-tiny:1.0.0           |           |           | Run yolo v2 tiny on a picture                                      |
| yolo.v3                 | darknet       | Yolo          | networks.yolo:yolov3:1.0.0                |               |               | Run yolo v3 on a picture                                           |
| yolo.v3tiny             | darknet   | Yolo      | networks.yolo:yolov3-tiny:1.0.0           |           |           | Run yolo v3 tiny on a picture                                      |
| yolo.v4                 | darknet | Yolo | networks.yolo:yolov4:1.0.0                | | | Run yolo v4 on a picture                                           |
| yolo.v6                 | darknet   | Yolo      | networks.yolo:yolov6n:1.0                 |           |           | Run yolo v6 on a picture                                           |
| yolo.v6cam              | darknet | Yolo | networks.yolo:yolov6n:1.0               | | | Run yolo v6 on a cam                                               |
| yolo.videotoscreen      | darknet | Yolo | networks.yolo:yolov2-tiny:1.0.0           |    |    | Run yolo on a video file, and display the file in a window         |
| convnet.gender          | caffe | ConvNet | networks.caffe:convnet-gender:1.0.0       |    | classification | Run convnet on a picture, determine male or female                 |
| convnet.age             | caffe | ConvNet | networks.caffe:convnet-age:1.0.0          |  | classification | Run cnet on a picture, determine age                               |
| marcel                  | caffe | MobileNet | networks.caffe:mobilenet:1.0.0            |  | detection | Run detection using mobilet on video and display                   |
| marcel2                 | caffe | MobileNet | networks.caffe:mobilenet:1.0.0            |  | detection | Run detection using mobilenet on video and save to file            |
| bvlc                    | caffe | AlexNet | networks.caffe:bvlc_alexnet:1.0.0         |  | classification | Run object classification using bvlc                               |
| places365               | caffe |  | networks.caffe:places365:1.0.0            |  | classification | Run object classification using places365                          |
| resnet                  | caffe | ResNet | networks.caffe:resnet:1.0.0               |  | classification | Run object classfication using Resnet                              |
| cifar                   | darknet |  | networks.darknet:cifar-custom:1.0.0       |  | classification | Classification using a custom Trained Darknet Model based on cifar |
| enet                    | darknet | Enet | networks.darknet:enet-coco:1.0.0          |  |  | Run detection with enet                                            |
| openimages              | darknet |  | network.darknet:yolo-openimages:1.0.0     |  |  | Run detection with Yolo v3 Trained on OpenImages                   |
| flowers                 | caffe |  | networks.caffe:flowers:1.0.0              |  |  | Flower detection based on trained oxford102                        |
| tensorflow.mobilenet    | tensorflow | MobileNet | networks.tensorflow:tf-ssdmobilenet:1.0.0 | Coco | Detection | On an image                                                        |

# Marcel le chat

This is a sample output generated on a macbook.

```
lein run -m  origami-dnn.demo.mobilenet.catvideotofile resources/vids/Marcel.m4v
# or 
lein run -m  origami-dnn.demo.marcel.marcel
```

![](doc/marcel.gif)

or another one ...

![](doc/marcel2.gif)

Video courtesy of Marcel le chat.

# Testing networks using the clj command

Create a deps.edn with the following content:

```
{:mvn/repos
   {"vendredi" {:url "https://repository.hellonico.info/repository/hellonico/"}}
 :deps
   { origami-dnn {:mvn/version "0.1.16"}}}
```

and run one of the namespaces like shown below:
```bash
# Run age detection on a cam
clj -m origami-dnn.demo.agecam
# Run Yolo on a cam
clj -m origami-dnn.demo.yolo.cam
# Run YoloV6 on a cam
clj -m origami-dnn.demo.yolo.v6cam
```

or start a repl and do the same:
```clj
# clj 
(require '[origami-dnn.demo.agecam :as agecam])
(agecam/-main)
```