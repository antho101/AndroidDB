/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Anthony
 */
public class NoteDB extends Note implements CRUD {

    protected static Connection dbConnect = null;

    public NoteDB() {
        super();
    }


    public static void setConnection(Connection nouvdbConnect) {
        dbConnect = nouvdbConnect;
    }

    public void create() throws Exception {
        CallableStatement cstmt = null;
        try {
            String query1 = "call createNote(?,?,?,?,?)";
            String query2 = "select note_seq.currval from dual";
            PreparedStatement pstm1 = dbConnect.prepareStatement(query1);
            PreparedStatement pstm2 = dbConnect.prepareStatement(query2);
            pstm1.setString(1, titre);
            pstm1.setString(2, contenu);
            pstm1.setDate(3, date_note);
            pstm1.setInt(4, id_carnet);
            pstm1.setInt(5, id_categorie);
            int nl = pstm1.executeUpdate();
            ResultSet rs = pstm2.executeQuery();
            if (rs.next()) {
                int nc = rs.getInt(1);
                id_note = nc;
            } else {
                System.out.println("Erreur de l'ajout");
            }

        } catch (Exception e) {
            throw new Exception("Erreur de cr�ation " + e.getMessage());
        } finally {
            try {
                cstmt.close();
            } catch (Exception e) {
            }
        }
    }

    

    public void read(int numeroTmp) throws Exception {

        CallableStatement cstmt = null;
        try {
            boolean trouve = false;
            String query1 = "SELECT * FROM note WHERE id_note = ?";
            PreparedStatement pstm1 = dbConnect.prepareStatement(query1);
            pstm1.setInt(1, numeroTmp);
            ResultSet rs = pstm1.executeQuery();
            if (rs.next()) {
                trouve = true;
                id_note = rs.getInt("ID_NOTE");
                titre = rs.getString("TITRE");
                contenu = rs.getString("CONTENU");
                id_carnet = rs.getInt("ID_CARNET");
                id_categorie = rs.getInt("ID_CATEGORIE");
            }
            if (!trouve) {
                throw new Exception("numero inconnu dans la table !");
            }
        } catch (Exception e) {

            throw new Exception("Erreur de lecture " + e.getMessage());
        } finally {
            try {
                cstmt.close();
            } catch (Exception e) {
            }
        }
    }

    public static ArrayList<NoteDB> rechNumero(String titre) throws Exception {
        ArrayList<NoteDB> plusieurs = new ArrayList<NoteDB>();
        CallableStatement cstmt = null;
        try {
            boolean trouve = false;
            String query1 = "SELECT * FROM note WHERE titre = ?";
            PreparedStatement pstm1 = dbConnect.prepareStatement(query1);
            pstm1.setString(2, titre);
            ResultSet rs = pstm1.executeQuery();
            if (rs.next()) {
                trouve = true;
                int id_noteTmp = rs.getInt("ID_NOTE");
                String cotenuTmp = rs.getString("CONTENU");
                Date DateTmp = rs.getDate("DATE_NOTE");
                int id_carnetTmp = rs.getInt("ID_CARNET");
                int id_categorieTmp = rs.getInt("ID_CATEGORIE");
                plusieurs.add(new NoteDB(id_noteTmp, cotenuTmp, DateTmp, id_carnetTmp, id_categorieTmp));
            }

            if (!trouve) {
                throw new Exception("nom inconnu");
            } else {
                return plusieurs;
            }
        } catch (Exception e) {

            throw new Exception("maj echou�");
        } finally {
            try {
                cstmt.close();
            } catch (Exception e) {
            }
        }
    }

    /**
     * mise � jour des donn�es de la chambre sur base de son numero
     *
     * @throws Exception erreur de mise � jour
     */
    public void update() throws Exception {
        CallableStatement cstmt = null;

        try {
            String query1 = "UPDATE note SET titre = ?, contenu = ?,date_note = ?,id_carnet = ?,id_categorie = ? WHERE id_note = ?";
            PreparedStatement pstm1 = dbConnect.prepareStatement(query1);
            pstm1.setInt(1, id_note);
            pstm1.setString(2, titre);
            pstm1.setString(3, contenu);
            pstm1.setDate(4, date_note);
            pstm1.setInt(5, id_carnet);
            pstm1.setInt(6, id_categorie);
            int nl = pstm1.executeUpdate();
            if (nl == 1) {
                System.out.println("La ligne a bien �t� mise a jour !");
            } else {
                System.out.println("Aucune ligne n'a �t� mise a jour !");
            }

        } catch (Exception e) {

            throw new Exception("Erreur de mise � jour : " + e.getMessage());
        } finally {//effectu� dans tous les cas 
            try {
                cstmt.close();
            } catch (Exception e) {
            }
        }
    }

    /**
     * effacement de la chambre sur base de son numero
     *
     * @throws Exception erreur d'effacement
     */
    public void delete() throws Exception {

        CallableStatement cstmt = null;
        try {
            String query1 = "DELETE note WHERE id_note = ?";
            PreparedStatement pstm1 = dbConnect.prepareStatement(query1);
            pstm1.setInt(1, id_note);
            int nl = pstm1.executeUpdate();
            if (nl == 1) {
                System.out.println("La ligne a bien �t� suprim� !");
            } else {
                System.out.println("Aucune ligne n'a �t� suprim� !");
            }

        } catch (Exception e) {

            throw new Exception("Erreur d'effacement : " + e.getMessage());
        } finally {
            try {
                cstmt.close();
            } catch (Exception e) {
            }
        }
    }

    public static ArrayList<NoteDB> getCarnet(int var) throws Exception {
        ArrayList<NoteDB> list = new ArrayList<>();
        CallableStatement cstmt = null;
        try {
            boolean trouve = false;
            String query1 = "select * from note where id_carnet = ?";
            PreparedStatement pstm1 = dbConnect.prepareStatement(query1);
            pstm1.setInt(1, var);
            ResultSet rs = pstm1.executeQuery();
            while (rs.next()) {
                trouve = true;
                list.add(new NoteDB(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDate(4), rs.getInt(5), rs.getInt(6)));
            }
            if (!trouve) {
                return null;
            } else {
                return list;
            }
        } catch (Exception e) {
            throw new Exception("Erreur: " + e.getMessage());
        } finally {//effectué dans tous les cas 
            try {
                cstmt.close();
            } catch (Exception e) {
            }
        }

    }

    @Override
    public void read() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
