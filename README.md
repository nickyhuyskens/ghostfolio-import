## Run environment

- docker compose up -d
---

## Run application

- ./mvnw mn:run

---

## Upload trading212 csv example

```bash 
curl --location --request POST 'localhost:8080/upload' \
--form 'file=@"./from_2021-01-01_to_2021-12-31_MTY1NTkwNjMyNTAzOQ.csv"' \
--form 'token="<GHOSTFOL.IO BEARER TOKEN eg Bearer eyJ...>"' \
--form 'platform="Trading212"'
```



