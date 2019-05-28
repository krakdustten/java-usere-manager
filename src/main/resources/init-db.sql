DROP TABLE IF EXISTS `messages`;
DROP TABLE IF EXISTS `teams`;
DROP TABLE IF EXISTS `team_users`;
DROP TABLE IF EXISTS `users`;
DROP TABLE IF EXISTS `user_friends`;


CREATE TABLE `messages` (
    `id` bigint(20) NOT NULL,
    `receiver_id` bigint(20) NOT NULL,
    `sender_id` bigint(20) NOT NULL,
    `message` varchar(1000) NOT NULL,
    `sentTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `receiverRead` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
ALTER TABLE `messages`
    ADD PRIMARY KEY (`id`);
ALTER TABLE `messages`
    MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;


CREATE TABLE `teams` (
    `id` bigint(20) NOT NULL,
    `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
ALTER TABLE `teams`
    ADD PRIMARY KEY (`id`),
    ADD UNIQUE KEY `name` (`name`);
ALTER TABLE `teams`
    MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;


CREATE TABLE `team_users` (
    `id` bigint(20) NOT NULL,
    `user_id` bigint(20) NOT NULL,
    `team_id` bigint(20) NOT NULL,
    `role` bigint(11) NOT NULL DEFAULT '0',
    `enrollTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
ALTER TABLE `team_users`
    ADD PRIMARY KEY (`id`);
ALTER TABLE `team_users`
    MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;


CREATE TABLE `users` (
    `id` bigint(20) NOT NULL,
    `name` varchar(255) NOT NULL,
    `email` varchar(255) NOT NULL,
    `password` varchar(128) NOT NULL,
    `salt` varchar(128) NOT NULL,
    `currentID` varchar(128) DEFAULT NULL,
    `validUntilID` datetime DEFAULT NULL,
    `rights` bigint(20) NOT NULL DEFAULT '0',
    `confirmed` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
ALTER TABLE `users`
    ADD PRIMARY KEY (`id`),
    ADD UNIQUE KEY `name` (`name`);
ALTER TABLE `users`
    MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;


CREATE TABLE `user_friends` (
    `id` bigint(20) NOT NULL,
    `user1_id` bigint(20) NOT NULL,
    `user2_id` bigint(20) NOT NULL,
    `startTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `accepted` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
ALTER TABLE `user_friends`
    ADD PRIMARY KEY (`id`);
ALTER TABLE `user_friends`
    MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

