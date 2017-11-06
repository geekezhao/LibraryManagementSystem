delimiter //
    
create trigger borrow_if_fine after insert on borrow
for each row
begin
	if exists(select usr_num
		from fine
        where usr_num = new.usr_num) then
		rollback;
	end if;
end//
