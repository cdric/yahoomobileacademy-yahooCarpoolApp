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
 1. As a passenger, I want to get notified if a driver accepted to offer me a spot on his/her vehicule

Release notes
-------------

TODO
 - Must have for demo-day
    - Implement progress bars all across the board
    - Implement empty notification view (by displaying a text view instead of a list item view)
    - Polish UI across the board
       - Style button and layouts
       - Update ActionBar Tabs icons
    - Add one activity animation / one fragment animation
    - Style action bar
 - Nice to have for demo-day
    - Support offline mode 
    - Protect app again unavailable / failed API requests
    - Review the TODO I left in the code
    - Code cleanup
       - Move all strings into res folder
 - Update the action bar for both driver and passenger to include the Facebook icon of the authenticated user
    - FIXIT: Code is there but the actionBar does not render. 

Version 0.03 (2/19/2014)
 - Implement story for the Driver to accept/reject passengers for his ride
 - Implement a RotateImageButton class that leverage an animation to build a button from a image that will be rotated by 180 degrees

Version 0.02 (2/17/2014)
 - Fix layout design issues (like misalignments of views)
 - Implement Passenger's user stories
   - User can search for a ride
   - User can send a request to book a ride
   - User can read his/her notifications
 - Implement a Generic Notifications view that is being reused by both drivers and passengers activities
 - Simplify the Driver view into two tabs instead of three by merging fragments together
   - The main tab contain bot the ride and current passenger for the ride
 - Code improvements
   - Use ParseAPI using Parse Model and remove ParseObjectSerializable interface that became irrelevant
   - Use Joda-Time API to parse Date
   - Various code cleanups
	
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






