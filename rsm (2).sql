-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Aug 06, 2026 at 08:58 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `rsm`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `adminId` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`adminId`, `username`, `password`) VALUES
(1, 'admin', 'Admin@123');

-- --------------------------------------------------------

--
-- Table structure for table `booking`
--

CREATE TABLE `booking` (
  `bookingId` int(11) NOT NULL,
  `customerId` int(11) DEFAULT NULL,
  `roomId` int(11) DEFAULT NULL,
  `checkInDate` date DEFAULT NULL,
  `checkOutDate` date DEFAULT NULL,
  `numberOfGuests` int(11) DEFAULT NULL,
  `totalAmount` double DEFAULT NULL,
  `bookingStatus` varchar(30) DEFAULT NULL,
  `createdTime` timestamp NOT NULL DEFAULT current_timestamp(),
  `canCancel` tinyint(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `booking`
--

INSERT INTO `booking` (`bookingId`, `customerId`, `roomId`, `checkInDate`, `checkOutDate`, `numberOfGuests`, `totalAmount`, `bookingStatus`, `createdTime`, `canCancel`) VALUES
(1, 9, 22, '2026-08-07', '2026-08-09', 2, 4200, 'conformed', '2026-08-04 19:12:40', 1),
(2, 7, 13, '2026-08-04', '2026-08-07', 4, 3500, 'Cancelled', '2026-08-04 18:43:39', 1),
(5, 9, 23, '2026-08-07', '2026-08-10', 5, 16200, 'Cancelled', '2026-08-04 19:18:13', 1),
(6, 9, 20, '2026-09-07', '2026-09-10', 2, 16500, 'Cancelled', '2026-08-04 19:23:43', 1),
(14, 2, 20, '2027-08-04', '2027-08-07', 2, 16500, 'Cancelled', '2026-08-04 14:07:42', 1),
(15, 2, 22, '2026-08-07', '2026-08-09', 2, 4200, 'Confirmed', '2026-08-04 21:03:35', 1),
(16, 2, 21, '2026-08-09', '2026-08-11', 2, 90, 'Confirmed', '2026-08-04 21:04:20', 1),
(123, 4, 16, '2026-08-07', '2026-08-11', 2, 20800, 'Confirmed', '2026-08-06 09:52:44', 1),
(142, 1, 8, '2026-08-11', '2026-08-14', 2, 15000, 'Confirmed', '2026-08-06 13:00:38', 1),
(143, 14, 9, '2026-08-17', '2026-08-19', 1, 3200, 'Confirmed', '2026-08-06 12:10:18', 1),
(145, 14, 11, '2026-08-11', '2026-08-14', 2, 7800, 'Confirmed', '2026-08-06 10:46:20', 1),
(146, 1, 7, '2026-08-07', '2026-08-11', 3, 20000, 'Cancelled', '2026-08-06 12:54:04', 1),
(147, 13, 23, '2026-08-10', '2026-08-12', 2, 10800, 'Cancelled', '2026-08-06 09:45:17', 1),
(152, 13, 20, '2026-08-07', '2026-08-10', 2, 16500, 'Confirmed', '2026-08-06 09:47:15', 1),
(154, 11, 13, '2026-08-07', '2026-08-10', 2, 10800, 'Confirmed', '2026-08-06 11:07:01', 1),
(158, 14, 15, '2026-08-07', '2026-08-11', 4, 20800, 'Confirmed', '2026-08-06 10:27:30', 1),
(214, 14, 10, '2026-09-11', '2026-09-12', 1, 1600, 'Confirmed', '2026-08-06 12:06:31', 1),
(247, 14, 17, '2026-08-07', '2026-08-11', 1, 6800, 'Confirmed', '2026-08-06 10:26:06', 1),
(254, 14, 14, '2026-10-14', '2026-10-16', 2, 7200, 'Confirmed', '2026-08-06 09:59:33', 1),
(257, 1, 8, '2026-09-11', '2026-09-14', 4, 15000, 'Cancelled', '2026-08-06 12:33:21', 1),
(258, 4, 23, '2026-08-09', '2026-08-11', 2, 10800, 'Confirmed', '2026-08-06 09:52:04', 1),
(321, 1, 7, '2027-02-08', '2027-02-11', 4, 15000, 'Confirmed', '2026-08-06 13:05:28', 1),
(369, 14, 19, '2026-12-12', '2026-12-16', 2, 15200, 'Cancelled', '2026-08-06 10:14:00', 1);

--
-- Triggers `booking`
--
DELIMITER $$
CREATE TRIGGER `After_Booking_Cancel` AFTER UPDATE ON `booking` FOR EACH ROW BEGIN

    IF NEW.bookingStatus = 'Cancelled' THEN

        UPDATE room
        SET status = 'Available'
        WHERE roomId = NEW.roomId;

    END IF;

END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `After_Booking_Insert` AFTER INSERT ON `booking` FOR EACH ROW BEGIN

    UPDATE room
    SET status = 'Booked'
    WHERE roomId = NEW.roomId;

END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `checkinout`
--

CREATE TABLE `checkinout` (
  `checkId` int(11) NOT NULL,
  `bookingId` int(11) DEFAULT NULL,
  `checkInDate` date DEFAULT NULL,
  `checkOutDate` date DEFAULT NULL,
  `status` varchar(30) DEFAULT NULL,
  `customerId` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `checkinout`
--

INSERT INTO `checkinout` (`checkId`, `bookingId`, `checkInDate`, `checkOutDate`, `status`, `customerId`) VALUES
(7, 15, '2026-08-07', '2026-08-09', 'Checked-In', 2),
(8, 16, '2026-08-09', '2026-08-11', 'Not Checked-In', 2),
(9, 147, '2026-08-10', '2026-08-12', 'Checked-In', 13),
(10, 152, '2026-08-07', '2026-08-10', 'Not Checked-In', 13),
(11, 258, '2026-08-09', '2026-08-11', 'Not Checked-In', 4),
(12, 123, '2026-08-07', '2026-08-11', 'Checked-In', 4),
(13, 254, '2026-10-14', '2026-10-16', 'Not Checked-In', 14),
(15, 247, '2026-08-07', '2026-08-11', 'Not Checked-In', 14),
(16, 158, '2026-08-07', '2026-08-11', 'Not Checked-In', 14),
(17, 145, '2026-08-11', '2026-08-14', 'Checked-In', 14),
(18, 154, '2026-08-07', '2026-08-10', 'Not Checked-In', 11),
(19, 214, '2026-09-11', '2026-09-12', 'Checked-Out', 14),
(20, 143, '2026-08-17', '2026-08-19', 'Not Checked-In', 14),
(21, 257, '2026-09-11', '2026-09-14', 'Checked-In', 1),
(22, 146, '2026-08-07', '2026-08-11', 'Checked-In', 1),
(23, 142, '2026-08-11', '2026-08-14', 'Checked-In', 1),
(24, 321, '2027-02-08', '2027-02-11', 'Not Checked-In', 1);

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `customerId` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `phone` varchar(15) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `address` varchar(200) DEFAULT NULL,
  `idProof` varchar(50) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `firstLogin` int(11) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`customerId`, `name`, `gender`, `age`, `phone`, `email`, `address`, `idProof`, `password`, `firstLogin`) VALUES
(1, 'khushi', 'female', 26, '8200719386', 'khushi@gmail.com', 'anjar ', 'adharcard', 'khushi@13\r\n', 1),
(2, 'rahi', 'Male', 20, '8521479631', 'rahi@gmail.com', 'kutch', 'PAN Card', 'rahi@22400', 1),
(3, 'rahul', 'Male', 24, '8279631458', 'rahul@gmail.com', 'goa', 'PAN Card', 'Rahul@14', 1),
(4, 'ritika', 'Female', 23, '6598741230', 'ritu@gmail.com', 'bhuj', 'PAN Card', 'Rittu@13', 1),
(7, 'Rahul Sharma', 'Male', 25, '9876543210', 'rahul@gmail.com', 'Ahmedabad', 'Aadhar', 'rahul123', 1),
(8, 'Priya Patel', 'Female', 22, '9876501234', 'priya@gmail.com', 'Surat', 'PAN', 'priya123', 1),
(9, 'Amit Verma', 'Male', 30, '9988776655', 'amit@gmail.com', 'Vadodara', 'Passport', 'amit123', 1),
(11, 'karan', 'Male', 35, '9012345678', 'karan@gmail.com', 'Bhavnagar', 'Aadhar', 'karan123', 1),
(12, 'riyandh', 's,', 25, '6598741234', 'd@gmail.com', 'kutch', 'PAN Card', 'Rituu@1313', 1),
(13, 'snjkdh', 'Female', 24, '8200719384', 'chd@gmail.com', 'jfb', 'PAN Card', 'Riitu@12', 0),
(14, 'riddhi', 'Female', 25, '8200719384', 'riddhi@gmail.com', 'ahemdabad', 'PAN Card', 'Riddhi@13', 0),
(120, 'khushi', 'female', 26, '8200719386', 'khushi@gmail.com', 'anjar ', 'adharcard', 'khushi@13\r\n', 1);

-- --------------------------------------------------------

--
-- Table structure for table `designation`
--

CREATE TABLE `designation` (
  `designationId` int(11) NOT NULL,
  `designationName` varchar(50) NOT NULL,
  `salary` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `designation`
--

INSERT INTO `designation` (`designationId`, `designationName`, `salary`) VALUES
(1, 'Manager', 50000),
(2, 'Receptionist', 25000),
(3, 'Chef', 35000),
(4, 'Housekeeping', 22000),
(5, 'Security Guard', 20000),
(6, 'Waiter', 18000),
(7, 'Cleaner', 17000),
(8, 'Accountant', 40000);

-- --------------------------------------------------------

--
-- Table structure for table `feedback`
--

CREATE TABLE `feedback` (
  `feedbackId` int(11) NOT NULL,
  `customerId` int(11) DEFAULT NULL,
  `rating` int(11) DEFAULT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `feedbackDate` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `feedback`
--

INSERT INTO `feedback` (`feedbackId`, `customerId`, `rating`, `comment`, `feedbackDate`) VALUES
(2, 9, 5, 'Good', '2026-08-05'),
(3, 14, 1, 'Good', '2026-08-06');

-- --------------------------------------------------------

--
-- Table structure for table `food`
--

CREATE TABLE `food` (
  `foodId` int(11) NOT NULL,
  `foodName` varchar(100) NOT NULL,
  `category` varchar(50) NOT NULL,
  `price` double NOT NULL,
  `status` varchar(20) DEFAULT 'Available'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `food_menu`
--

CREATE TABLE `food_menu` (
  `foodId` int(11) NOT NULL,
  `foodName` varchar(100) DEFAULT NULL,
  `category` varchar(50) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `availability` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `food_menu`
--

INSERT INTO `food_menu` (`foodId`, `foodName`, `category`, `price`, `availability`) VALUES
(1, 'Tea', 'Beverage', 20, 'Available'),
(2, 'Coffee', 'Beverage', 30, 'Available'),
(3, 'Cold Drink', 'Beverage', 50, 'Available'),
(4, 'Mineral Water', 'Beverage', 20, 'Available'),
(5, 'Veg Sandwich', 'Snacks', 80, 'Available'),
(6, 'Cheese Sandwich', 'Snacks', 120, 'Available'),
(7, 'French Fries', 'Snacks', 100, 'Available'),
(8, 'Burger', 'Fast Food', 150, 'Available'),
(9, 'Pizza', 'Fast Food', 300, 'Available'),
(10, 'Pasta', 'Fast Food', 250, 'Available'),
(11, 'Paneer Butter Masala', 'Main Course', 280, 'Available'),
(12, 'Dal Tadka', 'Main Course', 180, 'Available'),
(13, 'Mix Veg', 'Main Course', 220, 'Available'),
(14, 'Butter Naan', 'Bread', 40, 'Available'),
(15, 'Tandoori Roti', 'Bread', 20, 'Available'),
(16, 'Veg Biryani', 'Rice', 250, 'Available'),
(17, 'Chicken Biryani', 'Rice', 350, 'Available'),
(18, 'Ice Cream', 'Dessert', 100, 'Available'),
(19, 'Gulab Jamun', 'Dessert', 80, 'Available'),
(20, 'dairy milk silk', 'Dessert', 150, 'Available');

-- --------------------------------------------------------

--
-- Table structure for table `food_order`
--

CREATE TABLE `food_order` (
  `orderId` int(11) NOT NULL,
  `customerId` int(11) DEFAULT NULL,
  `bookingId` int(11) DEFAULT NULL,
  `foodId` int(11) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `totalBill` double DEFAULT NULL,
  `orderStatus` varchar(30) DEFAULT 'Pending'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `food_order`
--

INSERT INTO `food_order` (`orderId`, `customerId`, `bookingId`, `foodId`, `quantity`, `totalBill`, `orderStatus`) VALUES
(5, 2, NULL, 20, 2, 300, 'Ordered'),
(6, 2, NULL, 7, 3, 300, 'Cancelled'),
(7, 2, NULL, 13, 2, 440, 'Ordered'),
(8, 2, NULL, 19, 2, 160, 'Ordered'),
(15, 7, 2, 10, 2, 500, 'Ordered'),
(18, 9, 6, 16, 2, 500, 'Ordered'),
(19, 9, 6, 19, 2, 160, 'Ordered'),
(20, 13, 152, 19, 2, 160, 'Cancelled'),
(21, 14, 254, 20, 2, 300, 'Ordered'),
(22, 14, 254, 19, 2, 160, 'Ordered'),
(29, 11, NULL, 14, 2, 80, 'Ordered'),
(30, 11, NULL, 17, 2, 700, 'Ordered'),
(31, 11, 154, 14, 2, 80, 'Ordered'),
(34, 1, NULL, 14, 2, 80, 'Ordered'),
(35, 1, NULL, 14, 2, 80, 'Ordered'),
(36, 1, NULL, 17, 2, 700, 'Ordered'),
(37, 1, 257, 5, 2, 160, 'Ordered'),
(38, 1, 321, 14, 2, 80, 'Ordered'),
(39, 1, 321, 14, 2, 80, 'Ordered'),
(40, 1, 321, 14, 2, 80, 'Ordered');

-- --------------------------------------------------------

--
-- Table structure for table `game`
--

CREATE TABLE `game` (
  `gameId` int(11) NOT NULL,
  `gameName` varchar(100) NOT NULL,
  `gameType` varchar(50) DEFAULT NULL,
  `price` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `game`
--

INSERT INTO `game` (`gameId`, `gameName`, `gameType`, `price`) VALUES
(1, 'Cricket', 'Outdoor', 500),
(2, 'Football', 'Outdoor', 600),
(3, 'Badminton', 'Indoor', 300),
(4, 'Table Tennis', 'Indoor', 250),
(5, 'Chess', 'Board Game', 100),
(6, 'Carrom', 'Board Game', 120),
(7, 'Snooker', 'Indoor', 400),
(8, 'Bowling', 'Indoor', 700),
(9, 'Swimming', 'Water Sport', 350),
(10, 'Basketball', 'Outdoor', 450),
(11, 'Volleyball', 'Outdoor', 400),
(12, 'Lawn Tennis', 'Outdoor', 600),
(13, 'Archery', 'Adventure', 800),
(14, 'Horse Riding', 'Adventure', 1200),
(15, 'Go Karting', 'Adventure', 1500),
(16, 'Paintball', 'Adventure', 1000),
(17, 'Cycling', 'Outdoor', 300),
(18, 'Mini Golf', 'Outdoor', 500),
(19, 'Air Hockey', 'Indoor', 250),
(20, 'VR Gaming', 'Indoor', 900);

-- --------------------------------------------------------

--
-- Table structure for table `game_booking`
--

CREATE TABLE `game_booking` (
  `gameBookingId` int(11) NOT NULL,
  `customerId` int(11) NOT NULL,
  `bookingId` int(11) DEFAULT NULL,
  `gameId` int(11) NOT NULL,
  `numberOfPlayers` int(11) NOT NULL,
  `totalAmount` double DEFAULT NULL,
  `bookingStatus` varchar(30) DEFAULT 'Booked'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `game_booking`
--

INSERT INTO `game_booking` (`gameBookingId`, `customerId`, `bookingId`, `gameId`, `numberOfPlayers`, `totalAmount`, `bookingStatus`) VALUES
(4, 2, NULL, 20, 14, 12600, 'Cancelled'),
(5, 2, NULL, 19, 2, 500, 'Booked'),
(9, 9, 6, 14, 2, 2400, 'Booked'),
(10, 13, 152, 14, 2, 2400, 'Cancelled'),
(11, 14, 254, 18, 2, 1000, 'Booked'),
(12, 14, 254, 17, 2, 600, 'Booked'),
(16, 11, 154, 14, 5, 6000, 'Booked'),
(17, 1, NULL, 19, 4, 1000, 'Booked'),
(18, 1, NULL, 16, 2, 2000, 'Booked'),
(19, 1, NULL, 3, 2, 600, 'Booked');

-- --------------------------------------------------------

--
-- Table structure for table `payment`
--

CREATE TABLE `payment` (
  `paymentId` int(11) NOT NULL,
  `customerId` int(11) DEFAULT NULL,
  `bookingId` int(11) DEFAULT NULL,
  `roomCharges` double DEFAULT 0,
  `restaurantCharges` double DEFAULT 0,
  `gameCharges` double DEFAULT 0,
  `subTotal` double DEFAULT 0,
  `gst` double DEFAULT 0,
  `totalAmount` double DEFAULT 0,
  `paymentMethod` varchar(20) DEFAULT NULL,
  `paymentDate` date DEFAULT NULL,
  `paymentStatus` varchar(20) DEFAULT 'Pending'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `payment`
--

INSERT INTO `payment` (`paymentId`, `customerId`, `bookingId`, `roomCharges`, `restaurantCharges`, `gameCharges`, `subTotal`, `gst`, `totalAmount`, `paymentMethod`, `paymentDate`, `paymentStatus`) VALUES
(3, 2, NULL, 0, 1040, 12600, 13640, 2455.2, 16095.2, 'Card', '2026-08-04', 'Paid'),
(4, 2, NULL, 0, 1200, 12600, 13800, 2484, 188.79999999999927, 'Card', '2026-08-04', 'Paid'),
(13, 9, NULL, 0, 500, 2400, 2900, 522, 3422, 'Card', '2026-08-05', 'Paid'),
(14, 9, NULL, 0, 660, 2400, 3060, 550.8, 188.80000000000018, 'Card', '2026-08-05', 'Paid'),
(15, 13, 152, 16500, 160, 2400, 19060, 3430.8, 22490.8, 'Card', '2026-08-06', 'Paid'),
(16, 4, 123, 20800, 0, 0, 20800, 3744, 24544, 'Card', '2026-08-06', 'Paid'),
(17, 14, 254, 7200, 300, 1000, 8500, 1530, 10030, 'Card', '2026-08-06', 'Paid'),
(18, 14, 254, 7200, 460, 1600, 9260, 1666.8, 896.7999999999993, 'Card', '2026-08-06', 'Paid'),
(19, 14, 254, 7200, 660, 1600, 9460, 1702.8, 236, 'Card', '2026-08-06', 'Paid'),
(20, 14, 254, 7200, 1360, 2200, 10760, 1936.8, 1534, 'Cash', '2026-08-06', 'Paid'),
(21, 14, 158, 20800, 1520, 2200, 24520, 4413.6, 16236.8, 'Cash', '2026-08-06', 'Paid'),
(22, 14, 158, 20800, 1520, 5200, 27520, 4953.6, 3540, 'Cash', '2026-08-06', 'Paid'),
(23, 11, NULL, 0, 80, 0, 80.00000000000001, 14.399999999999991, 94.4, 'Card', '2026-08-06', 'Paid'),
(24, 11, 154, 10800, 780, 0, 11500, 2070, 13570, 'Cash', '2026-08-06', 'Paid'),
(25, 11, 154, 10800, 780, 6000, 6000.000000000002, 1080, 7080.000000000002, 'Cash', '2026-08-06', 'Paid'),
(27, 1, NULL, 0, 80, 1000, 1080.0000000000002, 194.39999999999986, 1274.4, 'Card', '2026-08-06', 'Paid'),
(28, 1, NULL, 0, 860, 1000, 780.0000000000001, 140.39999999999998, 920.4000000000001, 'Card', '2026-08-06', 'Paid'),
(29, 1, NULL, 0, 860, 3600, 2600, 468, 3068, 'Card', '2026-08-06', 'Paid'),
(30, 1, 257, 15000, 860, 3600, 15000, 2700, 17700, 'Card', '2026-08-06', 'Paid'),
(31, 1, 142, 15000, 1020, 3600, 159.9999999999994, 28.79999999999987, 188.79999999999927, 'Cash', '2026-08-06', 'Paid'),
(32, 1, 142, 15000, 1100, 3600, 80.00000000000124, 14.400000000000219, 94.40000000000146, 'Card', '2026-08-06', 'Paid'),
(33, 1, 142, 15000, 1180, 3600, 80.00000000000124, 14.400000000000219, 94.40000000000146, 'Cash', '2026-08-06', 'Paid'),
(34, 1, 142, 15000, 1260, 3600, 79.99999999999815, 14.399999999999665, 94.39999999999782, 'Cash', '2026-08-06', 'Paid');

-- --------------------------------------------------------

--
-- Table structure for table `room`
--

CREATE TABLE `room` (
  `roomId` int(11) NOT NULL,
  `roomNumber` varchar(20) DEFAULT NULL,
  `roomType` varchar(50) DEFAULT NULL,
  `pricePerNight` double DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  `capacity` int(11) DEFAULT NULL,
  `floor` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `room`
--

INSERT INTO `room` (`roomId`, `roomNumber`, `roomType`, `pricePerNight`, `status`, `capacity`, `floor`) VALUES
(1, '101', 'Single', 1500, 'Available', 1, 1),
(2, '102', 'Single', 1500, 'Available', 1, 1),
(3, '103', 'Double', 2500, 'Available', 2, 1),
(4, '104', 'Double', 2500, 'Available', 2, 1),
(5, '105', 'Deluxe', 3500, 'Available', 2, 1),
(6, '106', 'Deluxe', 3500, 'Available', 2, 1),
(7, '107', 'Suite', 5000, 'Booked', 4, 1),
(8, '108', 'Suite', 5000, 'Booked', 4, 1),
(9, '201', 'Single', 1600, 'Booked', 1, 2),
(10, '202', 'Single', 1600, 'Booked', 1, 2),
(11, '203', 'Double', 2600, 'Booked', 2, 2),
(12, '212', 'Double', 5400, 'Booked', 2, 2),
(13, '205', 'Deluxe', 3600, 'Booked', 2, 2),
(14, '206', 'Deluxe', 3600, 'Booked', 2, 2),
(15, '207', 'Suite', 5200, 'Booked', 4, 2),
(16, '208', 'Suite', 5200, 'Booked', 4, 2),
(17, '301', 'Single', 1700, 'Booked', 1, 3),
(18, '302', 'Double', 2700, 'Booked', 2, 3),
(19, '303', 'Deluxe', 3800, 'Available', 3, 3),
(20, '304', 'Suite', 5500, 'Booked', 5, 3),
(21, '258', 'exclusive', 600, 'Booked', 4, 14),
(22, '14', 'exculsive', 2100, 'Booked', 2, 2),
(23, '147', 'Double', 5400, 'Booked', 5, 4),
(26, '124', 'Double', 2500, 'Booked', 2, 2),
(107, '401', 'Single', 500, 'Available', 1, 0),
(108, '402', 'Single', 700, 'Available', 1, 1),
(109, '403', 'Double', 900, 'Available', 2, 2),
(110, '404', 'Double', 1100, 'Available', 2, 3),
(111, '405', 'Deluxe', 1300, 'Available', 3, 4),
(112, '406', 'Deluxe', 1500, 'Available', 3, 5),
(113, '407', 'Suite', 1800, 'Available', 4, 6),
(114, '408', 'Suite', 2000, 'Available', 5, 7),
(115, '409', 'Single', 2200, 'Available', 1, 8),
(116, '410', 'Double', 2400, 'Available', 2, 9),
(117, '411', 'Deluxe', 2600, 'Available', 3, 10),
(118, '412', 'Suite', 2800, 'Available', 4, 11),
(119, '413', 'Single', 3000, 'Available', 1, 12),
(120, '414', 'Double', 3300, 'Available', 2, 13),
(121, '415', 'Deluxe', 3600, 'Available', 3, 14),
(122, '416', 'Suite', 3900, 'Available', 4, 15),
(123, '417', 'Single', 4200, 'Available', 1, 16),
(124, '418', 'Double', 4500, 'Available', 2, 17),
(125, '419', 'Deluxe', 4800, 'Available', 3, 18),
(126, '420', 'Suite', 5000, 'Available', 5, 20);

-- --------------------------------------------------------

--
-- Table structure for table `staff`
--

CREATE TABLE `staff` (
  `staffId` int(11) NOT NULL,
  `staffName` varchar(100) DEFAULT NULL,
  `gender` varchar(20) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `phone` varchar(15) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `designation` varchar(50) DEFAULT NULL,
  `salary` double DEFAULT NULL,
  `shift` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `staff`
--

INSERT INTO `staff` (`staffId`, `staffName`, `gender`, `age`, `phone`, `email`, `designation`, `salary`, `shift`) VALUES
(101, 'Rahul Sharma', 'Male', 28, '9876543210', 'rahul@gmail.com', 'Manager', 50000, 'Morning'),
(102, 'Priya Patel', 'Female', 25, '9876543211', 'priya@gmail.com', 'Receptionist', 25000, 'Morning'),
(103, 'Amit Verma', 'Male', 30, '9876543212', 'amit@gmail.com', 'Chef', 35000, 'Evening'),
(104, 'Sneha Joshi', 'Female', 27, '9876543213', 'sneha@gmail.com', 'Housekeeping', 22000, 'Morning'),
(105, 'Rohit Singh', 'Male', 35, '9876543214', 'rohit@gmail.com', 'Security', 20000, 'Night'),
(106, 'Neha Shah', 'Female', 29, '9876543215', 'neha@gmail.com', 'Accountant', 40000, 'Morning'),
(107, 'Karan Mehta', 'Male', 31, '9876543216', 'karan@gmail.com', 'Waiter', 18000, 'Evening'),
(108, 'Pooja Desai', 'Female', 26, '9876543217', 'pooja@gmail.com', 'Receptionist', 25000, 'Evening'),
(109, 'Vikas Kumar', 'Male', 32, '9876543218', 'vikas@gmail.com', 'Maintenance', 28000, 'Morning'),
(110, 'Anjali Gupta', 'Female', 24, '9876543219', 'anjali@gmail.com', 'Housekeeping', 22000, 'Morning'),
(111, 'Suresh Yadav', 'Male', 38, '9876543220', 'suresh@gmail.com', 'Driver', 23000, 'Morning'),
(112, 'Komal Jain', 'Female', 27, '9876543221', 'komal@gmail.com', 'HR', 45000, 'Morning'),
(113, 'Deepak Mishra', 'Male', 29, '9876543222', 'deepak@gmail.com', 'Chef', 36000, 'Evening'),
(114, 'Ritu Sharma', 'Female', 30, '9876543223', 'ritu@gmail.com', 'Cashier', 26000, 'Morning'),
(115, 'Arjun Patel', 'Male', 34, '9876543224', 'arjun@gmail.com', 'Security', 21000, 'Night'),
(116, 'Nisha Verma', 'Female', 25, '9876543225', 'nisha@gmail.com', 'Waiter', 18000, 'Evening'),
(117, 'Manish Gupta', 'Male', 36, '9876543226', 'manish@gmail.com', 'Manager', 55000, 'Morning'),
(118, 'Kavita Singh', 'Female', 28, '9876543227', 'kavita@gmail.com', 'Receptionist', 26000, 'Morning'),
(119, 'Harsh Mehta', 'Male', 27, '9876543228', 'harsh@gmail.com', 'Maintenance', 29000, 'Evening'),
(120, 'Payal Shah', 'Female', 26, '9876543229', 'payal@gmail.com', 'Housekeeping', 22000, 'Morning');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`adminId`),
  ADD UNIQUE KEY `username` (`username`);

--
-- Indexes for table `booking`
--
ALTER TABLE `booking`
  ADD PRIMARY KEY (`bookingId`),
  ADD KEY `customerId` (`customerId`),
  ADD KEY `roomId` (`roomId`);

--
-- Indexes for table `checkinout`
--
ALTER TABLE `checkinout`
  ADD PRIMARY KEY (`checkId`),
  ADD KEY `bookingId` (`bookingId`),
  ADD KEY `fk_check_customer` (`customerId`);

--
-- Indexes for table `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`customerId`);

--
-- Indexes for table `designation`
--
ALTER TABLE `designation`
  ADD PRIMARY KEY (`designationId`),
  ADD UNIQUE KEY `designationName` (`designationName`);

--
-- Indexes for table `feedback`
--
ALTER TABLE `feedback`
  ADD PRIMARY KEY (`feedbackId`),
  ADD KEY `customerId` (`customerId`);

--
-- Indexes for table `food`
--
ALTER TABLE `food`
  ADD PRIMARY KEY (`foodId`);

--
-- Indexes for table `food_menu`
--
ALTER TABLE `food_menu`
  ADD PRIMARY KEY (`foodId`);

--
-- Indexes for table `food_order`
--
ALTER TABLE `food_order`
  ADD PRIMARY KEY (`orderId`),
  ADD KEY `customerId` (`customerId`),
  ADD KEY `bookingId` (`bookingId`),
  ADD KEY `foodId` (`foodId`);

--
-- Indexes for table `game`
--
ALTER TABLE `game`
  ADD PRIMARY KEY (`gameId`);

--
-- Indexes for table `game_booking`
--
ALTER TABLE `game_booking`
  ADD PRIMARY KEY (`gameBookingId`),
  ADD KEY `customerId` (`customerId`),
  ADD KEY `bookingId` (`bookingId`),
  ADD KEY `gameId` (`gameId`);

--
-- Indexes for table `payment`
--
ALTER TABLE `payment`
  ADD PRIMARY KEY (`paymentId`),
  ADD KEY `customerId` (`customerId`),
  ADD KEY `bookingId` (`bookingId`);

--
-- Indexes for table `room`
--
ALTER TABLE `room`
  ADD PRIMARY KEY (`roomId`),
  ADD UNIQUE KEY `roomNumber` (`roomNumber`);

--
-- Indexes for table `staff`
--
ALTER TABLE `staff`
  ADD PRIMARY KEY (`staffId`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `booking`
--
ALTER TABLE `booking`
  MODIFY `bookingId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=370;

--
-- AUTO_INCREMENT for table `checkinout`
--
ALTER TABLE `checkinout`
  MODIFY `checkId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT for table `customer`
--
ALTER TABLE `customer`
  MODIFY `customerId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=121;

--
-- AUTO_INCREMENT for table `designation`
--
ALTER TABLE `designation`
  MODIFY `designationId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `feedback`
--
ALTER TABLE `feedback`
  MODIFY `feedbackId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `food`
--
ALTER TABLE `food`
  MODIFY `foodId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `food_menu`
--
ALTER TABLE `food_menu`
  MODIFY `foodId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT for table `food_order`
--
ALTER TABLE `food_order`
  MODIFY `orderId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=41;

--
-- AUTO_INCREMENT for table `game`
--
ALTER TABLE `game`
  MODIFY `gameId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT for table `game_booking`
--
ALTER TABLE `game_booking`
  MODIFY `gameBookingId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT for table `payment`
--
ALTER TABLE `payment`
  MODIFY `paymentId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=35;

--
-- AUTO_INCREMENT for table `room`
--
ALTER TABLE `room`
  MODIFY `roomId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=127;

--
-- AUTO_INCREMENT for table `staff`
--
ALTER TABLE `staff`
  MODIFY `staffId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=125;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `booking`
--
ALTER TABLE `booking`
  ADD CONSTRAINT `booking_ibfk_1` FOREIGN KEY (`customerId`) REFERENCES `customer` (`customerId`),
  ADD CONSTRAINT `booking_ibfk_2` FOREIGN KEY (`roomId`) REFERENCES `room` (`roomId`);

--
-- Constraints for table `checkinout`
--
ALTER TABLE `checkinout`
  ADD CONSTRAINT `checkinout_ibfk_1` FOREIGN KEY (`bookingId`) REFERENCES `booking` (`bookingId`),
  ADD CONSTRAINT `fk_check_customer` FOREIGN KEY (`customerId`) REFERENCES `customer` (`customerId`);

--
-- Constraints for table `feedback`
--
ALTER TABLE `feedback`
  ADD CONSTRAINT `feedback_ibfk_1` FOREIGN KEY (`customerId`) REFERENCES `customer` (`customerId`);

--
-- Constraints for table `food_order`
--
ALTER TABLE `food_order`
  ADD CONSTRAINT `food_order_ibfk_1` FOREIGN KEY (`customerId`) REFERENCES `customer` (`customerId`),
  ADD CONSTRAINT `food_order_ibfk_2` FOREIGN KEY (`bookingId`) REFERENCES `booking` (`bookingId`),
  ADD CONSTRAINT `food_order_ibfk_3` FOREIGN KEY (`foodId`) REFERENCES `food_menu` (`foodId`);

--
-- Constraints for table `game_booking`
--
ALTER TABLE `game_booking`
  ADD CONSTRAINT `game_booking_ibfk_1` FOREIGN KEY (`customerId`) REFERENCES `customer` (`customerId`),
  ADD CONSTRAINT `game_booking_ibfk_2` FOREIGN KEY (`bookingId`) REFERENCES `booking` (`bookingId`),
  ADD CONSTRAINT `game_booking_ibfk_3` FOREIGN KEY (`gameId`) REFERENCES `game` (`gameId`);

--
-- Constraints for table `payment`
--
ALTER TABLE `payment`
  ADD CONSTRAINT `payment_ibfk_1` FOREIGN KEY (`customerId`) REFERENCES `customer` (`customerId`),
  ADD CONSTRAINT `payment_ibfk_2` FOREIGN KEY (`bookingId`) REFERENCES `booking` (`bookingId`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
