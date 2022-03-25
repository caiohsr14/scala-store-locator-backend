package com.helio.http

import java.io.FileInputStream
import java.util.Properties

object Properties {

  private def load(): Properties = {
    val path: String = Thread.currentThread.getContextClassLoader.getResource("app.properties").getPath
    val appProps = new Properties()
    appProps.load(new FileInputStream(path))
    appProps
  }

  lazy val MQ_API_KEY: Option[String] = {
    val props = load()
    Option(props.getProperty("MQ_API_KEY"))
  }

}
