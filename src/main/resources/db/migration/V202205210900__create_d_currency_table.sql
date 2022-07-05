
create table LAB4_CHEPIHAVV_D_CURRENCY(
                "ID" number,
                "R030" number not null,
                "TXT" varchar2(64) not null,
                "CC" varchar2(3) not null,
                constraint "LAB4_CHEPIHAVV_D_CURRENCY_PK" primary key ("ID")
);
/

CREATE sequence "LAB4_CHEPIHAVV_D_CURRENCY_SEQ";
/

CREATE trigger "BI_LAB4_CHEPIHAVV_D_CURRENCY"
    before insert on "LAB4_CHEPIHAVV_D_CURRENCY"
    for each row
begin
    if :NEW."ID" is null then
        select "LAB4_CHEPIHAVV_D_CURRENCY_SEQ".nextval into :NEW."ID" from dual;
    end if;
end;
/