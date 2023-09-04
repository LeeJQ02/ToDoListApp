//reference from addressapp practical by Dr Chin
package ch.makery.todolist.model
import ch.makery.address.util.DateUtil._
import ch.makery.todolist.util.Database
import scalafx.beans.property.{ObjectProperty, StringProperty}
import java.time.LocalDate
import scalikejdbc._
import scala.util.Try

class ImportantTask(taskS: String, progresses: String){
  def this() = this(null, null)
  var task = new StringProperty(taskS)
  var progress = new StringProperty(progresses)
  var due = ObjectProperty[LocalDate](LocalDate.now())

  def save() : Try[Int] = {
    if(!(isExist)) {
      Try(DB autoCommit { implicit session =>
        sql"""
        insert into importantTask (task, progress, due) values
          (${task.value}, ${progress.value}, ${due.value.asString})
      """.update.apply()
      })
    } else {
      Try(DB autoCommit { implicit session =>
        sql"""
				update importantTask
				set
				task  = ${task.value} ,
				progress = ${progress.value},
				due   = ${due.value.asString}
				 where task = ${task.value} and progress = ${progress.value}
				""".update.apply()
      })
    }
  }

  def delete() : Try[Int] = {
    if (isExist) {
      Try(DB autoCommit { implicit session =>
        sql"""
				delete from importantTask where
					task = ${task.value} and progress = ${progress.value}
				""".update.apply()
      })
    } else
      throw new Exception("Task not Exists in Database")
  }

  def isExist : Boolean =  {
    DB readOnly { implicit session =>
      sql"""
				select * from importantTask where
				task = ${task.value} and progress = ${progress.value}
			""".map(rs => rs.string("task")).single.apply()
    } match {
      case Some(x) => true
      case None => false
    }

  }
}

object ImportantTask extends Database{
  def apply(
             taskS: String,
             progresses: String,
             dueS: String
           ): ImportantTask = {
    new ImportantTask(taskS, progresses) {
      due.value = dueS.parseLocalDate
    }
  }

  def initializeTable() = {
    DB autoCommit { implicit session =>
      sql"""
			create table importantTask (
			  id int not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
			  task varchar(300),
			  progress varchar(64),
			  due varchar(64)
			)
			""".execute.apply()
    }
  }

  def getAllImportantTasks: List[ImportantTask] = {
    DB readOnly { implicit session =>
      sql"select * from importantTask".map(rs => ImportantTask(rs.string("task"),
        rs.string("progress"),rs.string("due") )).list.apply()
    }
  }
}
