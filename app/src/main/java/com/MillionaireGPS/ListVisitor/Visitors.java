package com.MillionaireGPS.ListVisitor;

public class Visitors {

    private String visitorName;
    private String visitorCode;
    private String visitorMobile;

    public Visitors(String visitorName, String visitorCode, String visitorMobile) {
        this.visitorName = visitorName;
        this.visitorCode = visitorCode;
        this.visitorMobile = visitorMobile;
    }

    public String getVisitorName() {
        return visitorName;
    }

    public void setVisitorName(String visitorName) {
        this.visitorName = visitorName;
    }

    public String getVisitorCode() {
        return visitorCode;
    }

    public void setVisitorCode(String visitorCode) {
        this.visitorCode = visitorCode;
    }

    public String getVisitorMobile() {
        return visitorMobile;
    }

    public void setVisitorMobile(String visitorMobile) {
        this.visitorMobile = visitorMobile;
    }
}
