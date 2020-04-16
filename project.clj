(defproject origami-dnn "0.1.8-SNAPSHOT"
  :main opencv4.dnn.core

  ; FIXME: just replace this in all the examples and revert
  :resource-paths ["pkg-resources"]

  :release-tasks 
  [["vcs" "assert-committed"]
   ["change" "version" "leiningen.release/bump-version" "release"]
   ["vcs" "commit"]
   ["vcs" "tag" "--no-sign"]
   ["deploy" "clojars"]
   ["change" "version" "leiningen.release/bump-version"]
   ["vcs" "commit"]
   ["vcs" "push"]]

  :jvm-opts ["-Djava.library.path=natives"]
  :aliases {"mobilenet.cam" ["run" "-m" "origami-dnn.demo.ssdnet.cam"]
            "mobilenet.videotofile" ["run" "-m" "origami-dnn.demo.ssdnet.catvideotofile"]
            "mobilenet.videotoscreen" ["run" "-m" "origami-dnn.demo.ssdnet.catvideotoscreen"]
            "mobilenet.one" ["run" "-m" "origami-dnn.demo.ssdnet.onecat"]
           
            "yolo.cam" ["run" "-m" "origami-dnn.demo.yolo.cam"]
            "yolo.one" ["run" "-m" "origami-dnn.demo.yolo.v2-tiny"]
            "yolo.v2" ["run" "-m" "origami-dnn.demo.yolo.v2"]
            "yolo.v2tiny" ["run" "-m" "origami-dnn.demo.yolo.v2-tiny"]
            "yolo.v3" ["run" "-m" "origami-dnn.demo.yolo.v3"]
            "yolo.v3tiny" ["run" "-m" "origami-dnn.demo.yolo.v3-tiny"]
            "yolo.videotoscreen" ["run" "-m" "origami-dnn.demo.yolo.catvideotoscreen"]
            "yolo.videotofile" ["run" "-m" "origami-dnn.demo.yolo.catvideotofile"]
           
            "convnet.age" ["run" "-m" "origami-dnn.demo.age"]
            "convnet.gender" ["run" "-m" "origami-dnn.demo.gender"]
           
            "bvlc" ["run" "-m" "origami-dnn.demo.bvlc"]
            "googlenet" ["run" "-m" "origami-dnn.demo.googlenet"]
            "places365" ["run" "-m" "origami-dnn.demo.places365"]
            "resnet" ["run" "-m" "origami-dnn.demo.resnet"]
           
            "marcel" ["run" "-m" "origami-dnn.demo.marcel.marcel"]
            "marcel2" ["run" "-m" "origami-dnn.demo.marcel.marcel2"]
            }
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :repositories [["vendredi" "https://repository.hellonico.info/repository/hellonico/"]]
  :dependencies [[org.clojure/clojure "1.8.0"] [origami/origami "4.3.0-1"]
  ])
