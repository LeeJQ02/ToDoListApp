//reference from addressapp practical by Dr Chin
package ch.makery.todolist.view
import ch.makery.todolist.Main
import scalafx.scene.control.{Alert, PasswordField, TextField}
import scalafxml.core.macros.sfxml
import scalafx.stage.Stage
import scalafx.event.ActionEvent

@sfxml
class LoginController (
                           private val usernameField : TextField,
                           private val passwordField : PasswordField
                         ) {
  var displayStage : Stage = null
  val username = "admin"
  val password = "admin"
  var login = false

  def handleLogin(action :ActionEvent){
    if (isInputValid()) {
      login = true;
      Main.showToday()
    }
  }

  def nullChecking (x : String) = x == null || x.length == 0

  def isInputValid() : Boolean = {
    var errorMessage = ""

    if (nullChecking(usernameField.text.value))
      errorMessage += "Please enter username!\n"
    else if (nullChecking(passwordField.text.value))
      errorMessage += "Please enter password!\n"
    else if (!usernameField.text.value.equals(username) || !passwordField.text.value.equals(password))
      errorMessage += "Wrong username or password! Please try again!\n"

    if (errorMessage.length() == 0) {
      return true;
    } else {
      val alert = new Alert(Alert.AlertType.Error){
        initOwner(displayStage)
        title = "Invalid Username or Password"
        headerText = "Wrong username or password! Please try again!"
        contentText = errorMessage
      }.showAndWait()
      return false;
    }
  }
}
