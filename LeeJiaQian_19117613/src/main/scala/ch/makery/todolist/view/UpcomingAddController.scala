//reference from addressapp practical by Dr Chin
package ch.makery.todolist.view
import ch.makery.todolist.model.UpcomingTask
import scalafx.scene.control.{Alert, DatePicker, TextField}
import scalafxml.core.macros.sfxml
import scalafx.stage.Stage
import scalafx.event.ActionEvent

@sfxml
class UpcomingAddController (
 private val taskField : TextField,
 private val dueDate : DatePicker
                         ) {
  var displayStage : Stage = null
  private var _task : UpcomingTask = null
  var ok = false

  def task = _task
  def task_=(t : UpcomingTask): Unit ={
    _task = t

    taskField.text = _task.task.value
    dueDate.value = _task.due.value

  }

  def handleOk(action :ActionEvent){
    if(isInputValid()){
      _task.task <== taskField.text
      _task.due <== dueDate.value

      ok = true;
      displayStage.close()
    }
  }

  def handleCancel(action :ActionEvent) {
    displayStage.close();
  }

  def nullChecking (x : String) = x == null || x.length == 0

  def isInputValid() : Boolean = {
    var errorMessage = ""

    if (nullChecking(taskField.text.value))
      errorMessage += "No valid task!\n"
    if (nullChecking(dueDate.value.value.toString))
      errorMessage += "No valid date selected!\n"

    if (errorMessage.length() == 0) {
      return true;
    } else {
      // Show the error message.
      val alert = new Alert(Alert.AlertType.Error){
        initOwner(displayStage)
        title = "Invalid Fields"
        headerText = "Please correct invalid fields"
        contentText = errorMessage
      }.showAndWait()

      return false;
    }
  }
}
