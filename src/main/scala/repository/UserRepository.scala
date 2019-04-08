package repository

import domain.{CreateUser, User}

import scala.concurrent.Future

trait UserRepository {

  def findById(id: Long): Future[Option[User]]

  def insert(createUser: CreateUser): Future[User]

  def list(): Future[List[User]]

}

class UserRepositoryImpl extends UserRepository {

  import io.getquill._

  lazy val ctx = new PostgresJdbcContext(SnakeCase, "ctx")

  import ctx._

  def findById(id: Long): Future[Option[User]] = Future {

    val q = quote {
      querySchema[User]("user_db.tb_user").filter(_.id == lift(id))
    }

    ctx.run(q).headOption

  }

  def insert(createUser: CreateUser): Future[User] = Future {

    val q = quote {
      querySchema[User]("user_db.tb_user")
        .insert(lift(
          User(-1, createUser.name, createUser.username, createUser.birthDate)))
        .returning(_.id)
    }

    User(ctx.run(q).toInt,
         createUser.name,
         createUser.username,
         createUser.birthDate)

  }

  def list(): Future[List[User]] = Future {

    val q = quote {
      querySchema[User]("user_db.tb_user")
    }

    ctx.run(q)

  }
}
