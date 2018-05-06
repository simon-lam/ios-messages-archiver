package controllers

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow
import javax.inject.{Inject, Singleton}
import play.api.libs.json.JsValue
import play.api.mvc.{AbstractController, Action, ControllerComponents}

@Singleton
class OAuthController @Inject()(flow: GoogleAuthorizationCodeFlow,
                                cc: ControllerComponents) extends AbstractController(cc) {

  def exchangeAuthCode(): Action[JsValue] = Action(parse.json) { request =>
    (request.body \ "code").asOpt[String] match {
      case Some(authCode) =>
        credential(authCode)
        Ok("Auth code successfully exchanged for credentials.")

      case None =>
        BadRequest("No auth code found!")
    }
  }

  private def credential(authCode: String) = {
    val tokenResponse = flow.newTokenRequest(authCode).setRedirectUri("http://localhost:9000").execute()
    flow.createAndStoreCredential(tokenResponse, "me")
  }
}