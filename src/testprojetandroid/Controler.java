package testprojetandroid;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import modele.*;
import myconnections.DBConnection;

public class Controler {

    DBConnection dbc = new DBConnection();
    Connection con = dbc.getConnection();
    protected Scanner sc = new Scanner(System.in);
    protected int choix = 0;

    public void Menu() {
        do {
            System.out.println("===================================");
            System.out.println("                Menu");
            System.out.println("===================================");
            System.out.println("1. UserDB");
            System.out.println("2. CarnetDB");
            System.out.println("3. Quitter");
            System.out.println("Choix :");
            choix = Integer.parseInt(sc.nextLine());
            switch (choix) {
                case 1:
                    MenuUser();
                    break;
                case 2:
                    MenuCarnet();
                    break;
                case 3:
                    System.out.println("Fin du programme.");
                    break;
                default:
                    System.out.println("Choix incorrecte");
            }
        } while (choix != 3);
    }

    /*
     =======================================================
     TEST UNITAIRE POUR LA CLASSE : USERDB
     =======================================================
     */
    public void MenuUser() {
        do {
            System.out.println("===================================");
            System.out.println("            Menu USERDB");
            System.out.println("===================================");
            System.out.println("1. Inserer un client");
            System.out.println("2. Modifier un client par id");
            System.out.println("3. Verifier les acces d'un client");
            System.out.println("4. voir les infos d'un client");
            System.out.println("5. Supprimer un client par id");
            System.out.println("6. Retour a l'accueil");
            System.out.println("Choix :");
            choix = Integer.parseInt(sc.nextLine());
            switch (choix) {
                case 1:
                    createUser();
                    break;
                case 2:
                    updateUser();
                    break;
                case 3:
                    checkLoginUser();
                    break;
                case 4:
                    viewUser();
                    break;
                case 5:
                    deleteUser();
                    break;
                case 6:
                    System.out.println("Retour a l'accueil");
                    break;
                default:
                    System.out.println("Choix incorrecte");
            }
        } while (choix != 6);
    }

