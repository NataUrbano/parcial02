-- Insertar roles
INSERT INTO roles (role_id, role) VALUES (1, 'RECTOR') ON CONFLICT (role_id) DO NOTHING;
INSERT INTO roles (role_id, role) VALUES (2, 'DOCENTE') ON CONFLICT (role_id) DO NOTHING;
INSERT INTO roles (role_id, role) VALUES (3, 'ESTUDIANTE') ON CONFLICT (role_id) DO NOTHING;

-- Insertar usuarios
INSERT INTO users (user_id, username, password) VALUES (1, 'RECTOR', 'admin') ON CONFLICT (user_id) DO NOTHING;
INSERT INTO users (user_id, username, password) VALUES (2, 'DOCENTE1', 'docente') ON CONFLICT (user_id) DO NOTHING;
INSERT INTO users (user_id, username, password) VALUES (3, 'DOCENTE2', 'docente') ON CONFLICT (user_id) DO NOTHING;
INSERT INTO users (user_id, username, password) VALUES (4, 'ESTUDIANTE1', 'estudiante') ON CONFLICT (user_id) DO NOTHING;
INSERT INTO users (user_id, username, password) VALUES (5, 'ESTUDIANTE2', 'estudiante') ON CONFLICT (user_id) DO NOTHING;

-- Insertar users_roles
INSERT INTO users_roles (role_id, user_id) VALUES (1, 1);
INSERT INTO users_roles (role_id, user_id) VALUES (2, 2);
INSERT INTO users_roles (role_id, user_id) VALUES (2, 3);
INSERT INTO users_roles (role_id, user_id) VALUES (3, 4);
INSERT INTO users_roles (role_id, user_id) VALUES (3, 5);
