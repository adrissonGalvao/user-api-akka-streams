package repository

import domain.{CreateUser, User}

trait UserRepository {

  def findById(id: Long): Option[User]

  def insert(createUser: CreateUser): User

}

class UserRepositoryImpl extends UserRepository {

  import io.getquill._

  lazy val ctx = new PostgresJdbcContext(SnakeCase, "ctx")

  import ctx._

  def findById(id: Long): Option[User] = {

    val q = quote {
      query[User].filter(_.id == lift(id))
    }

    ctx.run(q).headOption
  }

  def insert(createUser: CreateUser): User = {

    val q = quote {
      query[User]
        .insert(lift(
          User(-1, createUser.name, createUser.username, createUser.birthDate)))
        .returning(_.id)
    }

    User(ctx.run(q).toInt,
         createUser.name,
         createUser.username,
         createUser.birthDate)

  }
}
