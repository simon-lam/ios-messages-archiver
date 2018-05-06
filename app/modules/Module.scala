package modules

import java.io.FileReader

import com.google.api.client.googleapis.auth.oauth2.{GoogleAuthorizationCodeFlow, GoogleClientSecrets}
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.client.util.store.{FileDataStoreFactory, MemoryDataStoreFactory}
import com.google.api.services.gmail.GmailScopes
import com.google.inject.AbstractModule

import scala.collection.JavaConverters._
class Module extends AbstractModule {

  override def configure(): Unit = {
    bind(classOf[GoogleAuthorizationCodeFlow]).toInstance({
      val clientSecretFile = "/Users/simonlam/Desktop/client_secret_368401229586-vrsfbrfsr8k4u4ac5mu56h6d0a8l9hlp.apps.googleusercontent.com.json"
      val clientSecrets = GoogleClientSecrets.load(JacksonFactory.getDefaultInstance(), new FileReader(clientSecretFile))

      new GoogleAuthorizationCodeFlow.Builder(new NetHttpTransport(),
        JacksonFactory.getDefaultInstance(), clientSecrets, List(GmailScopes.MAIL_GOOGLE_COM).asJava)
        .setDataStoreFactory(MemoryDataStoreFactory.getDefaultInstance)
//        .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(clientSecretFile)))
        .setAccessType("offline")
        .build()
    })
  }
}
