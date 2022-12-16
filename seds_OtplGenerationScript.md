```SQL
    CREATE TABLE nv_src_city (
        cityid              INTEGER NOT NULL,
        cityname            VARCHAR2(30) NOT NULL,
        citypopulation      INTEGER NOT NULL,
        nv_src_region_regid INTEGER NOT NULL
    );

    CREATE TABLE nv_src_customer (
        custid                  INTEGER NOT NULL,
        custfirstname           VARCHAR2(30) NOT NULL,
        custlastname            VARCHAR2(30) NOT NULL,
        custdateofbirth         DATE NOT NULL,
        custaddress             VARCHAR2(30) NOT NULL,
        custphone               VARCHAR2(30) NOT NULL,
        nv_src_salesperson_spid INTEGER NOT NULL,
        nv_src_city_cityid      INTEGER NOT NULL
    );

    CREATE TABLE nv_src_order (
        orderid                INTEGER NOT NULL,
        orderdate              DATE NOT NULL,
        orderstatus            VARCHAR2(20) NOT NULL,
        nv_src_customer_custid INTEGER NOT NULL
    );

    CREATE TABLE nv_src_orderitem (
        oitemid               INTEGER NOT NULL,
        oitemquantity         NUMBER(6, 2) NOT NULL,
        nv_src_order_orderid  INTEGER NOT NULL,
        nv_src_product_prodid INTEGER NOT NULL
    );

    CREATE TABLE nv_src_product (
        prodid       INTEGER NOT NULL,
        prodname     VARCHAR2(30) NOT NULL,
        prodprice    NUMBER(8, 2) NOT NULL,
        prodfamily   VARCHAR2(30) NOT NULL,
        prodproducer VARCHAR2(30) NOT NULL
    );

    CREATE TABLE nv_src_region (
        regid      INTEGER NOT NULL,
        regname    VARCHAR2(30) NOT NULL,
        regcountry VARCHAR2(40) NOT NULL,
        countryid  INTEGER NOT NULL
    );

    CREATE TABLE nv_src_salesperson (
        spid        INTEGER NOT NULL,
        spfirstname VARCHAR2(30) NOT NULL,
        splastname  VARCHAR2(30),
        sphiredate  DATE NOT NULL
    );

    ALTER TABLE nv_src_city ADD CONSTRAINT nv_src_city_pk PRIMARY KEY ( cityid );

    ALTER TABLE nv_src_customer ADD CONSTRAINT nv_src_customer_pk PRIMARY KEY ( custid );

    ALTER TABLE nv_src_order ADD CONSTRAINT nv_src_order_pk PRIMARY KEY ( orderid );

    ALTER TABLE nv_src_orderitem ADD CONSTRAINT nv_src_orderitem_pk PRIMARY KEY ( oitemid, nv_src_order_orderid );

    ALTER TABLE nv_src_product ADD CONSTRAINT nv_src_product_pk PRIMARY KEY ( prodid );

    ALTER TABLE nv_src_region ADD CONSTRAINT nv_src_region_pk PRIMARY KEY ( regid );

    ALTER TABLE nv_src_salesperson ADD CONSTRAINT nv_src_salesperson_pk PRIMARY KEY ( spid );

    ALTER TABLE nv_src_city
        ADD CONSTRAINT nv_src_city_region_fk FOREIGN KEY ( nv_src_region_regid )
            REFERENCES nv_src_region ( regid );

    ALTER TABLE nv_src_customer
        ADD CONSTRAINT nv_src_customer_city_fk FOREIGN KEY ( nv_src_city_cityid )
            REFERENCES nv_src_city ( cityid );

    ALTER TABLE nv_src_customer
        ADD CONSTRAINT nv_src_customer_salesperson_fk FOREIGN KEY ( nv_src_salesperson_spid )
            REFERENCES nv_src_salesperson ( spid );

    ALTER TABLE nv_src_order
        ADD CONSTRAINT nv_src_order_customer_fk FOREIGN KEY ( nv_src_customer_custid )
            REFERENCES nv_src_customer ( custid );

    ALTER TABLE nv_src_orderitem
        ADD CONSTRAINT nv_src_orderitem_order_fk FOREIGN KEY ( nv_src_order_orderid )
            REFERENCES nv_src_order ( orderid );

    ALTER TABLE nv_src_orderitem
        ADD CONSTRAINT nv_src_orderitem_product_fk FOREIGN KEY ( nv_src_product_prodid )
            REFERENCES nv_src_product ( prodid );
```
