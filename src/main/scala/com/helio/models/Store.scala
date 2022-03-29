package com.helio.models

object Mappings {
  implicit def ConvertFrom(dto: StoreDTO): Store =
    Store(
      addressLines = Option(dto.addressLine.mkString("\n")),
      storeName = dto.storeName,
      phoneNumber = dto.phoneNumber,
      streetAddressLine1 = dto.address.streetAddressLine1,
      streetAddressLine2 = dto.address.streetAddressLine2,
      streetAddressLine3 = dto.address.streetAddressLine3,
      city = dto.address.city,
      countrySubdivisionCode = dto.address.countrySubdivisionCode,
      countryCode = dto.address.countryCode,
      postalCode = dto.address.postalCode,
      openStatusText = dto.openStatusText,
      locationType = dto.location.`type`,
      longitude = dto.location.coordinates.head,
      latitude = dto.location.coordinates(1)
    )

  implicit def ConvertTo(store: Store): StoreDTO =
    StoreDTO(
      addressLine = store.addressLines.getOrElse("").split("\n").toList,
      storeName = store.storeName,
      phoneNumber = store.phoneNumber,
      address = AddressDTO(
        streetAddressLine1 = store.streetAddressLine1,
        streetAddressLine2 = store.streetAddressLine2,
        streetAddressLine3 = store.streetAddressLine3,
        city = store.city,
        countrySubdivisionCode = store.countrySubdivisionCode,
        countryCode = store.countryCode,
        postalCode = store.postalCode
      ),
      openStatusText = store.openStatusText,
      location = LocationDTO(
        `type` = store.locationType,
        coordinates = List(store.longitude, store.latitude)
      )
    )
}

case class Store(
    id: Int = 0,
    addressLines: Option[String],
    storeName: String,
    phoneNumber: Option[String],
    streetAddressLine1: String,
    streetAddressLine2: Option[String],
    streetAddressLine3: Option[String],
    city: String,
    countrySubdivisionCode: String,
    countryCode: String,
    postalCode: String,
    openStatusText: String,
    locationType: String,
    latitude: Double,
    longitude: Double
)

case class AddressDTO(
    streetAddressLine1: String,
    streetAddressLine2: Option[String],
    streetAddressLine3: Option[String],
    city: String,
    countrySubdivisionCode: String,
    countryCode: String,
    postalCode: String
)

case class LocationDTO(`type`: String, coordinates: Seq[Double])

case class StoreDTO(
    addressLine: List[String],
    storeName: String,
    phoneNumber: Option[String],
    address: AddressDTO,
    openStatusText: String,
    location: LocationDTO
)
