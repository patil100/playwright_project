package Utility;

import java.util.Random;
import java.util.UUID;

public class Random_data {
    private final Random random;
    private String lastGeneratedString;
    private int lastGeneratedNumber;
    private String lastGenerateddata;

    public Random_data() {
        this.random = new Random();

    }


    public Random_data getRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder("Automation_Test");

        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }
        lastGeneratedString = sb.toString();
        return this;
    }

    public Random_data getRandomNumber(int max, int min) {
        lastGeneratedNumber = random.nextInt((max - min) + 1) + min;
        return this;
    }

    public Random_data getRandomStringAndNumber() {
        lastGenerateddata=UUID.randomUUID().toString().replace("-", "").substring(0, 10);
        return this;
    }
    public String getString() {
        return lastGeneratedString;
    }

    public int getNumber() {
        return lastGeneratedNumber;
    }

    public static void main(String[]args){
        Random_data randomData=new Random_data();
       randomData.getRandomString(5).getRandomNumber(99999999,1);
       int number=randomData.getNumber();
       String  name= randomData.getString();
        System.out.println(number);
        System.out.println(name);

    }
}
