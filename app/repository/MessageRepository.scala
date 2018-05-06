package repository

import java.time.LocalDateTime

import javax.inject.Inject
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import repository.ChatHandleJoinRepository.ChatHandleJoin
import repository.ChatHandleJoinTableMapping.ChatHandleJoinTable
import repository.ChatMessageJoinRepository.ChatMessageJoin
import repository.ChatMessageRecordJoinTableMapping.ChatMessageJoinTable
import repository.ChatTableMapping.ChatTable
import repository.HandleTableMapping.HandleTable
import repository.MessageRepository.RawMessage
import repository.MessageTableMapping.MessageTable
import slick.jdbc.JdbcProfile

class MessageRepository @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends
  HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._

  private val message = TableQuery[MessageTable]
  private val chat = TableQuery[ChatTable]
  private val handle = TableQuery[HandleTable]
  private val chatMessageJoin = TableQuery[ChatMessageJoinTable]
  private val chatHandleJoin = TableQuery[ChatHandleJoinTable]

  def streamAll = {
    db.stream(message
      .join(handle)
      .on(_.handleId === _.id)
      .result
      .withStatementParameters(fetchSize = 5000)
      .transactionally)
  }
}

object MessageRepository {

  case class RawMessage(id: Long,
                        guid: String,
                        text: Option[String],
                        _date: Long,
                        isFromMe: Boolean,
                        handleId: Long) {

    def date = LocalDateTime.now()
  }
}

object MessageTableMapping extends JdbcProfile {

  import api._

  class MessageTable(tag: Tag) extends Table[RawMessage](tag, "message") {

    def id: Rep[Long] = column[Long]("ROWID", O.PrimaryKey)
    def guid: Rep[String] = column[String]("guid")
    def text: Rep[Option[String]] = column[Option[String]]("text")
    def _date: Rep[Long] = column[Long]("date")
    def isFromMe: Rep[Boolean] = column[Boolean]("is_from_me")
    def handleId: Rep[Long] = column[Long]("handle_id")

    def * =
      (id, guid, text, _date, isFromMe, handleId) <> ((RawMessage.apply _).tupled, RawMessage.unapply)
  }
}