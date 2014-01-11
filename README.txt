### BUILD INSTRUCTIONS

!!ATTENTION!!
Make sure you have JDK 7 and Maven 3.1.0 or newer.

1) Install Maven
    1.1) Download latest version from http://maven.apache.org [A];
    1.2) Extract to whatever path you see fit.
    1.3) Set the extracted folder full path as M2_HOME in your ~/.bashrc file [B];
         echo M2_HOME=/path/to/extracted/maven >> ~/.bashrc
         echo PATH=$PATH:$M2_HOME/bin >> ~/.bashrc
    1.4) Execute 'which mvn' and check if the path is correct.
    
2) Build project
    2.1) Make sure your machine has access to the Internet [C];
    2.2) Go to the project root folder and execute:
    2.2.1) 'mvn clean package'
    2.2.2) 'java -jar server/server-<version>.jar'
    2.2.3) If you want to change binding from port 8080 just append "--port XX" to the prior command
    
### FOOTNOTES
A - Avoid using 'maven' provided by your Linux distro or MacOS bundle.
B - If you're using any other terminal other than BASH, please adapt accordingly.
C - If you're using a proxy, make sure to configure it in the terminal.


### NOTES
