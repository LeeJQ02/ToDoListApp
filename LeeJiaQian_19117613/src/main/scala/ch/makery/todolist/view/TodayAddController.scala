//reference from addressapp practical by Dr Chin
package ch.makery.todolist.view
import ch.makery.todolist.model.TodayTask
import scalafx.scene.control.{Alert, ChoiceBox,  TextField}
import scalafxml.core.macros.sfxml
import scalafx.stage.Stage
import scalafx.event.ActionEvent
import scalafx.collections.ObservableBuffer

@sfxml
class TodayAddController (
  private val taskField : TextField,
  private val progressChoice : ChoiceBox[String]
                         ) {
  //choice box reference:
  //https://appdoc.app/artifact/org.scalafx/scalafx_2.9.3/2.2.60-R9/scalafx/scene/control/ChoiceBox.html
  //https://stackoverflow.com/questions/61520879/how-to-set-the-items-in-a-choicebox
  val progressOption = new ObservableBuffer[String]()
  progressOption += ("Finish", "In Progress", "To Do")
  progressChoice.items = progressOption
  var displayStage : Stage = null
  private var _task : TodayTask = null
  var ok = false

  def task = _task
  def task_=(t : TodayTask): Unit ={
    _task = t

    taskField.text = _task.task.value
    progressChoice.value = _task.progress.value

  }

  // function to record and display in table view
  def handleOk(action :ActionEvent){
    if (isInputValid()) {
      _task.task <== taskField.text
      _task.progress <== progressChoice.value

      ok = true;
      displayStage.close()
    }
  }

  // function to cancel
  def handleCancel(action :ActionEvent) {
    displayStage.close();
  }

  // function to check null value
  def nullChecking (x : String) = x == null || x.length == 0

  // function to check valid input
  def isInputValid() : Boolean = {
    var errorMessage = ""

    if (nullChecking(taskField.text.value))
      errorMessage += "No valid task!\n"
    else if (nullChecking(progressChoice.value.value))
      errorMessage += "No valid progress status!\n"

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
