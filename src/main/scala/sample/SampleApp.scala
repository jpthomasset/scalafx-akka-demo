package sample

import akka.actor.{Actor, Props, ActorSystem}
import sample.SampleActor.{TickMe, TickCount}

import scalafx.application.{Platform, JFXApp}
import scalafx.application.JFXApp.PrimaryStage
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.control.Label
import scalafx.scene.layout.BorderPane

/**
 * Sample app entry point
 */
object SampleApp extends JFXApp {

  // Create actor context
  implicit val system = ActorSystem("sample-system")

  // Create Main actor
  val ticker = system.actorOf(Props(new SampleActor))

  // Define UI Actor
  class UIActor extends Actor {
    // Request ticker to send TickCount
    ticker ! TickMe

    /**
     * UIActor message processing
     * @return
     */
    def receive = {
      /**
       * The important part here is the 'Platform.runLater' which ensures
       * we are updating the UI from the UI Thread !
       */
      case TickCount(tick) => Platform.runLater {
        label.text = "Tick " + tick
      }
    }
  }

  // Create UI Actor
  val uiactor = system.actorOf(Props(new UIActor))

  // Set UI
  val label = new Label("Loading")

  stage = new PrimaryStage {
    scene = new Scene {
      root = new BorderPane {
        padding = Insets(25)
        center = label
      }
    }
  }
}
