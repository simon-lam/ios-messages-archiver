package repository

import javax.inject.Inject
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import repository.ChatMessageJoinRepository.ChatMessageJoin
import repository.ChatMessageRecordJoinTableMapping.ChatMessageJoinTable
import slick.jdbc.JdbcProfile

class ChatMessageJoinRepository @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends
  HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._

  private val chatMessageJoin = TableQuery[ChatMessageJoinTable]
}

object ChatMessageJoinRepository {

  case class ChatMessageJoin(chatId: Long, messageId: Long)
}

object ChatMessageRecordJoinTableMapping extends JdbcProfile {

  import api._

  class ChatMessageJoinTable(tag: Tag) extends Table[ChatMessageJoin](tag, "chat_message_join") {

    def chatId: Rep[Long] = column[Long]("chat_id")
    def messageId: Rep[Long] = column[Long]("message_id")

    def * =
      (chatId, messageId) <> ((ChatMessageJoin.apply _).tupled, ChatMessageJoin.unapply)
  }
}