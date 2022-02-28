package com.hataysoftware;

import java.awt.*;
import java.util.Locale;

public class cart {
    String type;
    String symbol;
    String color;
    int puan;

    public cart(String _type, String _symbol, int _puan,String color) {
        this.type = _type;
        this.symbol = _symbol;
        this.puan = _puan;
       if (color!=null)
           this.color= colors.setColor(color);
       else
          this.color= colors.setColor("");

    }

    public int getPuan() {
        return puan;
    }

    public String getType() {
        return type;
    }

    public String getSymbol() {
        return symbol;
    }




}
