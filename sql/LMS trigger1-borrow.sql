delimiter //
    
create trigger borrow_update after insert on borrow
for each row
begin
	if (select state
		from book_info
        where ISBN = new.ISBN and duplicate_num = new.duplicate_num)
        = 'on' then
		update book_info
        set state = 'out'
        where ISBN = new.ISBN and duplicate_num = new.duplicate_num;
	else
		rollback;
	end if;
end//
