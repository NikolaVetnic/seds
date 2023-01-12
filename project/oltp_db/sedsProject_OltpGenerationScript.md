```SQL
    CREATE TABLE nvp_src_agent (
        agid                       INTEGER NOT NULL,
        agfirstname                VARCHAR2(200) NOT NULL,
        aglastname                 VARCHAR2(200) NOT NULL,
        aghiredate                 DATE NOT NULL,
        nvp_src_organization_orgid INTEGER NOT NULL
    );

    CREATE TABLE nvp_src_city (
        cityid               INTEGER NOT NULL,
        cityname             VARCHAR2(100) NOT NULL,
        citypopulation       INTEGER NOT NULL,
        nvp_src_region_regid INTEGER NOT NULL
    );

    CREATE TABLE nvp_src_customer (
        custid              INTEGER NOT NULL,
        custfirstname       VARCHAR2(100) NOT NULL,
        custlastname        VARCHAR2(100) NOT NULL,
        custdateofbirth     DATE NOT NULL,
        custaddress         VARCHAR2(200) NOT NULL,
        custphone           VARCHAR2(50) NOT NULL,
        nvp_src_city_cityid INTEGER NOT NULL
    );

    CREATE TABLE nvp_src_excursion (
        exid                INTEGER NOT NULL,
        exfrom              DATE NOT NULL,
        exto                DATE NOT NULL,
        exlocation          VARCHAR2(200) NOT NULL,
        exrating            INTEGER NOT NULL,
        nvp_src_guide_guid  INTEGER NOT NULL,
        nvp_src_tour_tourid INTEGER NOT NULL
    );

    CREATE TABLE nvp_src_guide (
        guid                       INTEGER NOT NULL,
        gufirstname                VARCHAR2(200) NOT NULL,
        gulastname                 VARCHAR2(200) NOT NULL,
        guhiredate                 DATE NOT NULL,
        nvp_src_organization_orgid INTEGER NOT NULL
    );

    CREATE TABLE nvp_src_hotel (
        hotid               INTEGER NOT NULL,
        hotname             VARCHAR2(200) NOT NULL,
        hotaddress          VARCHAR2(200) NOT NULL,
        hotphone            VARCHAR2(50) NOT NULL,
        nvp_src_city_cityid INTEGER NOT NULL
    );

    CREATE TABLE nvp_src_organization (
        orgid               INTEGER NOT NULL,
        orgname             VARCHAR2(200) NOT NULL,
        orgaddress          VARCHAR2(200) NOT NULL,
        orgphone            VARCHAR2(50) NOT NULL,
        orgemail            VARCHAR2(100) NOT NULL,
        nvp_src_city_cityid INTEGER NOT NULL
    );

    CREATE TABLE nvp_src_region (
        regid       INTEGER NOT NULL,
        regname     VARCHAR2(100) NOT NULL,
        countryid   INTEGER NOT NULL,
        countryname VARCHAR2(100) NOT NULL
    );

    CREATE TABLE nvp_src_tour (
        tourid                  INTEGER NOT NULL,
        tourcreated             DATE NOT NULL,
        tourroomno              VARCHAR2(50) NOT NULL,
        tourdatefrom            DATE NOT NULL,
        tourdateto              DATE NOT NULL,
        tourprice               INTEGER NOT NULL,
        tourstatus              VARCHAR2(200) NOT NULL,
        tourrating              INTEGER NOT NULL,
        nvp_src_customer_custid INTEGER NOT NULL,
        nvp_src_hotel_hotid     INTEGER NOT NULL,
        nvp_src_agent_agid      INTEGER NOT NULL
    );

    ALTER TABLE nvp_src_agent ADD CONSTRAINT nvp_src_agent_pk PRIMARY KEY ( agid );

    ALTER TABLE nvp_src_city ADD CONSTRAINT nvp_src_city_pk PRIMARY KEY ( cityid );

    ALTER TABLE nvp_src_customer ADD CONSTRAINT nvp_src_customer_pk PRIMARY KEY ( custid );

    ALTER TABLE nvp_src_excursion ADD CONSTRAINT nvp_src_excursion_pk PRIMARY KEY ( exid );

    ALTER TABLE nvp_src_guide ADD CONSTRAINT nvp_src_guide_pk PRIMARY KEY ( guid );

    ALTER TABLE nvp_src_hotel ADD CONSTRAINT nvp_src_hotel_pk PRIMARY KEY ( hotid );

    ALTER TABLE nvp_src_organization ADD CONSTRAINT nvp_src_organization_pk PRIMARY KEY ( orgid );

    ALTER TABLE nvp_src_region ADD CONSTRAINT nvp_src_region_pk PRIMARY KEY ( regid );

    ALTER TABLE nvp_src_tour ADD CONSTRAINT nvp_src_tour_pk PRIMARY KEY ( tourid );

    ALTER TABLE nvp_src_tour
        ADD CONSTRAINT nvp_src_agent_fk FOREIGN KEY ( nvp_src_agent_agid )
            REFERENCES nvp_src_agent ( agid );

    ALTER TABLE nvp_src_customer
        ADD CONSTRAINT nvp_src_city_fk FOREIGN KEY ( nvp_src_city_cityid )
            REFERENCES nvp_src_city ( cityid );

    ALTER TABLE nvp_src_hotel
        ADD CONSTRAINT nvp_src_city_fkv2 FOREIGN KEY ( nvp_src_city_cityid )
            REFERENCES nvp_src_city ( cityid );

    ALTER TABLE nvp_src_organization
        ADD CONSTRAINT nvp_src_city_fkv3 FOREIGN KEY ( nvp_src_city_cityid )
            REFERENCES nvp_src_city ( cityid );

    ALTER TABLE nvp_src_tour
        ADD CONSTRAINT nvp_src_customer_fk FOREIGN KEY ( nvp_src_customer_custid )
            REFERENCES nvp_src_customer ( custid );

    ALTER TABLE nvp_src_excursion
        ADD CONSTRAINT nvp_src_guide_fk FOREIGN KEY ( nvp_src_guide_guid )
            REFERENCES nvp_src_guide ( guid );

    ALTER TABLE nvp_src_tour
        ADD CONSTRAINT nvp_src_hotel_fk FOREIGN KEY ( nvp_src_hotel_hotid )
            REFERENCES nvp_src_hotel ( hotid );

    ALTER TABLE nvp_src_agent
        ADD CONSTRAINT nvp_src_organization_fk FOREIGN KEY ( nvp_src_organization_orgid )
            REFERENCES nvp_src_organization ( orgid );

    ALTER TABLE nvp_src_guide
        ADD CONSTRAINT nvp_src_organization_fkv2 FOREIGN KEY ( nvp_src_organization_orgid )
            REFERENCES nvp_src_organization ( orgid );

    ALTER TABLE nvp_src_city
        ADD CONSTRAINT nvp_src_region_fk FOREIGN KEY ( nvp_src_region_regid )
            REFERENCES nvp_src_region ( regid );

    ALTER TABLE nvp_src_excursion
        ADD CONSTRAINT nvp_src_tour_fk FOREIGN KEY ( nvp_src_tour_tourid )
            REFERENCES nvp_src_tour ( tourid );
```
