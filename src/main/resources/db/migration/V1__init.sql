CREATE TABLE tasks (
  id BIGSERIAL PRIMARY KEY,
  title TEXT NOT NULL
);

CREATE TABLE task_idempotency (
  id BIGSERIAL PRIMARY KEY,
  idempotency_key TEXT NOT NULL UNIQUE,
  request_hash TEXT NOT NULL,
  task_id BIGINT NOT NULL REFERENCES tasks(id),
  created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);
