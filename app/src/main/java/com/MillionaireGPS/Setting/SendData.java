package com.MillionaireGPS.Setting;

public class SendData {

    private static SendData instance = null;
    public static SendData newInstance() {
        if (instance == null ){
            instance = new SendData();
        }
        return instance;
    }

    private boolean isListVisitor ;

    public boolean isListVisitor() {
        return isListVisitor;
    }

    public void setListVisitor(boolean listVisitor) {
        isListVisitor = listVisitor;
    }
}
