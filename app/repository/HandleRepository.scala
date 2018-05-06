package repository

import javax.inject.Inject
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import repository.HandleRepository.Handle
import repository.HandleTableMapping.HandleTable
import slick.jdbc.JdbcProfile

class HandleRepository @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends
  HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._

  private val handle = TableQuery[HandleTable]
}

object HandleRepository {

  case class Handle(id: Long, handleId: String, service: String)
}

object HandleTableMapping extends JdbcProfile {

  import api._

  class HandleTable(tag: Tag) extends Table[Handle](tag, "handle") {

    def id: Rep[Long] = column[Long]("ROWID", O.PrimaryKey)
    def handleId: Rep[String] = column[String]("id")
    def service: Rep[String] = column[String]("service")

    def * =
      (id, handleId, service) <> ((Handle.apply _).tupled, Handle.unapply)
  }
}