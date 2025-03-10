-- Inserting sample data...
INSERT INTO Trip -- (Trip#, startLocation, destinationName)
VALUES (001, 'Pomona Transit Center', 'Golden Springs Dr. & Diamond Bar Blvd.'),
(002, 'Pomona Transit Center', 'W.Covina Pkwy. & California Ave.'),
(003, 'Pomona Transit Center', 'Durward Way & D St.'),
(004, 'Pomona Transit Center', 'Claremont Transit Center'),
(005, 'Claremont Transit Center', 'Pomona Transit Center');

INSERT INTO Bus -- (BusID, Model, Year)
VALUES (482, 'ModelA', 2015),
(480, 'ModelB', 2016),
(291, 'ModelC', 2017);

INSERT INTO Driver -- (DriverName, DriverTelephoneNumber)
VALUES ('Mindy', '(626)-101-2030'),
('Ben', '(909)-111-4369'),
('Jim', '(626)-168-6035');

INSERT INTO TripOffering -- (Trip#, Date, ScheduledStartTime, ScheduledArrivalTime, DriverName, BusID)
VALUES (001, '2022-11-21', '04:00:00', '04:10:00', 'Mindy', '482'),
(001, '2022-11-21', '04:30:00', '04:40:00', 'Ben', '482'),
(002, '2022-11-21', '04:40:00', '04:47:00', 'Ben', '482'),
(003, '2022-11-21', '04:47:00', '04:52:00', 'Ben', '482'),
(004, '2022-11-21', '04:52:00', '04:58:00', 'Ben', '482'),
(001, '2022-11-21', '05:00:00', '05:11:00', 'Jim', '482'),
(002, '2022-11-21', '05:11:00', '05:18:00', 'Jim', '482'),
(003, '2022-11-21', '05:18:00', '05:23:00', 'Jim', '482'),
(004, '2022-11-21', '05:23:00', '05:29:00', 'Jim', '482'),
(005, '2022-11-21', '04:10:00', '04:28:00', 'Mindy', '482');

INSERT INTO Stop -- (Stop#, StopAddress)
VALUES (001, 'Valley Blvd. & Humane Way'),
(002, 'Temple Ave. & S.Campus Dr.'),
(003, 'Mission Blvd. & Temple Ave.'),
(004, 'Golden Springs Dr. & Diamond Bar Blvd.'),
(005, 'Eastland Center'),
(006, 'W.Covina Pkwy. & California Ave.');

INSERT INTO ActualTripStopInfo -- (Trip#, Date, ScheduledStartTime, Stop#, ScheduledArrivalTime, ActualStartTime, ActualArrivalTime, #OfPassengersIn, #OfPassengerOut)
VALUES (001, '2022-11-21', '04:00:00', 001, '04:10:00', '04:00:00', '04:09:00', 2,0),
(001, '2022-11-21', '04:00:00', 002, '04:17:00', '04:00:00', '04:18:00', 3,1),
(001, '2022-11-21', '04:00:00', 003, '04:22:00', '04:00:00', '04:20:00', 1,2),
(001, '2022-11-21', '04:00:00', 004, '04:28:00', '04:00:00', '04:30:00', 0,3),
(001, '2022-11-21', '08:00:00', 001, '08:14:00', '08:00:00', '08:15:00', 4,0),
(001, '2022-11-21', '08:00:00', 002, '08:23:00', '08:00:00', '08:23:00', 5,2),
(001, '2022-11-21', '08:00:00', 003, '08:29:00', '08:00:00', '08:29:00', 2,6),
(001, '2022-11-21', '08:00:00', 004, '08:37:00', '08:00:00', '08:35:00', 0,3),
(002, '2022-11-21', '07:02:00', 002, '07:25:00', '07:02:00', '07:25:00', 6,0),
(002, '2022-11-21', '07:02:00', 005, '07:45:00', '07:02:00', '07:45:00', 2,3),
(002, '2022-11-21', '07:02:00', 006, '07:58:00', '07:02:00', '08:00:00', 3,5),
(002, '2022-11-21', '07:34:00', 002, '07:59:00', '07:34:00', '08:00:00', 5,0),
(002, '2022-11-21', '07:34:00', 005, '08:22:00', '07:34:00', '08:22:00', 2,3),
(002, '2022-11-21', '07:34:00', 006, '08:35:00', '07:34:00', '08:35:00', 0,1),
(002, '2022-11-21', '08:34:00', 002, '08:59:00', '08:34:00', '09:00:00', 4,3),
(002, '2022-11-21', '08:34:00', 005, '09:22:00', '08:34:00', '09:23:00', 2,3),
(002, '2022-11-21', '08:34:00', 006, '09:35:00', '08:34:00', '09:35:00', 2,2);

INSERT INTO TripStopInfo -- (Trip#, Stop#, Sequence#. DrivingTime)
VALUES (001, 001, 1, '00:10:00'),
(001, 002, 2, '00:17:00'),
(001, 003, 3, '00:22:00'),
(001, 004, 4, '00:28:00'),
(002, 002, 1, '00:23:00'),
(002, 005, 2, '00:43:00'),
(002, 006, 3, '00:56:00');


SELECT * FROM Bus