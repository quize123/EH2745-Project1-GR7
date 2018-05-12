----------------------------------------------------------------------------------------------------------------------------------------
This program was developed as part of KTH EH2745 course by Amar Abideen and Hatem Alatawi (Group 7)

To report any technical issues please contact aabideen@kau.edu.sa or h.s.alatawi@gmail.com

----------------------------------------------------------------------------------------------------------------------------------------
The program is inteded to capture CIM files (EQ and SSH) to parse them and store them in a database localhost then calculate the Y bus matrix
------------------------------------------------------------------------------------------------------------------------------------------
EH2745Project1.jar can be used to run the program
----------------------------------------------------------------------------------------------------------------------------------------

The primary main class is Project1.java

This class includes program main graphical user interface and program excution stages which are:

1- Import EQ file using file browser

2- Import SSH file using file browser

3- Login to database frame and establish connection 

4- Parse and store related data in arraylist

5- Create and upload related data to database

6- Calculate Y matrix

7- Show Y matrix in seperated frame as a table

----------------------------------------------------------------------------------------------------------------------------------------

Secoundry classes are:

CalculateYBus.java - Contains methods used for Y bus calculations
CMPLX.java - Contains methods used for dealing with complex numbers
DataBaseClass.java - Contain methods used for dealing with database
DatabaseUserChecker.java - Contain login frame and user athentication (Localhost / User: root / Password: y9mkrg6wyc8r)
ReadEQfile.java - Contains methods used to read and parse EQ file
ReadSSHfile.java - Contains methods used to read and parse SSH file
YbusGUI.java - Contains methods used for Y bus GUI representation
