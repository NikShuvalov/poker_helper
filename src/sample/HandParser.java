package sample;

import java.io.*;
import java.util.ArrayList;

public class HandParser {

    //ToDo: Need to handle "is Timed out" cause these fucktards added that useless bit of info to the hand history
    private static ArrayList<String> mDealtPlayers = new ArrayList<>();
    private static int mButtonPosition = 0;
    private static int mActivePlayers = 0;
    private static int mActingPlayerIndex = 0;

    public static final String FOLDS = "folds";
    public static final String CALLS = "calls";
    public static final String RAISES = "raises";
    public static final String CHECKS = "checks";

    public static final String GAME = "Game";
    public static final String ID = "ID";
    public static final String PLAYER = "Player";
    public static final String SEAT = "Seat";

    public static final String RECEIVED = "received";

    public static final String FLOP = "FLOP";
    public static final String TURN = "TURN";
    public static final String RIVER = "RIVER";

    public static final String SUMMARY = "Summary";

    /**
     * Pass a handHistory file to this in order to parse the data into something meaningful.
     * @param handHistory
     * @throws IOException
     */
    public static void parseHandHistory(File handHistory) throws IOException {
        int hand= 0;
        int blindCount = 0;
        int street = 0;
        boolean newHand = true;
        boolean handsDealt = false;

        String lastTracked = "";
        TempDataStorage tempDataStorage = TempDataStorage.getInstance();
        BufferedReader br = new BufferedReader(new FileReader(handHistory));


        for(String line = br.readLine(); line!=null; line=br.readLine()){
            String[] words = line.trim().split("\\s");

            //Skips empty lines which are showing up for some reason.
            if(words.length==1){
                continue;
            }

            //If "Game ID:" it's a new hand, reset all values and increment hand
            if(words[0].replaceAll("\\W","").equals(GAME) && words[1].replaceAll("\\W","").equals(ID)){
                hand++;
                newHand = true;
                blindCount=0;
                lastTracked = "";
                handsDealt = false;
                street =0;
                mActivePlayers = 0;
                mDealtPlayers.clear();
            }

            //Move to the seating/button arrangement of table.
            if(words[0].replaceAll("\\W","").equals(SEAT)){
                if(newHand){ //First iteration gives us the button position
                    mButtonPosition = Integer.valueOf(words[1].trim());
                    newHand = false;
                }else{
                    //Store seat numbers and stacks?
                }
            }

            if(handsDealt && words[0].replaceAll("\\W","").equals(PLAYER)){
                switch (street){
                    case 0:
                        parsePreflopLine(line);
                        break;
                }
            } else if (handsDealt &&
                    (line.replaceAll("\\W","").contains(FLOP) ||
                            line.replaceAll("\\W","").contains(TURN) ||
                            line.replaceAll("\\W","").contains(RIVER))){
                street++;
                mActingPlayerIndex =0;
            } else if (line.replaceAll("\\W","").equals(SUMMARY)){
                System.out.println("---Summary---");
            }

            //Move to blinds-posting and hand-dealing
            if(words[0].replaceAll("\\W","").equals(PLAYER) && !handsDealt){
                if(blindCount<2) { //First and second iteration are blinds posted. We'll ignore these for now.
                    blindCount++;
                }else{
                    String username = "";
                    for(int i =1; i< words.length-3; i++){ //Get the string between "Player" and "Received" as the username.
                        username+=words[i]+" ";
                    }

                    //Ignores duplicate lines for when each player gets a card.
                    if(!lastTracked.equals(username.trim())){
                        //Gonna need to create a queue/stack/list/array to keep track of players remaining in hand, in order to keep track of action.
                        lastTracked= username.trim();
                        System.out.println(username);
                        mActivePlayers++;
                        mDealtPlayers.add(lastTracked);
                        TempDataStorage.getInstance().incrementHandCount(lastTracked);
                    }

                    //If the 3rd to last string isn't "Received"(magically enough this works for hero as well) then we've moved on to the preflop action.
                    if(!words[words.length-3].replaceAll("\\W","").equals(RECEIVED)){
                        handsDealt=true;
                        mActingPlayerIndex = (mButtonPosition+1)%mActivePlayers;
                        parsePreflopLine(line);
                    }
                }
            }
        }
        System.out.println("Hands: " +hand);

    }

    private static void parsePreflopLine(String line){
        TempDataStorage tempDataStorage = TempDataStorage.getInstance();
        String[] words = line.split(" ");
        String actingPlayer = mDealtPlayers.get(mActingPlayerIndex);
        int actionIndex = actingPlayer.split(" ").length+1;
        String action = words[actionIndex].replaceAll("\\W","");
        System.out.println(action);
        if(action.equals(FOLDS)){
            mDealtPlayers.remove(mActingPlayerIndex);
            mActivePlayers--;
        }else if (action.equals(CALLS)){
            tempDataStorage.incrementVPIP(actingPlayer);
        }else if (action.equals(RAISES)){
            tempDataStorage.incrementVPIP(actingPlayer);
            tempDataStorage.incrementPFR(actingPlayer);
        }
        //I can add in CHECKS but not sure why I would, that's only for BB
        mActingPlayerIndex = (mActingPlayerIndex+1) % mActivePlayers;
    }

}

//Line 0 = Hand start time
//Line 2 = Hand ID plus game type/tournament name
//Line 4 = Button position
//Line 6 - ? = Player's names/seats/stacks
//
