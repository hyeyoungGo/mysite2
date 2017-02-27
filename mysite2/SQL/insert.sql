select * from users;

select * from guestbook;

update guestbook set name = :var1, content = :var2  where no = :var3 and password = :var4;

rollback;

commit;