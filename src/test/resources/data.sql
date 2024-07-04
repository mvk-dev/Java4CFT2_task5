delete from account;
delete from account_pool;
delete from tpp_product_register;
delete from tpp_ref_product_register_type;
delete from tpp_ref_account_type;
delete from tpp_ref_product_class;
delete from agreement;
delete from tpp_product;

INSERT INTO tpp_ref_account_type (value)
VALUES ('Внутрибанковский');

with product_class as (
    INSERT INTO tpp_ref_product_class(value, gbi_code, gbi_name, product_row_code, product_row_name, subclass_code, subclass_name)
        values ('03.012.002', '03', 'Розничный бизнес', '012', 'Драг. металлы', '002', 'Хранение')
        returning id
), account_type as (
    insert into tpp_ref_account_type (value)
        values ('Клиентский')
        returning id
)
insert into tpp_ref_product_register_type (value, register_type_name, product_class_id, account_type_id)
select '03.012.002_47533_ComSoLd', 'Хранение ДМ.', product_class.id, account_type.id
from product_class, account_type;

with product_class as (
    insert into tpp_ref_product_class(value, gbi_code, gbi_name, product_row_code, product_row_name, subclass_code, subclass_name)
        values ('02.001.005', '02', 'Розничный бизнес', '001', 'Сырье', '005', 'Продажа')
        returning id
),  account_type as (
    select id from tpp_ref_account_type where value = 'Клиентский'
)
insert into tpp_ref_product_register_type (value, register_type_name, product_class_id, account_type_id)
select '02.001.005_45343_CoDowFF', 'Серебро. Выкуп.', product_class.id, account_type.id from product_class, account_type union
select '02.001.005_4217_Gold', 'Золото. Выкуп.', product_class.id, account_type.id from product_class, account_type;

INSERT INTO account_pool (branch_code
                         , currency_code
                         , mdm_code
                         , priority_code
                         , registry_type_id)
select '0022', '800', '15', '00', (select id from tpp_ref_product_register_type where value = '03.012.002_47533_ComSoLd') union
select '0021', '500', '13', '00', (select id from tpp_ref_product_register_type where value = '02.001.005_45343_CoDowFF') union
select '0021', '500', '13', '00', (select id from tpp_ref_product_register_type where value = '02.001.005_4217_Gold');

INSERT INTO account(account_pool_id, account_number, bussy)
SELECT id, '475335516415314841861', false
FROM account_pool
WHERE registry_type_id = (select id from tpp_ref_product_register_type where value = '03.012.002_47533_ComSoLd');

INSERT INTO account(account_pool_id, account_number, bussy)
SELECT id, '4753321651354151', false
FROM account_pool
WHERE registry_type_id = (select id from tpp_ref_product_register_type where value = '03.012.002_47533_ComSoLd');

INSERT INTO account(account_pool_id, account_number, bussy)
SELECT id, '4753352543276345', false
FROM account_pool
WHERE registry_type_id = (select id from tpp_ref_product_register_type where value = '03.012.002_47533_ComSoLd');

INSERT INTO account(account_pool_id, account_number, bussy)
SELECT id, '453432352436453276', false
FROM account_pool
WHERE registry_type_id = (select id from tpp_ref_product_register_type where value = '02.001.005_45343_CoDowFF');

INSERT INTO account(account_pool_id, account_number, bussy)
SELECT id, '45343221651354151', false
FROM account_pool
WHERE registry_type_id = (select id from tpp_ref_product_register_type where value = '02.001.005_45343_CoDowFF');

INSERT INTO account(account_pool_id, account_number, bussy)
SELECT id, '4534352543276345', false
FROM account_pool
WHERE registry_type_id = (select id from tpp_ref_product_register_type where value = '02.001.005_45343_CoDowFF');

INSERT INTO account(account_pool_id, account_number, bussy)
SELECT id, '4534352543298700', false
FROM account_pool
WHERE registry_type_id = (select id from tpp_ref_product_register_type where value = '02.001.005_4217_Gold');

-- Для теста на дублирование записей по product_code_id
insert into tpp_product(number)
values ('testProduct_1');

insert into agreement(product_id, number)
values (1, 'testAgr_1');