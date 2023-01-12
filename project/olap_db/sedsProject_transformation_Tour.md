Table input:

```SQL
    SELECT
        tourId AS "tourId",
        tourPrice AS "tourPrice",
        timeId AS "NVP_DW_Time_timeId",
        agFirstName || ' ' || agLastName AS "tourAgentName",
        NVP_SRC_City_cityId AS "NVP_DW_City_cityId",
        tsId AS "NVP_DW_TourSales_tsId",
        CASE
            WHEN tourRating > 8 THEN 5
            WHEN tourRating > 6 THEN 4
            WHEN tourRating > 4 THEN 3
            WHEN tourRating > 2 THEN 2
            ELSE 1
        END "NVP_DW_RatingClass_rc"
    FROM NVP_SRC_Tour
    LEFT JOIN NVP_SRC_Agent ON NVP_SRC_Agent_agId = agId
    LEFT JOIN NVP_SRC_Hotel ON NVP_SRC_Hotel_hotId = hotId
    LEFT JOIN NVP_DW_Time ON tourCreated = "date"
    LEFT JOIN NVP_DW_TourSales ON NVP_SRC_Customer_custId = NVP_DW_Customer_custId
```

Oracle SQL insert:

```SQL
    INSERT INTO NVP_DW_Tour
        (tourId, tourPrice, NVP_DW_Time_timeId, tourAgentName, NVP_DW_City_cityId, NVP_DW_TourSales_tsId, NVP_DW_RatingClass_rc)
    SELECT
        tourId,
        tourPrice,
        timeId AS "NVP_DW_Time_timeId",
        agFirstName || ' ' || agLastName AS "tourAgentName",
        NVP_SRC_City_cityId AS "NVP_DW_City_cityId",
        tsId AS "NVP_DW_TourSales_tsId",
        CASE
            WHEN tourRating > 8 THEN 5
            WHEN tourRating > 6 THEN 4
            WHEN tourRating > 4 THEN 3
            WHEN tourRating > 2 THEN 2
            ELSE 1
        END "NVP_DW_RatingClass_rc"
    FROM NVP_SRC_Tour
    LEFT OUTER JOIN NVP_DW_Time ON "date" = tourCreated
    LEFT OUTER JOIN NVP_SRC_Agent ON NVP_SRC_Agent_agId = agId
    LEFT OUTER JOIN NVP_SRC_Hotel ON NVP_SRC_Hotel_hotId = hotId
    LEFT OUTER JOIN NVP_DW_TourSales ON NVP_DW_Customer_custId = NVP_SRC_Customer_custId;
```

Check:

```SQL
    SELECT * FROM NVP_DW_Tour;
```

Other:

```SQL
    SELECT tourCreated FROM NVP_SRC_Tour ORDER BY tourCreated;
```
