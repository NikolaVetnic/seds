Table input:

```SQL
    SELECT
        EXTRACT(DAY FROM tourCreated) AS "day",
        TO_CHAR(tourCreated, 'Month') AS "month",
        EXTRACT(YEAR FROM tourCreated) AS "year",
        tourCreated AS "date",
        TO_CHAR(tourCreated, 'DDD') AS "dayInYear",
        EXTRACT(MONTH FROM tourCreated) AS "monthInYear"
    FROM NVP_SRC_Tour
```

Check:

```SQL
    SELECT * FROM NVP_DW_Time;
```
