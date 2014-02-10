Carpooling App
==============

*Yahoo, Introduction to Android - Group Project*

Description
-----------
The purpose of this app is to help people at Yahoo find a way to carpool together

User stories
------------
Driver user stories
 1. As a driver, I want to offer riders to other Yahoo employees that live near my place so that I can save money, have a more enjoyable ride and be home quicker by taking the carpool lane
 1. As a driver, I want to get notified when someone is interested to ride with me
 1. As a driver, I want to be able to review who will be riding back with me
 1. As a driver, I want to accept/reject potential ride buddies

Passenger user stories
 1. As a passenger, I want to find a ride back so that I can come back home
 1. As a passenger, I want to get norified if a driver accepted to offer me a spot on his/her vehicule

Release notes
-------------

TODO
 - Implement progress bars all across the board
 - Polish UI across the board
    - Fixing syling of tabs to prevent them to slide horizontally (on the Driver view)
    - And many more... 

Version 0.01 (2/9/2014) 
 - Initial implementation of the application's models (Driver, Passenger, Ride)
 - Initial integration with Facebook SDK to retrieve user related information
 - Initial integration with Parse API to persist data
 - Implemented Driver Activity with three different fragments
    - Fragment: DriverRideDetailsFragment: fully implemented
       - Implemented ability to save the ride
	   - Implemented ability to load the ride (if it has been previously saved)
	   - Implemented ability to delete the ride (if it has been previsouly saved)
    - Fragment: DriverPassengerListFragment: partially implemented using fake data
    - Fragment: DriverGuestNotifications: not implemented




