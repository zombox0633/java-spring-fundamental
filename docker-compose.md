docker-compose up -d
docker-compose down

CREATE TABLE users (
  id UUID NOT NULL PRIMARY KEY,
  email VARCHAR(250) NOT NULL,
  password VARCHAR(250),
  name VARCHAR(250) ,
  create_timestamp  TIMESTAMP WITH TIME ZONE NOT NULL,
  last_updated_timestamp TIMESTAMP WITH TIME ZONE NOT NULL
)

INSERT INTO users (id, email, password, name, create_timestamp, last_updated_timestamp)
VALUES(gen_random_uuid(),'ghostet360@gmail.com','123456789','earth',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP)

9dfc722b-2275-49d7-8c14-364979fefc51 users

ff909e36-c23f-4865-8a33-f1aa53047e52 category