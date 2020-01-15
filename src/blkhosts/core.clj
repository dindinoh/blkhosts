(ns blkhosts.core
  "ns"
  (:gen-class)
  (:require
   [clj-http.client :as client]
   [clojure.java.io :as io]
   [clojure.string :as string]))

(def keeppatterns
  "regexes to keep"
  [#"^0.0.0.0.*|^127.0.0.1.*|^255.255.255.255.*"])

(def removepatterns
  "regexes to remove"
  [#".*reddit.*|.*redd\.it.*|.*linkedin.*|^\#.*|^$"])

(def hostsfile
  "download"
  (let [url (str "https://raw.githubusercontent.com/"
                 "StevenBlack/hosts/master/alternates/"
                 "fakenews-gambling-porn-social/hosts")]
    (client/get url)))

(defn write-file
  "writethis"
  [filename & content]
  (if (re-matches (first keeppatterns) (reduce str content))
    (let [cnt (reduce str content)]
      (with-open [w (io/writer filename :append true)]
        (.write w (str cnt "\n"))))))

(defn -main
  "main"
  []
  (io/delete-file "/tmp/newhosts" true)
  (doall (map
          #(write-file "/tmp/newhosts" %)
          (remove #(re-seq (first removepatterns) %)
                  (string/split-lines
                   (-> hostsfile
                       :body))))))
