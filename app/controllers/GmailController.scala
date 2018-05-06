package controllers

import javax.inject.{Inject, Singleton}
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import services.MessageService
import services.google.GmailService

import scala.concurrent.ExecutionContext

@Singleton
class GmailController @Inject()(cc: ControllerComponents,
                                messageService: MessageService,
                                gmailService: GmailService)
                               (implicit ec: ExecutionContext) extends AbstractController(cc) {

//  def openMessages: Action[AnyContent] = Action {
//    val result = gmailService.openMessages.map(_.toString)
//    Ok(result.mkString("\n"))
//  }
  def openMessages: Action[AnyContent] = Action.async({
    messageService.all.map(_ => Ok("abcd"))
  })
}
