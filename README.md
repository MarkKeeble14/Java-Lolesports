# LoL esports Tournament Simulator

### Uploaded in December 2021

## Running
You can either open the project in an IDE like Eclipse or run it on the command line by navigating to the \src directory then inputting the following commands:
- javac Drivers/Driver.java
- java Drivers/Driver.java

## Walkthrough
You'll first be prompted with which league you'd like to simulate a tournament from (current options are LCS, LEC, and International Events).

Once you've made your selection, you'll then be prompted with which tournament from that league you'd like to simulate (such as LCS Lockin, LEC Summer, MSI, etc).

Once you've selected your tournament, the program will automatically simulate that tournament 10,000 (This number is currently hard coded. It's a good balence between speed & sample size) times before asking you for the tricode (TSM, C9, TL, 100T, etc) of whatever team you'd like to see win in a simulation. You can also type "any" at this point to get a random simulation.

### There's a couple of options which determine what aspects of a simulation are printed out. They are as follows:

- **Tournament Progression** - Will show the stage by stage breakdown of what occurred, i.e., will print out group stage game results, series results in playoffs, etc.

- **Champion Records** - Will show the path that the champion (winner) of the tournament took, i.e., in Group A they had a record of B-C. In Quarterfinals they beat team D with a gamescore of E-F, etc.

- **Tournament Standings** - Will show the complete standings as of the end of the tournament and where each of the teams tournament runs ended.

- **Tournament Stats** - Will show stats relating to the format of the tournament, such as the number of games played in each individual stage.
