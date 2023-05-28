# Musify

A music artists information aggregator reads information from 4 sources:  
MusicBrainz, WikiData, Wikipedia and CoverArtArchive  
to build a detailed result about en artist's gender, country, name and albums id, title and front cover image url

## Prerequisites

- maven
- redis(running on localhost 6379)
- Java 17
- 8081 on localhost is free to use
- curl or Postman

## Build & Run

- From the root of the project run ```mvn spring-boot:run```

## How to Test

- Michael Jackson ```curl -X GET "http://localhost:8081/musify/music-artist/details/`f27ec8db-af05-4f36-916e-3d57f91ecf5e```

- Snoop Dog ```curl -X GET "http://localhost:8081/musify/music-artist/details/f90e8b26-9e52-4669-a5c9-e28529c47894"```

- Queen ```curl -X GET "http://localhost:8081/musify/music-artist/details/`0383dadf-2a4e-4d10-a46a-e9e041da8eb3```

- ```mvn test```
## How to monitor

- ``` curl -X GET "http://localhost:8081/actuator/prometheus"```  
service_wikidata_byqx_seconds_max{application="musify",} 2.3589841  
http_server_requests_seconds_count{application="musify",error="none",exception="none",method="GET",outcome="SUCCESS",status="200",uri="/musify/music-artist/details/{mbid}",} 3.0

## Some Tools or Approaches used

- Connections Pooling on restTemplate
- MultiThread consumption third party web service
- Redis Caching
- Actuator, Prometheus and Micrometer

## Why use those tools or approaches
- Get the information one by one is extremely time-consuming
- The result from those third parties is relatively static
- To be production ready

## In case more time is given
- Definitely circuit breaker, on redis and/or on one of the third parity websites.