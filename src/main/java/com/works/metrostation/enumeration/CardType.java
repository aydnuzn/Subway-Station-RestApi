package com.works.metrostation.enumeration;

public enum CardType {

    STANDARD(3.5F),
    STUDENT(2F),
    FREE(.0F);

    private final float price;

    private CardType(float price){
        this.price = price;
    }

    public float getPrice(){
        return price;
    }

}