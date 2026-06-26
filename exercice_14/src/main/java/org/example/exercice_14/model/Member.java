package org.example.exercice_14.model;

public class Member {

    private String id;
    private boolean suspended;
    private int significantLateReturns;

    public Member(String id) {
        this.id = id;
        this.suspended = false;
        this.significantLateReturns = 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isSuspended() {
        return suspended;
    }

    public void setSuspended(boolean suspended) {
        this.suspended = suspended;
    }

    public int getSignificantLateReturns() {
        return significantLateReturns;
    }

    public void setSignificantLateReturns(int significantLateReturns) {
        this.significantLateReturns = significantLateReturns;
    }
}
