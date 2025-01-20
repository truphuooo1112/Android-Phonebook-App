package com.example.danhba;

public class LienheActivity {
    private  boolean isNam;
    private String mName;
    private String mNumber;

    public LienheActivity(boolean isNam, String mName, String mNumber) {
        this.isNam = isNam;
        this.mName = mName;
        this.mNumber = mNumber;
    }

    public boolean isNam() {
        return isNam;
    }

    public void setNam(boolean nam) {
        isNam = nam;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmNumber() {
        return mNumber;
    }

    public void setmNumber(String mNumber) {
        this.mNumber = mNumber;
    }
}
