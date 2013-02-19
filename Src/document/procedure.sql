CREATE OR REPLACE PROCEDURE stu_proc
(
  --多个用逗号隔开
  v_id IN NUMBER
) IS
  --多个用分号隔开
  v_max_id NUMBER;
  v_name VARCHAR2(20);
  v_raise EXCEPTION; 
BEGIN
  SELECT MAX(a.id) INTO v_max_id FROM student a;
  IF v_id>v_max_id THEN
    RAISE v_raise;
  END IF;
  SELECT o.sname INTO v_name FROM student o WHERE o.id=v_id;
  dbms_output.put_line('学生名称为：'||v_name);
EXCEPTION
  WHEN v_raise THEN 
    RAISE_APPLICATION_ERROR(-20010, 'v_id not exists!');
  WHEN NO_DATA_FOUND THEN 
    RAISE_APPLICATION_ERROR(-20011, 'ERROR：不存在！');
END stu_proc;
