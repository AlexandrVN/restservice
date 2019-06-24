UPDATE user_db SET password = PASSWORD(password);


#create extension if not exists pgcrypto;
#update usr set password = crypt(password, gen_salt('bf', 8));