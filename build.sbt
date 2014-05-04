name := "pruebaES"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache
)     

libraryDependencies += "org.apache.poi" % "poi" % "3.10-FINAL"

libraryDependencies += "org.apache.poi" % "poi-ooxml" % "3.10-FINAL"

libraryDependencies += "org.elasticsearch" % "elasticsearch" % "1.1.1"

play.Project.playJavaSettings
