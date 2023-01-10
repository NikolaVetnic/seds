Table input:

```SQL
    SELECT
        exId AS "exId",
        guFirstName || ' ' || guLastName AS "exGuideName",
        NVP_SRC_City_cityId AS "NVP_DW_City_cityId",
        tourId AS "NVP_DW_Tour_tourId",
        CASE
            WHEN exRating > 8 THEN 5
            WHEN exRating > 6 THEN 4
            WHEN exRating > 4 THEN 3
            WHEN exRating > 2 THEN 2
            ELSE 1
        END "NVP_DW_RatingClass_rc"
    FROM NVP_SRC_Excursion
    LEFT JOIN NVP_SRC_Tour ON NVP_SRC_Tour_tourId = tourId
    LEFT JOIN NVP_SRC_Hotel ON NVP_SRC_Hotel_hotId = hotId
    LEFT JOIN NVP_SRC_Guide ON NVP_SRC_Guide_guId = guId
```

Check:

```SQL
    SELECT * FROM NVP_DW_Excursion;
```
