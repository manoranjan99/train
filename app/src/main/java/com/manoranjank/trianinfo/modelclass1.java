package com.manoranjank.trianinfo;

/**
 * Created by Manoranjan K on 08-06-2019.
 */

public class modelclass1 {

    private int id;
    private String Name;
    public int seatno;
    public String Train;
    public String PNR;

    public modelclass1(int id,String Name,int seatno,String train,String PNR)
    {
        this.id=id;
        this.Name=Name;
        this.seatno=seatno;
        this.Train=train;
        this.PNR=PNR;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getSeatno() {
        return seatno;
    }

    public void setSeatno(int seatno) {
        this.seatno = seatno;
    }

    public String getTrain() {
        return Train;
    }

    public void setTrain(String train) {
        Train = train;
    }

    public String getPNR() {
        return PNR;
    }

    public void setPNR(String PNR) {
        this.PNR = PNR;
    }
}
