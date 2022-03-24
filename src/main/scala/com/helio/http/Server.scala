package com.helio.http

import cats.data.Kleisli
import cats.effect._
import com.comcast.ip4s._
import org.http4s.{Request, Response}
import org.http4s.ember.server._
import org.http4s.implicits._
import org.http4s.server.Router
import org.log4s.{getLogger, Logger}


object Server extends IOApp {

  private val log: Logger = getLogger

  def run(args: List[String]): IO[ExitCode] = {
    val httpApp: Kleisli[IO, Request[IO], Response[IO]] = Router(
      "/test" -> Endpoints.helloWorldService
    ).orNotFound

    log.info("Starting HTTP Server")
    EmberServerBuilder
      .default[IO]
      .withHost(ipv4"0.0.0.0")
      .withPort(port"8080")
      .withHttpApp(httpApp)
      .build
      .use(_ => IO.never)
      .as(ExitCode.Success)
  }
}
