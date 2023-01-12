Table input:

```SQL
    SELECT DISTINCT
    CASE
        WHEN tourRating > 8 THEN 'magnificent'
        WHEN tourRating > 6 THEN 'exceptional'
        WHEN tourRating > 4 THEN 'decent'
        WHEN tourRating > 2 THEN 'correct'
        ELSE 'appalling'
    END rcName
    FROM NVP_SRC_Tour
    ORDER BY rcName
```

Oracle SQL insert:

```SQL
    INSERT INTO NVP_DW_RatingClass (rc, rcName) VALUES (1, 'appalling');
    INSERT INTO NVP_DW_RatingClass (rc, rcName) VALUES (2, 'correct');
    INSERT INTO NVP_DW_RatingClass (rc, rcName) VALUES (3, 'decent');
    INSERT INTO NVP_DW_RatingClass (rc, rcName) VALUES (4, 'exceptional');
    INSERT INTO NVP_DW_RatingClass (rc, rcName) VALUES (5, 'magnificent');
```

Check:

```SQL
    SELECT * FROM NVP_DW_RatingClass;
```

Other:

```SQL
    SELECT DISTINCT
    CASE
        WHEN tourRating > 8 THEN 'magnificent'
        WHEN tourRating > 6 THEN 'exceptional'
        WHEN tourRating > 4 THEN 'decent'
        WHEN tourRating > 2 THEN 'correct'
        ELSE 'appalling'
    END ratingCategory
    FROM NVP_SRC_Tour
    ORDER BY ratingCategory;
```
