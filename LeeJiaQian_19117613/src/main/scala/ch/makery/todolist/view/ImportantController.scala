//reference from addressapp practical by Dr Chin
package ch.makery.todolist.view
import ch.makery.todolist.model.ImportantTask
import ch.makery.todolist.Main
import scalafx.scene.control.{Alert, TableColumn, TableView}
import scalafxml.core.macros.sfxml
import scalafx.Includes._
import scalafx.event.ActionEvent
import scalafx.scene.control.Alert.AlertType
import java.time.LocalDate
import scala.util.{Failure, Success}

@sfxml
class ImportantController(
 private var taskTable : TableView[ImportantTask],
 private var taskCol : TableColumn[ImportantTask, String],
 private var dueCol : TableColumn[ImportantTask, LocalDate],
 private var progressCol : TableColumn[ImportantTask, String]
                     ) {
  taskTable.items = Main.importantTaskData
  taskCol.cellValueFactory = {_.value.task}
  dueCol.cellValueFactory = {_.value.due}
  progressCol.cellValueFactory = {_.value.progress}

  def handleNewTask(action : ActionEvent) = {
    val task = new ImportantTask("","")
    val ok = Main.showImportantAdd(task);
    if (ok) {
      task.save() match {
      case Success(x) =>
        Main.importantTaskData += task
      case Failure(e) =>
        val alert = new Alert(Alert.AlertType.Warning) {
          initOwner(Main.stage)
          title = "Failed to Save"
          headerText = "Database Error"
          contentText = "Database problem filed to save changes"
        }.showAndWait()
      }
    }
  }

  def handleEditTask(action : ActionEvent) = {
    val selectedTask = taskTable.selectionModel().selectedItem.value

    if(selectedTask != null) {
      val ok = Main.showImportantAdd(selectedTask)

      if(ok){
        selectedTask.save() match {
          case Success(x) =>
            true
          case Failure(e) =>
            val alert = new Alert(Alert.AlertType.Warning) {
              initOwner(Main.stage)
              title = "Failed to Save"
              headerText = "Database Error"
              contentText = "Database problem filed to save changes"
            }.showAndWait()
        }
      }
    }else {
      val alert = new Alert(Alert.AlertType.Warning){
        initOwner(Main.stage)
        title       = "No Selection"
        headerText  = "No Task Selected"
        contentText = "Please select a task in the table."
      }.showAndWait()
    }
  }

  def handleDeleteTask(action : ActionEvent) = {
    val selectedIndex = taskTable.selectionModel().selectedIndex.value
    val selectedTask = taskTable.selectionModel().selectedItem.value
    if(selectedIndex >= 0) {
      selectedTask.delete() match {
        case Success(x) =>
          taskTable.items().remove(selectedTask);
        case Failure(e) =>
          val alert = new Alert(Alert.AlertType.Warning) {
            initOwner(Main.stage)
            title = "Failed to Save"
            headerText = "Database Error"
            contentText = "Database problem filed to save changes"
          }.showAndWait()
      }
    }else {
      val alert = new Alert(AlertType.Warning){
        initOwner(Main.stage)
        title       = "No Selection"
        headerText  = "No Task Selected"
        contentText = "Please select a task in the table."
      }.showAndWait()
    }
  }
}
