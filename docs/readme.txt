
Assumptions
=============

1 J2SE SDK (JDK) 1.4 installed.

2 ant 1.6 installed.

3 Tomcat installed and working. Application tested also on BEA WebLogic 8.1 SP3

4 Oracle 8i-10i installed and working.


How to run application
=======================

1. run /db/ddl.sql

2. Change build.properties if you want to use ANT. 
   Change property server.app.home if you want to compile into application 
   server directory. 
	
3. Change /web/WEB-INF/config.properties in order to rename DataSource name 
   and JDBC Connection parameters (for testing).

4. Change default locale in faces-config.xml

5. run 

      $ ant build war  (creates *.war) 
      OR 
      $ ant deploy 

   (For compiling project into application server directory).
   Property server.app.home must be specified.
  
   At 26 November was added the support of cache. 
   In order to enable/disable it you have to change property in config.properties

     cache.enable=true  or cache.enable=false

6. deploy *.war and create DataSource.
