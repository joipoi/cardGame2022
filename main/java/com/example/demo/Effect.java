package com.example.demo;

public class Effect {
    private String type; //dmg, healing,draw, permanent power, aoeDmg, buff, dot, weaken...
    private int amount;
    private boolean isSelectTarget;

    public boolean isSelectTarget() {
        return isSelectTarget;
    }

    public void setSelectTarget(boolean selectTarget) {
        isSelectTarget = selectTarget;
    }

    public String getType() {
        return type;
    }



    public void setType(String type) {
        this.type = type;
    }



    public int getAmount() {
        return amount;
    }



    public void setAmount(int amount) {
        this.amount = amount;
    }



    public Effect(String type, int amount) {
        this.type = type;
        this.amount = amount;

        if(this.type == "damage" || this.type == "healing") {
            this.isSelectTarget = true;
        }else {
            this.isSelectTarget = false;
        }
    }
    
}
