package com.example.infiny.pickup.Helpers;

import java.util.Arrays;
import java.util.List;





public class MenuItem {
    public static List<Menu> makeGenres() {
        return Arrays.asList(makeEspressoMenu(),makeAmericonoMenu(),makeLatteMenu());
    };
    public static Menu makeEspressoMenu() {
        return new Menu("Espresso", makeJazzEspreso());
    }
    public static Menu makeAmericonoMenu() {
        return new Menu("Americano", makeJazzAmerinco());
    }
    public static Menu makeLatteMenu() {
        return new Menu("Latte", makeJazzLatte());
    }
    public static List<Drinks> makeJazzLatte() {
        Drinks milesDavis = new Drinks("Small","Medium","Large","£ 2.20","£ 2.25","£ 2.30");


        return Arrays.asList(milesDavis);
    }

    public static List<Drinks> makeJazzAmerinco() {
        Drinks milesDavis = new Drinks("Small","Medium","Large","£ 3.20","£ 3.25","£ 3.30");
        return Arrays.asList(milesDavis);
    }
    public static List<Drinks> makeJazzEspreso() {
        Drinks milesDavis = new Drinks("Small","Medium","Large","£ 4.20","£ 4.25","£ 4.30");
        return Arrays.asList(milesDavis);
    }
    public static List<Menu> makedessrtsGenres(){
        return Arrays.asList(makeBrownieMenu(),makeCheesecakeMenu(),makeCarrotecakeMenu());
    }
    public static List<Menu> makeorder(){
        return Arrays.asList(makeordermenu());
    }
    public static Menu makeordermenu() {
        return new Menu("ORDER", makeJazzLatte());
    }
    public static Menu makeBrownieMenu() {
        return new Menu("Tuna Sandwich", makeJazzLatte());
    }
    public static Menu makeCheesecakeMenu() { return new Menu("Cheese Sandwich", makeJazzLatte());}
    public static Menu makeCarrotecakeMenu() {return new Menu("Grill Sandwich", makeJazzLatte());}

}
