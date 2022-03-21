

lazy val `TheBAR-EA` = (project in file(".")).
  settings(
    name := "thebar-ea",
    organization:= "com.beamsuntory.thebar.ea",
    version := "1.2.0-01",
    scalaVersion := "2.11.11",
    mainClass in Compile := Some("myPackage.BarEAMain")
  )

val sparkVersion = "2.4.8"

// disable using the Scala version in output paths and artifacts
crossPaths := false

parallelExecution in Test := false

//option to avoid warnings
updateOptions := updateOptions.value.withLatestSnapshots(false)

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion % "provided",
  "org.apache.spark" %% "spark-sql" % sparkVersion % "provided",
  "org.apache.spark" %% "spark-hive" % sparkVersion % "provided",
  "junit" % "junit" % "4.12" % "test",
  "com.novocode" % "junit-interface" % "0.11" % "test",
  "com.beamsuntory.bgc.commons" % "commons" % "2.0.2-05",
  "com.novocode" % "junit-interface" % "0.11" % Test,
  "junit" % "junit" % "4.12" % Test,
  "org.scalatest" %% "scalatest" % "3.0.0" % Test,
  "com.crealytics" % "spark-excel_2.11" % "0.13.5"
)

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}

artifact in (Compile, assembly) := {
  val art = (artifact in (Compile, assembly)).value
  art.copy(`classifier` = Some("assembly"))
}

addArtifact(artifact in (Compile, assembly), assembly)

sources in (Compile,doc) := Seq.empty
publishArtifact in (Compile, packageDoc) := false


credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")

resolvers += "Nexus-Snapshot" at "http://104.154.138.29:8081/repository/maven-snapshots/"
resolvers += "Nexus-Releases" at "http://104.154.138.29:8081/repository/maven-releases/"

publishTo := {
  val nexus = "http://104.154.138.29:8081/"
  if (version.value.trim.endsWith("SNAPSHOT"))
    Some("snapshots" at nexus + "repository/maven-snapshots/")
  else
    Some("releases"  at nexus + "repository/maven-releases/")
}