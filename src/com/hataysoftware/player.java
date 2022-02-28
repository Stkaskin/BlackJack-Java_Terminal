package com.hataysoftware;

import java.util.ArrayList;

public class player {
    String player_name;
    boolean play = false;
    boolean wait = false;
    boolean win = false;
    boolean lose = false;
    ArrayList<cart> carts = new ArrayList<cart>();
    ArrayList puans = new ArrayList();


    public player(String name) {
        this.player_name=name;
        this.play = true;
      risk risk=new risk();
      risk.puan=0;
        risk.risk=risk.riskCalculator();
        this.puans.add(risk);
    }

    public void addPuan(int index, int puan) {
        int old_puan =  ((risk)this.puans.get(index)).puan;
        risk risk=new risk();
        risk.puan=old_puan + puan;
        risk.risk=risk.riskCalculator();

        this.puans.set(index, risk);
    }

    public void newPuan(int puan) {
        risk risk=new risk();
        risk.puan= puan;
        risk.risk=risk.riskCalculator();

        this.puans.add(risk);
    }



}
