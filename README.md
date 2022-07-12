# cds

Boot up: 
1. git clone https://github.com/HoJinKind/cds.git
2. docker-compose up -d --build      (this starts up the mysql database and seed the data) preloaded data is in 01.sql too
3. using intellij OR cd to demo
4. mvn clean install    
5. mvn spring-boot:run    


What kind of testing would you perform on the application in addition to unit testing?

- Junit mockito, with reflections through org.powermock.reflect.Whitebox.invokeMethod to test the service business logic.
- deep stubs if required
- ReflectionTestUtils.setField(...) for setting class variables. 
  

● How would you scale up the system to handle potentially millions of files?
- a redis lock (on the names) would be required on the insertions to ensure that no two containers are inseting a id at the same time.
  

● CSV files can be very large and take a long time to process. This can be a problem using HTTP POST calls.
How would you update the design to handle HTTP POST requests that can potentially take a long time to
execute?
- currently insertion is already done by batch. But if the data is huge, this can be split into even more batches and done concurrently like with completable futures, and have each handle 1000 insertions for example. 
- Another concern with the timing is that it can be done with a maessage queue such as kafka, return the id of the job first, the insertion can then be taken up by a worker node. 