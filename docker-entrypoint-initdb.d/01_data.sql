INSERT INTO users(login, password, roles)
VALUES ('admin', '$argon2id$v=19$m=4096,t=3,p=1$Z296275rtoKE+6XjIEj2gg$hQqaCo7cDTVtja4XqJvTX+Sk6B4YjB22tnhyFbIkuqY',
        '{ACCOUNTS_VIEW_ALL, ACCOUNTS_EDIT_ALL, USERS_VIEW_ALL, USERS_EDIT_ALL}'
       ),
       ('operator', '$argon2id$v=19$m=4096,t=3,p=1$Z296275rtoKE+6XjIEj2gg$hQqaCo7cDTVtja4XqJvTX+Sk6B4YjB22tnhyFbIkuqY',
        '{ACCOUNTS_VIEW_ALL, USERS_VIEW_ALL}'
       ),
       ('vasya', '$argon2id$v=19$m=4096,t=3,p=1$Z296275rtoKE+6XjIEj2gg$hQqaCo7cDTVtja4XqJvTX+Sk6B4YjB22tnhyFbIkuqY',
        DEFAULT
       ),
       ('petya', '$argon2id$v=19$m=4096,t=3,p=1$Z296275rtoKE+6XjIEj2gg$hQqaCo7cDTVtja4XqJvTX+Sk6B4YjB22tnhyFbIkuqY',
        DEFAULT
       );