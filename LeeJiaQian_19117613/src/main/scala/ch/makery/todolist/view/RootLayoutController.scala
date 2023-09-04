//reference from addressapp practical by Dr Chin
package ch.makery.todolist.view
import ch.makery.todolist.Main
import scalafxml.core.macros.sfxml
import scalafx.event.ActionEvent
import scalafx.stage.Stage

@sfxml
class RootLayoutController() {
  var displayStage : Stage = null

  // function to display today
  def handleToday(actionEvent: ActionEvent) ={
    Main.showToday()
  }

  // function to display upcoming
  def handleUpcoming(actionEvent: ActionEvent)={
    Main.showUpcoming()
  }

  // function to display important
  def handleImportant(actionEvent: ActionEvent)={
    Main.showImportant()
  }

  // function to display about
  def handleAbout(actionEvent: ActionEvent)={
    Main.showAbout()
  }

//  system exit scala reference
//  https://www.scala-lang.org/api/2.13.x/scala/sys/index.html#exit():Nothing
  def handleExit(actionEvent: ActionEvent)={
    sys.exit()
  }

  // function to display login
  def handleLogout(actionEvent: ActionEvent)={
    Main.showLogin()
  }

}
