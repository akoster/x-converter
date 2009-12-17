README x-converter
17 december 2009
--

To install jars, go to the project root and execute the command line:

mvn install

Follow maven's instructions about installing the libraries in the repository
 
For example, to install the MySQL driver:

mvn install:install-file -DgroupId=mysql -DartifactId=mysql -Dversion=5.1.6 
-Dpackaging=jar -DgeneratePom=true -Dfile=mysql-connector-java-5.1.6-bin.jar