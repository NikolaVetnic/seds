Table input:

```SQL
    SELECT cityId, cityName, NVP_SRC_Region_regId
    FROM NVP_SRC_City
    ORDER BY cityId
```

Oracle SQL insert:

```SQL
    INSERT INTO NVP_DW_City
        (cityId, cityName, cityPopulation, NVP_DW_Region_regId)
    SELECT cityId, cityName, cityPopulation, NVP_SRC_Region_regId FROM NVP_SRC_City;
```

Check:

```SQL
    SELECT cityId, cityName, cityPopulation, regName, countryName FROM NVP_DW_City
    LEFT OUTER JOIN NVP_DW_Region ON regId = NVP_DW_Region_regId
    LEFT OUTER JOIN NVP_DW_Country ON countryId = NVP_DW_Country_countryId;
```
