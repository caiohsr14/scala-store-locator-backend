package com.helio.integration

import com.helio.models._
import com.helio.persistence.StoreRepository
import io.circe.generic.auto._
import io.circe.parser._
import org.log4s.{Logger, getLogger}

import scala.io.Source

object StoreImporter {
  private val log: Logger = getLogger

  def loadSampleStores: List[StoreDTO] = {
    val url = this.getClass.getResource("/stores.json")
    val ds = Source.fromURL(url)
    val data = ds.mkString
    ds.close()
    val stores = parse(data).toOption.flatMap(_.as[List[StoreDTO]].toOption)
    stores.getOrElse(List())
  }

  def main(args: Array[String]): Unit = {
    log.info("Importing stores from json into database")
    val dtos = loadSampleStores
    val stores = dtos.map(Mappings.ConvertFrom)
    log.info(s"${stores.length} stores found")

    StoreRepository.batch(stores)
    log.info("Stores successfully imported")
  }
}
