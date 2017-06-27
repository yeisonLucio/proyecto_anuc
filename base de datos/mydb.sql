-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';



-- -----------------------------------------------------
-- Table `mydb`.`municipio`
-- -----------------------------------------------------
CREATE TABLE `municipio` (
  `idmunicipio` INT(11) NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(50) NULL DEFAULT NULL,
  PRIMARY KEY (`idmunicipio`))
ENGINE = InnoDB
AUTO_INCREMENT = 10
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`vereda`
-- -----------------------------------------------------
CREATE TABLE `vereda` (
  `idvereda` INT(11) NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(40) NULL DEFAULT NULL,
  `municipio_idmunicipio` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`idvereda`),
  INDEX `fk_vereda_municipio1_idx` (`municipio_idmunicipio` ASC),
  CONSTRAINT `fk_vereda_municipio1`
    FOREIGN KEY (`municipio_idmunicipio`)
    REFERENCES `municipio` (`idmunicipio`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`persona`
-- -----------------------------------------------------
CREATE TABLE `persona` (
  `idpersona` INT(11) NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(20) NOT NULL,
  `apellido` VARCHAR(20) NOT NULL,
  `identificacion` VARCHAR(11) NOT NULL,
  `codigoAnuc` INT(11) NULL DEFAULT NULL,
  `telefono` VARCHAR(11) NULL DEFAULT NULL,
  `edad` INT(11) NULL DEFAULT NULL,
  `genero` VARCHAR(2) NULL DEFAULT NULL,
  `estudios` VARCHAR(20) NULL DEFAULT NULL,
  `tierra` VARCHAR(20) NULL DEFAULT NULL,
  `sector` VARCHAR(30) NULL DEFAULT NULL,
  `afiliado` VARCHAR(2) NULL DEFAULT 'NO',
  `foto` MEDIUMBLOB NULL DEFAULT NULL,
  `longitudBytes` INT(11) NULL DEFAULT NULL,
  `desplazado` VARCHAR(2) NULL DEFAULT 'NO',
  `prioridad` INT(45) NULL DEFAULT NULL,
  `vereda_idvereda` INT(11) NOT NULL,
  `eliminado` VARCHAR(2) NOT NULL DEFAULT 'NO',
  PRIMARY KEY (`idpersona`, `vereda_idvereda`),
  UNIQUE INDEX `codigoAnuc_UNIQUE` (`codigoAnuc` ASC),
  INDEX `fk_persona_vereda1_idx` (`vereda_idvereda` ASC),
  CONSTRAINT `fk_persona_vereda1`
    FOREIGN KEY (`vereda_idvereda`)
    REFERENCES `vereda` (`idvereda`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 17
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`proyecto`
-- -----------------------------------------------------
CREATE TABLE `proyecto` (
  `idproyecto` INT(11) NOT NULL AUTO_INCREMENT,
  `abreviatura` VARCHAR(30) NOT NULL,
  `estado` VARCHAR(30) NOT NULL,
  `fecha` DATE NOT NULL,
  `cantidad` INT(11) NULL DEFAULT NULL,
  `rubro` INT(11) NULL DEFAULT NULL,
  `aporte` INT(11) NULL DEFAULT NULL,
  `observaciones` VARCHAR(500) NULL DEFAULT NULL,
  `nombre` VARCHAR(500) NULL DEFAULT NULL,
  PRIMARY KEY (`idproyecto`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`persona_proyecto`
-- -----------------------------------------------------
CREATE TABLE `persona_proyecto` (
  `idpersona_proyecto` INT(11) NOT NULL AUTO_INCREMENT,
  `persona_idpersona` INT(11) NOT NULL,
  `proyecto_idproyecto` INT(11) NOT NULL,
  `eliminado` VARCHAR(2) NOT NULL DEFAULT 'NO',
  `fechaIngreso` DATE NULL DEFAULT NULL,
  `esCambio` VARCHAR(2) NULL DEFAULT 'NO',
  `motivo` VARCHAR(100) NULL DEFAULT NULL,
  `usuarioAnterior` INT(11) NULL DEFAULT NULL,
  `nomConyugue` VARCHAR(40) NULL,
  `puntajeSisben` DECIMAL(5) NULL,
  `finca` VARCHAR(70) NULL,
  `area` DECIMAL(5) NULL,
  `Bsisben` VARCHAR(11) NULL,
  `Bconyugue` VARCHAR(11) NULL,
  `Bposecion` VARCHAR(11) NULL,
  `Bantecedentes` VARCHAR(11) NULL,
  `Bcaracterizacion` VARCHAR(11) NULL,
  `Bsuelos` VARCHAR(11) NULL,
  `Bfirma` VARCHAR(11) NULL,
  `Bgeo` VARCHAR(11) NULL COMMENT 'todos los qe inician con B son booleanos\n',
  PRIMARY KEY (`idpersona_proyecto`, `persona_idpersona`, `proyecto_idproyecto`),
  INDEX `fk_persona_has_proyecto_proyecto1_idx` (`proyecto_idproyecto` ASC),
  INDEX `fk_persona_has_proyecto_persona1_idx` (`persona_idpersona` ASC),
  CONSTRAINT `fk_persona_has_proyecto_persona1`
    FOREIGN KEY (`persona_idpersona`)
    REFERENCES `persona` (`idpersona`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_persona_has_proyecto_proyecto1`
    FOREIGN KEY (`proyecto_idproyecto`)
    REFERENCES `proyecto` (`idproyecto`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`proyecto_has_vereda`
-- -----------------------------------------------------
CREATE TABLE `proyecto_has_vereda` (
  `idproyecto_has_vereda` INT(11) NOT NULL AUTO_INCREMENT,
  `proyecto_idproyecto` INT(11) NOT NULL,
  `vereda_idvereda` INT(11) NOT NULL,
  PRIMARY KEY (`idproyecto_has_vereda`, `proyecto_idproyecto`, `vereda_idvereda`),
  INDEX `fk_proyecto_has_vereda_vereda1_idx` (`vereda_idvereda` ASC),
  INDEX `fk_proyecto_has_vereda_proyecto1_idx` (`proyecto_idproyecto` ASC),
  CONSTRAINT `fk_proyecto_has_vereda_proyecto1`
    FOREIGN KEY (`proyecto_idproyecto`)
    REFERENCES `proyecto` (`idproyecto`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_proyecto_has_vereda_vereda1`
    FOREIGN KEY (`vereda_idvereda`)
    REFERENCES `vereda` (`idvereda`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`valoresadicionales`
-- -----------------------------------------------------
CREATE TABLE `valoresadicionales` (
  `campo` VARCHAR(30) NOT NULL,
  `valor` VARCHAR(30) NOT NULL)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;



-- -----------------------------------------------------
-- Placeholder table for view `mydb`.`vistapersona`
-- -----------------------------------------------------
CREATE TABLE `vistapersona` (`id` INT);

-- -----------------------------------------------------
-- View `mydb`.`vistapersona`
-- -----------------------------------------------------
DROP TABLE `vistapersona`;

CREATE  OR REPLACE VIEW  vistapersona as (
SELECT m.nombre Municipio, pr.abreviatura Proyecto, p.nombre Nombre, p.apellido Apellido, 
p.identificacion CC, p.codigoAnuc Codigo, p.telefono Celular, p.edad Edad, p.genero Genero,
 p.estudios Estudios, p.tierra Tierra, p.sector Sector 
 from persona p LEFT OUTER JOIN persona_proyecto pp on p.idpersona=pp.persona_idpersona 
 LEFT OUTER join proyecto pr on pr.idproyecto=pp.proyecto_idproyecto 
 LEFT join vereda v on v.idvereda=p.vereda_idvereda 
 LEFT join municipio m on m.idmunicipio=v.municipio_idmunicipio 
 where p.eliminado='NO'  );

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
