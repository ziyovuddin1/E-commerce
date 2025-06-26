package ECommerce.Input;

import java.util.Scanner;

public class Input {
    private static final Scanner sc1 = new Scanner(System.in);
    public static int num(String s) {
        System.out.print(s);
        return sc1.nextInt();
    }

    private static final Scanner sc2 = new Scanner(System.in);
    public static String str(String s) {
        System.out.print(s);
        return sc2.nextLine();
    }
}
