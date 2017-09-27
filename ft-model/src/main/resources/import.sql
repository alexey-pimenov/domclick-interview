INSERT INTO USER (ID, FULL_NAME, LOGIN) VALUES (user_seq.nextval, 'Василий Васильевич', 'vasya'),
  (user_seq.nextval, 'Петр Петровчи', 'petya'),
  (user_seq.nextval, 'Иван Иванович', 'ivan');


INSERT INTO ACCOUNT (ID, ID_USER, NAME, BALANCE) VALUES
  (account_seq.nextval, 1, 'vasya-main', 0),
  (account_seq.nextval, 2, 'petya-main', 0),
  (account_seq.nextval, 3, 'ivan-main', 0.0);


INSERT INTO TRANSACTION (ID, ID_ACCOUNT, AMOUNT, time_created) VALUES
  (transaction_seq.nextval, 1, 300, CURRENT_TIMESTAMP()),
  (transaction_seq.nextval, 1, -50.50, CURRENT_TIMESTAMP()),
  (transaction_seq.nextval, 1, 200, CURRENT_TIMESTAMP()),
  (transaction_seq.nextval, 2, 500, CURRENT_TIMESTAMP()),
  (transaction_seq.nextval, 3, 10000, CURRENT_TIMESTAMP()),
  (transaction_seq.nextval, 3, -1000, CURRENT_TIMESTAMP()),
  (transaction_seq.nextval, 3, -1000, CURRENT_TIMESTAMP()),
  (transaction_seq.nextval, 3, 5000, CURRENT_TIMESTAMP());


UPDATE ACCOUNT acc
SET acc.BALANCE = (SELECT SUM(tr.AMOUNT)
                   FROM TRANSACTION tr
                   WHERE tr.ID_ACCOUNT = acc.ID);