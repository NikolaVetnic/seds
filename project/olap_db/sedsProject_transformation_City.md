Table input:

```SQL
    SELECT cityId, cityName, NVP_SRC_Region_regId
    FROM NVP_SRC_City
    ORDER BY cityId
```

Check:

```SQL
    SELECT city.cityId, city.cityname, region.regname, country.countryname
    FROM NVP_DW_City city
    LEFT JOIN NVP_DW_Region region ON city.NVP_DW_Region_regId = region.regId
    LEFT JOIN NVP_DW_Country country ON region.NVP_DW_Country_countryId = country.countryId;
```
