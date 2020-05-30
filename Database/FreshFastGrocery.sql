-- phpMyAdmin SQL Dump
-- version 4.9.5
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Apr 30, 2020 at 06:50 AM
-- Server version: 10.2.31-MariaDB
-- PHP Version: 7.2.29

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `u291070854_newgroc`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `id` int(8) NOT NULL,
  `username` text NOT NULL,
  `password` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`id`, `username`, `password`) VALUES
(1, 'admin', 'admin@123');

-- --------------------------------------------------------

--
-- Table structure for table `area_db`
--

CREATE TABLE `area_db` (
  `id` int(8) NOT NULL,
  `name` text NOT NULL,
  `dcharge` int(11) NOT NULL,
  `status` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `area_db`
--

INSERT INTO `area_db` (`id`, `name`, `dcharge`, `status`) VALUES
(1, 'Katargam, Surat', 6, '0'),
(2, 'Pune, Maharashtra', 8, '1'),
(3, 'Varachha, Surat', 5, '1'),
(4, 'Vesu, Surat', 20, '1'),
(5, 'Amroli, Surat', 10, '1'),
(6, 'Nanpura, Surat', 30, '1'),
(7, 'Abc Colony, AHM', 0, '1');

-- --------------------------------------------------------

--
-- Table structure for table `banner`
--

CREATE TABLE `banner` (
  `id` int(8) NOT NULL,
  `bimg` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `banner`
--

INSERT INTO `banner` (`id`, `bimg`) VALUES
(15, 'banner/5e9192f8909c8banner3.jpg'),
(16, 'banner/5e91931b08241banner2.jpg'),
(17, 'banner/5e91933f98f6dbanner4-min.jpg'),
(18, 'banner/5e91936beeb51banner1-min.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

CREATE TABLE `category` (
  `id` int(11) NOT NULL,
  `catname` text NOT NULL,
  `catimg` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `category`
--

INSERT INTO `category` (`id`, `catname`, `catimg`) VALUES
(1, 'Vegetables', 'cat/thump_1586602961.png'),
(2, 'Fruits', 'cat/thump_1586602997.png'),
(3, 'Juice', 'cat/thump_1586603029.png'),
(4, 'Dessert', 'cat/thump_1586603073.png'),
(5, 'Drink', 'cat/thump_1586603102.png'),
(6, 'Pizza', 'product/thump_1586603235.png'),
(7, 'Cake', 'product/thump_1586603254.png'),
(8, 'Brochettes', 'product/thump_1586603301.png'),
(10, 'General', 'product/thump_1588153291.png');

-- --------------------------------------------------------

--
-- Table structure for table `feedback`
--

CREATE TABLE `feedback` (
  `id` int(8) NOT NULL,
  `uid` int(11) NOT NULL,
  `rate` text NOT NULL,
  `msg` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `noti`
--

CREATE TABLE `noti` (
  `id` int(11) NOT NULL,
  `title` text NOT NULL,
  `img` text NOT NULL,
  `msg` text NOT NULL,
  `date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `id` int(8) NOT NULL,
  `oid` text NOT NULL,
  `uid` int(11) NOT NULL,
  `pname` text NOT NULL,
  `pid` text NOT NULL,
  `ptype` text NOT NULL,
  `pprice` text NOT NULL,
  `ddate` text NOT NULL,
  `timesloat` text NOT NULL,
  `order_date` date NOT NULL,
  `status` text NOT NULL,
  `qty` text NOT NULL,
  `total` int(11) NOT NULL,
  `rate` int(11) NOT NULL DEFAULT 0,
  `p_method` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`id`, `oid`, `uid`, `pname`, `pid`, `ptype`, `pprice`, `ddate`, `timesloat`, `order_date`, `status`, `qty`, `total`, `rate`, `p_method`) VALUES
(1, '#5ea9ac81bf2e4', 1, 'Brinjal$;Broccoli$;Tomato$;Tomato', '12$;13$;15$;15', '2 KG$;1 KG$;500GMS$;1KG', '80$;40$;80$;120', '--30-04-2020', '14:00 - 16:00', '2020-04-29', 'completed', '1$;2$;1$;1', 348, 1, 'Pickup myself'),
(2, '#5ea9c8e888add', 1, 'Daily Milk$;Vicks VapoRub$;All Stationary Pack$;Pomegranate Juice$;Pineapple Juice', '34$;33$;32$;18$;17', '2 LTR$;1 gms$;20 PCS$;2LTR$;1LTR', '500$;10$;200$;120$;300', '--01-05-2020', '14:00 - 16:00', '2020-04-30', 'processing', '1$;1$;1$;1$;1', 998, 0, 'Cash on delivery'),
(3, '#5ea9cfb231a7c', 1, 'Brochette$;Brochette', '28$;28', '9 PCS$;12 PCS', '80$;120', '--01-05-2020', '12:00 - 13:00', '2020-04-30', 'pending', '3$;3', 600, 0, 'Pickup myself'),
(4, '#5eaa6e1daa35e', 3, 'Strawberry', '11', '1 KG', '200', '--01-05-2020', '14:00 - 16:00', '2020-04-30', 'pending', '1', 200, 0, 'Cash on delivery'),
(5, '#5eaa6f3fd4de2', 1, 'Pineapple Juice$;Pomegranate Juice', '17$;18', '1LTR$;2LTR', '300$;120', '--01-05-2020', '12:00 - 13:00', '2020-04-30', 'pending', '1$;2', 548, 0, 'Cash on delivery');

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `id` int(11) NOT NULL,
  `pname` text NOT NULL,
  `sname` text NOT NULL,
  `cid` int(11) NOT NULL,
  `sid` int(11) NOT NULL,
  `psdesc` text NOT NULL,
  `pgms` text NOT NULL,
  `pprice` text NOT NULL,
  `status` int(11) NOT NULL,
  `stock` int(11) NOT NULL,
  `pimg` text NOT NULL,
  `date` datetime NOT NULL,
  `discount` int(11) NOT NULL DEFAULT 0,
  `popular` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`id`, `pname`, `sname`, `cid`, `sid`, `psdesc`, `pgms`, `pprice`, `status`, `stock`, `pimg`, `date`, `discount`, `popular`) VALUES
(9, 'Banana', 'Ambika Niketan', 2, 20, 'A banana is an elongated, edible fruit â€“ botanically a berry â€“ produced by several kinds of large herbaceous flowering plants in the genus Musa.', '12 PCS$;18 PCS', '60$;100', 1, 1, 'product/thump_1586929730.png', '2020-04-11 18:59:09', 0, 0),
(11, 'Strawberry', 'KMS', 2, 6, 'The garden strawberry is a widely grown hybrid species of the genus Fragaria', '1 KG', '200', 1, 1, 'product/thump_1586930721.png', '2020-04-15 11:35:21', 5, 0),
(12, 'Brinjal', 'KMS', 1, 17, 'Eggplant, aubergine or brinjal is a plant species in the nightshade family Solanaceae', '2 KG', '80', 1, 1, 'product/thump_1586934385.png', '2020-04-15 12:18:40', 0, 0),
(13, 'Broccoli', 'KMS', 1, 17, 'Broccoli is an edible green plant in the cabbage family whose large flowering head and stalk is eaten as a vegetable.', '1 KG', '40', 1, 1, 'product/thump_1586938942.png', '2020-04-15 13:52:22', 0, 0),
(14, 'Corn', 'KMS', 1, 17, 'Maize, also known as corn, is a cereal grain first domesticated by indigenous peoples in southern Mexico about 10,000 years ago', '2 KG', '60', 1, 1, 'product/thump_1586939067.png', '2020-04-15 13:54:27', 0, 0),
(15, 'Tomato', 'KMS', 1, 17, 'The tomato is the edible, often red, berry of the plant Solanum lycopersicum, commonly known as a tomato plant.', '500GMS$;1KG', '80$;120', 1, 1, 'product/thump_1586939525.png', '2020-04-15 14:02:05', 6, 0),
(16, 'Cherry', 'Ambika Niketan', 2, 5, 'A cherry is the fruit of many plants of the genus Prunus, and is a fleshy drupe. ', '1 KG', '100', 1, 1, 'product/thump_1586939873.png', '2020-04-15 14:07:53', 0, 0),
(17, 'Pineapple Juice', 'KMS', 3, 8, 'Pineapple juice is a liquid made from pressing the natural liquid from the pulp of the pineapple tropical plant.', '1LTR', '300', 1, 1, 'product/thump_1586940788.png', '2020-04-15 14:23:08', 0, 0),
(18, 'Pomegranate Juice', 'KMS', 3, 8, 'Pomegranate juice is made from the fruit of the pomegranate. It is used in cooking both as a fresh juice and as a concentrated syrup.', '2LTR', '120', 1, 1, 'product/thump_1586940999.png', '2020-04-15 14:26:39', 0, 0),
(19, 'Almond Cookie', 'KMS', 4, 10, 'An almond lover\'s cookie!', '20 PCS', '300', 1, 1, 'product/thump_1586941679.png', '2020-04-15 14:37:59', 0, 0),
(20, 'Doughnut', 'KMS', 4, 10, 'A doughnut or donut is a type of fried dough confection or dessert food', '3 PCS', '500', 1, 1, 'product/thump_1586942246.png', '2020-04-15 14:47:26', 0, 0),
(21, 'Ice Cream Jar', 'KMS', 5, 11, 'Pour cream, sugar, vanilla and salt into a 16 ounce mason jar and secure tightly with a lid', '500 ML', '100', 1, 1, 'product/thump_1586943051.png', '2020-04-15 15:00:51', 0, 0),
(22, 'Aperol Spritz', 'Ambika Niketan', 5, 12, 'A Spritz Veneziano, also called just Spritz, is an Italian wine-based cocktail', '800 ML', '80', 1, 1, 'product/thump_1586943234.png', '2020-04-15 15:03:54', 0, 1),
(23, 'Margarita', 'Ambika Niketan', 5, 12, 'A margarita is a cocktail consisting of tequila, orange liqueur, and lime juice often served with salt on the rim of the glass.', '300 ML', '20', 1, 1, 'product/thump_1586943383.png', '2020-04-15 15:06:23', 0, 0),
(24, 'Pizza Margherita', 'KMS', 6, 14, 'Pizza Margherita is a typical Neapolitan pizza, made with San Marzano tomatoes, mozzarella cheese, fresh basil, salt and extra-virgin olive oil. ', '9 PCS$;12 PCS', '100$;130', 1, 1, 'product/thump_1586943621.png', '2020-04-15 15:10:21', 0, 0),
(25, 'New York-Style Pizza', 'KMS', 6, 13, 'New York-style pizza is pizza made with a characteristically large hand-tossed thick crust', '9 PCS$;12 PCS', '80$;120', 1, 1, 'product/thump_1586944032.png', '2020-04-15 15:17:12', 0, 0),
(26, 'Fruitcake', 'KMS', 7, 15, 'Fruitcake is a cake made with candied or dried fruit, nuts, and spices, and optionally soaked in spirits', '1 KG', '500', 1, 1, 'product/thump_1586944255.png', '2020-04-15 15:20:55', 0, 0),
(27, 'Pastry Cake', 'KMS', 7, 16, 'Fruit Pastry Cake - yummy, juicy fruits on top of a rich and sweet cake', '1 KG', '80', 1, 1, 'product/thump_1586944376.png', '2020-04-15 15:22:56', 5, 0),
(28, 'Brochette', 'Ambika Niketan', 8, 19, 'A skewer is a thin metal or wood stick used to hold pieces of food together.', '9 PCS$;12 PCS', '80$;120', 1, 1, 'product/thump_1586944814.png', '2020-04-15 15:30:14', 0, 0),
(32, 'All Stationary Pack', 'KMS', 10, 21, 'Stationery is a mass noun referring to commercially manufactured writing materials, including cut paper, envelopes, writing implements, continuous form paper, and other office supplies. Stationery includes materials to be written on by hand or by equipment such as computer printers.', '20 PCS', '200', 1, 1, 'product/thump_1588154375.png', '2020-04-29 15:29:35', 20, 1),
(33, 'Vicks VapoRub', 'KMS', 10, 22, 'Don’t let cough and cold come in the way of you and your family’s sweet dreams. Vicks VapoRub gives you 8 hours of relief from cough and cold so you can enjoy a full night of restful sleep, even with a cold.', '1 gms$;250 gms', '10$;200', 1, 1, 'product/thump_1588156821.png', '2020-04-29 16:10:21', 5, 1),
(34, 'Daily Milk', 'Ambika Niketan', 10, 22, 'Milk is a nutrient-rich, white liquid food produced by the mammary glands of mammals. ', '2 LTR', '500', 1, 1, 'product/thump_1588158399.png', '2020-04-29 16:36:39', 20, 1);

-- --------------------------------------------------------

--
-- Table structure for table `rate_order`
--

CREATE TABLE `rate_order` (
  `id` int(8) NOT NULL,
  `oid` text NOT NULL,
  `uid` int(11) NOT NULL,
  `msg` text NOT NULL,
  `rate` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `rate_order`
--

INSERT INTO `rate_order` (`id`, `oid`, `uid`, `msg`, `rate`) VALUES
(1, '#5ea9ac81bf2e4', 1, 'You\'re service and food products really amazing ! i am always doing order regularly. ', 1);

-- --------------------------------------------------------

--
-- Table structure for table `setting`
--

CREATE TABLE `setting` (
  `id` int(11) NOT NULL,
  `one_key` text NOT NULL,
  `one_hash` text NOT NULL,
  `otp_key` text NOT NULL,
  `currency` text CHARACTER SET utf8 NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `setting`
--

INSERT INTO `setting` (`id`, `one_key`, `one_hash`, `otp_key`, `currency`) VALUES
(1, '283fb55d-2a76-406e-902b-12345678', 'ZjhjODlkMWItYzRmZS00MTY1LThkN2ItOTBkNjhl12345678', '312954A2HQoO1XpGym5e12345678', '₹');

-- --------------------------------------------------------

--
-- Table structure for table `subcategory`
--

CREATE TABLE `subcategory` (
  `id` int(11) NOT NULL,
  `cat_id` int(11) NOT NULL,
  `name` text NOT NULL,
  `img` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `subcategory`
--

INSERT INTO `subcategory` (`id`, `cat_id`, `name`, `img`) VALUES
(4, 1, 'Salad', 'product/thump_1588150565.png'),
(5, 2, 'Avocado', 'product/thump_1588150668.png'),
(6, 2, 'Mix', 'product/thump_1588150705.png'),
(7, 3, 'Liquid Juice', 'product/thump_1588150829.png'),
(8, 3, 'Fruits Juice', 'product/thump_1588150867.png'),
(9, 4, 'French Fries ', 'product/thump_1588150945.png'),
(10, 4, 'Candy', 'product/thump_1588150976.png'),
(11, 5, 'Coffe', 'product/thump_1588151043.png'),
(12, 5, 'Wine', 'product/thump_1588151084.png'),
(13, 6, 'Mushroom ', 'product/thump_1588151281.png'),
(14, 6, 'Garlic', 'product/thump_1588151312.png'),
(15, 7, 'Fruits Cake', 'product/thump_1588151366.png'),
(16, 7, 'Muffin', 'product/thump_1588151405.png'),
(17, 1, 'All Vegi', 'product/thump_1588151560.png'),
(18, 8, 'Noodles', 'product/thump_1588151656.png'),
(19, 8, 'Chiken', 'product/thump_1588151693.png'),
(20, 2, 'Banana', 'product/thump_1588152504.png'),
(21, 10, 'Stationary ', 'product/thump_1588153521.png'),
(22, 10, 'Medicine', 'product/thump_1588153607.png');

-- --------------------------------------------------------

--
-- Table structure for table `template`
--

CREATE TABLE `template` (
  `id` int(11) NOT NULL,
  `title` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `message` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `url` text COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `timeslot`
--

CREATE TABLE `timeslot` (
  `id` int(11) NOT NULL,
  `mintime` text NOT NULL,
  `maxtime` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `timeslot`
--

INSERT INTO `timeslot` (`id`, `mintime`, `maxtime`) VALUES
(2, '09:00', '10:30'),
(3, '10:00', '11:00'),
(4, '12:00', '13:00'),
(5, '14:00', '16:00'),
(6, '07:00', '10:00'),
(7, '01:11', '14:00');

-- --------------------------------------------------------

--
-- Table structure for table `uread`
--

CREATE TABLE `uread` (
  `id` int(11) NOT NULL,
  `uid` int(11) NOT NULL,
  `nid` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `name` text NOT NULL,
  `hno` text NOT NULL,
  `society` text NOT NULL,
  `area` text NOT NULL,
  `pincode` text NOT NULL,
  `imei` text NOT NULL,
  `email` text NOT NULL,
  `mobile` text NOT NULL,
  `landmark` text NOT NULL,
  `rdate` datetime NOT NULL,
  `password` text NOT NULL,
  `status` int(11) NOT NULL DEFAULT 1,
  `pin` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `name`, `hno`, `society`, `area`, `pincode`, `imei`, `email`, `mobile`, `landmark`, `rdate`, `password`, `status`, `pin`) VALUES
(1, 'test', '101', 'Ram', 'Pune, Maharashtra', '696969', '4221f6732d0bfa6e', 'test@gmail.com', '7276465975', 'Chandni chowk', '2020-04-29 22:01:37', '123', 1, NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `area_db`
--
ALTER TABLE `area_db`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `banner`
--
ALTER TABLE `banner`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `feedback`
--
ALTER TABLE `feedback`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `noti`
--
ALTER TABLE `noti`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `rate_order`
--
ALTER TABLE `rate_order`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `setting`
--
ALTER TABLE `setting`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `subcategory`
--
ALTER TABLE `subcategory`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `template`
--
ALTER TABLE `template`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `timeslot`
--
ALTER TABLE `timeslot`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `uread`
--
ALTER TABLE `uread`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin`
--
ALTER TABLE `admin`
  MODIFY `id` int(8) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `area_db`
--
ALTER TABLE `area_db`
  MODIFY `id` int(8) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `banner`
--
ALTER TABLE `banner`
  MODIFY `id` int(8) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT for table `category`
--
ALTER TABLE `category`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `feedback`
--
ALTER TABLE `feedback`
  MODIFY `id` int(8) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `noti`
--
ALTER TABLE `noti`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `id` int(8) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `product`
--
ALTER TABLE `product`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=36;

--
-- AUTO_INCREMENT for table `rate_order`
--
ALTER TABLE `rate_order`
  MODIFY `id` int(8) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `setting`
--
ALTER TABLE `setting`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `subcategory`
--
ALTER TABLE `subcategory`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- AUTO_INCREMENT for table `template`
--
ALTER TABLE `template`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `timeslot`
--
ALTER TABLE `timeslot`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `uread`
--
ALTER TABLE `uread`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
