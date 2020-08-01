# abcdator [![build](https://github.com/Shpota/abcdator/workflows/build/badge.svg)](https://github.com/Shpota/abcdator/actions?query=workflow%3Abuild)
A REST API that performs math operations. Implemented as
a part of a technical interview process.

## Build & Run
In order to build and run the application, you need to have
`Docker` on your machine.

### Build:
```sh
docker build --tag abcdator .
```
### Run:
```sh
docker run -p 8000:8000 abcdator 
```
The REST API of the application will be available on
port 8000 with 3 endpoints:
- `/calculate/base`
- `/calculate/custom-1`
- `/calculate/custom-2`

Here are examples of `curl` queries to different substitute
expressions.

Base:
```sh
curl -X GET "http://localhost:8000/calculate/base?A=true&B=true&C=false&D=1.3&E=1&F=1"
```
Custom 1:
```sh
curl -X GET "http://localhost:8000/calculate/custom-1?A=true&B=true&C=false&D=1.3&E=1&F=1"
```
Custom 2:
```sh
curl -X GET "http://localhost:8000/calculate/custom-2?A=true&B=true&C=false&D=1.3&E=1&F=1"
```

## Architecture
The application consists of a business logic layer and 
an HTTP layer.

The business logic layer operates with `predicates` and 
`formulas` abstractions which represent correspondingly
boolean mappings like `A && B && !C => H` and numeric
formulas like `K = D + (D * (E - F) / 25.5)`. Predicates
and formulas can then be passed to the `execute()` function
which would produce a result based on the input.

The HTTP layer is responsible for user-facing API, input validation,
and HTTP server. It binds REST paths to the `execute()` function
and predicates/formulas based on the selected set of rules
(base/custom).

## Technologies
The application is implemented in `Kotlin` using the `http4k`
library. It is built and run using `Docker`. It is covered with
`Kotest`-based unit tests.


