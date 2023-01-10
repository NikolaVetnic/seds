Table input:

```SQL
    SELECT regId, regName, countryId
    FROM NVP_SRC_Region
    ORDER BY regId
```

Check:

```SQL
    SELECT * FROM NVP_DW_Region
    LEFT JOIN NVP_DW_Country
    ON countryId = NVP_DW_Country_countryId;
```
