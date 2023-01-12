Table input:

```SQL
    SELECT DISTINCT countryId, countryName
    FROM NVP_SRC_Region
    ORDER BY countryId
```

Oracle SQL insert:

```SQL
    INSERT INTO NVP_DW_Country
        (countryId, countryName)
    SELECT DISTINCT countryId, countryName FROM NVP_SRC_Region;
```

Check:

```SQL
    SELECT * FROM NVP_DW_Country;
```
