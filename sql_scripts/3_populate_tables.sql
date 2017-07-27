TRUNCATE TABLE HOLIDAYS;

BEGIN
    pkg_holidays.insert_holiday('02-25');
    pkg_holidays.insert_holiday('04-11');
    pkg_holidays.insert_holiday('05-11');
    pkg_holidays.insert_holiday('07-11');
    pkg_holidays.insert_holiday('10-19');
    pkg_holidays.insert_holiday('11-23');
    pkg_holidays.insert_holiday('12-05');
    COMMIT;
END;
/

TRUNCATE TABLE USERS;

BEGIN
    INSERT INTO USERS(ID, LOGIN, PASSWORD) VALUES(users_seq.NEXTVAL, 'user1', 'user1');
    COMMIT;
END;
/