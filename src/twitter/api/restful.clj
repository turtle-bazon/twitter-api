(ns twitter.api.restful
  (:require [clojure.java.io :as io]
            [twitter.api :refer [clean-resource-path ->ApiContext]]
            [twitter.callbacks :refer [get-default-callbacks]]
            [twitter.core :refer [def-twitter-method]])
  (:import (com.ning.http.client.multipart ByteArrayPart StringPart)))

(def ^:dynamic *rest-api-11* (->ApiContext "https" "api.twitter.com" "1.1"))
(def ^:dynamic *rest-api-2* (->ApiContext "https" "api.twitter.com" "2"))
(def ^:dynamic *oauth-api* (->ApiContext "https" "api.twitter.com" nil))
(def ^:dynamic *rest-upload-api* (->ApiContext "https" "upload.twitter.com" "1.1"))

(defmacro def-twitter-restful-method-11
  {:requires [#'def-twitter-method get-default-callbacks]}
  [verb resource-path & rest]
  (let [json-path (str resource-path ".json") ; v1.1 is .json only.
        fn-name (-> resource-path clean-resource-path symbol)]
    `(def-twitter-method ~fn-name ~verb ~json-path :api ~*rest-api-11* :callbacks (get-default-callbacks :sync :single) ~@rest)))

(defmacro def-twitter-restful-method-2
  {:requires [#'def-twitter-method get-default-callbacks]}
  [verb resource-path & rest]
  (let [fn-name (-> resource-path clean-resource-path symbol)]
    `(def-twitter-method ~fn-name ~verb ~resource-path :api ~*rest-api-2* :callbacks (get-default-callbacks :sync :single) ~@rest)))

;; Accounts
(def-twitter-restful-method-11 :get  "account/settings")
(def-twitter-restful-method-11 :get  "account/verify_credentials")
(def-twitter-restful-method-11 :post "account/update_delivery_device")
(def-twitter-restful-method-11 :post "account/update_profile")
(def-twitter-restful-method-11 :post "account/update_profile_background_image")
(def-twitter-restful-method-11 :post "account/update_profile_colors")
(def-twitter-restful-method-11 :post "account/update_profile_image")
(def-twitter-restful-method-11 :post "account/remove_profile_banner")
(def-twitter-restful-method-11 :post "account/update_profile_banner")
(def-twitter-restful-method-11 :post "users/profile_banner")
(def-twitter-restful-method-11 :get  "application/rate_limit_status")

;; Blocks
(def-twitter-restful-method-11 :get  "blocks/list")
(def-twitter-restful-method-11 :get  "blocks/ids")
(def-twitter-restful-method-11 :post "blocks/create")
(def-twitter-restful-method-11 :post "blocks/destroy")

;; Timeline
(def-twitter-restful-method-11 :get "statuses/mentions_timeline")
(def-twitter-restful-method-11 :get "statuses/user_timeline")
(def-twitter-restful-method-11 :get "statuses/home_timeline")
(def-twitter-restful-method-11 :get "statuses/retweets_of_me")

;; Statuses
(def-twitter-restful-method-11 :get  "statuses/lookup")
(def-twitter-restful-method-11 :get  "statuses/retweets/{:id}")
(def-twitter-restful-method-11 :get  "statuses/show/{:id}")
(def-twitter-restful-method-11 :post "statuses/destroy/{:id}")
(def-twitter-restful-method-11 :post "statuses/update")
(def-twitter-restful-method-11 :post "statuses/retweet/{:id}")
(def-twitter-restful-method-11 :get  "statuses/oembed")
; Supply the status and file to the :body as a sequence using the functions 'file-body-part' and 'status-body-part'
; i.e. :body [(file-body-part "/pics/mypic.jpg") (status-body-part "hello world")]
; for an example, see twitter.test.upload
(def-twitter-restful-method-11 :post "statuses/update_with_media"
                               :api *rest-api-11*
                               :headers {:content-type "multipart/form-data"})

;; Media
(def-twitter-restful-method-11 :post "media/upload"
                               :api *rest-upload-api*
                               :headers {:content-type "multipart/form-data"})

;; Search
(def-twitter-restful-method-11 :get "search/tweets")

;; User
(def-twitter-restful-method-11 :get "users/show")
(def-twitter-restful-method-11 :get "users/lookup")
(def-twitter-restful-method-11 :get "users/search")
(def-twitter-restful-method-11 :get "users/contributees")
(def-twitter-restful-method-11 :get "users/contributors")
(def-twitter-restful-method-11 :get "users/suggestions")
(def-twitter-restful-method-11 :get "users/suggestions/{:slug}")
(def-twitter-restful-method-11 :get "users/suggestions/{:slug}/members")

;; Trends
(def-twitter-restful-method-11 :get "trends/place")
(def-twitter-restful-method-11 :get "trends/available")
(def-twitter-restful-method-11 :get "trends/closest")

;; Oauth
(def-twitter-restful-method-11 :get  "oauth/authenticate" :api *oauth-api*)
(def-twitter-restful-method-11 :get  "oauth/authorize" :api *oauth-api*)
(def-twitter-restful-method-11 :post "oauth/access_token" :api *oauth-api*)
(def-twitter-restful-method-11 :post "oauth/request_token" :api *oauth-api*)

;; Lists
(def-twitter-restful-method-11 :get  "lists/list")
(def-twitter-restful-method-11 :get  "lists/statuses")
(def-twitter-restful-method-11 :get  "lists/show")
(def-twitter-restful-method-11 :get  "lists/memberships")
(def-twitter-restful-method-11 :get  "lists/subscriptions")
(def-twitter-restful-method-11 :get  "lists/ownerships")
(def-twitter-restful-method-11 :post "lists/create")
(def-twitter-restful-method-11 :post "lists/update")
(def-twitter-restful-method-11 :post "lists/destroy")

;; List members
(def-twitter-restful-method-11 :post "lists/members/destroy")
(def-twitter-restful-method-11 :post "lists/members/destroy_all")
(def-twitter-restful-method-11 :get  "lists/members")
(def-twitter-restful-method-11 :get  "lists/members/show")
(def-twitter-restful-method-11 :post "lists/members/create")
(def-twitter-restful-method-11 :post "lists/members/create_all")

;; List subscribers
(def-twitter-restful-method-11 :get  "lists/subscribers")
(def-twitter-restful-method-11 :get  "lists/subscribers/show")
(def-twitter-restful-method-11 :post "lists/subscribers/create")
(def-twitter-restful-method-11 :post "lists/subscribers/destroy")

;; Direct messages
(def-twitter-restful-method-11 :get  "direct_messages")
(def-twitter-restful-method-11 :get  "direct_messages/sent")
(def-twitter-restful-method-11 :get  "direct_messages/show")
(def-twitter-restful-method-11 :post "direct_messages/new")
(def-twitter-restful-method-11 :post "direct_messages/destroy")

;; Friendships
(def-twitter-restful-method-11 :get  "friendships/lookup")
(def-twitter-restful-method-11 :post "friendships/create")
(def-twitter-restful-method-11 :post "friendships/destroy")
(def-twitter-restful-method-11 :post "friendships/update")
(def-twitter-restful-method-11 :get  "friendships/show")
(def-twitter-restful-method-11 :get  "friendships/incoming")
(def-twitter-restful-method-11 :get  "friendships/outgoing")
(def-twitter-restful-method-11 :get  "friendships/no_retweets/ids")

;; Friends and followers
(def-twitter-restful-method-11 :get "friends/ids")
(def-twitter-restful-method-11 :get "friends/list")
(def-twitter-restful-method-11 :get "followers/ids")
(def-twitter-restful-method-11 :get "followers/list")

;; Favourites
(def-twitter-restful-method-11 :get  "favorites/list")
(def-twitter-restful-method-11 :post "favorites/destroy")
(def-twitter-restful-method-11 :post "favorites/create")

;; Report spam
(def-twitter-restful-method-11 :post "users/report_spam")

;; Saved searches
(def-twitter-restful-method-11 :get  "saved_searches/list")
(def-twitter-restful-method-11 :get  "saved_searches/show/{:id}")
(def-twitter-restful-method-11 :post "saved_searches/create")
(def-twitter-restful-method-11 :post "saved_searches/destroy/{:id}")

;; Collections
(def-twitter-restful-method-11 :get  "collections/entries")
(def-twitter-restful-method-11 :get  "collections/list")
(def-twitter-restful-method-11 :get  "collections/show")
(def-twitter-restful-method-11 :post  "collections/create")
(def-twitter-restful-method-11 :post  "collections/destory")
(def-twitter-restful-method-11 :post  "collections/entries/add")
(def-twitter-restful-method-11 :post  "collections/entries/curate")
(def-twitter-restful-method-11 :post  "collections/entries/move")
(def-twitter-restful-method-11 :post  "collections/entries/remove")
(def-twitter-restful-method-11 :post  "collections/update")

;; Geo
(def-twitter-restful-method-11 :get  "geo/id/{:place_id}")
(def-twitter-restful-method-11 :get  "geo/reverse_geocode")
(def-twitter-restful-method-11 :get  "geo/search")
(def-twitter-restful-method-11 :get  "geo/similar_places")
(def-twitter-restful-method-11 :post "geo/place")

;; Help
(def-twitter-restful-method-11 :get "help/configuration")
(def-twitter-restful-method-11 :get "help/languages")
(def-twitter-restful-method-11 :get "help/tos")
(def-twitter-restful-method-11 :get "help/privacy")

;;
(def-twitter-restful-method-2 :post "tweets")

(defn media-upload-chunked
  "helper for uploading media using the chunked media upload API"
  ;; See https://developer.twitter.com/en/docs/media/upload-media/api-reference/post-media-upload-init
  [& {:keys [oauth-creds media media-type media-category additional-owners buffer-size]
      :or {buffer-size (* 1024 1024)}}]
  {:pre [(string? media)]}
  (let [media-size (.length (io/file media))
        media-stream (io/input-stream media)
        buffer (byte-array buffer-size)
        init-params (merge {:command "INIT"
                            :media-type media-type
                            :total-bytes media-size}
                           (when media-category
                             {:media-category media-category})
                           (when additional-owners
                             {:additional-owners additional-owners}))
        init-resp (media-upload
                   :oauth-creds oauth-creds
                   :params init-params)
        media-id (get-in init-resp [:body :media_id])]
    (loop [segment-index 0
           bytes-sent 0
           bytes-read (.read media-stream buffer)]
      (when-not (neg? bytes-read)
        (media-upload
         :oauth-creds oauth-creds
         :body [(StringPart. "command" "APPEND")
                (StringPart. "media_id" (str media-id))
                (StringPart. "segment_index" (str segment-index))
                (ByteArrayPart. "media" (if (= bytes-read buffer-size)
                                          buffer
                                          (java.util.Arrays/copyOfRange buffer 0 bytes-read)))])
        (recur (inc segment-index)
               (+ bytes-sent bytes-read)
               (.read media-stream buffer))))
    (media-upload
     :oauth-creds oauth-creds
     :params {:command "FINALIZE"
              :media-id media-id})))
