-- --------------------------------------------------------
-- Database Structure for `jobmatch`
-- --------------------------------------------------------

CREATE DATABASE IF NOT EXISTS `jobmatch` /*!40100 DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci */;
USE `jobmatch`;

-- Cities Table
CREATE TABLE IF NOT EXISTS `cities` (
                                        `city_id` INT(11) NOT NULL AUTO_INCREMENT,
                                        `city_name` VARCHAR(30) NOT NULL,
                                        PRIMARY KEY (`city_id`),
                                        UNIQUE KEY `cities_pk_2` (`city_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Users Table
CREATE TABLE IF NOT EXISTS `users` (
                                       `user_id` INT(11) NOT NULL AUTO_INCREMENT,
                                       `username` VARCHAR(50) NOT NULL UNIQUE,   -- Unique username for login
                                       `password` VARCHAR(255) NOT NULL,         -- Hashed password
                                       `role` ENUM('Professional', 'Company') NOT NULL,  -- Role to distinguish user type
                                       `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                       PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Companies Table
CREATE TABLE IF NOT EXISTS `companies` (
                                           `id` INT(11) NOT NULL AUTO_INCREMENT,
                                           `user_id` INT(11) NOT NULL,               -- Reference to users table
                                           `company_name` VARCHAR(30) NOT NULL,
                                           `description` VARCHAR(300) DEFAULT NULL,
                                           `contact_info` VARCHAR(255) DEFAULT NULL,
                                           `logo` VARCHAR(255) DEFAULT NULL,
                                           `city` INT(11) DEFAULT NULL,
                                           PRIMARY KEY (`id`),
                                           UNIQUE KEY `companies_pk_2` (`id`),
                                           KEY `city` (`city`),
                                           KEY `user_id` (`user_id`),
                                           CONSTRAINT `company_user_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
                                           CONSTRAINT `city` FOREIGN KEY (`city`) REFERENCES `cities` (`city_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Professionals Table
CREATE TABLE IF NOT EXISTS `professionals` (
                                               `professional_id` INT(11) NOT NULL AUTO_INCREMENT,
                                               `user_id` INT(11) NOT NULL,               -- Reference to users table
                                               `first_name` VARCHAR(30) NOT NULL,
                                               `last_name` VARCHAR(30) NOT NULL,
                                               `email` VARCHAR(50) NOT NULL UNIQUE,      -- Ensure email is unique
                                               `city` INT(11) NOT NULL,
                                               `short_summary` VARCHAR(300) DEFAULT NULL,
                                               `status` ENUM('Active','Busy') NOT NULL DEFAULT 'Active',
                                               `profile_picture` VARCHAR(300) DEFAULT NULL,
                                               PRIMARY KEY (`professional_id`),
                                               UNIQUE KEY `professionals_pk_2` (`professional_id`),
                                               KEY `professional_city` (`city`),
                                               KEY `user_id` (`user_id`),
                                               CONSTRAINT `professional_user_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
                                               CONSTRAINT `professional_city` FOREIGN KEY (`city`) REFERENCES `cities` (`city_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Job Adds Table
CREATE TABLE IF NOT EXISTS `job_adds` (
                                          `id` INT(11) NOT NULL,
                                          `company_id` INT(11) NOT NULL,
                                          `title` VARCHAR(50) NOT NULL,
                                          `salary_min` DECIMAL(10,2) DEFAULT NULL,
                                          `salary_max` DECIMAL(10,2) DEFAULT NULL,
                                          `description` VARCHAR(255) DEFAULT NULL,
                                          `location` INT(11) NOT NULL,
                                          `status` ENUM('Active','Archived') DEFAULT NULL,
                                          `requirements` VARCHAR(100) NOT NULL,
                                          `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP(),
                                          `updated_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP() ON UPDATE CURRENT_TIMESTAMP(),
                                          `skillset` INT(11) NOT NULL,
                                          PRIMARY KEY (`id`),
                                          UNIQUE KEY `job_adds_pk_2` (`id`),
                                          KEY `company_id` (`company_id`),
                                          KEY `location_id` (`location`),
                                          KEY `job_adds_skills_id_fk` (`skillset`),
                                          CONSTRAINT `company_id` FOREIGN KEY (`company_id`) REFERENCES `companies` (`id`),
                                          CONSTRAINT `job_adds_skills_id_fk` FOREIGN KEY (`skillset`) REFERENCES `skills` (`id`) ON DELETE CASCADE,
                                          CONSTRAINT `location_id` FOREIGN KEY (`location`) REFERENCES `cities` (`city_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Job Application Table
CREATE TABLE IF NOT EXISTS `job_application` (
                                                 `id` INT(11) NOT NULL AUTO_INCREMENT,
                                                 `professional_id` INT(11) NOT NULL,
                                                 `desired_salary_min` DECIMAL(10,2) DEFAULT NULL,
                                                 `desired_salary_max` DECIMAL(10,2) DEFAULT NULL,
                                                 `motivation` VARCHAR(255) DEFAULT NULL,
                                                 `status` ENUM('Active','Hidden','Private','Matched') DEFAULT 'Active',
                                                 `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP(),
                                                 `updated_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP() ON UPDATE CURRENT_TIMESTAMP(),
                                                 `skillset` INT(11) NOT NULL,
                                                 PRIMARY KEY (`id`),
                                                 UNIQUE KEY `job_application_pk_2` (`id`),
                                                 KEY `job_application_professionals_professional_id_fk` (`professional_id`),
                                                 KEY `job_application_skills_id_fk` (`skillset`),
                                                 CONSTRAINT `job_application_professionals_professional_id_fk` FOREIGN KEY (`professional_id`) REFERENCES `professionals` (`professional_id`),
                                                 CONSTRAINT `job_application_skills_id_fk` FOREIGN KEY (`skillset`) REFERENCES `skills` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Matches Table
CREATE TABLE IF NOT EXISTS `matches` (
                                         `id` INT(11) NOT NULL AUTO_INCREMENT,
                                         `job_application_id` INT(11) NOT NULL,
                                         `job_ad_id` INT(11) NOT NULL,
                                         `match_status` ENUM('Pending','Accepted','Rejected') DEFAULT 'Pending',
                                         `match_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP(),
                                         PRIMARY KEY (`id`),
                                         UNIQUE KEY `matches_pk_2` (`id`),
                                         KEY `matches_job_application_id_fk` (`job_application_id`),
                                         KEY `matches_job_adds_id_fk` (`job_ad_id`),
                                         CONSTRAINT `matches_job_adds_id_fk` FOREIGN KEY (`job_ad_id`) REFERENCES `job_adds` (`id`),
                                         CONSTRAINT `matches_job_application_id_fk` FOREIGN KEY (`job_application_id`) REFERENCES `job_application` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Skills Table
CREATE TABLE IF NOT EXISTS `skills` (
                                        `id` INT(11) NOT NULL AUTO_INCREMENT,
                                        `name` VARCHAR(255) NOT NULL,
                                        `created_by` ENUM('administrator') DEFAULT NULL,
                                        `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP(),
                                        PRIMARY KEY (`id`),
                                        UNIQUE KEY `skills_pk_2` (`id`),
                                        UNIQUE KEY `skills_pk_3` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=91 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
