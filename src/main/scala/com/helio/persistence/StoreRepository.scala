package com.helio.persistence

import com.helio.models.Store
import scalikejdbc._
import scalikejdbc.config._

object StoreRepository {
  val dbName = "store-locator"
  val env = Option(System.getenv("ENVIRONMENT")).getOrElse("dev")
  DBsWithEnv(env).setup(dbName)

  val toStore = (rs: WrappedResultSet) => {
    Store(
      rs.int("id"),
      rs.stringOpt("address_lines"),
      rs.string("store_name"),
      rs.stringOpt("phone_number"),
      rs.string("street_address_line_1"),
      rs.stringOpt("street_address_line_2"),
      rs.stringOpt("street_address_line_3"),
      rs.string("city"),
      rs.string("country_subdivision_code"),
      rs.string("country_code"),
      rs.string("postal_code"),
      rs.string("open_status_text"),
      rs.string("location_type"),
      rs.double("latitude"),
      rs.double("longitude")
    )
  }

  def findByCoordinates(
      latitude: Double,
      longitude: Double,
      radius: Int
  ): List[Store] = {
    NamedDB(dbName) readOnly { implicit session =>
      sql"""select *, ST_Distance(geography_point, ST_MakePoint(${longitude}, ${latitude})) as distance
        from store
        where ST_DWithin(ST_MakePoint(${longitude}, ${latitude}), geography_point, ${radius})
        order by distance ASC""".map(toStore).list.apply()
    }
  }

  def batch(stores: Seq[Store]) = {
    val batchData: Seq[Seq[(String, Any)]] = stores.map(s =>
      (s.productElementNames zip s.productIterator).toList.drop(1)
    )

    NamedDB(dbName) localTx { implicit session =>
      sql"""insert into store (address_lines,
        store_name,
        phone_number,
        street_address_line_1,
        street_address_line_2,
        street_address_line_3,
        city,
        country_subdivision_code,
        country_code,
        postal_code,
        open_status_text,
        location_type,
        latitude,
        longitude)

      values ({addressLines},
        {storeName},
        {phoneNumber},
        {streetAddressLine1},
        {streetAddressLine2},
        {streetAddressLine3},
        {city},
        {countrySubdivisionCode},
        {countryCode},
        {postalCode},
        {openStatusText},
        {locationType},
        {latitude},
        {longitude})""".batchByName(batchData: _*).apply()
    }
  }

}
