(defproject clojure_pedestal_todo_api "0.1.0-SNAPSHOT"
  :description "This is a simple project to explore some concepts in Clojure."
  :url "-"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [io.pedestal/pedestal.jetty "0.6.1"]
                 [org.slf4j/slf4j-simple "2.0.9"]]
  :repl-options {:init-ns clojure-pedestal-todo-api.core})
