package repository

import javax.inject.Inject
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import repository.MessageRepository.Message
import repository.MessageTableMapping.MessageTable
import slick.jdbc.JdbcProfile

class MessageRepository @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends
  HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._

  private val message = TableQuery[MessageTable]
}

object MessageRepository {

  case class Message(id: Long, guid: String, text: String, _date: Long)
}

object MessageTableMapping extends JdbcProfile {

  import api._

  class MessageTable(tag: Tag) extends Table[Message](tag, "message") {

    def id: Rep[Long] = column[Long]("ROWID", O.PrimaryKey)
    def guid: Rep[String] = column[String]("guid")
    def text: Rep[String] = column[String]("text")
    def _date: Rep[Long] = column[Long]("date")

    def * =
      (id, guid, text, _date) <> ((Message.apply _).tupled, Message.unapply)
  }
}