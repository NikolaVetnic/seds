Table input:

```SQL
    SELECT
        COUNT(*) AS "tsQuantity",
        NVP_SRC_Customer_custId AS "NVP_DW_Customer_custId",
        MIN(tourCreated) AS "tsCreatedFirst",
        MAX(tourCreated) AS "tsCreatedLast"
    FROM NVP_SRC_Tour
    GROUP BY NVP_SRC_Customer_custId
    ORDER BY NVP_SRC_Customer_custId
```

Check:

```SQL
    SELECT * FROM NVP_DW_TourSales;
```

Other:

```SQL
    SELECT
        COUNT(*) AS "tsQuantity",
        MIN(tourCreated) AS "tsCreatedFirst",
        MAX(tourCreated) AS "tsCreatedLast",
        NVP_SRC_Customer_custId AS "NVP_DW_Customer_custId"
    FROM NVP_SRC_Tour
    GROUP BY NVP_SRC_Customer_custId;
```
