//reference from addressapp practical by Dr Chin
package ch.makery.todolist.view
import scalafx.stage.Stage
import scalafx.event.ActionEvent
import scalafxml.core.macros.sfxml


@sfxml
class AboutController {
  var displayStage: Stage = null
  var ok: Boolean = false

//  function used for the stage to close after user click ok
  def handleOk(actionEvent: ActionEvent): Unit ={
    displayStage.close()
  }

}
