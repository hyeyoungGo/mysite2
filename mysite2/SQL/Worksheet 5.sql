select * from	
	(select rownum as rn, a.*
		from ( select no,
					  name,
					  content,
					  to_char(reg_date, 'yyyy-mm-dd') as regDate
				from guestbook
		order by no desc) a)
	where (2-5)*2 <= rownum and rownum <= 2*5;


insert into guestbook  values (seq_guestbook.nextval, '11', '11', '11', sysdate);


commit;

   select no,
		  name,
		  content,
		  to_char(reg_date, 'yyyy-mm-dd') as regDate
	from guestbook
order by reg_date desc;

drop sequence seq_gallery;

create sequence seq_gallery
start with 1
increment by 1
maxvalue 9999999999;

commit;

select * from gallery;

select no, orgfile as orgFile, savefile as saveFile, comments
	from gallery;
	
insert into gallery values (seq_gallery.nextval, '123', '456', '아하');

delete from gallery;