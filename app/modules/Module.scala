package modules

import java.io.FileReader

import com.google.api.client.googleapis.auth.oauth2.{GoogleAuthorizationCodeFlow, GoogleClientSecrets}
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.client.util.store.MemoryDataStoreFactory
import com.google.api.services.gmail.GmailScopes
import com.google.inject.AbstractModule
import com.typesafe.config.Config

import scala.collection.JavaConverters._

class Module(config: Config) extends AbstractModule {

  override def configure(): Unit = {
    bind(classOf[GoogleAuthorizationCodeFlow]).toInstance({
      val clientSecretFile = config.getString("google.clientSecretFile")
      val clientSecrets = GoogleClientSecrets.load(JacksonFactory.getDefaultInstance(), new FileReader(clientSecretFile))

      new GoogleAuthorizationCodeFlow.Builder(new NetHttpTransport(),
        JacksonFactory.getDefaultInstance(), clientSecrets, List(GmailScopes.MAIL_GOOGLE_COM, GmailScopes.GMAIL_MODIFY).asJava)
        .setDataStoreFactory(MemoryDataStoreFactory.getDefaultInstance)
//        .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(clientSecretFile)))
        .setAccessType("offline")
        .build()
    })
  }
}
