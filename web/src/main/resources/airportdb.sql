CREATE TABLE IF NOT EXISTS auth
(
  login character varying(10) NOT NULL,
  password character varying(10) NOT NULL,
  CONSTRAINT auth_pkey PRIMARY KEY (login),
  CONSTRAINT login_check CHECK (login ~ '\w{3,10}'),
  CONSTRAINT pass_check CHECK (password ~ '\w{3,10}')
);

CREATE TABLE IF NOT EXISTS destination
(
  city character varying(50) NOT NULL,
  CONSTRAINT dest_pkey PRIMARY KEY (city)
);

CREATE TABLE IF NOT EXISTS history
(
  login character varying(10) NOT NULL,
  city character varying(50) NOT NULL,
  id serial NOT NULL,
  date date NOT NULL DEFAULT CURRENT_DATE,
  CONSTRAINT history_pkey PRIMARY KEY (id),
  CONSTRAINT city_fkey FOREIGN KEY (city) REFERENCES destination (city),
  CONSTRAINT login_fkey FOREIGN KEY (login) REFERENCES auth (login)
);

CREATE TABLE IF NOT EXISTS roles
(
  role character varying(10) NOT NULL,
  CONSTRAINT roles_pkey PRIMARY KEY (role)
);

CREATE TABLE IF NOT EXISTS login_role
(
  login character varying(10) NOT NULL,
  role character varying(10) NOT NULL,
  CONSTRAINT login_role_pkey PRIMARY KEY (login, role),
  CONSTRAINT login_fkey FOREIGN KEY (login) REFERENCES public.auth (login) MATCH FULL
      ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT role_fkey FOREIGN KEY (role) REFERENCES public.roles (role) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

INSERT INTO roles(role) VALUES ('Admin');
INSERT INTO roles(role) VALUES ('Member');
INSERT INTO auth(login, password) VALUES ('admin', 123456);
INSERT INTO login_role(login, role) VALUES ('admin', 'Admin');



