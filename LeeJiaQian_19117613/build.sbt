name := "LeeJiaQian_19117613"
version := "1.0"
scalaVersion := "2.12.11"

fork := true

// https://mvnrepository.com/artifact/org.scalafx/scalafx
libraryDependencies += "org.scalafx" %% "scalafx" % "8.0.192-R14"

// https://mvnrepository.com/artifact/org.scalafx/scalafxml-core-sfx8
libraryDependencies += "org.scalafx" %% "scalafxml-core-sfx8" % "0.5"

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.1" cross CrossVersion.full)

libraryDependencies ++= Seq(
  "org.scalafx" % "scalafx_2.12" % "8.0.144-R12",
  "org.scalafx" % "scalafxml-core-sfx8_2.12" % "0.4",
  "org.scalikejdbc" %% "scalikejdbc"       % "3.1.0",
  "com.h2database"  %  "h2"                % "1.4.196",
  "ch.qos.logback"  %  "logback-classic"   % "1.2.3",
  "org.apache.derby" % "derby" % "10.13.1.1"
)
