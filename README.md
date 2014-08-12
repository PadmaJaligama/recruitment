Recruitment Test Project
===========

A test project skeleton for testing potential recruits.

Please fork this repository and submit your solution as a pull request.

The Challenge
----------

The Eclipse project `com.snapon.recruit.proj` contains a bare minimum skeleton codebase that you must complete.

Take the following file as input:

* `input_data.bin` - 32 bit unsigned integers

You will modify the following code to implement the indicated behaviour:

* `ProcessFileMain.java` - Java main() method
   * Open `input_data.bin`
   * Create `output_data.bin` and overwrite if it already exists
   * Read in each record (32 bit unsigned integer)
   * For each record: 
      * Write the record as read
      * Write the record converted from big endian to little endian
      * Write the record value as denary number in ASCII right justified within 16 bytes
  * `ProcessDataUtil.java` - Java utility methods to allow reuse of conversion functionality
     * Any reusable functions (if any) to be placed here.

The expected output:

* `output_data.bin`	- Output file

Notes
--------
* Complete any Javadoc
* If any error conditions are encountered they are to be reported in a sensible way
* Please modify this file to list any 3rd party libraries you have used (if any). For each library provide brief details of the license terms of the library and why you have included the library
* Please assume that this code is to be used in a production customer environment
