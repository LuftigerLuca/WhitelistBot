create table if not exists guilds (
    id varchar(32) not null,
    name varchar(255) not null,
    prefix varchar(32) not null,
    language varchar(32) not null,

    primary key (id)
);

create table if not exists users (
    guild_id varchar(32) not null,
    user_id varchar(32) not null,
    can_configure boolean not null,
    can_whitelist boolean not null,
    can_unwhitelist boolean not null,

    primary key (guild_id, user_id),
    foreign key (guild_id) references guilds(id)
);