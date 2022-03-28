package com.helio.http

import cats.effect.IO
import cats.effect.unsafe.implicits.global
import org.http4s.circe.jsonOf
import org.http4s.ember.client._
import org.http4s.client._
import io.circe.generic.auto._
import org.http4s.EntityDecoder
import org.log4s.{Logger, getLogger}

import scala.concurrent.duration._
import java.util.concurrent._

object MapQuestService {

  private val log: Logger = getLogger

  private implicit val mapQuestDecoder: EntityDecoder[IO, MapQuestResponse] = jsonOf[IO, MapQuestResponse]

  EmberClientBuilder.default[IO].build.use { client =>
    // use `client` here and return an `IO`.
    // the client will be acquired and shut down
    // automatically each time the `IO` is run.
    IO.unit
  }

  val blockingPool: ExecutorService = Executors.newFixedThreadPool(5)
  val httpClient: Client[IO] = JavaNetClientBuilder[IO].create

  /*
  Map Quest is pretty optimistic and will often return results on some pretty nonsensical strings, you can decide what
  to do with them
   */
  def geocode(zipCode: String): Option[MapQuestResponse] = {
    Config.MQ_API_KEY match {
      case Some(key)=>
        val helloJames = httpClient.expect[MapQuestResponse](s"https://www.mapquestapi.com/geocoding/v1/address?key=$key&location=${zipCode}")
        helloJames.unsafeRunTimed(5000.seconds)
      case None =>
        log.error("No Map Quest Key Set!")
        None
    }
  }
}

// Sample Run.
object MapQuestTest {
  def main(args: Array[String]): Unit = {
    println(MapQuestService.geocode(zipCode = "01742"))
  }
}
