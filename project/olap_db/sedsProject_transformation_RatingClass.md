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
