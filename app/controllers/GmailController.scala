package controllers

import javax.inject.{Inject, Singleton}
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import services.GmailService

@Singleton
class GmailController @Inject()(cc: ControllerComponents,
                                gmailService: GmailService) extends AbstractController(cc) {

  def openMessages: Action[AnyContent] = Action {
    val result = gmailService.openMessages.map(_.toString)
    Ok(result.mkString("\n"))
  }
}
