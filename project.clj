(defproject lipase "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [org.clojure/clojurescript "1.10.773"
                  :exclusions [com.google.javascript/closure-compiler-unshaded
                               org.clojure/google-closure-library
                               org.clojure/google-closure-library-third-party]]
                 [thheller/shadow-cljs "2.10.19"]
                 [reagent "0.10.0"]
                 [re-frame "1.0.0"]
                 [day8.re-frame/tracing "0.6.0"]
                 [re-com "2.8.0"]
                 [clj-commons/secretary "1.2.4"]
                 [devcards "0.2.7"]]

  :plugins [[lein-shadow "0.2.0"]
            [lein-shell "0.5.0"]]
            ; [deraen/lein-sass4clj "0.5.1"]
            ; [lein-pdo "0.1.1"]]

  ; :sass {:source-paths ["resources/public/scss"]
  ;        :target-path  "resources/public/css"
  ;        :output-style :nested
  ;        :source-map   true}

  :min-lein-version "2.9.0"

  :source-paths ["src/clj" "src/cljs"]

  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"]


  :shell {:commands {"open" {:windows ["cmd" "/c" "start"]
                             :macosx  "open"
                             :linux   "xdg-open"}}}

  :shadow-cljs {:nrepl {:port 8777}
                :builds {:app {:target :browser
                               :output-dir "resources/public/js/compiled"
                               :asset-path "/js/compiled"
                               :module-loader true
                               :modules {:shared {:preloads [devtools.preload
                                                             day8.re-frame-10x.preload]}
                                         :app {:init-fn lipase.core/init
                                               :depends-on #{:shared}}
                                         :cards {:init-fn cards.core/init
                                                 :depends-on #{:shared}}}
                               :dev {:compiler-options {:devcards true
                                                        :closure-defines {re-frame.trace.trace-enabled? true
                                                                          day8.re-frame.tracing.trace-enabled? true}}
                                     :build-options {:ns-aliases {devcards-marked cljsjs.marked
                                                                  devcards-syntax-highlighter cljsjs.highlight}}}
                               :release {:build-options
                                         {:ns-aliases
                                          {day8.re-frame.tracing day8.re-frame.tracing-stubs}}}

                               :devtools {:http-root "resources/public"
                                          :http-port 8280}}}}

  :aliases {"build"        ["shadow" "compile" "app"]
            "dev"          ["with-profile" "dev" "do";"pdo"
                            ; ["sass4clj" "auto"]
                            ["shadow" "watch" "app"]]
            "prod"         ["with-profile" "prod" "do"
                            ["shadow" "release" "app"]]
            "build-report" ["with-profile" "prod" "do"
                            ["shadow" "run" "shadow.cljs.build-report" "app" "target/build-report.html"]
                            ["shell" "open" "target/build-report.html"]]
            "karma"        ["with-profile" "prod" "do"
                            ["shadow" "compile" "karma-test"]
                            ["shell" "karma" "start" "--single-run" "--reporters" "junit,dots"]]}

  :profiles
  {:dev
   {:dependencies [[binaryage/devtools "1.0.2"]
                   [day8.re-frame/re-frame-10x "0.7.0"]]
    :source-paths ["dev"]}

   :prod {}}

  :prep-tasks [])
