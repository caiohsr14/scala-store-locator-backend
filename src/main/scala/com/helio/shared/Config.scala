package com.helio.shared

import com.comcast.ip4s.Port
import com.typesafe.config._

object Config {
  val base = ConfigFactory.load()
  val env = Option(System.getenv("ENVIRONMENT")).getOrElse("dev")
  val config =
    if (base.hasPath(env)) base.getConfig(env).withFallback(base) else base

  lazy val MQ_API_KEY: Option[String] = {
    Option(config.getString("mq-api-key"))
  }

  lazy val SERVER_PORT: Port = {
    Port.fromInt(config.getInt("server-port")).get
  }
}
