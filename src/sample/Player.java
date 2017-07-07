package sample;

public class Player {
    private String mPlayerName;
    private int mTotalHands;
    private double mVPIP, mPreFlopRaise;

    public Player(String userName){
        mPlayerName = userName;
        mTotalHands =0;
        mVPIP = 0;
        mPreFlopRaise = 0;
    }

    public String getPlayerName() {
        return mPlayerName;
    }

    public int getTotalHands() {
        return mTotalHands;
    }

    public double getVpipRatio() {
        return mVPIP/mTotalHands;
    }

    public double getPreflopRaiseRatio() {
        return mPreFlopRaise/mTotalHands;
    }

    public void incrementHands(){
        mTotalHands++;
    }

    public void incrementVPIP(){
        mVPIP++;
    }

    public void incrementPFR(){
        mPreFlopRaise++;
    }


}
