import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

    val appName         = "gecote"
    val appVersion      = "1.0"

    val appDependencies = Seq(
      jdbc,
      javaCore,
      javaJdbc,
      javaJpa,
      "postgresql" % "postgresql" % "9.1-901.jdbc4",
      "org.hibernate" % "hibernate-entitymanager" % "4.2.1.Final"
    )

    val main = play.Project(appName, appVersion, appDependencies).settings(
      ebeanEnabled := false   
    )

}
            
