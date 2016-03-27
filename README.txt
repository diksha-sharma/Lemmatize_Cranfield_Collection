The Submission.zip file contains 3 folders, Assignment Report and README.txt file.

1. src - contains the java source file - all *.java files

To compile the .java files execute below commands in order below:

Go to folder containing source java files using cd command then:

source /usr/local/corenlp350/classpath.sh
source /usr/local/corenlp341/classpath.sh
javac -cp "/usr/local/corenlp350/joda-time.jar:/usr/local/corenlp350/jollyday.jar:/usr/local/corenlp350/ejml-0.23.jar:/usr/local/corenlp350/xom.jar:/usr/local/corenlp350/javax.json.jar:/usr/local/corenlp350/stanford-corenlp-3.5.0.jar:/usr/local/corenlp350/stanford-corenlp-3.5.0-models.jar:" *.java

To execute: - 
Replace argument 1 with the location where the data files are placed. 
Replace argument 2 with the location where the output files need to be placed. 

Please include double // for directory location in argument

java Assignment2 "//people//cs//d//dxs134530//IR-Assignment1//data//" "//people//cs//d//dxs134530//IR_assignment2//"
