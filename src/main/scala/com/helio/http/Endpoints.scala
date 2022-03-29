package com.helio.http

import cats.effect._
import com.helio.models.Mappings
import com.helio.persistence.StoreRepository
import io.circe.syntax.EncoderOps
import org.http4s._
import org.http4s.dsl.io._
import io.circe.generic.auto._
import org.http4s.circe.CirceEntityEncoder._
import org.log4s.{Logger, getLogger}

import java.time.LocalDateTime

object Endpoints {
  private val log: Logger = getLogger

  object ZipCodeMatcher extends QueryParamDecoderMatcher[String]("zip_code")

  // Simple Example Endpoint
  // This is accessible with:
  // http://localhost:8080/test/hello/<string here>
  val testService: HttpRoutes[IO] = HttpRoutes.of[IO] {
    case GET -> Root / "hello" / name =>
      val json = ExampleObject(s"Hello, $name.", LocalDateTime.now()).asJson
      Ok(json)
  }

  /*
  This is the starting endpoint you will want to replicate to be backed by Postgres
  Here we're using the local stores file to show what an endpoint would likely return.
  This is the same/similar endpoint in the stores-locator project:

  app.get("/api/stores", (req, res) => {
    const zipCode = req.query.zip_code;
    geocoder...

   You'll want replicate what that does here using Postgres!
   */
  val apiService: HttpRoutes[IO] = HttpRoutes.of[IO] {
    case GET -> Root / "stores" :? ZipCodeMatcher(zipCode) =>
      log.info(s"api/stores handling zip: ${zipCode}")
      // Example of Geocoder Service
      val mqResult = MapQuestService.geocode(zipCode).map { res =>
        log.info("Zipcode encode")
        log.info(res.toString)
        res
      }

      mqResult match {
        case Some(response) =>
          val location = response.results.head.locations.head.latLng.get
          val stores = StoreRepository.findByCoordinates(location.lat, location.lng, 10000)
          val dtos = stores.map(s => Mappings.ConvertTo(s))
          Ok(dtos.asJson)
        case None =>
          NotFound()
      }
  }

}

case class ExampleObject(phrase: String, date: LocalDateTime)
