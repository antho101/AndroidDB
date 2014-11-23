package testprojetandroid;

import java.util.Scanner;

public class TestProjetAndroid {

    protected Scanner sc = new Scanner(System.in);
    protected Controler c1 = new Controler();
    protected Controler_antho c2 = new Controler_antho();

    public static void main(String[] args) {
        TestProjetAndroid projet = new TestProjetAndroid();
    }

    public TestProjetAndroid() {
        int choix = 0;

        do {
            System.out.println("1. alex");
            System.out.println("2. anthony");
            System.out.println("3. Quitter");
            choix = Integer.parseInt(sc.nextLine());
            if (choix == 1) {
                Controler c1 = new Controler();
                c1.Menu();
            }
            if (choix == 2) {
                Controler_antho c2 = new Controler_antho();
                c2.Menu();
            }
        } while (choix != 3);
    }
}
