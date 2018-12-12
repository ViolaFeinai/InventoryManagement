package com.example.viola.engdict;

import java.util.ArrayList;
import java.util.List;

public class Lists {


    public static List<String> getLocationList () {
        List<String> list = new ArrayList();
        list.add("Lince");
        list.add("San Miguel");
        list.add("Arequipa");
        list.add("Chiclayo");
        list.add("Sold");
        return list;
    }

    public static List<String> getTypesList () {
        List<String> list = new ArrayList();
        list.add("Audiphono ITC");
        list.add("Audiphono ITE");
        list.add("Audiphono D_ITE");
        list.add("Audiphono BTE");
        list.add("Audiphono SPBTE");
        return list;
    }

}
