CREATE TABLE HOLIDAYS
(
    ID            NUMBER PRIMARY KEY,
    HOLIDAY_DATE  DATE
);

CREATE SEQUENCE holidays_seq START WITH 1;

CREATE TABLE USERS
(
    ID          NUMBER PRIMARY KEY,
    LOGIN       VARCHAR2(30) NOT NULL UNIQUE,
    PASSWORD    VARCHAR2(30) NOT NULL UNIQUE
);

CREATE SEQUENCE users_seq START WITH 1;


CREATE OR REPLACE FUNCTION HOLIDAY_BASE_YEAR RETURN NUMBER AS BEGIN RETURN 1904; END;
/
CREATE OR REPLACE FUNCTION HOLIDAY_DATE_FMT RETURN CHAR AS BEGIN RETURN 'YYYY-MM-DD'; END;
/

CREATE OR REPLACE PACKAGE pkg_holidays
AS
    -- constants declarations
    FUNCTION HOLIDAY_BASE_YEAR RETURN NUMBER;
    FUNCTION HOLIDAY_DATE_FMT RETURN CHAR;

    -- functions and procedures
    PROCEDURE insert_holiday
    (
        p_date    IN VARCHAR2
    );
    
    PROCEDURE count_holidays_between
    (
        p_start_date        IN DATE,
        p_end_date          IN DATE,
        p_holidays_count    OUT NUMBER
    );
    
    FUNCTION to_holiday_base_year(p_date IN DATE) RETURN DATE;

END pkg_holidays;
/


CREATE OR REPLACE PACKAGE BODY pkg_holidays
AS

    FUNCTION HOLIDAY_BASE_YEAR RETURN NUMBER AS BEGIN RETURN 1904; 
    END;
    FUNCTION HOLIDAY_DATE_FMT RETURN CHAR AS BEGIN RETURN 'YYYY-MM-DD'; 
    END;

    PROCEDURE insert_holiday
    (
        p_date      IN VARCHAR2
    )
    IS
    
    BEGIN
        INSERT INTO HOLIDAYS(ID, HOLIDAY_DATE) 
        VALUES (holidays_seq.NEXTVAL, to_date(
                    HOLIDAY_BASE_YEAR() || '-' || p_date, HOLIDAY_DATE_FMT()));
    END insert_holiday;
    
    PROCEDURE count_holidays_between
    (
        p_start_date        IN DATE,
        p_end_date          IN DATE,
        p_holidays_count    OUT NUMBER
    )
    IS
    
    start_year    NUMBER := EXTRACT(YEAR FROM p_start_date);
    end_year      NUMBER := EXTRACT(YEAR FROM p_end_date);
    years_between NUMBER := end_year - start_year;
    
    BEGIN
        IF years_between > 0 THEN
            DECLARE
                start_year_holidays_count    NUMBER;
                end_year_holidays_count      NUMBER;
                years_between_holidays_count NUMBER;
            BEGIN
                SELECT count(*) INTO start_year_holidays_count
                    FROM HOLIDAYS
                    WHERE HOLIDAY_DATE >= to_holiday_base_year(p_start_date)
                      AND HOLIDAY_DATE < to_date(HOLIDAY_BASE_YEAR() + 1, 'YYYY'); -- to year end (next year start minus one day)
                SELECT count(*) INTO end_year_holidays_count
                    FROM HOLIDAYS
                    WHERE HOLIDAY_DATE <= to_holiday_base_year(p_end_date);
                SELECT count(*) * (years_between - 1) -- -1 because one year is already calculated above
                    INTO years_between_holidays_count
                    FROM HOLIDAYS;
                p_holidays_count := start_year_holidays_count +
                    years_between_holidays_count + 
                    end_year_holidays_count;
            END;
        ELSE
            SELECT count(*) INTO p_holidays_count
                FROM HOLIDAYS
                WHERE HOLIDAY_DATE >= to_holiday_base_year(p_start_date)
                  AND HOLIDAY_DATE <= to_holiday_base_year(p_end_date);
        END IF;
    END count_holidays_between;
    
    FUNCTION to_holiday_base_year(p_date IN DATE)
    RETURN DATE
    IS
    BEGIN
        RETURN add_months(p_date, (HOLIDAY_BASE_YEAR - EXTRACT(YEAR FROM p_date))*12);
    END to_holiday_base_year;
    
END pkg_holidays;
/

-- should select holidays between two dates correctly
DECLARE
    expected_holidays_count1 NUMBER := 10;
    expected_holidays_count2 NUMBER := 24;
    actual_holidays_count1 NUMBER;
    actual_holidays_count2 NUMBER;
BEGIN
    pkg_holidays.insert_holiday('02-25');
    pkg_holidays.insert_holiday('04-11');
    pkg_holidays.insert_holiday('05-11');
    pkg_holidays.insert_holiday('07-11');
    pkg_holidays.insert_holiday('10-19');
    pkg_holidays.insert_holiday('11-23');
    pkg_holidays.insert_holiday('12-05');

    pkg_holidays.count_holidays_between(DATE '2016-03-10', DATE '2017-08-10', actual_holidays_count1);
    pkg_holidays.count_holidays_between(DATE '2014-03-10', DATE '2017-08-10', actual_holidays_count2);
    
    IF expected_holidays_count1 != actual_holidays_count1 THEN
        dbms_output.put_line('expected_holidays_count1 = ' || expected_holidays_count1 || 
                             '; actual_holidays_count1: ' || actual_holidays_count1);
    END IF;
    IF expected_holidays_count2 != actual_holidays_count2 THEN
        dbms_output.put_line('expected_holidays_count2 = ' || expected_holidays_count2 || 
                             '; actual_holidays_count2: ' || actual_holidays_count2);
    END IF;
    dbms_output.put_line('Test is finished.');
    
    ROLLBACK;
END;
/
    

    
    
    
    