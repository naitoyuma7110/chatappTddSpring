CREATE TABLE IF NOT EXISTS channels (
  id INT NOT NULL,
  name VARCHAR(30) NOT NULL,
  PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS messages (
  id CHAR(49) NOT NULL,
  channel_id INT NOT NULL,
  text TEXT NOT NULL,
  username VARCHAR(30) NOT NULL,
  timestamp TIMESTAMP NOT NULL,
  PRIMARY KEY(id),
  CONSTRAINT fk_department_id
  FOREIGN KEY (channel_id)
  REFERENCES channels (id)
  ON DELETE CASCADE
);