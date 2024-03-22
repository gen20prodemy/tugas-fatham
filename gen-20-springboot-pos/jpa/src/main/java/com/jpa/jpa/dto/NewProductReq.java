package com.jpa.jpa.dto;

public class NewProductReq {
    private String stdname2;
    private int stdprice2;

    public NewProductReq(String stdname2, int stdprice2) {
        this.stdname2 = stdname2;
        this.stdprice2 = stdprice2;
    }

    public NewProductReq() {
    }

    public String getStdname2() {
        return stdname2;
    }

    public void setStdname2(String stdname2) {
        this.stdname2 = stdname2;
    }

    public int getStdprice2() {
        return stdprice2;
    }

    public void setStdprice2(int stdprice2) {
        this.stdprice2 = stdprice2;
    }
}
