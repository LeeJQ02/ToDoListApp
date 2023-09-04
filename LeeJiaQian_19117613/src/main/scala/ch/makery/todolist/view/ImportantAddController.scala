//reference from addressapp practical by Dr Chin
package ch.makery.todolist.view
import ch.makery.todolist.model.ImportantTask
import scalafx.scene.control.{Alert, ChoiceBox, DatePicker, TextField}
import scalafxml.core.macros.sfxml
import scalafx.stage.Stage
import scalafx.event.ActionEvent
import scalafx.collections.ObservableBuffer

@sfxml
class ImportantAddController (
                           private val taskField : TextField,
                           private val dueDate : DatePicker,
                           private val progressChoice : ChoiceBox[String]
                         ) {

  val progressOption = new ObservableBuffer[String]()
  progressOption += ("Finish", "In Progress", "To Do")
  progressChoice.items = progressOption
  var displayStage : Stage = null
  private var _task : ImportantTask = null
  var ok = false

  def task = _task
  def task_=(t : ImportantTask): Unit ={
    _task = t

    taskField.text = _task.task.value
    dueDate.value = _task.due.value
    progressChoice.value = _task.progress.value

  }

  def handleOk(action :ActionEvent){
    _task.task <== taskField.text
    _task.due <== dueDate.value
    _task.progress <== progressChoice.value

    ok = true;
    displayStage.close()
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
    if (nullChecking(progressChoice.value.value))
      errorMessage += "No valid date selected!\n"

    if (errorMessage.length() == 0) {
      return true;
    } else {
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
