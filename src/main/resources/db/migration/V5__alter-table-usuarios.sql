ALTER TABLE usuarios
    ADD COLUMN token varchar(64),
    ADD COLUMN expiracao_token timestamp;