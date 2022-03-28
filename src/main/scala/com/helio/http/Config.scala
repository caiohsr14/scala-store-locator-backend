package com.helio.http

import com.typesafe.config._

object Config {
  val base = ConfigFactory.load().getConfig("store-locator")
  val env = Option(System.getenv("ENVIRONMENT")).getOrElse("dev")
  val config = if (base.hasPath(env)) base.getConfig(env).withFallback(base) else base

  lazy val MQ_API_KEY: Option[String] = {
    Option(config.getString("mq-api-key"))
  }
}
