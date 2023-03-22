This repository holds the Java code for the RISK game.

![pipeline](https://gitlab.oit.duke.edu/yl852/multi-project-gradle-651-team/badges/Development/pipeline.svg)
![coverage](https://gitlab.oit.duke.edu/yl852/multi-project-gradle-651-team/badges/Development/coverage.svg?job=test)

Group Members: yl852, xm62, xc197, and cc804

Links:
1. Task Managment Sheet: https://docs.google.com/spreadsheets/d/1_RX_TUBK8XSad400mmBN2CcvGU30YDEz/edit#gid=1788131893
2. UML Diagram: https://lucid.app/lucidchart/0428cb54-9a2e-46b1-82f6-df5d5ea04d1f/edit?beaconFlowId=58C99C64CE97FB3C&invitationId=inv_7e48b7d1-5bc0-47bf-8fff-90c2b1828d8f&page=0_0#

How to run the game:
1. run "./gradlew installDist" in the top directory of the project
2. cd into server folder and run "tee src/test/resources/input.txt | ./build/install/server/bin/server | tee src/test/resources/output.txt"
3. when the game states that it is ready, on separate machines/terminals, cd into the client folder for each player.
4. in the client folder, run "tee src/test/resources/input-client-<number>.txt | ./build/install/client/bin/client | tee src/test/resources/output-client-<number>.txt"
The <number> is whatever the number of client is. So, either 1,2,3, or 4. For example, if you are the first player, you should put 1 in place of <number> and likewise for all other players. 


## Coverage
[Detailed coverage](https://yl852.pages.oit.duke.edu/multi-project-gradle-651-team/dashboard.html)
