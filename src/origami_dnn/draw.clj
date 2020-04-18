(ns origami-dnn.draw
   (:require 
    [opencv4.colors.rgb :as rgb]
    [opencv4.core :refer [new-scalar new-point rectangle put-text! FONT_HERSHEY_PLAIN FONT_HERSHEY_TRIPLEX]]))

(defn black-boxes! [result labels]
  (let [img (first result) detected (second result)]
    (doseq [{confidence :confidence label :label box :box} detected]
      (rectangle img box (new-scalar 0 0 0) 2)
      (put-text! img
                 (str (nth labels label) "[" (int (* 100 confidence)) "% ]")
                 (new-point (double (.-x box)) (double (.-y box)))
                 FONT_HERSHEY_PLAIN 1.0 (new-scalar 0 0 0) 2))
    img))

(defn red-boxes! [result labels]
  (let [img (first result) detected (second result)]
  (put-text! img (str "Detecting [" (count detected) "]") (new-point 10 10)  FONT_HERSHEY_PLAIN 1 (new-scalar 0 0 0))
   (doseq [{confidence :confidence label :label box :box} detected]
    (put-text! img (str  (nth labels label) "[" confidence  " %]") (new-point (double (.-x box)) (double (.-y box))) FONT_HERSHEY_TRIPLEX 1 (new-scalar 0 0 255))
    (rectangle img box (new-scalar 0 0 255) 1))
  img))

(defn blue-boxes! [result labels]
  (let [img (first result) detected (second result)]
  (doseq [{confidence :confidence label :label box :box} detected]
    (put-text! img (str (nth labels label) "[" confidence " %]") (new-point (double (.-x box)) (double (.-y box))) FONT_HERSHEY_PLAIN 2 (new-scalar 255 0 0))
    (rectangle img box (new-scalar 255 0 0) 2))
  img))

(defn guess-gender [result labels]
   (let [img (first result) detected (second result) {confidence :confidence label :label} detected]
     (put-text! img (str (nth labels label) "[" (int (* 100 confidence)) " %]") (new-point 100 100) FONT_HERSHEY_PLAIN 4 rgb/black 3)
     img))

(defn write-in-white [result labels]
  (let [img (first result) detected (second result) {confidence :confidence label :label} detected]
    (println "Found: " label  "> " (nth labels label))
    (put-text! img (str (nth labels label) "[" (int (* 100 confidence)) " %]") (new-point 100 100) FONT_HERSHEY_PLAIN 3 rgb/white 2)
    img))

(defn tf-boxes! [result labels]
  (let [img (first result) detected (second result)]
    (doseq [{confidence :confidence label :label box :box} detected]
      (println "Found: " label  "> " (nth labels label))
      (put-text! img (str (nth labels label) " [" confidence " %]") (new-point (double (.-x box)) (double (+ 10 (.-y box)))) FONT_HERSHEY_PLAIN 2 rgb/red-1)
      (rectangle img box rgb/red-1 2))
    img))