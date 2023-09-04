//reference from addressapp practical by Dr Chin
package ch.makery.todolist.model
import ch.makery.todolist.util.Database
import scalafx.beans.property.StringProperty
import scalikejdbc._
import scala.util.Try

class TodayTask(taskS: String, progresses: String) extends Database{
  def this() = this(null, null)
  var task = new StringProperty(taskS)
  var progress = new StringProperty(progresses)

  def save() : Try[Int] = {
    if(!(isExist)) {
      Try(DB autoCommit { implicit session =>
        sql"""
        insert into todayTask (task, progress) values
          (${task.value}, ${progress.value})
      """.update.apply()
      })
    } else {
      Try(DB autoCommit { implicit session =>
        sql"""
				update todayTask
				set
				task  = ${task.value} ,
				progress   = ${progress.value}
				 where task = ${task.value} and
				 progress = ${progress.value}
				""".update.apply()
      })
    }
  }

  def delete() : Try[Int] = {
    if (isExist) {
      Try(DB autoCommit { implicit session =>
        sql"""
				insert into deletedTask (task, progress) values
          (${task.value}, ${progress.value})
				""".update.apply()
      })

      Try(DB autoCommit { implicit session =>
        sql"""
				delete from todayTask where
					task = ${task.value} and progress = ${progress.value}
				""".update.apply()
      })
    } else
      throw new Exception("Task not Exists in Database")
  }

  def isExist : Boolean =  {
    DB readOnly { implicit session =>
      sql"""
				select * from todayTask where
				task = ${task.value} and progress = ${progress.value}
			""".map(rs => rs.string("task")).single.apply()
    } match {
      case Some(x) => true
      case None => false
    }

  }
}

object TodayTask extends Database{
  def apply (
              taskS : String,
              progresses : String
            ) : TodayTask = {
    new TodayTask(taskS, progresses)
  }

  def initializeTable() = {
    DB autoCommit { implicit session =>
      sql"""
			create table todayTask (
			  id int not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
			  task varchar(300),
			  progress varchar(64)
			)
			""".execute.apply()
    }
  }

  def getAllTodayTasks : List[TodayTask] = {
    DB readOnly { implicit session =>
      sql"select * from todayTask".map(rs => TodayTask(rs.string("task"),
        rs.string("progress") )).list.apply()
    }
  }
}
