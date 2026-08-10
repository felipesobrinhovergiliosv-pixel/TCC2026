-- Comentários de texto simples não deveriam ser obrigados a referenciar uma mídia.
ALTER TABLE comentario MODIFY COLUMN midia_id INT NULL;
