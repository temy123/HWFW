package com.naver.temy123.baseproject.base.Entry;

/**
 * Created by lhw on 2017. 5. 8..
 */

public class BeanSpinnerExtends {

    private int seq;
    private String text;


    public BeanSpinnerExtends() {
    }

    public BeanSpinnerExtends(int seq, String text) {
        this.seq = seq;
        this.text = text;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
