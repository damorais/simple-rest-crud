# --- !Ups

create table "people" (
  "id" serial NOT NULL,
  "name" varchar not null,
  CONSTRAINT "people_pk" PRIMARY KEY (id)
);