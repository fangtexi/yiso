# 建表脚本
# @author <a href="https://github.com/liyupi">程序员鱼皮</a>
# @from <a href="https://yupi.icu">编程导航知识星球</a>

# -- 创建库
# create database if not exists my_db;

-- 切换库
use yiso;


INSERT INTO post (title, content, tags, thumbNum, favourNum, userId, createTime, updateTime, isDelete) VALUES ('人生第一次旅行，看到了大海的壮观', '我终于有机会去旅行了！这是我第一次踏上飞机，来到了离家很远的地方。当我走近海边时，眼前的景象让我瞠目结舌——那壮阔的海景，那无边无际的海洋，那波涛汹涌的浪花…… 兴奋之余，我拍下了这些美丽的瞬间，并决定把这篇游记分享给大家。', '["旅行","海景","游记"]', 120, 68, 1001, '2022-05-20 12:34:56', '2022-05-20 12:34:56', 0);

INSERT INTO post (title, content, tags, thumbNum, favourNum, userId, createTime, updateTime, isDelete) VALUES ('如何提高英语口语表达能力？', '作为一个非英语国家的人，学好英语对于我们来说尤为重要。但是，学过英语的同学们应该都知道，英语的口语表达一直不是很容易，尤其是在跟英语母语人士交流的时候。那么，如何提高英语口语表达能力呢？在这篇文章中，我将分享一些自己的经验和方法，希望对正在学习英语的同学们有所帮助。', '["英语","口语","学习"]', 80, 45, 1002, '2022-06-15 09:12:34', '2022-06-15 09:12:34', 0);

INSERT INTO post (title, content, tags, thumbNum, favourNum, userId, createTime, updateTime, isDelete) VALUES ('如何正确使用 Git 进行版本控制？', 'Git 是目前最流行的版本控制工具之一，它可以非常方便地管理代码的版本，并与其他开发者协作。但是，对于初学者来说，Git 的使用可能会比较困难。在这篇文章中，我们将介绍 Git 的一些基础概念及其常用命令，帮助大家更好地使用 Git 进行版本控制。', '["Git","版本控制","代码管理"]', 230, 98, 1003, '2022-07-03 14:56:23', '2022-07-03 14:56:23', 0);




-- 用户表
create table if not exists user
(
    id           bigint auto_increment comment 'id' primary key,
    userAccount  varchar(256)                           not null comment '账号',
    userPassword varchar(512)                           not null comment '密码',
    unionId      varchar(256)                           null comment '微信开放平台id',
    mpOpenId     varchar(256)                           null comment '公众号openId',
    userName     varchar(256)                           null comment '用户昵称',
    userAvatar   varchar(1024)                          null comment '用户头像',
    userProfile  varchar(512)                           null comment '用户简介',
    userRole     varchar(256) default 'user'            not null comment '用户角色：user/admin/ban',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint      default 0                 not null comment '是否删除',
    index idx_unionId (unionId)
) comment '用户' collate = utf8mb4_unicode_ci;

-- 帖子表
create table if not exists post
(
    id         bigint auto_increment comment 'id' primary key,
    title      varchar(512)                       null comment '标题',
    content    text                               null comment '内容',
    tags       varchar(1024)                      null comment '标签列表（json 数组）',
    thumbNum   int      default 0                 not null comment '点赞数',
    favourNum  int      default 0                 not null comment '收藏数',
    userId     bigint                             not null comment '创建用户 id',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete   tinyint  default 0                 not null comment '是否删除',
    index idx_userId (userId)
) comment '帖子' collate = utf8mb4_unicode_ci;

-- 帖子点赞表（硬删除）
create table if not exists post_thumb
(
    id         bigint auto_increment comment 'id' primary key,
    postId     bigint                             not null comment '帖子 id',
    userId     bigint                             not null comment '创建用户 id',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    index idx_postId (postId),
    index idx_userId (userId)
) comment '帖子点赞';

-- 帖子收藏表（硬删除）
create table if not exists post_favour
(
    id         bigint auto_increment comment 'id' primary key,
    postId     bigint                             not null comment '帖子 id',
    userId     bigint                             not null comment '创建用户 id',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    index idx_postId (postId),
    index idx_userId (userId)
) comment '帖子收藏';
