# OLTP baza podataka

## 1 Popunjavanje

Tabele `NVP_SRC_Region` i `NVP_SRC_City` su popunjene rucno okruzima i gradovima iz Srbije i BiH (RS):

```SQL
    SELECT * FROM NVP_SRC_City
    LEFT OUTER JOIN NVP_SRC_Region ON regId = NVP_SRC_Region_regId;
```

Tabele `NVP_SRC_Hotel` i `NVP_SRC_Organization` su popunjene preko _Mockaroo_-a koriscenjem naziva biljaka za nazive hotela, odnosno preduzeca za nazive turistickih agencija:

```SQL
    SELECT * FROM NVP_SRC_Hotel
    LEFT OUTER JOIN NVP_SRC_City ON cityId = NVP_SRC_City_cityId
    LEFT OUTER JOIN NVP_SRC_Region ON regId = NVP_SRC_Region_regId
    ORDER BY cityName;

    SELECT * FROM NVP_SRC_Organization
    LEFT OUTER JOIN NVP_SRC_City ON cityId = NVP_SRC_City_cityId
    LEFT OUTER JOIN NVP_SRC_Region ON regId = NVP_SRC_Region_regId
    ORDER BY cityName;
```

Tabele `NVP_SRC_Customer`, `NVP_SRC_Agent` i `NVP_SRC_Guide` su popunjene preko _Mockaroo_-a koriscenjem podataka za osobe:

```SQL
    SELECT * FROM NVP_SRC_Customer
    LEFT OUTER JOIN NVP_SRC_City ON cityId = NVP_SRC_City_cityId
    LEFT OUTER JOIN NVP_SRC_Region ON regId = NVP_SRC_Region_regId
    ORDER BY custId;

    SELECT * FROM NVP_SRC_Agent
    LEFT OUTER JOIN NVP_SRC_Organization ON orgId = NVP_SRC_Organization_orgId
    LEFT OUTER JOIN NVP_SRC_City ON cityId = NVP_SRC_City_cityId
    LEFT OUTER JOIN NVP_SRC_Region ON regId = NVP_SRC_Region_regId
    ORDER BY agId;

    SELECT * FROM NVP_SRC_Guide
    LEFT OUTER JOIN NVP_SRC_Organization ON orgId = NVP_SRC_Organization_orgId
    LEFT OUTER JOIN NVP_SRC_City ON cityId = NVP_SRC_City_cityId
    LEFT OUTER JOIN NVP_SRC_Region ON regId = NVP_SRC_Region_regId
    ORDER BY guId;
```
