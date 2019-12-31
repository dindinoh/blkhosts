(ns blkhosts.core
  (:gen-class)
  (:require
   [clojure.tools.cli :refer [parse-opts]]
   [clj-http.client :as client])
  )

(defn -main
  []
  (def printfile (delay (def hostsfile (client/get "https://raw.githubusercontent.com/StevenBlack/hosts/master/alternates/fakenews-gambling-porn-social/hosts"))))
  (force printfile)
  (spit "NEWHOSTS.txt" hostsfile))


