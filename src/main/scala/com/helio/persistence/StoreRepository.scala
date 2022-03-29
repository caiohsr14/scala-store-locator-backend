package com.helio.persistence

import com.helio.models.Store
import scalikejdbc._
import scalikejdbc.config._

object StoreRepository {
  val dbName = "store-locator"
  val env = Option(System.getenv("ENVIRONMENT")).getOrElse("dev")
  DBsWithEnv(env).setup(dbName)

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
