Table input:

```SQL
    SELECT
        cust.custId,
        cust.custFirstName || ' ' || cust.custLastName AS "custName",
        cust.custAddress,
        cust.custPhone,
        TRUNC(months_between(sysdate, cust.custDateOfBirth) / 12) AS "custAge",
        cust.NVP_SRC_City_cityId AS "NVP_DW_City_cityId"
    FROM NVP_SRC_Customer cust
    ORDER BY cust.custId
```

Oracle SQL insert:

```SQL
    INSERT INTO NVP_DW_Customer
        (custId, custName, custAddress, custPhone, custAge, NVP_DW_City_cityId)
    SELECT
        cust.custId,
        cust.custFirstName || ' ' || cust.custLastName AS "custName",
        cust.custAddress,
        cust.custPhone,
        TRUNC(months_between(sysdate, cust.custDateOfBirth) / 12) AS "custAge",
        cust.NVP_SRC_City_cityId AS "NVP_DW_City_cityId"
    FROM NVP_SRC_Customer cust;
```

Check:

```SQL
    SELECT custId, custName, custAge, custPhone, custAddress, cityName, regName, countryName
    FROM NVP_DW_Customer
    LEFT JOIN NVP_DW_City ON cityId = NVP_DW_City_cityId
    LEFT JOIN NVP_DW_Region ON NVP_DW_Region_regId = regId
    LEFT JOIN NVP_DW_Country ON NVP_DW_Country_countryId = countryId;
```
