-- Marca usuários com permissão de administrador (gestão de conteúdo: modulo, licao, midia, categoria_forum).
ALTER TABLE user ADD COLUMN admin BOOLEAN NOT NULL DEFAULT FALSE;
