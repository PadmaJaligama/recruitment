Recruitment Test Project
===========

A test project skeleton for testing potential recruits.

Please fork this repository and submit your solution as a pull request.

The Challenge
----------

This project contains a bare minimum skeleton codebase that you must complete.

Take the following file as input:
	`input_data.bin` - 32 bit unsigned integers


The expected process:
	`ProcessFileMain.java` - Java main() method
		Open input_data.bin
		Create output_data.bin
		Read in each record (32 bit unsigned integer)
		For each record 
			Write record as read
			Write record converted from big endian to little endian
			Write record value as denary number in ASCII right justified within 16 bytes

	`ProcessDataUtil.java` - Java utility methods to allow reuse of conversion functionality 
		Any reusable functions if any to be placed here.


The expected output:
	`output_data.bin`	- Output file

 
Notes:
* Complete any Javadoc
* If any error conditions are encountered they are to be reported
* Please list any 3rd party libraries you have used, detail the license used and why you have included them