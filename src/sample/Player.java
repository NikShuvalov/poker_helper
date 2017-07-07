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
        return ((int)mVPIP*100/mTotalHands)/100.0;
    }

    public double getPreflopRaiseRatio() {
        return ((int)(mPreFlopRaise*100/mTotalHands))/100.0;
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
