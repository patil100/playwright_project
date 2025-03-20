package Utility;

import java.util.Random;


public class Random_data {


    public int randomNumber() {
        Random rand = new Random();
        randNumber = rand.nextInt(100) + 1;
         return randNumber;
    }


    int randNumber;
}
