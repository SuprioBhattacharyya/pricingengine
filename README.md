Requirements :
Java 8
Gradle
Docker
Postman

How to build the application:
Run the following command from the root folder :

    gradlew clean build

How to run the application :-

Docker image can be created locally of the project by running docker build with image name from the project directory:

docker build -t <image-name> .
example : docker build -t pricingengine

Run the image by docker run command in detached mode :

docker run -it -d -p 9595:9595 <image-name>:tag
example : docker run -it -d -p 9595:9595 pricingengine:latest

Project has the following Structure :

1. main\java -> has the source code of the main classes of the project
2. test\java -> contains Mockito Junit test code . We should have a test code for all classes used in the project . 
3. it\java -> contains Integrations Tests to test the workflows.
4. Basic Postman Tests templates are created inside Postman folder.


## APIs

### /tick
Every time a new tick arrives, this endpoint will be called


### /statistics
This is the main endpoint of this task, this endpoint have to execute in constant time and memory (O(1)). It returns the statistic based on the transactions which happened in the last 60 seconds.

###/statistics/{instrument_identifier}
This is the endpoint with statistics for a given instrument.

### Assumptions

- I assume that the correct behaviour when dealing with a burst of messages too large
to cope with in a timely fashion *is to simply drop the messages*. This is obviously
extreme but in my experience it's better to design the system this way: 
- I process out-of-order messages by a schedule job.


### Improvements

- Even assuming that out-of-order messages must be processed, there are
more clever algorithm for min/max. I didn't do this because
I wanted to focus on other things like tests etc.
- Few more test cases i would write.

- About the Challenge
------------------
I find the challenge a simple yet trickier one as it said that the time in /tick can be of past as well.
