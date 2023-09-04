//reference from addressapp practical by Dr Chin
package ch.makery.todolist
import ch.makery.todolist.model.{ImportantTask, TodayTask, UpcomingTask}
import ch.makery.todolist.view.{ImportantAddController, TodayAddController, UpcomingAddController, AboutController}
import ch.makery.todolist.util.Database
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.Includes._
import scalafxml.core.{FXMLLoader, NoDependencyResolver}
import javafx.{scene => jfxs}
import scalafx.collections.ObservableBuffer
import scalafx.scene.image.Image
import scalafx.stage.{Modality, Stage}

object Main extends JFXApp {
  //initialize database
  Database.setupDB()
  // transform path of RootLayout.fxml to URL for resource location.
  val rootResource = getClass.getResource("view/RootLayout.fxml")

  // The data as an observable list of TodayTask
  val todayTaskData =  new ObservableBuffer[TodayTask]()
  //assign all TodayTask into todayTaskData array
  todayTaskData ++= TodayTask.getAllTodayTasks

  val upcomingTaskData = new ObservableBuffer[UpcomingTask]()
  upcomingTaskData ++= UpcomingTask.getAllUpcomingTasks

  val importantTaskData = new ObservableBuffer[ImportantTask]()
  importantTaskData ++= ImportantTask.getAllImportantTasks

  //todayTaskData += new TodayTask("Buy New Mouse", "In Progress")
  //upcomingTaskData += new UpcomingTask("Buy New Mouse")

  // initialize the loader object.
  val loader = new FXMLLoader(rootResource, NoDependencyResolver)
  // Load root layout from fxml file.
  loader.load();

  // retrieve the root component BorderPane from the FXML
  val roots = loader.getRoot[jfxs.layout.BorderPane]

  val cssResource = getClass.getResource("view/Theme.css")
  roots.stylesheets = List(cssResource.toExternalForm)

  // initialize stage
  stage = new PrimaryStage {
    title = "To Do List"
    icons += new Image("file:resources/images/checkbox.png")
    scene = new Scene {
      root = roots
    }
  }

  // actions for display login page
  def showLogin() = {
    val resource = getClass.getResource("view/Login.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load();
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots)
  }
  // call to display Login when app start
  showLogin()

  def showToday() = {
    val resource = getClass.getResource("view/Today.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load();
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots)
  }

  def showUpcoming() = {
    val resource = getClass.getResource("view/Upcoming.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load();
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots)
  }

  def showImportant() = {
    val resource = getClass.getResource("view/Important.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load();
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots)
  }


  def showTodayAdd(task: TodayTask): Boolean = {
    val resource = getClass.getResourceAsStream("view/TodayAdd.fxml")
    val loader = new FXMLLoader(null, NoDependencyResolver)
    loader.load(resource);
    val roots2  = loader.getRoot[jfxs.Parent]
    val control = loader.getController[TodayAddController#Controller]

    roots2.stylesheets = List(cssResource.toExternalForm)
    val display = new Stage() {
      initModality(Modality.APPLICATION_MODAL)
      initOwner(stage)
      icons += new Image("file:resources/images/list.png")
      title = "Add New Task"
      scene = new Scene {
        root = roots2
      }
    }
    control.displayStage = display
    control.task = task
    display.showAndWait()
    control.ok
  }

  def showUpcomingAdd(task: UpcomingTask): Boolean = {
    val resource = getClass.getResourceAsStream("view/UpcomingAdd.fxml")
    val loader = new FXMLLoader(null, NoDependencyResolver)
    loader.load(resource);
    val roots2  = loader.getRoot[jfxs.Parent]
    val control = loader.getController[UpcomingAddController#Controller]

    roots2.stylesheets = List(cssResource.toExternalForm)
    val display = new Stage() {
      initModality(Modality.APPLICATION_MODAL)
      initOwner(stage)
      icons += new Image("file:resources/images/list.png")
      title = "Add New Task"
      scene = new Scene {
        root = roots2
      }
    }
    control.displayStage = display
    control.task = task
    display.showAndWait()
    control.ok
  }

  def showImportantAdd(task: ImportantTask): Boolean = {
    val resource = getClass.getResourceAsStream("view/ImportantAdd.fxml")
    val loader = new FXMLLoader(null, NoDependencyResolver)
    loader.load(resource);
    val roots2  = loader.getRoot[jfxs.Parent]
    val control = loader.getController[ImportantAddController#Controller]

    roots2.stylesheets = List(cssResource.toExternalForm)
    val display = new Stage() {
      initModality(Modality.APPLICATION_MODAL)
      initOwner(stage)
      icons += new Image("file:resources/images/list.png")
      title = "Add New Task"
      scene = new Scene {
        root = roots2
      }
    }
    control.displayStage = display
    control.task = task
    display.showAndWait()
    control.ok
  }

  def showAbout() = {
    val resource = getClass.getResourceAsStream("view/About.fxml")
    val loader = new FXMLLoader(null, NoDependencyResolver)
    loader.load(resource);
    val roots2  = loader.getRoot[jfxs.Parent]
    val control = loader.getController[AboutController#Controller]

    roots2.stylesheets = List(cssResource.toExternalForm)
    val display = new Stage() {
      initModality(Modality.APPLICATION_MODAL)
      initOwner(stage)
      icons += new Image("file:resources/images/about.png")
      title = "About ToDo List"
      scene = new Scene {
        root = roots2
      }
    }
    control.displayStage = display
    display.showAndWait()
    control.ok
  }

}

