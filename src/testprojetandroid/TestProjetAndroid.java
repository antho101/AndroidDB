package testprojetandroid;

public class TestProjetAndroid {

    protected Controler c = new Controler();
    //protected Controler_antho c = new Controler_antho();

    public static void main(String[] args) {
        TestProjetAndroid projet = new TestProjetAndroid();
    }

    public TestProjetAndroid() {

        Controler c = new Controler();
        //Controler_antho c = new Controler_antho();
        c.Menu();
    }
}
