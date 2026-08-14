-- Campos de perfil (telefone, foto) e do negócio do MEI (empresa, cnpj, ramo, instagram),
-- usados pela página Perfil do frontend.
ALTER TABLE user
  ADD COLUMN telefone VARCHAR(20),
  ADD COLUMN foto MEDIUMTEXT,
  ADD COLUMN empresa VARCHAR(150),
  ADD COLUMN cnpj VARCHAR(18),
  ADD COLUMN ramo VARCHAR(100),
  ADD COLUMN instagram VARCHAR(100);
