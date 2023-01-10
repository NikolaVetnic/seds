```SQL
    CREATE TABLE nvp_dw_city (
        cityid              INTEGER NOT NULL,
        cityname            VARCHAR2(100) NOT NULL,
        citypopulation      INTEGER NOT NULL,
        nvp_dw_region_regid INTEGER NOT NULL
    );

    ALTER TABLE nvp_dw_city ADD CONSTRAINT nvp_dw_city_pk PRIMARY KEY ( cityid );

    CREATE TABLE nvp_dw_country (
        countryid   INTEGER NOT NULL,
        countryname VARCHAR2(100) NOT NULL
    );

    ALTER TABLE nvp_dw_country ADD CONSTRAINT nvp_dw_country_pk PRIMARY KEY ( countryid );

    CREATE TABLE nvp_dw_customer (
        custid             INTEGER NOT NULL,
        custname           VARCHAR2(400) NOT NULL,
        custaddress        VARCHAR2(500) NOT NULL,
        custphone          VARCHAR2(50) NOT NULL,
        custage            INTEGER NOT NULL,
        nvp_dw_city_cityid INTEGER NOT NULL
    );

    ALTER TABLE nvp_dw_customer ADD CONSTRAINT nvp_dw_customer_pk PRIMARY KEY ( custid );

    CREATE TABLE nvp_dw_excursion (
        exid                  INTEGER NOT NULL,
        exguidename           VARCHAR2(300) NOT NULL,
        nvp_dw_city_cityid    INTEGER NOT NULL,
        nvp_dw_tour_tourid    INTEGER NOT NULL,
        nvp_dw_ratingclass_rc INTEGER NOT NULL
    );

    ALTER TABLE nvp_dw_excursion ADD CONSTRAINT nvp_dw_excursion_pk PRIMARY KEY ( exid );

    CREATE TABLE nvp_dw_ratingclass (
        rc     INTEGER NOT NULL,
        rcname VARCHAR2(50)
    );

    ALTER TABLE nvp_dw_ratingclass ADD CONSTRAINT nvp_dw_ratingclass_pk PRIMARY KEY ( rc );

    CREATE TABLE nvp_dw_region (
        regid                    INTEGER NOT NULL,
        regname                  VARCHAR2(100) NOT NULL,
        nvp_dw_country_countryid INTEGER NOT NULL
    );

    ALTER TABLE nvp_dw_region ADD CONSTRAINT nvp_dw_region_pk PRIMARY KEY ( regid );

    CREATE TABLE nvp_dw_time (
        timeid      INTEGER NOT NULL,
        day         INTEGER NOT NULL,
        month       VARCHAR2(20) NOT NULL,
        year        INTEGER NOT NULL,
        "date"      DATE NOT NULL,
        dayinyear   INTEGER NOT NULL,
        monthinyear INTEGER NOT NULL
    );

    ALTER TABLE nvp_dw_time ADD CONSTRAINT nvp_dw_time_pk PRIMARY KEY ( timeid );

    CREATE TABLE nvp_dw_tour (
        tourid                INTEGER NOT NULL,
        tourprice             INTEGER NOT NULL,
        nvp_dw_time_timeid    INTEGER NOT NULL,
        touragentname         VARCHAR2(300) NOT NULL,
        nvp_dw_city_cityid    INTEGER NOT NULL,
        nvp_dw_toursales_tsid INTEGER NOT NULL,
        nvp_dw_ratingclass_rc INTEGER NOT NULL
    );

    ALTER TABLE nvp_dw_tour ADD CONSTRAINT nvp_dw_tour_pk PRIMARY KEY ( tourid );

    CREATE TABLE nvp_dw_toursales (
        tsid                   INTEGER NOT NULL,
        tsquantity             INTEGER NOT NULL,
        tscreatedfirst         DATE NOT NULL,
        tscreatedlast          DATE NOT NULL,
        nvp_dw_customer_custid INTEGER NOT NULL
    );

    ALTER TABLE nvp_dw_toursales ADD CONSTRAINT nvp_dw_toursales_pk PRIMARY KEY ( tsid );

    ALTER TABLE nvp_dw_customer
        ADD CONSTRAINT nvp_dw_city_fk FOREIGN KEY ( nvp_dw_city_cityid )
            REFERENCES nvp_dw_city ( cityid );

    ALTER TABLE nvp_dw_tour
        ADD CONSTRAINT nvp_dw_city_fkv1 FOREIGN KEY ( nvp_dw_city_cityid )
            REFERENCES nvp_dw_city ( cityid );

    ALTER TABLE nvp_dw_excursion
        ADD CONSTRAINT nvp_dw_city_fkv2 FOREIGN KEY ( nvp_dw_city_cityid )
            REFERENCES nvp_dw_city ( cityid );

    ALTER TABLE nvp_dw_region
        ADD CONSTRAINT nvp_dw_country_fk FOREIGN KEY ( nvp_dw_country_countryid )
            REFERENCES nvp_dw_country ( countryid );

    ALTER TABLE nvp_dw_toursales
        ADD CONSTRAINT nvp_dw_customer_fk FOREIGN KEY ( nvp_dw_customer_custid )
            REFERENCES nvp_dw_customer ( custid );

    ALTER TABLE nvp_dw_tour
        ADD CONSTRAINT nvp_dw_ratingclass_fk FOREIGN KEY ( nvp_dw_ratingclass_rc )
            REFERENCES nvp_dw_ratingclass ( rc );

    ALTER TABLE nvp_dw_excursion
        ADD CONSTRAINT nvp_dw_ratingclass_fkv1 FOREIGN KEY ( nvp_dw_ratingclass_rc )
            REFERENCES nvp_dw_ratingclass ( rc );

    ALTER TABLE nvp_dw_city
        ADD CONSTRAINT nvp_dw_region_fk FOREIGN KEY ( nvp_dw_region_regid )
            REFERENCES nvp_dw_region ( regid );

    ALTER TABLE nvp_dw_tour
        ADD CONSTRAINT nvp_dw_time_fk FOREIGN KEY ( nvp_dw_time_timeid )
            REFERENCES nvp_dw_time ( timeid );

    ALTER TABLE nvp_dw_excursion
        ADD CONSTRAINT nvp_dw_tour_fk FOREIGN KEY ( nvp_dw_tour_tourid )
            REFERENCES nvp_dw_tour ( tourid );

    ALTER TABLE nvp_dw_tour
        ADD CONSTRAINT nvp_dw_toursales_fk FOREIGN KEY ( nvp_dw_toursales_tsid )
            REFERENCES nvp_dw_toursales ( tsid );
```
