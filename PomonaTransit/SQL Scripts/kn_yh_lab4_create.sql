CREATE DATABASE PomonaTransit;
USE PomonaTransit;

CREATE TABLE Trip (
	TripNumber int PRIMARY KEY,
    StartLocationName varchar(50),
    DestinationName varchar(50)
);

CREATE TABLE Bus(
	BusID int PRIMARY KEY,
    Model varchar(20),
    Year int
);

CREATE TABLE Driver(
	DriverName varchar(20) PRIMARY KEY DEFAULT 'TBA',
    DriverTelephoneNumber varchar(15)
);

CREATE TABLE TripOffering(
	TripNumber int,
    Date date,
    ScheduledStartTime time,
    ScheduledArrivalTime time,
    DriverName varchar(20),
    BusID int,
    PRIMARY KEY(TripNumber, Date, ScheduledStartTime),
    FOREIGN KEY(TripNumber) REFERENCES Trip(TripNumber),
    FOREIGN KEY(DriverName) REFERENCES Driver(DriverName)
    ON DELETE SET NULL ON UPDATE CASCADE,
    FOREIGN KEY(BusID) REFERENCES Bus(BusID)
    ON DELETE SET NULL ON UPDATE CASCADE
);

CREATE TABLE Stop(
	StopNumber int PRIMARY KEY,
    StopAddress varchar(50)
);

CREATE TABLE ActualTripStopInfo(
	TripNumber int,
    Date date,
    ScheduledStartTime time,
    StopNumber int,
    ScheduledArrivalTime time,
    ActualStartTime time,
    ActualArrivalTime time,
    NumberOfPassengerIn int,
    NumberOfPassengerOut int,
    PRIMARY KEY(TripNumber, Date, ScheduledStartTime, StopNumber),
    FOREIGN KEY(TripNumber, Date, ScheduledStartTime) REFERENCES TripOffering(TripNumber, Date, ScheduledStartTime)
    ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY(StopNumber) REFERENCES Stop(StopNumber)
);

CREATE TABLE TripStopInfo(
	TripNumber int,
    StopNumber int,
    SequenceNumber int,
    DrivingTime time,
    PRIMARY KEY(TripNumber, StopNumber),
    FOREIGN KEY(TripNumber) REFERENCES Trip(TripNumber),
    FOREIGN KEY(StopNumber) REFERENCES Stop(StopNumber)
);


