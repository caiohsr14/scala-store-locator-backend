package com.helio.http

import cats.effect.IO
import cats.effect.unsafe.implicits.global
import com.helio.models.MapQuestResponse
import com.helio.shared.Config
import io.circe.generic.auto._
import org.http4s.EntityDecoder
import org.http4s.circe.jsonOf
import org.http4s.client._
import org.http4s.ember.client._
import org.log4s.{Logger, getLogger}

import java.util.concurrent._
import scala.concurrent.duration._

object MapQuestService {

  private val log: Logger = getLogger

  private implicit val mapQuestDecoder: EntityDecoder[IO, MapQuestResponse] =
    jsonOf[IO, MapQuestResponse]

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
      case Some(key) =>
        val helloJames = httpClient.expect[MapQuestResponse](
          s"https://www.mapquestapi.com/geocoding/v1/address?key=$key&location=${zipCode}"
        )
        helloJames.unsafeRunTimed(5000.seconds)
      case None =>
        log.error("No Map Quest Key Set!")
        None
    }
  }
}
