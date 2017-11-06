delimiter //
    
create trigger borrow_delete_change_reader_total after delete on borrow
for each row
begin
	
	if (select total
		from reader
        where reader.usr_num = old.usr_num) > 0 then
		update reader
        set total = total-1
        where usr_num = old.usr_num;
-- 	else
-- 		rollback to savepoint_i;
	end if;
end//

