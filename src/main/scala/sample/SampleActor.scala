package sample

import akka.actor.{ActorRef, Actor}
import scala.concurrent.duration._
import SampleActor._

/**
 * SampleActor Companion object
 */
object SampleActor {

  /**
   * Message indicating the current tick count
   * @param t the current tick count
   */
  case class TickCount(t:Int)

  /**
   * Message to request periodic TickCount. The sender
   * will be notified by a TickCount message every second
   */
  case class TickMe()
}

/**
 * Sample Actor which sends TickCount event every seconds
 * when it receives a TickMe event
 */
class SampleActor extends Actor {
  import context._

  // Internal message used by scheduler
  case class Tick()

  // Curent tick count
  var tickCount = 0

  // Start receiving as Idle
  def receive = idle

  /**
   * Idle state receiver
   * @return
   */
  def idle: Actor.Receive = {
    case TickMe =>
      become(active(sender))
      context.system.scheduler.schedule(1.seconds, 1.seconds, self, Tick)
  }

  /**
   * Active State receiver
   * @param listener the actor to be notified when ticking
   * @return
   */
  def active(listener:ActorRef): Actor.Receive = {
    case Tick =>
      tickCount += 1
      listener ! TickCount(tickCount)
  }

}
