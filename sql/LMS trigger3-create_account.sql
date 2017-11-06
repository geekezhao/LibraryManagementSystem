delimiter //
    
create trigger insert_protect after insert on reader
for each row
begin
	if new.phone<10000000000 or new.phone>=20000000000 then
		rollback;
	end if;
end//

