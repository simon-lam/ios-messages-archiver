package repository

import javax.inject.Inject
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import repository.ChatHandleJoinRepository.ChatHandleJoin
import repository.ChatHandleJoinTableMapping.ChatHandleJoinTable
import slick.jdbc.JdbcProfile

class ChatHandleJoinRepository @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends
  HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._

  private val chatHandleJoin = TableQuery[ChatHandleJoinTable]
}

object ChatHandleJoinRepository {

  case class ChatHandleJoin(chatId: Long, handleId: Long)
}

object ChatHandleJoinTableMapping extends JdbcProfile {

  import api._

  class ChatHandleJoinTable(tag: Tag) extends Table[ChatHandleJoin](tag, "chat_handle_join") {

    def chatId: Rep[Long] = column[Long]("chat_id")
    def handleId: Rep[Long] = column[Long]("handle_id")

    def * =
      (chatId, handleId) <> ((ChatHandleJoin.apply _).tupled, ChatHandleJoin.unapply)
  }
}