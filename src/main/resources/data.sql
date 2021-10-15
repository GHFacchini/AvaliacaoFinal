INSERT INTO USUARIOS(nome, email, senha) VALUES('Gabriel', 'gabriel@gmail.com', '$2a$10$MbLZ/NqJFZ.WAxRsLE95F.uwHYL7qechSg4L.IoXj/oxvTwgpoGRe');
INSERT INTO USUARIOS(nome, email, senha) VALUES('Gabriel1', 'gabriel1@gmail.com', '$2a$10$MbLZ/NqJFZ.WAxRsLE95F.uwHYL7qechSg4L.IoXj/oxvTwgpoGRe');

INSERT INTO PERFIL(id, nome) VALUES(1, 'ROLE_BOLSISTA');
INSERT INTO PERFIL(id, nome) VALUES(2, 'ROLE_MODERADOR');

INSERT INTO USUARIOS_PERFIS(usuario_id, perfis_id) VALUES(1,2);
INSERT INTO USUARIOS_PERFIS(usuario_id, perfis_id) VALUES(2,1);