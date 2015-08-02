# obd-server

REST API for storing OBD-II readings from [Android OBD-II Reader](https://github.com/pires/android-obd-reader).

**Attention**: the storage is ephemeral since it relies on an in-memory database.
I've made this decision for the sake of deployment simplicity.

## Pre-requisites

1. JDK 8
2. Maven 3.3.0 or newer

## Build

```
mvn clean package
```

## Run

```
mvn spring-boot:run
```

## Test

### Add records

```
$ ./tests/put_some_stuff.sh
HTTP/1.1 200 OK
Server: Apache-Coyote/1.1
Content-Length: 0
Date: Sun, 02 Aug 2015 21:07:12 GMT

HTTP/1.1 200 OK
Server: Apache-Coyote/1.1
Content-Length: 0
Date: Sun, 02 Aug 2015 21:07:12 GMT

HTTP/1.1 200 OK
Server: Apache-Coyote/1.1
Content-Length: 0
Date: Sun, 02 Aug 2015 21:07:12 GMT

HTTP/1.1 200 OK
Server: Apache-Coyote/1.1
Content-Length: 0
Date: Sun, 02 Aug 2015 21:07:12 GMT
```

### Read records for a certain VIN

Reading records is a paginated operation. Set `page` (_defaults to 0_) and `size` (_defaults to 100_) query parameters if you want to paginate.

```
./tests/read_testvin_json.sh
HTTP/1.1 200 OK
Server: Apache-Coyote/1.1
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Sun, 02 Aug 2015 21:14:14 GMT

{
  "content":[
    {
      "id":1,
      "version":0,
      "latitude":40.0,
      "longitude":-8.1,
      "timestamp":1234567890,
      "vin":"testvin",
      "readings":{
        "rpm":"3000",
        "speed":"55"
      }
    },
    {
      "id":2,
      "version":0,
      "latitude":40.1,
      "longitude":-8.2,
      "timestamp":1234568999,
      "vin":"testvin",
      "readings":{
        "rpm":"3200",
        "speed":"65"
      }
    },
    {
      "id":3,
      "version":0,
      "latitude":40.2,
      "longitude":-8.3,
      "timestamp":1234589999,
      "vin":"testvin",
      "readings":{
        "rpm":"4000",
        "speed":"75"
      }
    }
  ],
  "last":true,
  "totalPages":1,
  "totalElements":3,
  "size":100,
  "number":0,
  "sort":null,
  "first":true,
  "numberOfElements":3
}

```
