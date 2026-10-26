create database if not exists knowledge_blog
    default character set utf8mb4
    default collate utf8mb4_0900_ai_ci;

use knowledge_blog;

set names utf8mb4;

create table users
(
    id            int auto_increment comment '用户 id'
        primary key,
    username      varchar(32)                        not null comment '唯一登录名',
    password_hash varchar(100)                       not null comment 'bcrypt 密码哈希',
    email         varchar(254)                       not null comment '邮箱地址',
    create_time   datetime default current_timestamp not null comment '创建时间',
    update_time   datetime default current_timestamp not null on update current_timestamp comment '更新时间',
    constraint uk_users_username
        unique (username)
) comment '平台用户表';



create table categories
(
    id          int auto_increment comment '分类 id'
        primary key,
    name        varchar(50)                        not null comment '唯一分类名',
    creator_id  int                                not null comment '创建者用户 id',
    create_time datetime default current_timestamp not null comment '创建时间',
    update_time datetime default current_timestamp not null on update current_timestamp comment '更新时间',
    constraint uk_categories_name
        unique (name),
    key         idx_categories_creator_id (creator_id),
    constraint fk_categories_creator
        foreign key (creator_id) references users (id)
            on delete restrict
            on update restrict
) comment '文章分类表';



create table tags
(
    id          int auto_increment comment '文章标签 id'
        primary key,
    name        varchar(50)                        not null comment '唯一标签名',
    create_time datetime default current_timestamp not null comment '创建时间',
    constraint uk_tags_name
        unique (name)
) comment '文章标签表';



create table articles
(
    id          int auto_increment comment '文章 id'
        primary key,
    title       varchar(200)                       not null comment '文章标题',
    content     longtext                           not null comment '文章内容',
    cover_url   varchar(2048) null comment '封面图 url（可选）',
    category_id int                                not null comment '分类 id',
    author_id   int                                not null comment '作者用户 id',
    create_time datetime default current_timestamp not null comment '发布时间',
    update_time datetime default current_timestamp not null on update current_timestamp comment '更新时间',
    key         idx_articles_category_create (category_id, create_time),
    key         idx_articles_author_create (author_id, create_time),
    key         idx_articles_create_time (create_time),
    constraint fk_articles_category
        foreign key (category_id) references categories (id)
            on delete restrict
            on update restrict,
    constraint fk_articles_author
        foreign key (author_id) references users (id)
            on delete restrict
            on update restrict
) comment '已发布文章表';



create table article_tags
(
    article_id int not null comment '文章 id',
    tag_id     int not null comment '标签 id',
    primary key (article_id, tag_id),
    key        idx_article_tag_id (tag_id),
    constraint fk_article_tags_article
        foreign key (article_id) references articles (id)
            on delete cascade
            on update restrict,
    constraint fk_article_tags_tag
        foreign key (tag_id) references tags (id)
            on delete restrict
            on update restrict
) comment '文章与标签的多对多关联表';



create table comments
(
    id          int auto_increment comment '评论 id'
        primary key,
    article_id  int                                not null comment '文章 id',
    author_id   int                                not null comment '评论作者用户 id',
    content     text                               not null comment '评论内容',
    create_time datetime default current_timestamp not null comment '创建时间',
    key         idx_comments_article_create (article_id, create_time),
    key         idx_comments_author_id (author_id),
    constraint fk_comments_article
        foreign key (article_id) references articles (id)
            on delete cascade
            on update restrict,
    constraint fk_comments_author
        foreign key (author_id) references users (id)
            on delete restrict
            on update restrict
) comment '文章评论';
