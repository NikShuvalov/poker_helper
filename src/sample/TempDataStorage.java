package sample;

import java.util.ArrayList;

public class TempDataStorage {
    private ArrayList<Player> mPlayerList;
    private ArrayList<String> mPlayerNames;

    private TempDataStorage(){
        mPlayerList = new ArrayList<>();
        mPlayerNames = new ArrayList<>();
    }

    private static TempDataStorage sTempDataStorage;

    public static TempDataStorage getInstance() {
        if(sTempDataStorage== null){
            sTempDataStorage = new TempDataStorage();
        }
        return sTempDataStorage;
    }

    public ArrayList<Player> getPlayerList() {
        return mPlayerList;
    }

//    public void addPlayer(String playerName){
//        if(!mPlayerNames.contains(playerName)){
//            mPlayerNames.add(playerName);
//            mPlayerList.add(new Player(playerName));
//        }
//    }

    public void incrementHandCount(String playerName){
        if(!mPlayerNames.contains(playerName)){
            mPlayerNames.add(playerName);
            Player newPlayer = new Player(playerName);
            newPlayer.incrementHands();
            mPlayerList.add(newPlayer);
        }else{
            mPlayerList.get(mPlayerNames.indexOf(playerName)).incrementHands();
        }
    }

    public void incrementVPIP(String playerName){
        mPlayerList.get(mPlayerNames.indexOf(playerName)).incrementVPIP();
    }

    public void incrementPFR(String playerName){
        mPlayerList.get(mPlayerNames.indexOf(playerName)).incrementPFR();
    }
}
