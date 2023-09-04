//reference from addressapp practical by Dr Chin
package ch.makery.todolist.view
import ch.makery.todolist.model.TodayTask
import ch.makery.todolist.Main
import scalafx.scene.control.{TableView, TableColumn, Alert}
import scalafxml.core.macros.sfxml
import scalafx.Includes._
import scalafx.event.ActionEvent
import scalafx.scene.control.Alert.AlertType
import scala.util.{Failure, Success}

@sfxml
class TodayController(
                       private var taskTable : TableView[TodayTask],
                       private var taskCol : TableColumn[TodayTask, String],
                       private var progressCol : TableColumn[TodayTask, String]
                     ) {
  taskTable.items = Main.todayTaskData
  taskCol.cellValueFactory = {_.value.task}
  progressCol.cellValueFactory = {_.value.progress}

  // function to create the new today task
  def handleNewTask(action : ActionEvent) = {
    val task = new TodayTask("","")
    val ok = Main.showTodayAdd(task);
    if (ok) {
      task.save() match {
        case Success(x) =>
          Main.todayTaskData += task
        case Failure(e) =>
          val alert = new Alert(Alert.AlertType.Warning) {
            initOwner(Main.stage)
            title = "Failed to Save"
            headerText = "Database Error"
            contentText = "Database problem failed to save changes"
          }.showAndWait()
      }
    }
  }

  // function to edit task
  def handleEditTask(action : ActionEvent) = {
    val selectedTask = taskTable.selectionModel().selectedItem.value
    if(selectedTask != null) {
      val ok = Main.showTodayAdd(selectedTask)

      if(ok){
        selectedTask.save() match {
          case Success(x) =>
            true
          case Failure(e) =>
            val alert = new Alert(Alert.AlertType.Warning) {
              initOwner(Main.stage)
              title = "Failed to Save"
              headerText = "Database Error"
              contentText = "Database problem failed to save changes"
            }.showAndWait()
        }
      }
    }else {
      // Nothing selected.
      val alert = new Alert(Alert.AlertType.Warning){
        initOwner(Main.stage)
        title       = "No Selection"
        headerText  = "No Task Selected"
        contentText = "Please select a task in the table."
      }.showAndWait()
    }
  }

  // function to delete task
  def handleDeleteTask(action : ActionEvent) = {
    val selectedIndex = taskTable.selectionModel().selectedIndex.value
    val selectedTask = taskTable.selectionModel().selectedItem.value
    if(selectedIndex >= 0) {
      selectedTask.delete() match {
        case Success(x) =>
          taskTable.items().remove(selectedTask)
        case Failure(e) =>
          val alert = new Alert(Alert.AlertType.Warning) {
            initOwner(Main.stage)
            title = "Failed to Save"
            headerText = "Database Error"
            contentText = "Database problem failed to save changes"
          }.showAndWait()
      }
    }else {
      // Nothing selected.
      val alert = new Alert(AlertType.Warning){
        initOwner(Main.stage)
        title       = "No Selection"
        headerText  = "No Task Selected"
        contentText = "Please select a task in the table."
      }.showAndWait()
    }
  }
}
