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
--form 'platform="Trading212"' \
--form 'accountIdsByCurrency="{
\"EUR\": \"77613c2f-3c0f-40e8-9309-ea2c0648a643\", 
\"USD\":\"cbace329-3658-4080-817e-ee93bbb9bc11\",
\"GBP\": \"512fbd37-f000-4f19-bf64-d57356a18e50\",
\"GBX\": \"e5112c7d-ae60-4d0f-9546-0d4245add27d\",
\"CHF\": \"cdb3c4fc-294c-4c85-bda1-4490ae6dc4f0\"}"'
```



