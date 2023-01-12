# OLAP baza podataka

## 1 Pitanja

Moguca pitanja na koja DW baza treba da odgovori:

1. Koliko je putovanja (vaucera) prodato po gradovima?
2. Koji grad je najpopularnija destinacija - kao putovanje, i kao izlet?
3. Kakve su ocene (rejtinzi) po gradovima - kao putovanja, i kao izleta (dakle, sva putovanja za neki grad, ili svi izleti za isti grad)?
4. Koliko se moze zaraditi na aranzmanima po regionima?
5. Kakav je odnos broja aranzmana po gradovima/regijama i prosecne ocene (da li se moze desiti da za popularne gradove nema dovoljno ponuda)?

## 2 Upiti

### 2.1 Upit #1

Koliko je putovanja (vaucera) prodato po gradovima:

```SQL
    SELECT
        SUM(ts.tsQuantity) AS "toursSold",
        (
            SELECT cityName
            FROM NVP_DW_City
            WHERE cityId = cust.NVP_DW_City_cityId
        ) AS "city",
        (
            SELECT regName
            FROM NVP_DW_City, NVP_DW_Region
            WHERE cityId = cust.NVP_DW_City_cityId AND NVP_DW_Region_regId = regId
        ) AS "region",
        (
            SELECT countryName
            FROM NVP_DW_City, NVP_DW_Region, NVP_DW_Country
            WHERE cityId = cust.NVP_DW_City_cityId AND NVP_DW_Region_regId = regId AND NVP_DW_Country_countryId = countryId
        ) AS "country"
    FROM NVP_DW_TourSales ts
    FULL OUTER JOIN NVP_DW_Customer cust ON ts.NVP_DW_Customer_custId = cust.custId
    GROUP BY cust.NVP_DW_City_cityId
    ORDER BY "toursSold" DESC;
```

### 2.2 Upit #2

Koji grad je najpopularnija destinacija - kao putovanje, i kao izlet:

```SQL
    SELECT
        SUM(NVP_DW_City_cityId) AS "toursSold",
        (
            SELECT cityName
            FROM NVP_DW_City
            WHERE cityId = NVP_DW_City_cityId
        ) AS "city"
    FROM NVP_DW_Tour
    GROUP BY NVP_DW_City_cityId
    ORDER BY "toursSold" DESC;

    SELECT
        SUM(NVP_DW_City_cityId) AS "excursionsSold",
        (
            SELECT cityName
            FROM NVP_DW_City
            WHERE cityId = NVP_DW_City_cityId
        ) AS "city"
    FROM NVP_DW_Excursion
    GROUP BY NVP_DW_City_cityId
    ORDER BY "excursionsSold" DESC;
```

### 2.3 Upit #3

Kakve su ocene (rejtinzi) po gradovima - kao putovanja, i kao izleta (dakle, sva putovanja za neki grad, ili svi izleti za isti grad):

```SQL
    SELECT
        ROUND(TO_CHAR(AVG(tourRating)), 2) AS "avgRating",
        (
            SELECT cityName
            FROM NVP_SRC_City
            WHERE cityId = NVP_SRC_City_cityId
        ) AS "city",
        (
            SELECT regName
            FROM NVP_SRC_City, NVP_SRC_Region
            WHERE cityId = NVP_SRC_City_cityId AND NVP_SRC_Region_regId = regId
        ) AS "region",
        (
            SELECT countryName
            FROM NVP_SRC_City, NVP_DW_Region, NVP_DW_Country
            WHERE cityId = NVP_SRC_City_cityId AND NVP_SRC_Region_regId = regId AND NVP_DW_Country_countryId = countryId
        ) AS "country"
    FROM NVP_SRC_Tour, NVP_SRC_Hotel
    WHERE NVP_SRC_Hotel_hotId = hotId
    GROUP BY NVP_SRC_City_cityId
    ORDER BY "avgRating" DESC;

    SELECT
        ROUND(TO_CHAR(AVG(exRating)), 2) AS "avgRating",
        (
            SELECT cityName
            FROM NVP_SRC_City
            WHERE cityId = NVP_SRC_City_cityId
        ) AS "city",
        (
            SELECT regName
            FROM NVP_SRC_City, NVP_SRC_Region
            WHERE cityId = NVP_SRC_City_cityId AND NVP_SRC_Region_regId = regId
        ) AS "region",
        (
            SELECT countryName
            FROM NVP_SRC_City, NVP_DW_Region, NVP_DW_Country
            WHERE cityId = NVP_SRC_City_cityId AND NVP_SRC_Region_regId = regId AND NVP_DW_Country_countryId = countryId
        ) AS "country"
    FROM NVP_SRC_Excursion, NVP_SRC_Tour, NVP_SRC_Hotel
    WHERE NVP_SRC_Tour_tourId = tourId AND NVP_SRC_Hotel_hotId = hotId
    GROUP BY NVP_SRC_City_cityId
    ORDER BY "avgRating" DESC;
```

### 2.4 Upit #4

Koliko se moze zaraditi na aranzmanima po regionima:

```SQL
    SELECT
        SUM(tourPrice) AS "totalProfit",
        (
            SELECT regName
            FROM NVP_DW_Region reg1
            WHERE reg0.regId = reg1.regId
        ) AS "region",
        (
            SELECT countryName
            FROM NVP_DW_Region reg1, NVP_DW_Country co1
            WHERE reg0.regId = reg1.regId AND reg1.NVP_DW_Country_countryId = co1.countryId
        ) AS "country"
    FROM NVP_DW_Tour tour0, NVP_DW_City city0, NVP_DW_Region reg0
    WHERE tour0.NVP_DW_City_cityId = city0.cityId AND city0.NVP_DW_Region_regId = reg0.regId
    GROUP BY regId
    ORDER BY "totalProfit";
```

### 2.5 Upit #5

Kakav je odnos broja aranzmana po gradovima/regijama i prosecne ocene (da li se moze desiti da za popularne gradove nema dovoljno ponuda):

```SQL
    SELECT
        COUNT(tour0.tourId) AS "numTours",
        ROUND(TO_CHAR(AVG(tour0.tourRating)), 2) AS "avgRating",
        (
            SELECT regName
            FROM NVP_DW_Region reg1
            WHERE reg0.regId = reg1.regId
        ) AS "region"
    FROM NVP_SRC_Tour tour0, NVP_SRC_Hotel hot0, NVP_SRC_City city0, NVP_SRC_Region reg0, NVP_DW_Country co0
    WHERE
        tour0.NVP_SRC_Hotel_hotId = hot0.hotId AND
        hot0.NVP_SRC_City_cityId = city0.cityId AND
        city0.NVP_SRC_Region_regId = reg0.regId
    GROUP BY reg0.regId
    ORDER BY "avgRating" DESC;
```
