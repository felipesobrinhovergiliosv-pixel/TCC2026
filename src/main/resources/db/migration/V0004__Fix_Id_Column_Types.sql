-- V0001 criou todo id/FK como INT, mas as entidades JPA usam Long (BIGINT). Sob
-- spring.jpa.hibernate.ddl-auto=validate isso quebra o boot da aplicação ("wrong column
-- type encountered"). MySQL/MariaDB não deixa mudar o tipo de uma coluna com FK mesmo com
-- FOREIGN_KEY_CHECKS=0, então as constraints (nomes padrão gerados na V0001) precisam ser
-- removidas antes do ALTER e recriadas depois.
ALTER TABLE comentario DROP FOREIGN KEY comentario_ibfk_1;
ALTER TABLE comentario DROP FOREIGN KEY comentario_ibfk_2;
ALTER TABLE comentario DROP FOREIGN KEY comentario_ibfk_3;
ALTER TABLE comentario DROP FOREIGN KEY comentario_ibfk_4;
ALTER TABLE game DROP FOREIGN KEY game_ibfk_1;
ALTER TABLE licao DROP FOREIGN KEY licao_ibfk_1;
ALTER TABLE post DROP FOREIGN KEY post_ibfk_1;
ALTER TABLE post DROP FOREIGN KEY post_ibfk_2;
ALTER TABLE post DROP FOREIGN KEY post_ibfk_3;
ALTER TABLE progresso_usuario DROP FOREIGN KEY progresso_usuario_ibfk_1;
ALTER TABLE progresso_usuario DROP FOREIGN KEY progresso_usuario_ibfk_2;

ALTER TABLE user MODIFY COLUMN id BIGINT NOT NULL AUTO_INCREMENT;
ALTER TABLE modulo MODIFY COLUMN id BIGINT NOT NULL AUTO_INCREMENT;
ALTER TABLE midia MODIFY COLUMN id BIGINT NOT NULL AUTO_INCREMENT;
ALTER TABLE categoria_forum MODIFY COLUMN id BIGINT NOT NULL AUTO_INCREMENT;

ALTER TABLE licao MODIFY COLUMN id BIGINT NOT NULL AUTO_INCREMENT;
ALTER TABLE licao MODIFY COLUMN modulo_id BIGINT NOT NULL;

ALTER TABLE progresso_usuario MODIFY COLUMN id BIGINT NOT NULL AUTO_INCREMENT;
ALTER TABLE progresso_usuario MODIFY COLUMN user_id BIGINT NOT NULL;
ALTER TABLE progresso_usuario MODIFY COLUMN licao_id BIGINT NOT NULL;

ALTER TABLE post MODIFY COLUMN id BIGINT NOT NULL AUTO_INCREMENT;
ALTER TABLE post MODIFY COLUMN categoria_id BIGINT NOT NULL;
ALTER TABLE post MODIFY COLUMN user_id BIGINT NOT NULL;
ALTER TABLE post MODIFY COLUMN midia_id BIGINT NULL;

ALTER TABLE comentario MODIFY COLUMN id BIGINT NOT NULL AUTO_INCREMENT;
ALTER TABLE comentario MODIFY COLUMN post_id BIGINT NOT NULL;
ALTER TABLE comentario MODIFY COLUMN user_id BIGINT NOT NULL;
ALTER TABLE comentario MODIFY COLUMN parent_id BIGINT NULL;
ALTER TABLE comentario MODIFY COLUMN midia_id BIGINT NULL;

ALTER TABLE game MODIFY COLUMN id BIGINT NOT NULL AUTO_INCREMENT;
ALTER TABLE game MODIFY COLUMN user_id BIGINT NOT NULL;

-- Post.data_publicacao e ProgressoUsuario.dataConclusao são LocalDate (sem hora) nos
-- entities, mas as colunas eram DATETIME.
ALTER TABLE post MODIFY COLUMN data_publicacao DATE NOT NULL;
ALTER TABLE progresso_usuario MODIFY COLUMN data_conclusao DATE NULL;

ALTER TABLE licao ADD FOREIGN KEY (modulo_id) REFERENCES modulo(id);
ALTER TABLE progresso_usuario ADD FOREIGN KEY (user_id) REFERENCES user(id);
ALTER TABLE progresso_usuario ADD FOREIGN KEY (licao_id) REFERENCES licao(id);
ALTER TABLE post ADD FOREIGN KEY (categoria_id) REFERENCES categoria_forum(id);
ALTER TABLE post ADD FOREIGN KEY (user_id) REFERENCES user(id);
ALTER TABLE post ADD FOREIGN KEY (midia_id) REFERENCES midia(id);
ALTER TABLE comentario ADD FOREIGN KEY (post_id) REFERENCES post(id);
ALTER TABLE comentario ADD FOREIGN KEY (user_id) REFERENCES user(id);
ALTER TABLE comentario ADD FOREIGN KEY (parent_id) REFERENCES comentario(id);
ALTER TABLE comentario ADD FOREIGN KEY (midia_id) REFERENCES midia(id);
ALTER TABLE game ADD FOREIGN KEY (user_id) REFERENCES user(id);
