# ipconf.cc

ipconf.cc is a side project at Randomicu organization.

## Build notes

This project uses Maven for building production / development docker images.

Currently, there only two environments are configured: production and development.

Before building, edit the `.env` file.

### Manual production build

1. Build image and tag it with the closest git tag: `./mvnw -DskipTests -Pproduction spring-boot:build-image`
2. Tag docker image with `latest` tag: `docker tag image:1.0.0 image:latest` 
3. Push it twice: `docker push image:1.0.0 && docker push image:latest`
4. Download latest db-ip databases (ASN Lite, City Lite) and place it to `./mmdb` folder
5. Start container: `docker run --rm -p 8080:8080 --volume "${PWD}/mmdb:/workspace/mmdb" --env "SENTRY_DSN=$SENTRY_DSN" image:1.0.0`

## Development and releases

The following text will describe **ipconf** development and releasing process.

**First** - all commits for the [next version](https://next.ipconf.cc) are pushed to the `development` branch. Also, all new code changes will be deployed by hand.

**Second** - all releases for the [main site](https://ipconf.cc) are tagged with semver and could be found on the Github [releases page](https://github.com/randomicu/ipconf/releases).

**Third** - all Docker images could be found on the Github [packages page](https://github.com/orgs/randomicu/packages?repo_name=ipconf).

There is no public roadmap available for the **ipconf** project now.

## Thanks

Ipconf & related projects were built with [Jetbrains](https://www.jetbrains.com/?from=RandomicuQAAPI) PyCharm Professional
and Intellij IDEA Ultimate.

IP Geolocation is powered by [db-ip.com](https://db-ip.com/).
