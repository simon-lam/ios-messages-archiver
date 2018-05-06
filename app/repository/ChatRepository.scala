package repository

import javax.inject.Inject
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import repository.ChatRepository.Chat
import repository.ChatTableMapping.ChatTable
import slick.jdbc.JdbcProfile

class ChatRepository @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends
  HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._

  private val chat = TableQuery[ChatTable]
}

object ChatRepository {

  case class Chat(id: Long, guid: String)
}

object ChatTableMapping extends JdbcProfile {

  import api._

  class ChatTable(tag: Tag) extends Table[Chat](tag, "chat") {

    def id: Rep[Long] = column[Long]("ROWID", O.PrimaryKey)
    def guid: Rep[String] = column[String]("guid")

    def * =
      (id, guid) <> ((Chat.apply _).tupled, Chat.unapply)
  }
}