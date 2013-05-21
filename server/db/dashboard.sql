-- phpMyAdmin SQL Dump
-- version 3.2.4
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generato il: 21 mag, 2013 at 01:01 PM
-- Versione MySQL: 5.1.44
-- Versione PHP: 5.3.1

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `dashboard`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `Accesso`
--

CREATE TABLE IF NOT EXISTS `Accesso` (
  `IDUtente` int(11) NOT NULL DEFAULT '0',
  `IDSorgente` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`IDUtente`,`IDSorgente`),
  UNIQUE KEY `IDUtente` (`IDUtente`,`IDSorgente`),
  KEY `IDSorgente` (`IDSorgente`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dump dei dati per la tabella `Accesso`
--

INSERT INTO `Accesso` (`IDUtente`, `IDSorgente`) VALUES
(1, 1),
(1, 2),
(1, 3),
(2, 3),
(1, 4);

-- --------------------------------------------------------

--
-- Struttura della tabella `Contenuto`
--

CREATE TABLE IF NOT EXISTS `Contenuto` (
  `IDPost` int(11) NOT NULL DEFAULT '0',
  `IDTopic` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`IDPost`,`IDTopic`),
  UNIQUE KEY `IDPost` (`IDPost`,`IDTopic`),
  KEY `IDTopic` (`IDTopic`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dump dei dati per la tabella `Contenuto`
--

INSERT INTO `Contenuto` (`IDPost`, `IDTopic`) VALUES
(1, 1),
(2, 1),
(5, 1),
(7, 1),
(6, 2);

-- --------------------------------------------------------

--
-- Struttura della tabella `Post`
--

CREATE TABLE IF NOT EXISTS `Post` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Link` varchar(500) NOT NULL,
  `Data` date NOT NULL,
  `Polarity` enum('-1','+1','0') NOT NULL DEFAULT '0',
  `ID_Fonte` int(11) NOT NULL,
  `Text` varchar(20000) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `Nome_fonte` (`ID_Fonte`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=31 ;

--
-- Dump dei dati per la tabella `Post`
--

INSERT INTO `Post` (`ID`, `Link`, `Data`, `Polarity`, `ID_Fonte`, `Text`) VALUES
(1, 'linnkpost.html', '2013-04-18', '+1', 1, 'TESTO'),
(2, 'lonkjao.html', '2013-05-06', '0', 2, 'testo testo'),
(5, 'qwe', '2013-04-11', '0', 4, 'qweds'),
(6, 'wdqdwqa', '2013-05-02', '-1', 3, 'dqw'),
(7, 'wrdnlererererer', '2013-05-25', '+1', 3, 'questa-stringa-deve-essere-piu-lunga-di-venticinque-caratteri-ooooooooookkkk?????????????????????'),
(8, 'csdgcfb', '2013-05-15', '+1', 2, ''),
(9, 'csdgcfb', '2013-05-16', '+1', 2, ''),
(10, 'sdfguh', '2013-05-14', '0', 2, 'popoop'),
(11, 'ciao11', '2013-04-02', '-1', 2, '1-testotestotestotestotestotesto'),
(12, 'ciao12', '2013-04-02', '-1', 2, '2-testotestotestotestotestotesto'),
(13, 'ciao13', '2013-04-02', '-1', 2, '3-testotestotestotestotestotesto'),
(14, 'ciao14', '2013-04-02', '-1', 2, '4-testotestotestotestotestotesto'),
(15, 'ciao15', '2013-04-02', '-1', 2, '5-testotestotestotestotestotesto'),
(16, 'ciao16', '2013-04-02', '-1', 2, '6'),
(17, 'ciao17', '2013-04-02', '-1', 2, '7'),
(18, 'ciao18', '2013-04-02', '+1', 2, '8'),
(19, 'ciao19', '2013-04-02', '-1', 2, '9'),
(20, 'ciao20', '2013-04-02', '-1', 2, '14-testotestotestotestotestotesto'),
(21, 'ciao21', '2013-04-02', '-1', 2, '14-testotestotestotestotestotesto'),
(22, 'ciao22', '2013-04-02', '-1', 2, '14-testotestotestotestotestotesto'),
(23, 'ciao23', '2013-04-02', '-1', 2, '14-testotestotestotestotestotesto'),
(24, 'ciao24', '2013-04-02', '-1', 2, '14-testotestotestotestotestotesto'),
(25, 'cavolo.html', '2013-05-01', '-1', 2, 'qqqq'),
(26, 'cavolaccio.html', '2013-05-03', '-1', 4, 'qwqww'),
(27, '123', '2013-04-18', '+1', 2, 'qwergdvbc'),
(28, '', '2013-04-18', '+1', 2, ''),
(29, 'rew', '2013-04-18', '+1', 1, ''),
(30, 'eaFS', '2013-04-18', '-1', 1, '');

-- --------------------------------------------------------

--
-- Struttura della tabella `Sorgenti`
--

CREATE TABLE IF NOT EXISTS `Sorgenti` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Nome` varchar(30) NOT NULL,
  `Pagina` varchar(50) DEFAULT NULL,
  `Link` varchar(500) NOT NULL,
  `Tipo` varchar(30) NOT NULL,
  `Autore` int(11) NOT NULL,
  `Icona` varchar(50) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `Nome` (`Nome`),
  KEY `Autore` (`Autore`),
  KEY `Nome_2` (`Nome`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Dump dei dati per la tabella `Sorgenti`
--

INSERT INTO `Sorgenti` (`ID`, `Nome`, `Pagina`, `Link`, `Tipo`, `Autore`, `Icona`) VALUES
(1, 'FB', 'il rock come filosofia di vita', 'https://www.facebook.com/pages/Il-Rock-come-filosofia-di-vita/115779158443402', 'gruppo', 1, 'icona.png'),
(2, 'Twitter', NULL, 'https://twitter.com/', 'social', 1, 'icona2.png'),
(3, 'traines', 'tre', 'https://www.google.it/search?q=dreines', 'mailing list', 1, 'icon.jpg'),
(4, 'Twitter', 'twitterpage.html', 'qwertyuk', 'gruppo', 1, 'ic.jpg');

-- --------------------------------------------------------

--
-- Struttura della tabella `Topics`
--

CREATE TABLE IF NOT EXISTS `Topics` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Topic` varchar(30) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `Topic` (`Topic`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dump dei dati per la tabella `Topics`
--

INSERT INTO `Topics` (`ID`, `Topic`) VALUES
(1, 'primotopic'),
(2, 'topicdue');

-- --------------------------------------------------------

--
-- Struttura della tabella `Utenti`
--

CREATE TABLE IF NOT EXISTS `Utenti` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Nome` varchar(30) NOT NULL,
  `Cognome` varchar(30) NOT NULL,
  `Email` varchar(30) NOT NULL,
  `Password` text NOT NULL,
  `Data_nascita` date NOT NULL,
  `Citta` varchar(30) NOT NULL,
  `Creato_da` int(11) NOT NULL,
  `Data_ins` date NOT NULL,
  `Admin` tinyint(1) NOT NULL DEFAULT '0',
  `Attivo` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`),
  KEY `Creato_da` (`Creato_da`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=9 ;

--
-- Dump dei dati per la tabella `Utenti`
--

INSERT INTO `Utenti` (`ID`, `Nome`, `Cognome`, `Email`, `Password`, `Data_nascita`, `Citta`, `Creato_da`, `Data_ins`, `Admin`, `Attivo`) VALUES
(1, 'admin', 'admin', 'admin@admin.it', 'admin', '2013-03-26', 'teramo', 1, '2013-03-15', 1, 0),
(2, 'ketty@at.it', 'di silvestre', 'kettybdo@hotmail.it', 'd03fb619b3f94efa8b2f56e44aa827e3', '2013-03-22', 'roma', 1, '2013-03-07', 0, 1),
(8, 'mara', 'disi', 'mara@mara.it', 'qwejyfgsjvl', '2013-04-10', 'frosinone', 1, '2013-04-18', 0, 1);

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `Accesso`
--
ALTER TABLE `Accesso`
  ADD CONSTRAINT `accesso_ibfk_4` FOREIGN KEY (`IDSorgente`) REFERENCES `sorgenti` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `accesso_ibfk_5` FOREIGN KEY (`IDUtente`) REFERENCES `utenti` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `Contenuto`
--
ALTER TABLE `Contenuto`
  ADD CONSTRAINT `contenuto_ibfk_3` FOREIGN KEY (`IDPost`) REFERENCES `post` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `contenuto_ibfk_4` FOREIGN KEY (`IDTopic`) REFERENCES `topics` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `Post`
--
ALTER TABLE `Post`
  ADD CONSTRAINT `post_ibfk_1` FOREIGN KEY (`ID_Fonte`) REFERENCES `sorgenti` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `Sorgenti`
--
ALTER TABLE `Sorgenti`
  ADD CONSTRAINT `sorgenti_ibfk_1` FOREIGN KEY (`Autore`) REFERENCES `utenti` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `Utenti`
--
ALTER TABLE `Utenti`
  ADD CONSTRAINT `utenti_ibfk_1` FOREIGN KEY (`Creato_da`) REFERENCES `utenti` (`ID`) ON DELETE NO ACTION ON UPDATE CASCADE;
