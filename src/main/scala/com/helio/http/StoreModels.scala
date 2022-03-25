package com.helio.http

import io.circe.generic.auto._
import io.circe.parser._

import scala.io.Source

object StoreModels {

  def loadSampleStores: List[Store] = {
    val url = this.getClass.getResource("/stores.json")
    val ds = Source.fromURL(url)
    val data = ds.mkString
    ds.close()
    val stores = parse(data).toOption.flatMap(_.as[List[Store]].toOption)
    stores.getOrElse(List())
  }

}

case class Address(streetAddressLine1: String,
                   streetAddressLine2: Option[String],
                   streetAddressLine3: Option[String],
                   city: String,
                   countrySubdivisionCode: String,
                   countryCode: String,
                   postalCode: String)

case class Id($oid: String)

case class Location(`type`: String,
                    coordinates: Seq[Double])

case class RootInterface(store: Store)

case class Store(_id: Id,
                 addressLine: List[String],
                 storeName: String,
                 phoneNumber: Option[String],
                 address: Address,
                 openStatusText: String,
                 location: Location)




