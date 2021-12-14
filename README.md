
# Download, unzip and read
Project to download zip file(from special link), unzip downloaded file, read data from unzipped file and save them to database(exam for trixi software).




## Tech Stack

**Operation system:** any 

**Backend:** Java 11, Spring

**Application server:** Tomcat(Spring framework)

**Frontend:** HTML(using Thymeleaf)

**Database:** MySQL


## Deployment

To deploy this project run

```bash
  mvn spring-boot:run
```


## API Reference

#### Get started with downloading and other things

```http
  GET /start
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `url` | `String` | **NotRequired**. Desides from which URL will be zip file downloaded, if null it will use default URL.|

#### Get all Obec data from Database and show them
```http
  GET /show
```
#### Get all CastObce data which belongs to defined Obec from Database and show them
```http
  GET /{obecId}/parts
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `{obecId}` | `Long` | **Required**. Desides Obec which CastObce will be shown.|




## Authors

- [@vladislav_soshko](https://github.com/GreenTheSnail)

