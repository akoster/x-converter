README x-converter
27 february 2008
--
EnumTest shows how enums can be used.

AbstractTest shows how an abstract class can be used to allow multiple 
implementations for some methods, and enforce common implementations for other 
methods.

Note: another use of abstract classes is to encapsulate the lifecycle of a 
complex subsystem. Lifecycle methods which are left abstract must be implemented
by concrete subclasses. Other lifecycle methods can be implemented in the 
abstract class, and are optional for subclasses to override. An example 
of a similar situation is in: javax.servlet.http.HttpServlet.

to install mysql driver
http://dev.mysql.com/downloads/connector/j/5.1.html
mvn install:install-file -DgroupId=mysql -DartifactId=mysql -Dversion=5.1.6 
-Dpackaging=jar -DgeneratePom=true -Dfile=mysql-connector-java-5.1.6-bin.jar
