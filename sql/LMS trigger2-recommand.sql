delimiter //
    
create trigger recommand_update after insert on book_in_need
for each row
begin
	if new.name =some(select book_name,writer
		from book
        where book_name = new.book_name and writer = new.writer) then
		rollback;
	end if;
end//

-- 推荐书目查重