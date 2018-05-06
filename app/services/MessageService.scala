package services

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Source
import javax.inject.Inject
import models.Message
import org.slf4j.LoggerFactory
import repository.MessageRepository

class MessageService @Inject()(messageRepository: MessageRepository)
                              (implicit actorSystem: ActorSystem) {

  private val log = LoggerFactory.getLogger(this.getClass)

  private implicit val mat = ActorMaterializer()

  def all = {
    Source.fromPublisher(messageRepository.streamAll)
      .map({
        case (m, h) =>
          Message(
            from = if (m.isFromMe) "me" else h.handleId,
            to = if (m.isFromMe) h.handleId else "me",
            text = m.text,
            date = m.date)
      })
      .runForeach(m => {
        log.info(m.toString)
      })
  }
}
