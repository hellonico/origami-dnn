clj -e "(require '[opencv4.utils :as u]) (-> \"$1\" opencv4.core/imread u/imshow)"
