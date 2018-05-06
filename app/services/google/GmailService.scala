package services.google

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.gmail.Gmail
import com.google.api.services.gmail.model.Message
import javax.inject.Inject
import org.slf4j.LoggerFactory
import services.google.GmailService.MissingGoogleCredentials

import scala.collection.JavaConverters._

class GmailService @Inject()(flow: GoogleAuthorizationCodeFlow) {

  private val log = LoggerFactory.getLogger(this.getClass)

  private val defaultUser = "me"

  private def gmail: Gmail = {
    import com.google.api.services.gmail.Gmail
    val HTTP_TRANSPORT = new NetHttpTransport()
    val JSON_FACTORY = JacksonFactory.getDefaultInstance()
    val credential = Option(flow.loadCredential(defaultUser)) match {
      case Some(c) => c
      case None => throw MissingGoogleCredentials("No credentials loaded.")
    }
    new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
      .setApplicationName("iOS Messages Archiver")
      .build
  }

  def openMessages: List[Message] = {
    gmail.users().messages().list(defaultUser) //.setLabelIds(labelIds)
      .setQ("is:unread label:inbox").setMaxResults(3L).execute().getMessages.asScala.toList
  }
}

object GmailService {

  case class MissingGoogleCredentials(msg: String) extends Exception(msg)
}