#!/bin/bash
psql -v ON_ERROR_STOP=1 --username postgres <<-EOSQL

  CREATE DATABASE "job-search"
    WITH OWNER = postgres
    ENCODING = 'UTF8'
    TABLESPACE = pg_default
    LC_COLLATE = 'en_US.utf8'
    LC_CTYPE = 'en_US.utf8'
    CONNECTION LIMIT = -1;

  GRANT CONNECT, TEMPORARY ON DATABASE "job-search" TO public;
  GRANT ALL ON DATABASE "job-search" TO postgres;

EOSQL
