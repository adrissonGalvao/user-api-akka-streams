package main

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.HttpApp
import de.heikoseeberger.akkahttpcirce.ErrorAccumulatingCirceSupport
import domain.CreateUser
import io.circe.generic.auto._
import repository.{UserRepository, UserRepositoryImpl}

object Application extends HttpApp with App with ErrorAccumulatingCirceSupport {

  val userRepository: UserRepository = new UserRepositoryImpl()

  override final protected def routes =
    pathPrefix("users") {
      post {
        entity(as[CreateUser])(createUser =>
          complete(userRepository.insert(createUser)))
      } ~
        get {
          path(LongNumber) { id =>
            userRepository.findById(id) match {
              case Some(user) => complete(user)
              case _          => complete(StatusCodes.NotFound)
            }
          }
        }
    }

  startServer("localhost", 9090)

}
