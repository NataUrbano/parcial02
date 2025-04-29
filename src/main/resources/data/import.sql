-- Insertar roles
INSERT INTO roles (role_id, role) VALUES (1, 'ADMIN') ON CONFLICT (role_id) DO NOTHING;
INSERT INTO roles (role_id, role) VALUES (2, 'ACOMODADOR') ON CONFLICT (role_id) DO NOTHING;
INSERT INTO roles (role_id, role) VALUES (3, 'CLIENTE') ON CONFLICT (role_id) DO NOTHING;

-- Insertar usuarios
INSERT INTO users (user_id, username, password) VALUES (1, 'ADMIN', 'admin123') ON CONFLICT (user_id) DO NOTHING;
INSERT INTO users (user_id, username, password) VALUES (2, 'ACOMODADOR', 'acomodador123') ON CONFLICT (user_id) DO NOTHING;
INSERT INTO users (user_id, username, password) VALUES (3, 'CLIENTE', 'cliente123') ON CONFLICT (user_id) DO NOTHING;

-- Insertar users_roles
INSERT INTO users_roles (role_id, user_id) VALUES (1, 1);
INSERT INTO users_roles (role_id, user_id) VALUES (2, 2);
INSERT INTO users_roles (role_id, user_id) VALUES (3, 3);
