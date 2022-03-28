FROM hseeberger/scala-sbt:8u312_1.6.2_2.13.8

# very minimal sbt running dockerfile
WORKDIR /www/app

COPY . .

RUN sbt compile
ENTRYPOINT ["sbt", "run"]
