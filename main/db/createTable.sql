-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               11.5.2-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             12.6.0.6765
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for jobmatch
CREATE DATABASE IF NOT EXISTS `jobmatch` /*!40100 DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci */;
USE `jobmatch`;

-- Dumping structure for table jobmatch.cities
CREATE TABLE IF NOT EXISTS `cities` (
  `city_id` int(11) NOT NULL AUTO_INCREMENT,
  `city_name` varchar(30) NOT NULL,
  PRIMARY KEY (`city_id`),
  UNIQUE KEY `cities_pk_2` (`city_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Dumping data for table jobmatch.cities: ~0 rows (approximately)

-- Dumping structure for table jobmatch.companies
CREATE TABLE IF NOT EXISTS `companies` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) NOT NULL,
  `password` varchar(30) NOT NULL,
  `company_name` varchar(30) NOT NULL,
  `description` varchar(300) DEFAULT NULL,
  `contact_info` varchar(255) DEFAULT NULL,
  `logo` varchar(255) DEFAULT NULL,
  `city` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `companies_pk_2` (`id`),
  UNIQUE KEY `companies_pk_3` (`username`),
  KEY `city` (`city`),
  CONSTRAINT `city` FOREIGN KEY (`city`) REFERENCES `cities` (`city_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Dumping data for table jobmatch.companies: ~0 rows (approximately)

-- Dumping structure for table jobmatch.job_adds
CREATE TABLE IF NOT EXISTS `job_adds` (
  `id` int(11) NOT NULL,
  `company_id` int(11) NOT NULL,
  `title` varchar(50) NOT NULL,
  `salary_min` decimal(10,2) DEFAULT NULL,
  `salary_max` decimal(10,2) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `location` int(11) NOT NULL,
  `status` enum('Active','Archived') DEFAULT NULL,
  `requirements` varchar(100) NOT NULL,
  `created_at` timestamp NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `skillset` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `job_adds_pk_2` (`id`),
  KEY `company_id` (`company_id`),
  KEY `location_id` (`location`),
  KEY `job_adds_skills_id_fk` (`skillset`),
  CONSTRAINT `company_id` FOREIGN KEY (`company_id`) REFERENCES `companies` (`id`),
  CONSTRAINT `job_adds_skills_id_fk` FOREIGN KEY (`skillset`) REFERENCES `skills` (`id`) ON DELETE CASCADE,
  CONSTRAINT `location_id` FOREIGN KEY (`location`) REFERENCES `cities` (`city_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Dumping data for table jobmatch.job_adds: ~0 rows (approximately)

-- Dumping structure for table jobmatch.job_application
CREATE TABLE IF NOT EXISTS `job_application` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `professional_id` int(11) NOT NULL,
  `desired_salary_min` decimal(10,2) DEFAULT NULL,
  `desired_salary_max` decimal(10,2) DEFAULT NULL,
  `motivation` varchar(255) DEFAULT NULL,
  `status` enum('Active','Hidden','Private','Matched') DEFAULT 'Active',
  `created_at` timestamp NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `skillset` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `job_application_pk_2` (`id`),
  KEY `job_application_professionals_professional_id_fk` (`professional_id`),
  KEY `job_application_skills_id_fk` (`skillset`),
  CONSTRAINT `job_application_professionals_professional_id_fk` FOREIGN KEY (`professional_id`) REFERENCES `professionals` (`professional_id`),
  CONSTRAINT `job_application_skills_id_fk` FOREIGN KEY (`skillset`) REFERENCES `skills` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Dumping data for table jobmatch.job_application: ~0 rows (approximately)

-- Dumping structure for table jobmatch.matches
CREATE TABLE IF NOT EXISTS `matches` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_application_id` int(11) NOT NULL,
  `job_ad_id` int(11) NOT NULL,
  `match_status` enum('Pending','Accepted','Rejected') DEFAULT 'Pending',
  `match_date` timestamp NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`),
  UNIQUE KEY `matches_pk_2` (`id`),
  KEY `matches_job_application_id_fk` (`job_application_id`),
  KEY `matches_job_adds_id_fk` (`job_ad_id`),
  CONSTRAINT `matches_job_adds_id_fk` FOREIGN KEY (`job_ad_id`) REFERENCES `job_adds` (`id`),
  CONSTRAINT `matches_job_application_id_fk` FOREIGN KEY (`job_application_id`) REFERENCES `job_application` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Dumping data for table jobmatch.matches: ~0 rows (approximately)

-- Dumping structure for table jobmatch.professionals
CREATE TABLE IF NOT EXISTS `professionals` (
  `professional_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) NOT NULL,
  `first_name` varchar(30) NOT NULL,
  `last_name` varchar(30) NOT NULL,
  `password` varchar(30) DEFAULT NULL,
  `email` varchar(30) NOT NULL,
  `city` int(11) NOT NULL,
  `short_summary` varchar(300) DEFAULT NULL,
  `status` enum('Active','Busy') NOT NULL,
  `profile_picute` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`professional_id`),
  UNIQUE KEY `Professionals_pk_2` (`professional_id`),
  UNIQUE KEY `Professionals_pk_3` (`email`),
  KEY `professional_city` (`city`),
  CONSTRAINT `professional_city` FOREIGN KEY (`city`) REFERENCES `cities` (`city_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Dumping data for table jobmatch.professionals: ~0 rows (approximately)

-- Dumping structure for table jobmatch.skills
CREATE TABLE IF NOT EXISTS `skills` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `created_by` enum('administrator') DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`),
  UNIQUE KEY `skills_pk_2` (`id`),
  UNIQUE KEY `skills_pk_3` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=91 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Dumping data for table jobmatch.skills: ~90 rows (approximately)
INSERT INTO `skills` (`id`, `name`, `created_by`, `created_at`) VALUES
	(1, 'Java', 'administrator', '2024-11-21 09:36:16'),
	(2, 'Python', 'administrator', '2024-11-21 09:36:16'),
	(3, 'JavaScript', 'administrator', '2024-11-21 09:36:16'),
	(4, 'C++', 'administrator', '2024-11-21 09:36:16'),
	(5, 'C#', 'administrator', '2024-11-21 09:36:16'),
	(6, 'PHP', 'administrator', '2024-11-21 09:36:16'),
	(7, 'Ruby', 'administrator', '2024-11-21 09:36:16'),
	(8, 'Go', 'administrator', '2024-11-21 09:36:16'),
	(9, 'Swift', 'administrator', '2024-11-21 09:36:16'),
	(10, 'Kotlin', 'administrator', '2024-11-21 09:36:16'),
	(11, 'HTML', 'administrator', '2024-11-21 09:36:16'),
	(12, 'CSS', 'administrator', '2024-11-21 09:36:16'),
	(13, 'React.js', 'administrator', '2024-11-21 09:36:16'),
	(14, 'Angular', 'administrator', '2024-11-21 09:36:16'),
	(15, 'Vue.js', 'administrator', '2024-11-21 09:36:16'),
	(16, 'Node.js', 'administrator', '2024-11-21 09:36:16'),
	(17, 'SQL', 'administrator', '2024-11-21 09:36:16'),
	(18, 'MySQL', 'administrator', '2024-11-21 09:36:16'),
	(19, 'PostgreSQL', 'administrator', '2024-11-21 09:36:16'),
	(20, 'MongoDB', 'administrator', '2024-11-21 09:36:16'),
	(21, 'Oracle', 'administrator', '2024-11-21 09:36:16'),
	(22, 'Redis', 'administrator', '2024-11-21 09:36:16'),
	(23, 'Docker', 'administrator', '2024-11-21 09:36:16'),
	(24, 'Kubernetes', 'administrator', '2024-11-21 09:36:16'),
	(25, 'Jenkins', 'administrator', '2024-11-21 09:36:16'),
	(26, 'CI/CD Pipelines', 'administrator', '2024-11-21 09:36:16'),
	(27, 'AWS', 'administrator', '2024-11-21 09:36:16'),
	(28, 'Azure', 'administrator', '2024-11-21 09:36:16'),
	(29, 'Google Cloud Platform', 'administrator', '2024-11-21 09:36:16'),
	(30, 'R', 'administrator', '2024-11-21 09:36:16'),
	(31, 'MATLAB', 'administrator', '2024-11-21 09:36:16'),
	(32, 'TensorFlow', 'administrator', '2024-11-21 09:36:16'),
	(33, 'Pandas', 'administrator', '2024-11-21 09:36:16'),
	(34, 'NumPy', 'administrator', '2024-11-21 09:36:16'),
	(35, 'Apache Spark', 'administrator', '2024-11-21 09:36:16'),
	(36, 'Power BI', 'administrator', '2024-11-21 09:36:16'),
	(37, 'Tableau', 'administrator', '2024-11-21 09:36:16'),
	(38, 'Ethical Hacking', 'administrator', '2024-11-21 09:36:16'),
	(39, 'Penetration Testing', 'administrator', '2024-11-21 09:36:16'),
	(40, 'Network Security', 'administrator', '2024-11-21 09:36:16'),
	(41, 'ISO 27001', 'administrator', '2024-11-21 09:36:16'),
	(42, 'Firewalls', 'administrator', '2024-11-21 09:36:16'),
	(43, 'Encryption', 'administrator', '2024-11-21 09:36:16'),
	(44, 'Android Development', 'administrator', '2024-11-21 09:36:16'),
	(45, 'iOS Development', 'administrator', '2024-11-21 09:36:16'),
	(46, 'React Native', 'administrator', '2024-11-21 09:36:16'),
	(47, 'Flutter', 'administrator', '2024-11-21 09:36:16'),
	(48, 'Unity', 'administrator', '2024-11-21 09:36:16'),
	(49, 'Unreal Engine', 'administrator', '2024-11-21 09:36:16'),
	(50, 'Game Design', 'administrator', '2024-11-21 09:36:16'),
	(51, '3D Modeling', 'administrator', '2024-11-21 09:36:16'),
	(52, 'Agile', 'administrator', '2024-11-21 09:36:16'),
	(53, 'Scrum', 'administrator', '2024-11-21 09:36:16'),
	(54, 'Kanban', 'administrator', '2024-11-21 09:36:16'),
	(55, 'Waterfall Methodology', 'administrator', '2024-11-21 09:36:16'),
	(56, 'Stakeholder Management', 'administrator', '2024-11-21 09:36:16'),
	(57, 'SEO', 'administrator', '2024-11-21 09:36:16'),
	(58, 'SEM', 'administrator', '2024-11-21 09:36:16'),
	(59, 'Social Media Marketing', 'administrator', '2024-11-21 09:36:16'),
	(60, 'Content Creation', 'administrator', '2024-11-21 09:36:16'),
	(61, 'Email Marketing', 'administrator', '2024-11-21 09:36:16'),
	(62, 'Google Analytics', 'administrator', '2024-11-21 09:36:16'),
	(63, 'Financial Analysis', 'administrator', '2024-11-21 09:36:16'),
	(64, 'Budgeting', 'administrator', '2024-11-21 09:36:16'),
	(65, 'Forecasting', 'administrator', '2024-11-21 09:36:16'),
	(66, 'Accounting Principles', 'administrator', '2024-11-21 09:36:16'),
	(67, 'QuickBooks', 'administrator', '2024-11-21 09:36:16'),
	(68, 'Recruitment', 'administrator', '2024-11-21 09:36:16'),
	(69, 'Onboarding', 'administrator', '2024-11-21 09:36:16'),
	(70, 'Training & Development', 'administrator', '2024-11-21 09:36:16'),
	(71, 'Employee Relations', 'administrator', '2024-11-21 09:36:16'),
	(72, 'Circuit Design', 'administrator', '2024-11-21 09:36:16'),
	(73, 'PCB Design', 'administrator', '2024-11-21 09:36:16'),
	(74, 'IoT', 'administrator', '2024-11-21 09:36:16'),
	(75, 'CAD', 'administrator', '2024-11-21 09:36:16'),
	(76, 'CAM', 'administrator', '2024-11-21 09:36:16'),
	(77, 'Thermodynamics', 'administrator', '2024-11-21 09:36:16'),
	(78, 'FEA', 'administrator', '2024-11-21 09:36:16'),
	(79, 'Structural Analysis', 'administrator', '2024-11-21 09:36:16'),
	(80, 'GIS', 'administrator', '2024-11-21 09:36:16'),
	(81, 'Surveying', 'administrator', '2024-11-21 09:36:16'),
	(82, 'Construction Management', 'administrator', '2024-11-21 09:36:16'),
	(83, 'Adobe Photoshop', 'administrator', '2024-11-21 09:36:16'),
	(84, 'Adobe Illustrator', 'administrator', '2024-11-21 09:36:16'),
	(85, 'Figma', 'administrator', '2024-11-21 09:36:16'),
	(86, 'Sketch', 'administrator', '2024-11-21 09:36:16'),
	(87, 'UX/UI Design', 'administrator', '2024-11-21 09:36:16'),
	(88, 'Video Editing', 'administrator', '2024-11-21 09:36:16'),
	(89, 'Animation', 'administrator', '2024-11-21 09:36:16'),
	(90, '3D Rendering', 'administrator', '2024-11-21 09:36:16');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
