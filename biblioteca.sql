-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: 02-Jun-2017 às 02:57
-- Versão do servidor: 10.1.19-MariaDB
-- PHP Version: 5.5.38

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `biblioteca`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `cliente`
--

CREATE TABLE `cliente` (
  `id` int(11) NOT NULL,
  `matricula` varchar(20) DEFAULT NULL,
  `nome` varchar(55) DEFAULT NULL,
  `telefone` varchar(20) DEFAULT NULL,
  `diasAtraso` int(11) DEFAULT '0',
  `livrosRetirados` int(11) DEFAULT '0',
  `totalLivrosRetirados` int(11) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `cliente`
--

INSERT INTO `cliente` (`id`, `matricula`, `nome`, `telefone`, `diasAtraso`, `livrosRetirados`, `totalLivrosRetirados`) VALUES
(1, '1234', 'Rafael Abreu', '8888888', 0, 0, 1),
(3, '12345', 'Cliente atualizado', '555555', 0, 0, 0);

-- --------------------------------------------------------

--
-- Estrutura da tabela `emprestimo`
--

CREATE TABLE `emprestimo` (
  `id` int(11) NOT NULL,
  `cliente_id` int(11) DEFAULT NULL,
  `dataEntrega` date DEFAULT NULL,
  `diasAtraso` int(11) DEFAULT '0',
  `entregue` tinyint(1) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `emprestimo`
--

INSERT INTO `emprestimo` (`id`, `cliente_id`, `dataEntrega`, `diasAtraso`, `entregue`) VALUES
(1, 1, '2017-06-01', 0, 1),
(2, 1, '2017-12-12', 0, 1),
(3, 1, '1993-12-12', 0, 1),
(4, 1, '1993-12-12', 0, 1),
(5, 1, '1993-12-12', 0, 1),
(6, 1, '1999-12-12', 0, 1),
(8, 1, '1993-12-12', 0, 1),
(9, 1, '1993-12-12', 0, 1),
(10, 1, '1999-12-12', 0, 1),
(11, 1, '1993-12-12', 0, 1),
(13, 1, '1993-12-12', 0, 1),
(14, 1, '1993-12-12', 0, 1),
(15, 1, '1993-12-12', 0, 0);

-- --------------------------------------------------------

--
-- Estrutura da tabela `emprestimo_livro`
--

CREATE TABLE `emprestimo_livro` (
  `id` int(11) NOT NULL,
  `livro_id` int(11) DEFAULT NULL,
  `emprestimo_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `emprestimo_livro`
--

INSERT INTO `emprestimo_livro` (`id`, `livro_id`, `emprestimo_id`) VALUES
(1, 1, 1),
(2, 2, 1),
(3, 1, 1),
(4, 2, 1),
(5, 1, 1),
(6, 1, 1),
(7, 2, 15),
(8, 1, 15);

-- --------------------------------------------------------

--
-- Estrutura da tabela `livro`
--

CREATE TABLE `livro` (
  `id` int(11) NOT NULL,
  `isbn` varchar(20) DEFAULT NULL,
  `nome` varchar(55) DEFAULT NULL,
  `autores` varchar(255) DEFAULT NULL,
  `editora` varchar(50) DEFAULT NULL,
  `dataPublicacao` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `livro`
--

INSERT INTO `livro` (`id`, `isbn`, `nome`, `autores`, `editora`, `dataPublicacao`) VALUES
(1, '1324', 'Livro atualizado', 'Autor', 'Editora', '2015-12-12'),
(2, '12345', 'Livro 2', 'Autores 2', 'EditORA', '2017-06-02'),
(3, '12341', 'Livro 2', 'Autores', 'Editora', '2017-06-01'),
(4, '1234', 'Livro novo', 'dsasada', 'dasdsa', '1933-12-12'),
(5, '1234', 'Novo Livro', 'dasdsa', 'dasda', '1933-12-12'),
(6, '', '', '', '', '1993-12-12');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `matricula` (`matricula`);

--
-- Indexes for table `emprestimo`
--
ALTER TABLE `emprestimo`
  ADD PRIMARY KEY (`id`),
  ADD KEY `cliente_id` (`cliente_id`);

--
-- Indexes for table `emprestimo_livro`
--
ALTER TABLE `emprestimo_livro`
  ADD PRIMARY KEY (`id`),
  ADD KEY `livro_id` (`livro_id`),
  ADD KEY `emprestimo_livro_ibfk_2` (`emprestimo_id`);

--
-- Indexes for table `livro`
--
ALTER TABLE `livro`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `cliente`
--
ALTER TABLE `cliente`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `emprestimo`
--
ALTER TABLE `emprestimo`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;
--
-- AUTO_INCREMENT for table `emprestimo_livro`
--
ALTER TABLE `emprestimo_livro`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
--
-- AUTO_INCREMENT for table `livro`
--
ALTER TABLE `livro`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- Constraints for dumped tables
--

--
-- Limitadores para a tabela `emprestimo`
--
ALTER TABLE `emprestimo`
  ADD CONSTRAINT `emprestimo_ibfk_2` FOREIGN KEY (`cliente_id`) REFERENCES `cliente` (`id`);

--
-- Limitadores para a tabela `emprestimo_livro`
--
ALTER TABLE `emprestimo_livro`
  ADD CONSTRAINT `emprestimo_livro_ibfk_1` FOREIGN KEY (`livro_id`) REFERENCES `livro` (`id`),
  ADD CONSTRAINT `emprestimo_livro_ibfk_2` FOREIGN KEY (`emprestimo_id`) REFERENCES `emprestimo` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
