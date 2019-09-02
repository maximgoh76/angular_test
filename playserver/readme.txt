install sbt
sbt-1.2.8.msi
sbt new playframework/play-java-seed.g8
sbt -jvm-debug 9999 run 

https://www.playframework.com/documentation/2.7.x/IDE

the project-specific file at PROJECT_DIR/project/plugins.sbt
Add sbteclipse to the plugin definition file:
addSbtPlugin("com.typesafe.sbteclipse" % "sbteclipse-plugin" % "5.2.4")

In sbt use the command eclipse to create Eclipse project files
> eclipse

In Eclipse use the Import Wizard to import Existing Projects into Workspace

set JAVA_HOME
///////////////////-Dhttp.port-9443
sbt  -jvm-debug 9999 
run

PROTOCOL BUFFERS
mapAsyncUnordered