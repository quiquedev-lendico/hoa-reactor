# Hoa Reactor

Implement the following endpoint

```
POST /persons

{
    "ids": ["id-1", "id-2", "id-3"]
}
```

This endpoint needs to fetch the name and age from for each person id and save them into a database.

## Name Service
GET http://name-service.com/persons/{id}

{
    "id": "id-1",
    "name": "enrique"
}

## Age Service
GET http://age-service.com/persons/{id}

{
    "id": "id-1",
    "age": 40
}

### Requirements
* Every operation must be safely wrapped in Spring Reactor classes
* Performance
* Logging
* Database can be either real or mocked


Production code must perform real http calls.
WE ARE NOT GOING to run the service locally. That means, those calls are not going to happen.
BUT I want you to write some integration tests and use Wiremock to stub the name and age service.