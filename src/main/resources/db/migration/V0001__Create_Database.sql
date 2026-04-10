-- ENUM
CREATE TABLE tipo_midia_enum (
  tipo ENUM('VIDEO', 'IMAGEM', 'AUDIO', 'NENHUM') PRIMARY KEY
);

-- USER
CREATE TABLE user (
  id INT AUTO_INCREMENT PRIMARY KEY,
  user VARCHAR(50) NOT NULL UNIQUE,
  email VARCHAR(100) NOT NULL UNIQUE,
  senha VARCHAR(255) NOT NULL,
  xp_total INT DEFAULT 0,
  nvl INT DEFAULT 1,
  data_criacao DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- MODULO
CREATE TABLE modulo (
  id INT AUTO_INCREMENT PRIMARY KEY,
  titulo VARCHAR(100) NOT NULL,
  ordem INT NOT NULL UNIQUE COMMENT 'Define a sequência: 1, 2, 3...'
);

-- MIDIA
CREATE TABLE midia (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(255) NOT NULL,
  midia_url VARCHAR(500),
  tipo_midia ENUM('VIDEO', 'IMAGEM', 'AUDIO', 'NENHUM')
);

-- CATEGORIA FORUM
CREATE TABLE categoria_forum (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(50) NOT NULL UNIQUE
);

-- LICAO
CREATE TABLE licao (
  id INT AUTO_INCREMENT PRIMARY KEY,
  modulo_id INT NOT NULL,
  titulo VARCHAR(100) NOT NULL,
  conteudo_json JSON COMMENT 'Guarda as perguntas, alternativas e respostas',
  xp_recompensa INT DEFAULT 10,
  FOREIGN KEY (modulo_id) REFERENCES modulo(id)
);

-- PROGRESSO USUARIO
CREATE TABLE progresso_usuario (
  user_id INT NOT NULL,
  licao_id INT NOT NULL,
  concluido BOOLEAN DEFAULT FALSE,
  data_conclusao DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (user_id, licao_id),
  FOREIGN KEY (user_id) REFERENCES user(id),
  FOREIGN KEY (licao_id) REFERENCES licao(id)
);

-- POST
CREATE TABLE post (
  id INT AUTO_INCREMENT PRIMARY KEY,
  categoria_id INT NOT NULL,
  user_id INT NOT NULL,
  titulo VARCHAR(200) NOT NULL,
  conteudo_texto TEXT NOT NULL,
  midia_id INT NOT NULL,
  upvotes INT DEFAULT 0,
  data_publicacao DATETIME DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (categoria_id) REFERENCES categoria_forum(id),
  FOREIGN KEY (user_id) REFERENCES user(id),
  FOREIGN KEY (midia_id) REFERENCES midia(id)
);

-- COMENTARIO
CREATE TABLE comentario (
  id INT AUTO_INCREMENT PRIMARY KEY,
  post_id INT NOT NULL,
  user_id INT NOT NULL,
  parent_id INT NULL,
  conteudo_texto TEXT NOT NULL,
  midia_id INT NOT NULL,
  upvotes INT DEFAULT 0,
  data_publicacao DATETIME DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (post_id) REFERENCES post(id),
  FOREIGN KEY (user_id) REFERENCES user(id),
  FOREIGN KEY (parent_id) REFERENCES comentario(id),
  FOREIGN KEY (midia_id) REFERENCES midia(id)
);