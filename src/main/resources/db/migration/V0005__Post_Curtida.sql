-- Registra quem curtiu qual post, pra impedir curtida duplicada e permitir descurtir.
CREATE TABLE post_curtida (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  post_id BIGINT NOT NULL,
  user_id BIGINT NOT NULL,
  UNIQUE KEY uk_post_curtida_post_user (post_id, user_id),
  FOREIGN KEY (post_id) REFERENCES post(id),
  FOREIGN KEY (user_id) REFERENCES user(id)
);
