package pl.min;

import java.util.Random;

public class Min {

    private int[]  set;

    private void initializeSet(int elementNumber){
        Random r = new Random();
        set = new int[elementNumber];
        System.out.print("Input: ");

        for(int i=0; i < set.length; i++) {
            set[i] = r.nextInt(49) + 1;
            System.out.print(set[i] + (i==set.length - 1 ? "":", ") );
        }

        System.out.println();
    }

    private void findMax(){
        initializeSet(6);

        int min = set[0];

        for(int i=1; i < set.length; i++)
            if(min > set[i])
                min = set[i];
//        int i = 1;
//        do {
//            if(min > set[i])
//                min = set[i];
//            i++;
//        } while(set[i] != -1);

        System.out.println("Min = " + min);
    }

    public static void main(String[] args) {
        new Min().findMax();
    }

}
