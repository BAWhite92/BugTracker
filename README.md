# BugTracker

An app for tracking bugs that occur during software development and testing. 

Will provide ability to connect to databases for storing company bug information. And include secure log in capabilties
for individual developers and authorisation levels to enable management of the system. 

Executeable jar file is contained within BugTracker/target directory, BugTrackerMain in the src directory will need editing
for the MySQLAccess() method to set personal MySQL jdbc url information. (Currently working on JDK14 with jre 8.0.24 on local machine)

Schema required for app to work is included in the mysqlSchema.sql file and is required to be set up before running the app and should
be done before altering MySQLAccess().


Planned work currently:
- ~~Add ability for admin to delete bug tickets~~
- Add edit account ability including password reset
- ~~Implement secure password storage (hash)~~
- Add admin only ability to change the admin level password (required to create >1 admin account.)
- ~~Add admin ability to edit bug tickets.~~ decided all accounts will be able to edit.(necessary for updating further info discovered)
- Add table ordering for each table heading.
- ~~Ability to expand bug ticket by clicking on table cell, within this the edit, startwork and completed work options.~~
- Add colour coding on table cells to highlight risk in visual manner
- Add some settings options such as background colour fonts etc
- Make improvements to the GUI (long term) as Java Swing knowledge improves
    
