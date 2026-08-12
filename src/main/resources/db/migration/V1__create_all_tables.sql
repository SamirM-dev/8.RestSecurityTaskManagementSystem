CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY ,
    name VARCHAR(50) NOT NULL ,
    email VARCHAR(100) NOT NULL ,
    password VARCHAR(100) ,
    provider VARCHAR(50) ,
    provider_id VARCHAR(50),
    enabled BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT uq_users_email UNIQUE (email),
    CONSTRAINT uq_users_provider_provider_id UNIQUE (provider,provider_id)
);

CREATE TABLE tasks(
    id BIGSERIAL PRIMARY KEY ,
    title VARCHAR(100) NOT NULL ,
    description VARCHAR(500) NOT NULL ,
    status VARCHAR(50) NOT NULL DEFAULT 'NEW',
    priority VARCHAR(50)  NOT NULL ,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    user_id BIGINT NOT NULL ,

    CONSTRAINT ch_tasks_status CHECK ( status IN ('NEW','IN_PROGRESS','DONE') ),
    CONSTRAINT ch_tasks_priority CHECK ( priority IN ('LOW','MEDIUM','HIGH') ),
    CONSTRAINT fk_tasks_users FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE comments(
    id BIGSERIAL PRIMARY KEY ,
    text VARCHAR(500) NOT NULL ,
    author VARCHAR(100) NOT NULL ,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    task_id BIGINT NOT NULL ,

    CONSTRAINT fk_comments_tasks FOREIGN KEY (task_id) REFERENCES tasks(id)
);

CREATE TABLE refresh_tokens(
    id BIGSERIAL PRIMARY KEY ,
    token VARCHAR(100) NOT NULL ,
    user_id BIGINT NOT NULL ,
    expires_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_refresh_tokens_users FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE user_roles(
    user_id BIGINT NOT NULL ,
    role VARCHAR(50) NOT NULL DEFAULT 'ROLE_USER',

    CONSTRAINT pk_user_roles PRIMARY KEY (user_id,role),
    CONSTRAINT fk_user_roles_users FOREIGN KEY (user_id) REFERENCES users(id)
);