package models

import java.time.LocalDateTime

case class Message(from: String,
                   to: String,
                   text: Option[String],
                   date: LocalDateTime)
