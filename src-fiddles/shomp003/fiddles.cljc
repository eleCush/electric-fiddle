(ns shomp003.fiddles
  #?(:clj (:import [java.util Base64]))
  #?(:clj (:import [java.net URLEncoder]))
  #?(:clj (:import [java.io File FileOutputStream]))
  (:require 
  #?(:clj [shomp003.xtdb-contrib :as db])
            #?(:clj [clojure.java.io :as io])
            #?(:clj [clojure.string :as str])
            #?(:clj [clj-http.client :as client])
            #?(:clj [cheshire.core :as json])
            #?(:clj [cryptohash-clj.api :refer [hash-with verify-with]])
            [contrib.str :refer [empty->nil]]
            #?(:cljs [goog.crypt.base64 :as base64])
            #?(:cljs [ajax.core :as ajax :refer [PUT POST]])
            [hyperfiddle.electric :as e]
            [hyperfiddle.electric-dom2 :as dom]
            [hyperfiddle.electric-ui4 :as ui]
            [missionary.core :as m]
            [xtdb.api #?(:clj :as :cljs :as-alias) xt]
            ))
#?(:clj 
(defn start-xtdb! [] ; from XTDB’s getting started: xtdb-in-a-box
  (assert (= "true" (System/getenv "XTDB_ENABLE_BYTEUTILS_SHA1")))
  (letfn [(kv-store [dir] {:kv-store {:xtdb/module 'xtdb.rocksdb/->kv-store
                                      :db-dir (clojure.java.io/file dir)
                                      :sync? true}})]
    (xt/start-node
      {:xtdb/tx-log (kv-store "data/dev/tx-log")
       :xtdb/document-store (kv-store "data/dev/doc-store")
       :xtdb/index-store (kv-store "data/dev/index-store")}))))




(e/defn ShompCollar []
  (e/client
    (dom/h1 (dom/text "Hello from shomp revived."))))

(e/def fiddles ; Entries for the dev index
  {`ShompCollar ShompCollar})

(e/defn ShompMain [ring-req] ; prod entrypoint
  (e/server
    (binding [e/http-request ring-req])
      (e/client
        (binding [dom/node js/document.body]
          (ShompCollar.)))))


