package sample;

import java.io.*;

public class HandParser {

    //ToDo: To get the playerAction I should take 2nd to last position String since ACR allows spaces in names

    /**
     * Pass a handHistory file to this in order to parse the data into something meaningful.
     * @param handHistory
     * @throws IOException
     */
    public static void parseHandHistory(File handHistory) throws IOException {
        TempDataStorage tempDataStorage = TempDataStorage.getInstance();
        String seatString = "", playerString="", receivedString="";
        String[] gameIDStrings = new String[2];
        BufferedReader br = new BufferedReader(new FileReader(handHistory));
        int hand= 0;
        int count = 0;
        boolean newHand = true;
        boolean handsDealt = false;
        int buttonPosition = -1;
        int blindCount = 0;
        int street = 0;
        int activePlayers = 0;
        String lastTracked = "";

        for(String line = br.readLine(); line!=null; line=br.readLine()){
            String[] words = line.trim().split("\\s+");

            //Put this here because for some reason I can't hardcode "Game" and "ID:" because the output is different somehow
            if(count==2){
                gameIDStrings[0] = words[0];
                gameIDStrings[1] = words[1];
            }

            //Get the word "Seat" for comparing, cause the file for some reason has weird invisible spaces in it
            if(count==4){
                seatString = words[0];
            }

            //Get the word "Player" for comparing, cause the file for some reason has weird invisible spaces in it
            if(playerString.isEmpty() && count>4 && !words[0].equals(seatString)){
                playerString = words[0];
            }
            count++; //Just for initializing stuff

            //Skips empty lines which are showing up for some reason.
            if(words.length==1){
                continue;
            }

            //If "Game ID:" it's a new hand, reset all values and increment hand
            if(words[0].equals(gameIDStrings[0]) && words[1].equals(gameIDStrings[1])){
                hand++;
                newHand = true;
                blindCount=0;
                lastTracked = "";
                handsDealt = false;
                street =0;
                activePlayers = 0;
            }

            //Move to the seating/button arrangement of table.
            if(seatString.equals(words[0])){
                if(newHand){ //First iteration gives us the button position
                    buttonPosition = Integer.valueOf(words[1].trim());
                    System.out.println("Button: " + buttonPosition);
                    newHand = false;
                }else{
                    //Store seat numbers and stacks?
                }
            }

            if(handsDealt && words[0].equals(playerString)){
                switch (street){
                    case 0:
                        parsePreflopLine(line);
                        break;
                }
            } else if (handsDealt &&
                    (line.contains("*** FLOP ***") ||
                            line.contains("*** TURN ***") ||
                            line.contains("*** RIVER ***"))){
                street++;
            } else if (line.equals("------ Summary ------")){
                System.out.println("Summary");
            }

            //Move to blinds-posting and hand-dealing
            if(words[0].equals(playerString) && !handsDealt){
                if(blindCount<2) { //First and second iteration are blinds posted. We'll ignore these for now.
                    blindCount++;
                }else{
                    String username = "";
                    for(int i =1; i< words.length-3; i++){ //Get the string between "Player" and "Received" as the username.
                        username+=words[i]+" ";
                        if(receivedString.isEmpty()){
                            receivedString = words[words.length-3]; //Sets the pseudo-constant "Received" because, again, invisible characters.
                        }
                    }

                    //Ignores duplicate lines for when each player gets a card.
                    if(!lastTracked.equals(username.trim())){
                        //Gonna need to create a queue/stack/list/array to keep track of players remaining in hand, in order to keep track of action.
                        lastTracked= username.trim();
                        System.out.println(username);
                        activePlayers++;
                    }

                    //If the 3rd to last string isn't "Received"(magically enough this works for hero as well) then we've moved on to the preflop action.
                    if(!words[words.length-3].equals(receivedString)){
                        handsDealt=true;
                        parsePreflopLine(line);
                    }
                }
            }
        }
        System.out.println("Hands: " +hand);
    }

    private static void parsePreflopLine(String line){
        System.out.println(line);
    }

}

//Line 0 = Hand start time
//Line 2 = Hand ID plus game type/tournament name
//Line 4 = Button position
//Line 6 - ? = Player's names/seats/stacks
//
