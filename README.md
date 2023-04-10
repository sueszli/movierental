```shell
# run
brew tap bell-sw/liberica
brew install --cask liberica-jdk15-full
mvn clean compile
mvn exec:java

# test
mvn test
```

<br><br>

## Project structure

`app`: Dependency injection, running UI and calling constructor in DummyDAO classes.
<br>

`dto`: DTOs which get mapped to JSONs (TMDbMovie, TMDbResult).
<br>

`entity`: Entities (Customer, Movie) and relations between them (Rental).
<br>

`exceptions`: Self implemented Exceptions
<br>

`repository`: Classes implementing the CRUD interface. Additionally `DummyRentalDAO.class` implements
`readByCustomer(Customer customer)` which looks up all rentals by a specific customer.
<br>

`service`: Consists of 3 services.

-   `SimpleInvoiceService.class` implements `InvoiceService`: updates videopoints and creates invoice / bill as string.
-   `SimpleRatingService.class` implements `RatingService`: calls MovieDataService, fetches rating of result if only one result is existent.
-   `TheMovieDbMovieDataService.class` implements `MovieDataService`: Fetches Movies from the TMDb API, converts JSON-List to Entity-List.
    <br>

`ui/controller`: Consists of 4 controllers / pages.

-   `CustomerManagementController`: tab for "Kundenverwaltung"
-   `MovieManagementController`: subpage for "Filmverwaltung" -> Roughly the same feature functionality as `CustomerManagementController`
    with 2 additional functions (placed at the end).
-   `MovieRentalController`: root page, initialized in `App.class`
-   `RentalController`: tab for "Ausleihe / Rückgabe"
    <br>

`ui/controls`: Consists of 4 cells (each mapping different inputs to outputs)

-   `BooleanCell`: boolean -> ASCII symbol for true/false
-   `EuroCell`: integer -> "€ 0.00"
-   `RentalActionButton`: Rental -> Button to either pay or remove the Rental
-   `TemporalAccessorCell`: TemporalAccessor -> formatted with DateTimeFormatter
