Table input:

```SQL
    SELECT regId, regName, countryId
    FROM NVP_SRC_Region
    ORDER BY regId
```

Oracle SQL insert:

```SQL
    INSERT INTO NVP_DW_Region
        (regId, regName, NVP_DW_Country_countryId)
    SELECT regId, regName, countryId FROM NVP_SRC_Region;
```

Check:

```SQL
    SELECT * FROM NVP_DW_Region
    LEFT JOIN NVP_DW_Country
    ON countryId = NVP_DW_Country_countryId;
```
