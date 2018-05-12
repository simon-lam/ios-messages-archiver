package repositories

import java.time.format.DateTimeFormatter
import java.time.{LocalDateTime, ZoneId, ZoneOffset, ZonedDateTime}

import javax.inject.Inject
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import repositories.ChatHandleJoinRepository.ChatHandleJoin
import repositories.ChatHandleJoinTableMapping.ChatHandleJoinTable
import repositories.ChatMessageJoinRepository.ChatMessageJoin
import repositories.ChatMessageRecordJoinTableMapping.ChatMessageJoinTable
import repositories.ChatTableMapping.ChatTable
import repositories.HandleTableMapping.HandleTable
import repositories.MessageRepository.RawMessage
import repositories.MessageTableMapping.MessageTable
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

    private val macEpoch = LocalDateTime.parse("2001-01-01T00:00:00")

    def date: LocalDateTime = {
      val zdt = macEpoch.plusSeconds(_date).atZone(ZoneId.of("UTC"))
      val dtf = DateTimeFormatter.ofPattern("YYYY-MM-dd'T'HH:mm:ss.SSS").withZone(ZoneId.of("America/Chicago"))
      LocalDateTime.parse(zdt.format(dtf))
    }
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