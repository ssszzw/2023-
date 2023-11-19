
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

CREATE DATABASE IF NOT EXISTS `www_RBAC_com` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE `www_RBAC_com`;



--
-- 表的结构 `Characters`
--

CREATE TABLE `Characters` (
  `character_id` int NOT NULL,
  `character_name` varchar(255) NOT NULL,
  `character_type` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `status` enum('ACTIVE','INACTIVE') DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- 转存表中的数据 `Characters`
--

INSERT INTO `Characters` (`character_id`, `character_name`, `character_type`, `status`, `create_time`, `update_time`) VALUES
(1, '超级管理员', '内置', 'ACTIVE', '2023-10-30 15:17:02', '2023-11-03 04:52:14'),
(2, '普通用户', '内置', 'ACTIVE', '2023-10-30 15:17:19', '2023-11-03 04:52:28'),
(3, '测试账号555', '自定义', 'ACTIVE', '2023-10-30 15:17:33', '2023-11-17 12:05:02'),
(6, '测试角色2号', '自定义', 'ACTIVE', '2023-11-17 14:41:01', '2023-11-17 14:41:01'),
(12, 'test111999999999', '自定义', 'INACTIVE', '2023-11-18 03:28:00', '2023-11-18 15:46:03');

-- --------------------------------------------------------

--
-- 表的结构 `Character_Permissions`
--

CREATE TABLE `Character_Permissions` (
  `character_id` int NOT NULL,
  `permission_id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- 转存表中的数据 `Character_Permissions`
--

INSERT INTO `Character_Permissions` (`character_id`, `permission_id`) VALUES
(1, 1),
(2, 1),
(1, 2),
(3, 2),
(1, 3),
(1, 4),
(2, 4),
(1, 5),
(1, 6),
(1, 7),
(1, 8),
(1, 9),
(1, 10),
(1, 11),
(1, 12),
(1, 13),
(1, 14),
(2, 14),
(1, 15),
(2, 15),
(1, 16),
(2, 16),
(1, 17),
(1, 18),
(2, 18),
(2, 19);

-- --------------------------------------------------------

--
-- 表的结构 `Menus`
--

CREATE TABLE `Menus` (
  `menu_id` int NOT NULL,
  `menu_name` varchar(255) NOT NULL,
  `parent_id` int DEFAULT NULL,
  `url` varchar(255) NOT NULL,
  `component` varchar(255) NOT NULL,
  `order_num` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- 转存表中的数据 `Menus`
--

INSERT INTO `Menus` (`menu_id`, `menu_name`, `parent_id`, `url`, `component`, `order_num`) VALUES
(1, '首页', 0, '/', 'layout/index', 1),
(2, '系统管理', 0, '/system', 'layout/index', 2),
(3, '用户管理', 2, '/system/user', 'views/User/List', 3),
(4, '添加用户', 2, '/system/user/add', 'views/User/Add', 4),
(5, '登录', 0, '/login', 'views/Login', 5),
(6, '首页的children', 1, '/children', 'views/HomeView', 6),
(7, '角色管理', 2, '/system/character', 'views/Character/List', 7),
(8, '权限管理', 2, '/system/permission', 'views/Permission/List', 8);

-- --------------------------------------------------------

--
-- 表的结构 `Menu_Characters`
--

CREATE TABLE `Menu_Characters` (
  `menu_character_id` int NOT NULL,
  `menu_id` int DEFAULT NULL,
  `character_id` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- 转存表中的数据 `Menu_Characters`
--

INSERT INTO `Menu_Characters` (`menu_character_id`, `menu_id`, `character_id`) VALUES
(1, 1, 1),
(2, 2, 1),
(3, 3, 1),
(4, 4, 1),
(5, 5, 1),
(6, 1, 2),
(7, 5, 2),
(8, 6, 1),
(9, 7, 1);

-- --------------------------------------------------------

--
-- 表的结构 `Permissions`
--

CREATE TABLE `Permissions` (
  `permission_id` int NOT NULL,
  `parent_id` int NOT NULL,
  `permission_name` varchar(255) NOT NULL,
  `path` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `order_num` int NOT NULL,
  `description` text,
  `status` enum('ACTIVE','INACTIVE') DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- 转存表中的数据 `Permissions`
--

INSERT INTO `Permissions` (`permission_id`, `parent_id`, `permission_name`, `path`, `order_num`, `description`, `status`, `create_time`, `update_time`) VALUES
(1, 0, '系统管理', '/system', 1, '系统管理描述', 'ACTIVE', '2023-10-31 04:04:07', '2023-11-16 16:00:48'),
(2, 1, '用户管理', '/system/user', 2, '用户管理描述', 'ACTIVE', '2023-10-31 04:04:55', '2023-11-16 16:01:11'),
(3, 1, '角色管理', '/system/character', 3, '角色管理描述', 'ACTIVE', '2023-11-16 15:55:33', '2023-11-16 16:01:15'),
(4, 1, '权限管理', '/system/permission', 4, '权限管理描述', 'ACTIVE', '2023-10-31 09:57:41', '2023-11-16 16:01:08'),
(5, 2, '用户查询', '/system/user/searchUser', 5, NULL, 'ACTIVE', '2023-11-16 16:09:08', '2023-11-16 16:12:04'),
(6, 2, '用户新增', '/system/user/postUser', 6, '用户新增的描述', 'ACTIVE', '2023-11-16 16:03:15', '2023-11-16 16:12:02'),
(7, 2, '用户删除', '/system/user/deleteUser', 7, NULL, 'ACTIVE', '2023-11-16 16:04:50', '2023-11-16 16:11:59'),
(8, 2, '用户修改', '/system/user/putUser', 8, NULL, 'ACTIVE', '2023-11-16 16:05:28', '2023-11-16 16:11:46'),
(9, 3, '角色分配', '/system/character/assignCharacter', 9, NULL, NULL, '2023-11-16 16:13:01', '2023-11-16 16:13:14'),
(10, 3, '角色查询', '/system/character/searchCharacter', 10, NULL, NULL, '2023-11-16 16:14:10', '2023-11-16 16:14:38'),
(11, 3, '角色新增', '/system/character/postCharacter', 11, NULL, NULL, '2023-11-16 16:15:29', '2023-11-16 16:15:53'),
(12, 3, '角色删除', '/system/character/deleteCharacter', 12, NULL, NULL, '2023-11-16 16:16:41', '2023-11-16 16:16:41'),
(13, 3, '角色修改', '/system/character/putCharacter', 13, NULL, NULL, '2023-11-16 16:17:29', '2023-11-16 16:17:29'),
(14, 4, '权限分配', '/system/permission/assignPermission', 14, NULL, NULL, '2023-11-16 16:18:09', '2023-11-16 16:18:24'),
(15, 4, '权限搜索', '/system/permission/searchPermission', 15, NULL, NULL, '2023-11-16 16:22:09', '2023-11-16 16:22:09'),
(16, 4, '权限新增', '/system/permission/postPermission', 16, NULL, NULL, '2023-11-16 16:22:48', '2023-11-16 16:22:48'),
(17, 4, '权限删除', '/system/permission/deletePermission', 17, NULL, NULL, '2023-11-16 16:23:06', '2023-11-16 16:23:06'),
(18, 4, '权限修改', '/system/permission/putPermission', 18, NULL, NULL, '2023-11-16 16:23:21', '2023-11-16 16:23:21'),
(19, 4, '权限测试', '/system/permission/test', 19, 'testtttttttttttttttttttttttttttttttt', 'INACTIVE', '2023-11-18 02:57:53', '2023-11-18 03:00:19'),
(20, 4, 'crd', 'ced', 20, '', 'INACTIVE', '2023-11-18 03:09:51', '2023-11-18 03:09:51');

-- --------------------------------------------------------

--
-- 表的结构 `Users`
--

CREATE TABLE `Users` (
  `user_id` int NOT NULL,
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `gender` varchar(16) DEFAULT NULL,
  `user_name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `phone_number` varchar(20) DEFAULT NULL,
  `status` enum('ACTIVE','INACTIVE') DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- 转存表中的数据 `Users`
--

INSERT INTO `Users` (`user_id`, `name`, `gender`, `user_name`, `password`, `phone_number`, `status`, `create_time`, `update_time`) VALUES
(1, '拥有所有角色的真名', '男', 'szw', '123456', '987654321', NULL, '2023-10-26 03:41:04', '2023-11-03 09:19:52'),
(2, '真名2号', '男', 'lalala', '123456', '078315', NULL, '2023-10-26 04:36:43', '2023-10-26 04:56:14'),
(4, '真名三号', '男', '10.28 0:32', 'bbb', '31223321', NULL, '2023-10-27 16:32:50', '2023-10-27 16:32:50'),
(8, '真名七号', '女', '10.28 11:23', '234567', '124388888', NULL, '2023-10-28 03:24:09', '2023-10-28 12:37:40'),
(11, '真名十一号_第三次修改', '男', '10.28 16:51', 'lllllllllll', '76543', NULL, '2023-10-28 04:09:18', '2023-10-28 11:16:56'),
(12, '真名十号', '男', '10.28 13:00', 'jjjjjjj', '765', NULL, '2023-10-28 05:00:26', '2023-10-28 05:00:26'),
(14, '真名十三', '男', '10.28 21:45', '2434t', '45678', NULL, '2023-10-28 13:46:11', '2023-10-28 13:46:11'),
(15, '真名十四号第二次修改', '男', 'tytret', '987654rrr', 'pppppppppppp', NULL, '2023-10-28 15:05:41', '2023-10-31 10:12:00'),
(18, '真名十八号第二次修改', '男', '动态路由', '2435421', '654', NULL, '2023-11-14 10:23:21', '2023-11-14 10:48:07');

-- --------------------------------------------------------

--
-- 表的结构 `User_Characters`
--

CREATE TABLE `User_Characters` (
  `user_id` int NOT NULL,
  `character_id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- 转存表中的数据 `User_Characters`
--

INSERT INTO `User_Characters` (`user_id`, `character_id`) VALUES
(1, 1),
(1, 2),
(2, 2),
(4, 2),
(11, 2),
(1, 3),
(2, 3),
(4, 3),
(8, 3),
(11, 3);

--
-- 转储表的索引
--

--
-- 表的索引 `Characters`
--
ALTER TABLE `Characters`
  ADD PRIMARY KEY (`character_id`),
  ADD UNIQUE KEY `character_name` (`character_name`);

--
-- 表的索引 `Character_Permissions`
--
ALTER TABLE `Character_Permissions`
  ADD PRIMARY KEY (`character_id`,`permission_id`),
  ADD KEY `permission_id` (`permission_id`);

--
-- 表的索引 `Menus`
--
ALTER TABLE `Menus`
  ADD PRIMARY KEY (`menu_id`);

--
-- 表的索引 `Menu_Characters`
--
ALTER TABLE `Menu_Characters`
  ADD PRIMARY KEY (`menu_character_id`),
  ADD KEY `menu_id` (`menu_id`),
  ADD KEY `character_id` (`character_id`);

--
-- 表的索引 `Permissions`
--
ALTER TABLE `Permissions`
  ADD PRIMARY KEY (`permission_id`),
  ADD UNIQUE KEY `permission_name` (`permission_name`);

--
-- 表的索引 `Users`
--
ALTER TABLE `Users`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `user_name` (`user_name`);

--
-- 表的索引 `User_Characters`
--
ALTER TABLE `User_Characters`
  ADD PRIMARY KEY (`user_id`,`character_id`),
  ADD KEY `character_id` (`character_id`);

--
-- 在导出的表使用AUTO_INCREMENT
--

--
-- 使用表AUTO_INCREMENT `Characters`
--
ALTER TABLE `Characters`
  MODIFY `character_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- 使用表AUTO_INCREMENT `Menus`
--
ALTER TABLE `Menus`
  MODIFY `menu_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- 使用表AUTO_INCREMENT `Menu_Characters`
--
ALTER TABLE `Menu_Characters`
  MODIFY `menu_character_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- 使用表AUTO_INCREMENT `Permissions`
--
ALTER TABLE `Permissions`
  MODIFY `permission_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- 使用表AUTO_INCREMENT `Users`
--
ALTER TABLE `Users`
  MODIFY `user_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- 限制导出的表
--

--
-- 限制表 `Character_Permissions`
--
ALTER TABLE `Character_Permissions`
  ADD CONSTRAINT `Character_Permissions_ibfk_1` FOREIGN KEY (`character_id`) REFERENCES `Characters` (`character_id`),
  ADD CONSTRAINT `Character_Permissions_ibfk_2` FOREIGN KEY (`permission_id`) REFERENCES `Permissions` (`permission_id`);

--
-- 限制表 `Menu_Characters`
--
ALTER TABLE `Menu_Characters`
  ADD CONSTRAINT `Menu_Characters_ibfk_1` FOREIGN KEY (`menu_id`) REFERENCES `Menus` (`menu_id`),
  ADD CONSTRAINT `Menu_Characters_ibfk_2` FOREIGN KEY (`character_id`) REFERENCES `Characters` (`character_id`);

--
-- 限制表 `User_Characters`
--
ALTER TABLE `User_Characters`
  ADD CONSTRAINT `User_Characters_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `Users` (`user_id`),
  ADD CONSTRAINT `User_Characters_ibfk_2` FOREIGN KEY (`character_id`) REFERENCES `Characters` (`character_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

