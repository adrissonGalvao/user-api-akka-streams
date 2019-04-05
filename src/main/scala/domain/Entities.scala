package domain

import java.time.LocalDate

case class CreateUser(name: String, username: String, birthDate: LocalDate)

case class User(id: Long, name: String, username: String, birthDate: LocalDate)
