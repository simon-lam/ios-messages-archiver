package repositories

import java.time.{LocalDate, LocalDateTime}

import repositories.MessageRepository.RawMessage
import utils.UnitSpec

class RawMessageSpec extends UnitSpec {

  it should "covert MacTime date to LocalDateTime" in {
    val msg = RawMessage(
      id = 1L,
      guid = uuid,
      text = Some("Hello tuna!"),
      _date = 449808575,
      isFromMe = true,
      handleId = 1L)

    msg.date shouldBe LocalDateTime.parse("2015-04-03T21:49:35")
  }
}
