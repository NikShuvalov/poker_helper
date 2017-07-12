package sample;

public class Player {
    private String playerName;
    private int totalHands;
    private double vpip;
    private double preFlopRaise;
    private String mLastRaisedHand, mLastVoluntaryHand;

    public Player(String userName){
        this.playerName = userName;
        this.totalHands =0;
        this.vpip = 0;
        this.preFlopRaise = 0;
        mLastRaisedHand = "";
        mLastVoluntaryHand = "";
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public int getTotalHands() {
        return this.totalHands;
    }

    public double getVpip() {
        return ((int)this.vpip*100/this.totalHands)/100.0;
    }

    public double getPreFlopRaise() {
        return ((int)(preFlopRaise*100/this.totalHands))/100.0;
    }

    public void incrementHands(){
        this.totalHands++;
    }

    public void incrementVPIP(String handID){
        if(mLastVoluntaryHand.equals(handID)){
            return;
        }
        this.vpip++;
        mLastVoluntaryHand = handID;
    }

    public void incrementPFR(String handID){
        if(mLastRaisedHand.equals(handID)){
            return;
        }
        this.preFlopRaise++;
        mLastRaisedHand = handID;
    }


}
