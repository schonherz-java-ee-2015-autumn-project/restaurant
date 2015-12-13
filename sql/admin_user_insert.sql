delimiter $$
create procedure `create_default_user`()
begin
    declare roleuserid bigint default null;
    declare userid bigint default null;
    declare roleofuser bigint default null;
    set roleuserid := (select r.id from roles r where r.role_name like '%USER%' limit 1);
    if(roleuserid is null) then
        insert into roles (role_name) values ('ROLE_USER');
        set roleuserid := (select r.id from roles r where r.role_name like '%USER%' limit 1);
    end if;
    set userid := (select id from users where username like 'admin' limit 1);
    if(userid is null) then
        insert into users (pass, username, full_name, phone_number)
        values ('$2a$10$7YJsQW9reBfaPbyJnjkXf.S6qtr5uU8JNX/KUp3LrVeZIssr78a.a', 'admin', 'Admin Béla', '+36301234567');
        set userid := (select id from users where username like 'admin' limit 1);
    end if;
    set roleofuser := (select s.user_id from users_roles_sw s where s.user_id = userid and s.role_id = roleuserid);
    if(roleofuser is null) then
		insert into users_roles_sw (user_id, role_id) values (userid, roleuserid);
        set roleofuser := (select s.user_id from users_roles_sw s where s.user_id = userid and s.role_id = roleuserid);
    end if;
end$$
delimiter ;
call create_default_user;