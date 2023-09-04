//reference from addressapp practical by Dr Chin
package ch.makery.todolist.model
import ch.makery.address.util.DateUtil._
import ch.makery.todolist.util.Database
import scalafx.beans.property.{ObjectProperty, StringProperty}
import java.time.LocalDate
import scalikejdbc._
import scala.util.Try


class UpcomingTask (taskS: String) extends Database {
  def this() = this(null)
  var task = new StringProperty(taskS)
  var due = ObjectProperty[LocalDate](LocalDate.now())

  def save() : Try[Int] = {
    if(!(isExist)) {
      Try(DB autoCommit { implicit session =>
        sql"""
        insert into upcomingTask (task, due) values
          (${task.value}, ${due.value.asString})
      """.update.apply()
      })
    } else {
      Try(DB autoCommit { implicit session =>
        sql"""
				update upcomingTask
				set
				task  = ${task.value} ,
				due   = ${due.value.asString}
				 where task = ${task.value}
				""".update.apply()
      })
    }
  }

  def delete() : Try[Int] = {
    if (isExist) {
      Try(DB autoCommit { implicit session =>
        sql"""
				delete from upcomingTask where
					task = ${task.value}
				""".update.apply()
      })
    } else
      throw new Exception("Task not Exists in Database")
  }

  def isExist : Boolean =  {
    DB readOnly { implicit session =>
      sql"""
				select * from upcomingTask where
				task = ${task.value}
			""".map(rs => rs.string("task")).single.apply()
    } match {
      case Some(x) => true
      case None => false
    }

  }
}

object UpcomingTask extends Database{
  def apply(
             taskS: String,
             dueS: String
           ): UpcomingTask = {
    new UpcomingTask(taskS){
      due.value = dueS.parseLocalDate
    }
  }

  def initializeTable() = {
    DB autoCommit { implicit session =>
      sql"""
			create table upcomingTask (
			  id int not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
			  task varchar(300),
			  due varchar(64)
			)
			""".execute.apply()
    }
  }

  def getAllUpcomingTasks: List[UpcomingTask] = {
    DB readOnly { implicit session =>
      sql"select * from upcomingTask".map(rs => UpcomingTask(rs.string("task"),
        rs.string("due") )).list.apply()
    }
  }
}
