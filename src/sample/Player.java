package sample;

public class Player {
    private String mPlayerName;
    private int mTotalHands;
    private double mVPIP, mPreFlopRaise;
    private String mLastRaisedHand, mLastVoluntaryHand;

    public Player(String userName){
        mPlayerName = userName;
        mTotalHands =0;
        mVPIP = 0;
        mPreFlopRaise = 0;
        mLastRaisedHand = "";
        mLastVoluntaryHand = "";
    }

    public String getPlayerName() {
        return mPlayerName;
    }

    public int getTotalHands() {
        return mTotalHands;
    }

    public double getVpipRatio() {
        return ((int)mVPIP*100/mTotalHands)/100.0;
    }

    public double getPreflopRaiseRatio() {
        return ((int)(mPreFlopRaise*100/mTotalHands))/100.0;
    }

    public void incrementHands(){
        mTotalHands++;
    }

    public void incrementVPIP(String handID){
        if(mLastVoluntaryHand.equals(handID)){
            return;
        }
        mVPIP++;
        mLastVoluntaryHand = handID;
    }

    public void incrementPFR(String handID){
        if(mLastRaisedHand.equals(handID)){
            return;
        }
        mPreFlopRaise++;
        mLastRaisedHand = handID;
    }


}
