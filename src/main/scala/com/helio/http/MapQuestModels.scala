package com.helio.http

case class Info(statuscode: Int)

case class LatLng(lat: Double,
                  lng: Double)

case class Locations(street: Option[String],
                     adminArea6: Option[String],
                     adminArea6Type: Option[String],
                     adminArea5: Option[String],
                     adminArea5Type: Option[String],
                     adminArea4: Option[String],
                     adminArea4Type: Option[String],
                     adminArea3: Option[String],
                     adminArea3Type: Option[String],
                     adminArea1: Option[String],
                     adminArea1Type: Option[String],
                     postalCode: Option[String],
                     geocodeQualityCode: Option[String],
                     geocodeQuality: Option[String],
                     dragPoint: Option[Boolean],
                     sideOfStreet: Option[String],
                     linkId: Option[String],
                     unknownInput: Option[String],
                     `type`: Option[String],
                     latLng: Option[LatLng],
                     displayLatLng: Option[LatLng],
                     mapUrl: Option[String]
                    )

case class Options(maxResults: Option[Int],
                   thumbMaps: Option[Boolean],
                   ignoreLatLngInput: Option[Boolean])

case class ProvidedLocation(location: Option[String])

case class Results(providedLocation: ProvidedLocation,
                   locations: Seq[Locations])

case class MapQuestResponse(info: Info,
                            options: Options,
                            results: List[Results])

