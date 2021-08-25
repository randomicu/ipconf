# ipconf.cc

ipconf.cc is a side project at Randomicu organization.

## Build notes

This project uses Maven.

Before building, edit `.env` file.

### Manual production build

1. Build image and tag it with the closest git tag: `./mvnw -DskipTests -Pproduction spring-boot:build-image`
2. Tag docker image with `latest` tag: `docker tag image:1.0.0 image:latest` 
3. Push it twice: `docker push image:1.0.0 && docker push image:latest`
4. Download latest db-ip database and place it to `./mmdb` folder
5. Start container: `docker run --rm -p 8080:8080 --volume "${PWD}/mmdb:/workspace/mmdb" --env "SENTRY_DSN=$SENTRY_DSN" image:1.0.0`

## Thanks

Randomicu & related projects was built with [Jetbrains](https://www.jetbrains.com/?from=RandomicuQAAPI) PyCharm and Intellij IDEA.

IP Geolocation powered by [db-ip.com](https://db-ip.com/).
