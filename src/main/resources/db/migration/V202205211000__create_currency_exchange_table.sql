
create table LAB4_CHEPIHAVV_CURRENCY_EXCHANGE(
                "ID" number,
                "R030" number not null,
                "RATE" NUMBER(20,10) not null,
                "EXCHANGEDATE" date not null,
                constraint "LAB4_CHEPIHAVV_CURRENCY_EXCHANGE_PK" primary key ("ID")
);
/

CREATE sequence "LAB4_CHEPIHAVV_CURRENCY_EXCHANGE_SEQ";
/

CREATE trigger "BI_LAB4_CHEPIHAVV_CURRENCY_EXCHANGE"
    before insert on "LAB4_CHEPIHAVV_CURRENCY_EXCHANGE"
    for each row
begin
    if :NEW."ID" is null then
        select "LAB4_CHEPIHAVV_CURRENCY_EXCHANGE_SEQ".nextval into :NEW."ID" from dual;
    end if;
end;
/