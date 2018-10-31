SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci ;
CREATE SCHEMA IF NOT EXISTS `staffjobs` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;
USE `staffjobs` ;

-- -----------------------------------------------------
-- Table `staffjobs`.`candidate_state`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `staffjobs`.`candidate_state` (
  `name` VARCHAR(255) NOT NULL ,
  PRIMARY KEY (`name`) ,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `staffjobs`.`candidate`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `staffjobs`.`candidate` (
  `id` INT(11) NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(255) NOT NULL ,
  `surname` VARCHAR(255) NULL DEFAULT NULL ,
  `birthday` DATE NULL DEFAULT NULL ,
  `salary_in_dollars` DECIMAL(10,2) NULL DEFAULT NULL ,
  `candidate_state` VARCHAR(255) NULL DEFAULT NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) ,
  INDEX `fk_candidate_candidatestate1_idx` (`candidate_state` ASC) ,
  CONSTRAINT `fk_candidate_candidatestate`
    FOREIGN KEY (`candidate_state` )
    REFERENCES `staffjobs`.`candidate_state` (`name` )
    ON DELETE SET NULL
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 12
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `staffjobs`.`attachment`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `staffjobs`.`attachment` (
  `id_candidate` INT(11) NOT NULL ,
  `file_path` VARCHAR(1000) NULL DEFAULT NULL ,
  `attachment_type` ENUM('CV','COVER_LETTER','PHOTO') NOT NULL ,
  INDEX `fk_attachment_candidate` (`id_candidate` ASC) ,
  CONSTRAINT `fk_attachment_candidate`
    FOREIGN KEY (`id_candidate` )
    REFERENCES `staffjobs`.`candidate` (`id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `staffjobs`.`skill`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `staffjobs`.`skill` (
  `name` VARCHAR(255) NOT NULL ,
  PRIMARY KEY (`name`) ,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `staffjobs`.`candidate_competence`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `staffjobs`.`candidate_competence` (
  `skill` VARCHAR(255) NOT NULL ,
  `id_candidate` INT(11) NOT NULL ,
  INDEX `fk_candidatecompetence_skill1_idx` (`skill` ASC) ,
  INDEX `fk_candidatecompetence_candidate1_idx` (`id_candidate` ASC) ,
  CONSTRAINT `fk_candidatecompetence_candidate`
    FOREIGN KEY (`id_candidate` )
    REFERENCES `staffjobs`.`candidate` (`id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_candidatecompetence_skill`
    FOREIGN KEY (`skill` )
    REFERENCES `staffjobs`.`skill` (`name` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `staffjobs`.`candidate_experience`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `staffjobs`.`candidate_experience` (
  `id_candidate` INT(11) NOT NULL ,
  `date_from` DATE NOT NULL ,
  `date_to` DATE NOT NULL ,
  `job_description` VARCHAR(4000) NULL DEFAULT NULL ,
  `job_posistion` VARCHAR(1000) NULL DEFAULT NULL ,
  `company_name` VARCHAR(1000) NULL DEFAULT NULL ,
  INDEX `fk_candidateexperience_candidate` (`id_candidate` ASC) ,
  CONSTRAINT `fk_candidateexperience_candidate`
    FOREIGN KEY (`id_candidate` )
    REFERENCES `staffjobs`.`candidate` (`id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `staffjobs`.`contact_details`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `staffjobs`.`contact_details` (
  `id_candidate` INT(11) NOT NULL ,
  `contact_type` ENUM('EMAIL','PHONE','SKYPE') NOT NULL ,
  `contact_details` VARCHAR(1000) NOT NULL ,
  INDEX `fk_contactdetails_candidate` (`id_candidate` ASC) ,
  CONSTRAINT `fk_contactdetails_candidate`
    FOREIGN KEY (`id_candidate` )
    REFERENCES `staffjobs`.`candidate` (`id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `staffjobs`.`feedback_state`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `staffjobs`.`feedback_state` (
  `name` VARCHAR(255) NOT NULL ,
  PRIMARY KEY (`name`) ,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `staffjobs`.`user`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `staffjobs`.`user` (
  `id` INT(11) NOT NULL AUTO_INCREMENT ,
  `email` VARCHAR(100) NOT NULL ,
  `password` VARCHAR(255) NOT NULL ,
  `name` VARCHAR(255) NOT NULL ,
  `surname` VARCHAR(255) NULL DEFAULT NULL ,
  `user_state` ENUM('BLOCKED','ACTIVE') NOT NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) ,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) )
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `staffjobs`.`vacancy`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `staffjobs`.`vacancy` (
  `id` INT(11) NOT NULL AUTO_INCREMENT ,
  `position` VARCHAR(1000) NOT NULL ,
  `salary_in_dollars_from` DECIMAL(10,2) NULL DEFAULT NULL ,
  `salary_in_dollars_to` DECIMAL(10,2) NULL DEFAULT NULL ,
  `vacancy_state` ENUM('OPEN','CLOSE') NOT NULL ,
  `experience_years_require` DECIMAL(10,2) NULL DEFAULT NULL ,
  `id_developer` INT(11) NOT NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) ,
  INDEX `fk_vacancy_user1_idx` (`id_developer` ASC) ,
  CONSTRAINT `fk_vacancy_user`
    FOREIGN KEY (`id_developer` )
    REFERENCES `staffjobs`.`user` (`id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 24
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `staffjobs`.`interview`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `staffjobs`.`interview` (
  `id` INT(11) NOT NULL AUTO_INCREMENT ,
  `id_candidate` INT(11) NOT NULL ,
  `id_vacancy` INT(11) NOT NULL ,
  `plan_date` DATETIME NOT NULL ,
  `fact_date` DATETIME NULL DEFAULT NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) ,
  INDEX `fk_interview_candidate1_idx` (`id_candidate` ASC) ,
  INDEX `fk_interview_vacancy1_idx` (`id_vacancy` ASC) ,
  CONSTRAINT `fk_interview_candidate`
    FOREIGN KEY (`id_candidate` )
    REFERENCES `staffjobs`.`candidate` (`id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_interview_vacancy`
    FOREIGN KEY (`id_vacancy` )
    REFERENCES `staffjobs`.`vacancy` (`id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `staffjobs`.`interview_feedback`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `staffjobs`.`interview_feedback` (
  `id` INT(11) NOT NULL AUTO_INCREMENT ,
  `id_interview` INT(11) NOT NULL ,
  `id_interviewer` INT(11) NOT NULL ,
  `reason` VARCHAR(4000) NULL DEFAULT NULL ,
  `feedback_state` VARCHAR(255) NULL DEFAULT NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) ,
  INDEX `fk_interviewfeedback_user` (`id_interviewer` ASC) ,
  INDEX `fk_interviewfeedback_feedbackstate` (`feedback_state` ASC) ,
  INDEX `fk_interviewfeedback_interview` (`id_interview` ASC) ,
  CONSTRAINT `fk_interviewfeedback_feedbackstate`
    FOREIGN KEY (`feedback_state` )
    REFERENCES `staffjobs`.`feedback_state` (`name` )
    ON DELETE SET NULL
    ON UPDATE CASCADE,
  CONSTRAINT `fk_interviewfeedback_interview`
    FOREIGN KEY (`id_interview` )
    REFERENCES `staffjobs`.`interview` (`id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_interviewfeedback_user`
    FOREIGN KEY (`id_interviewer` )
    REFERENCES `staffjobs`.`user` (`id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `staffjobs`.`role`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `staffjobs`.`role` (
  `id` INT(11) NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(255) NOT NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) ,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `staffjobs`.`suitable_state`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `staffjobs`.`suitable_state` (
  `name` VARCHAR(255) NOT NULL ,
  PRIMARY KEY (`name`) ,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `staffjobs`.`user_roles`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `staffjobs`.`user_roles` (
  `user_id` INT(11) NOT NULL ,
  `role_id` INT(11) NOT NULL ,
  INDEX `fk_user_has_role_role1_idx` (`role_id` ASC) ,
  INDEX `fk_user_has_role_user_idx` (`user_id` ASC) ,
  CONSTRAINT `fk_userroles_role`
    FOREIGN KEY (`role_id` )
    REFERENCES `staffjobs`.`role` (`id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_userroles_user`
    FOREIGN KEY (`user_id` )
    REFERENCES `staffjobs`.`user` (`id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `staffjobs`.`vacancy_candidates`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `staffjobs`.`vacancy_candidates` (
  `id_vacancy` INT(11) NOT NULL ,
  `id_candidate` INT(11) NOT NULL ,
  `suitable_state` VARCHAR(255) NULL DEFAULT NULL ,
  `reason` VARCHAR(1000) NULL DEFAULT NULL ,
  INDEX `fk_vacancycandidates_candidate1_idx` (`id_candidate` ASC) ,
  INDEX `fk_vacancycandidates_suitablestate1_idx` (`suitable_state` ASC) ,
  INDEX `fk_vacancycandidates_vacancy` (`id_vacancy` ASC) ,
  CONSTRAINT `fk_vacancycandidates_candidate`
    FOREIGN KEY (`id_candidate` )
    REFERENCES `staffjobs`.`candidate` (`id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_vacancycandidates_suitablestate`
    FOREIGN KEY (`suitable_state` )
    REFERENCES `staffjobs`.`suitable_state` (`name` )
    ON DELETE SET NULL
    ON UPDATE CASCADE,
  CONSTRAINT `fk_vacancycandidates_vacancy`
    FOREIGN KEY (`id_vacancy` )
    REFERENCES `staffjobs`.`vacancy` (`id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `staffjobs`.`vacancy_requirement`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `staffjobs`.`vacancy_requirement` (
  `skill` VARCHAR(255) NOT NULL ,
  `id_vacancy` INT(11) NOT NULL ,
  INDEX `fk_vacancyrequirement_skill1_idx` (`skill` ASC) ,
  INDEX `fk_vacancyrequirement_vacancy1_idx` (`id_vacancy` ASC) ,
  CONSTRAINT `fk_vacancyrequirement_skill`
    FOREIGN KEY (`skill` )
    REFERENCES `staffjobs`.`skill` (`name` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_vacancyrequirement_vacancy`
    FOREIGN KEY (`id_vacancy` )
    REFERENCES `staffjobs`.`vacancy` (`id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