    public void createUser() {
        String pseudoTmp = "";
        String mailTmp = "";
        String passwordTmp = "";
        System.out.println("Pseudo :");
        pseudoTmp = sc.nextLine();
        System.out.println("Mail :");
        mailTmp = sc.nextLine();
        System.out.println("Password :");
        passwordTmp = sc.nextLine();
        UserDB.setConnection(con);
        UserDB user = null;
        user = new UserDB(pseudoTmp, mailTmp, passwordTmp);
        try {
            user.create();
        } catch (Exception ex) {
            Logger.getLogger(Controler.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (user.getId_user() != -1) {//client trouvé
            System.out.println("Object crée :");
            System.out.println(user.toString());
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Controler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateUser() {
        int id_userTmp = -1;
        System.out.println("ID :");
        id_userTmp = Integer.parseInt(sc.nextLine());
        UserDB.setConnection(con);
        UserDB user = null;
        user = new UserDB(id_userTmp);
        try {
            user.read();
        } catch (Exception ex) {
            Logger.getLogger(Controler.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (user.getId_user() != -1) {//client trouvé
            System.out.println("Object trouvé :");
            System.out.println(user.toString());
            String pseudoTmp = "";
            String mailTmp = "";
            String passwordTmp = "";
            System.out.println("Nouveau pseudo(" + user.getPseudo() + ") :");
            pseudoTmp = sc.nextLine();
            System.out.println("Nouveau mail(" + user.getMail() + ") :");
            mailTmp = sc.nextLine();
            System.out.println("Nouveau password(" + user.getPassword() + ") :");
            passwordTmp = sc.nextLine();
            user.setPseudo(pseudoTmp);
            user.setMail(mailTmp);
            user.setPassword(passwordTmp);
            try {
                user.update();
            } catch (Exception ex) {
                Logger.getLogger(Controler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Controler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void checkLoginUser() {
        String pseudoTmp = "";
        String passwordTmp = "";
        System.out.println("Pseudo :");
        pseudoTmp = sc.nextLine();
        System.out.println("Password :");
        passwordTmp = sc.nextLine();
        UserDB.setConnection(con);
        UserDB user = null;
        user = new UserDB(pseudoTmp, passwordTmp);
        try {
            user.checkLogin();
        } catch (Exception ex) {
            Logger.getLogger(Controler.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (user.getId_user() != -1) {//client trouvé
            System.out.println("Object trouvé :");
            System.out.println(user.toString());
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Controler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void viewUser() {
        int id_userTmp = -1;
        System.out.println("ID :");
        id_userTmp = Integer.parseInt(sc.nextLine());
        ArrayList<CarnetDB> list = null;
        UserDB.setConnection(con);
        UserDB user = null;
        user = new UserDB(id_userTmp);
        try {
            user.read();
        } catch (Exception ex) {
            Logger.getLogger(Controler.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (user.getId_user() != -1) {//client trouvé
            System.out.println("Object trouvé :");
            try {
                CarnetDB.setConnection(con);
                list = CarnetDB.getUser(user.getId_user());
            } catch (Exception ex) {
                Logger.getLogger(Controler.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (list == null) {
                System.out.println("Aucun carnet lié a ce compte");
            } else {
                user.setListCarnet(list);
            }
            System.out.println(user.toString());
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Controler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteUser() {
        int id_userTmp = -1;
        System.out.println("ID :");
        id_userTmp = Integer.parseInt(sc.nextLine());
        UserDB.setConnection(con);
        UserDB user = null;
        user = new UserDB(id_userTmp);
        try {
            user.read();
        } catch (Exception ex) {
            Logger.getLogger(Controler.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (user.getId_user() != -1) {//client trouvé
            String suppression = "";
            System.out.println("Object trouvé :");
            System.out.println(user.toString());
            System.out.println("Supprimer cet object ?");
            do {
                System.out.println("Choix(oui/non) :");
                suppression = sc.nextLine();
                if (suppression.equals("oui")) {
                    try {
                        user.delete();
                        System.out.println("Suppression réussis");
                    } catch (Exception ex) {
                        Logger.getLogger(Controler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (suppression.equals("non")) {
                    System.out.println("Annulation de la suppression");
                }
            } while (!suppression.equals("oui") && !suppression.equals("non"));
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Controler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /*
     =======================================================
     TEST UNITAIRE POUR LA CLASSE : CARNETDB
     =======================================================
     */

    public void MenuCarnet() {
        do {
            System.out.println("===================================");
            System.out.println("            Menu CARNETDB");
            System.out.println("===================================");
            System.out.println("1. Ajouter/créer un carnet a un client");
            System.out.println("2. Modifier un carnet par id");
            System.out.println("3. voir les infos d'un carnet");
            System.out.println("4. Supprimer un carnet par id");
            System.out.println("5. Retour a l'accueil");
            System.out.println("Choix :");
            choix = Integer.parseInt(sc.nextLine());
            switch (choix) {
                case 1:
                    createCarnet();
                    break;
                case 2:
                    updateCarnet();
                    break;
                case 3:
                    viewCarnet();
                    break;
                case 4:
                    deleteCarnet();
                    break;
                case 5:
                    System.out.println("Retour a l'accueil");
                    break;
                default:
                    System.out.println("Choix incorrecte");
            }
        } while (choix != 5);
    }

    private void createCarnet() {
        int id_userTmp = -1;
        System.out.println("ID du client :");
        id_userTmp = Integer.parseInt(sc.nextLine());
        UserDB.setConnection(con);
        UserDB user = null;
        user = new UserDB(id_userTmp);
        try {
            user.read();
        } catch (Exception ex) {
            Logger.getLogger(Controler.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (user.getId_user() != -1) {//client trouvé
            String titreTmp = "";
            System.out.println("Object trouvé :");
            System.out.println(user.toString());
            System.out.println("Titre du carnet :");
            titreTmp = sc.nextLine();
            CarnetDB.setConnection(con);
            CarnetDB carnet = null;
            carnet = new CarnetDB(titreTmp, user.getId_user());
            try {
                carnet.create();
            } catch (Exception ex) {
                Logger.getLogger(Controler.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (carnet.getId_carnet() != -1) {
                System.out.println("Object trouvé :");
                System.out.println(carnet.toString());
            }
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Controler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void updateCarnet() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void viewCarnet() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void deleteCarnet() {
        int id_carnetTmp = -1;
        System.out.println("ID du carnet :");
        id_carnetTmp = Integer.parseInt(sc.nextLine());
        CarnetDB.setConnection(con);
        CarnetDB carnet = null;
        carnet = new CarnetDB(id_carnetTmp);
        try {
            carnet.read();
        } catch (Exception ex) {
            Logger.getLogger(Controler.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (carnet.getId_user() != -1) {//client trouvé
            String suppression = "";
            System.out.println("Object trouvé :");
            System.out.println(carnet.toString());
            System.out.println("Supprimer cet object ?");
            do {
                System.out.println("Choix(oui/non) :");
                suppression = sc.nextLine();
                if (suppression.equals("oui")) {
                    try {
                        carnet.delete();
                        System.out.println("Suppression réussis");
                    } catch (Exception ex) {
                        Logger.getLogger(Controler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (suppression.equals("non")) {
                    System.out.println("Annulation de la suppression");
                }
            } while (!suppression.equals("oui") && !suppression.equals("non"));
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Controler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
