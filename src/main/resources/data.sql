INSERT INTO USUARIOS(nome, email, senha, tipo_usuario) VALUES('Gabriel', 'gabriel@gmail.com', '$2a$10$MbLZ/NqJFZ.WAxRsLE95F.uwHYL7qechSg4L.IoXj/oxvTwgpoGRe', 'MODERADOR');
INSERT INTO USUARIOS(nome, email, senha, tipo_usuario) VALUES('Gabriel1', 'gabriel1@gmail.com', '$2a$10$MbLZ/NqJFZ.WAxRsLE95F.uwHYL7qechSg4L.IoXj/oxvTwgpoGRe', 'BOLSISTA');
INSERT INTO USUARIOS(nome, email, senha, tipo_usuario) VALUES('Gabriel2', 'gabriel2@gmail.com', '$2a$10$MbLZ/NqJFZ.WAxRsLE95F.uwHYL7qechSg4L.IoXj/oxvTwgpoGRe', 'BOLSISTA');
INSERT INTO USUARIOS(nome, email, senha, tipo_usuario) VALUES('Gabriel3', 'gabriel3@gmail.com', '$2a$10$MbLZ/NqJFZ.WAxRsLE95F.uwHYL7qechSg4L.IoXj/oxvTwgpoGRe', 'BOLSISTA');
INSERT INTO USUARIOS(nome, email, senha, tipo_usuario) VALUES('Gabriel4', 'gabriel4@gmail.com', '$2a$10$MbLZ/NqJFZ.WAxRsLE95F.uwHYL7qechSg4L.IoXj/oxvTwgpoGRe', 'BOLSISTA');
INSERT INTO USUARIOS(nome, email, senha, tipo_usuario) VALUES('Gabriel5', 'gabriel5@gmail.com', '$2a$10$MbLZ/NqJFZ.WAxRsLE95F.uwHYL7qechSg4L.IoXj/oxvTwgpoGRe', 'BOLSISTA');

INSERT INTO PERFIL(id, nome) VALUES(1, 'ROLE_ALUNO');
INSERT INTO PERFIL(id, nome) VALUES(2, 'ROLE_MODERADOR');

INSERT INTO USUARIOS_PERFIS(usuario_id, perfis_id) VALUES(1,2);
INSERT INTO USUARIOS_PERFIS(usuario_id, perfis_id) VALUES(2,1);
