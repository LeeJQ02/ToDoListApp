//reference from addressapp practical by Dr Chin
package ch.makery.todolist.util
import scalikejdbc._
import ch.makery.todolist.model.{ImportantTask, TodayTask, UpcomingTask}

trait Database {
  val derbyDriverClassname = "org.apache.derby.jdbc.EmbeddedDriver"

  val dbURL = "jdbc:derby:myDB;create=true;";
  // initialize JDBC driver & connection pool
  Class.forName(derbyDriverClassname)
  ConnectionPool.singleton(dbURL, "me", "mine")

  // ad-hoc session provider on the REPL
  implicit val session = AutoSession
}

object Database extends Database{
  def setupDB() = {
    if (!hasTodayDBInitialize) {
      TodayTask.initializeTable()
    } else if (!hasUpcomingDBInitialize){
      UpcomingTask.initializeTable()
    } else if (!hasImportantDBInitialize){
      ImportantTask.initializeTable()
    }
  }

  def hasTodayDBInitialize() : Boolean = {
    DB getTable "todayTask" match {
      case Some(x) => true
      case None => false
    }
  }

  def hasUpcomingDBInitialize() : Boolean = {
    DB getTable "upcomingTask" match {
      case Some(x) => true
      case None => false
    }
  }

  def hasImportantDBInitialize() : Boolean = {
    DB getTable "importantTask" match {
      case Some(x) => true
      case None => false
    }
  }

}
