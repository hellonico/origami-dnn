# origami-dnn

OpenCV DNN project building on [origami](https://github.com/hellonico/origami) to run Tensorflow/Caffe/Darknet networks for image/video real time analysis.

This repository includes all the pretrained (large) network files required, and so is using [git lfs](https://git-lfs.github.com/) so make sure you install that first.



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
            [opencv4.dnn :as dnn]
            [origami-dnn.core :as origami-dnn]
            [origami-dnn.draw :as d]
            [opencv4.utils :refer [resize-by simple-cam-window]]))

(defn -main [& args]
  (let [ [net opts labels] (origami-dnn/read-net-from-folder "resources/caffe/mobilenet") ]
    (simple-cam-window
     (read-string (slurp "cam_config.edn"))
     (fn [buffer]
       (-> buffer 
        (find-objects net opts) 
        (d/red-boxes! labels))))))
```



![doc/detected.jpg](doc/detected.jpg)

| Lein Alias           | Example                                                      |
| -------------------- | ------------------------------------------------------------ |
| mobilenet.cam           | Run mobilenet on a webcam stream                    |
| mobilenet.videotofile   | Run mobilenet on a video file, and store it as a file  |
| mobilenet.videotoscreen | Run mobilenet on a video file, and display the file in a window |
| mobilenet.one           | Run mobilenet on one image and save the picture as a file |
| yolo.cam             | Run yolo on a webcam stream                                  |
| yolo.one             | Run yolo (tiny) on a picture                                 |
| yolo.v2              | Run yolo v2 on a picture                                     |
| yolo.v2tiny          | Run yolo v2 tiny on a picture                                |
| yolo.v3              | Run yolo v3 on a picture                                     |
| yolo.v3tiny          | Run yolo v3 tiny on a picture                                |
| yolo.videotoscreen   | Run yolo on a video file, and display the file in a window   |
| yolo.videotofile     | Run yolo don a video file, and save the picture as a file    |
| convnet.gender   | Run convnet on a picture, determine male or female |
| convnet.age | Run cnet on a picture, determine age                      |
| marcel | Run detection using mobilet on video and display |
| marcel2 | Run detection using mobilenet on video and save to file |
| bvlc | Run object classification using bvlc |
| googlenet | Run object classification using googlenet |
| places365 | Run object classification using places365 |
| resnet | Run object classfication using Resnet |

# Marcel le chat

This is a sample output generated on a macbook.

```
# lein run -m  origami-dnn.demo.mobilenet.catvideotofile resources/vids/Marcel.m4v
lein run -m  origami-dnn.demo.marcel.marcel
```

![](doc/marcel.gif)

or another one ...

![](doc/marcel2.gif)

Video courtesy of Marcel le chat.
