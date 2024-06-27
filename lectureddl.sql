create table lecture (
    id bigint not null,
    name varchar(255),
    primary key (id)
)

create table lecture_schedule (
    applied_user_cnt integer,
    max_user_cnt integer,
    date_millis bigint,
    id bigint not null,
    lecture_id bigint,
    primary key (id)
)

create table lecture_schedule_history (
    id bigint not null,
    lecture_schedule_id bigint not null,
    update_millis bigint,
    user_id bigint not null,
    primary key (id),
    constraint lectureScheduleUserUnique unique (lecture_schedule_id, user_id)
)