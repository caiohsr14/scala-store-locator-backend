# store-locator-backend

### Description

Using the service in https://github.com/Helio-Health-Group/store-locator project rewrite the backend using
http4s library and use PostgresSQL instead of Mongo DB. To achieve this goal you will need to replicate the
`/api/stores?zip_code=` endpoint, this includes but is not limited to:

1. Load the stores.json into postgres
2. Update the endpoint to retrieve the nearest coordinates, using the MapQuest Geocoding Service and calling a Postgres distance function.
   1. Note: Try to replicate this with the original as close as possible in terms of functionality.
3. Return the list of Stores in a JSON format similar to the original.

There is plenty of working examples and code to help you through most of the steps. The MapQuestService although
basic is functional, and the JSON examples should help you decode/encode all the data wherever you need it.

You are free to use any additional Scala/Java libs that you see fit beyond the required ones below
This project is open-ended as long as meet the core **requirements**:

### Technical Requirements

- Must use: Scala and HTTP4s for the application layer.
- Please no vars or mutable data structures, unless you absolutely have to. You may need to explain reasoning later on.
- You are free to use any Database library you like, that includes Java ones if you see fit.
  - Some recommended libs: ScalikeJDBC, Doobie, JOOQ, Quill
- Must use: Postgres as the data store replacing MongoDB.
  - How you structure the Schema is up to you, can be flat table, multiple tables, or JSON, etc. in postgres
  - We included the basic json used in the original service.
- **Finally**, the service should be as close to possible as store-locator in regard to the `/api/stores?zip_code=` endpoint
  - i.e., this means we should be able to use this new service with the UI with none, or a few changes.
  - you may also need to provide a mechanism for loading the `stores.json` data into PostgesSQL if you do not provide a dump

### To Run/Use
- For simplicity, we've set up a SBT project already. You are free to use the Editor/IDE of your choice, though we recommend Intellij as that can get you started very quickly (free 30 day trial)
- set the `MQ_API_KEY` in the `app.resources` found under resources.
- Run the `com.helio.http.Server` as the Main class -or- use sbt run and choose the same class.
- Server should start on 8080 by default
  - you can verify by hitting the test endpoint `http://localhost:8080/test/hello/world`

### Final Notes:

- Submit your own work! It's ok to use code-snippets from the old version, and/or stack-overflow, but paraphrase or
  rewrite in the spirit/purpose of the functionality of this app.
- Your choice of libraries are up to you (again beyond the core). Be prepared to explain your choices here.
- For submission please zip up the project minus the npm modules or any other files excluded by the .gitignore,
  and send it back via email. Feel free to email any questions or clarifications if unsure here.
- For the database please provide a database dump or the SQL needed to generate it. This should include data or a way to load it.
- This project is estimated to take anywhere from 3-8 hours based on some exposure to these concepts/env. If it takes longer feel free to submit and/or reach.
- There is no time limit, only what you need to complete this project.

Thank you,

HelioArgos Team
