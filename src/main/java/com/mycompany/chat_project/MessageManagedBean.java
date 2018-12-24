package com.mycompany.chat_project;

import com.mysql.jdbc.PreparedStatement;
import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;

@ManagedBean
public class MessageManagedBean {

    public PreparedStatement sqlDurum = null;
    public ResultSet satir = null;
    public Connection conn = null;

    private String isimler = "";
    private String message = "";

    private ArrayList<String> gelIsim = new ArrayList<String>();
    private ArrayList<String> gelMesaj = new ArrayList<String>();
    private ArrayList<Integer> say = new ArrayList<Integer>();

    public MessageManagedBean() {
        veriTabaniBaglanti();
        gel();
        sayac();
    }

    public String getIsimler() {

        return isimler;
    }

    public void setIsimler(String isimler) {
        this.isimler = isimler;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<String> getGelIsim() {
        return gelIsim;
    }

    public void setGelIsim(ArrayList<String> gelIsim) {
        this.gelIsim = gelIsim;
    }

    public ArrayList<String> getGelMesaj() {
        return gelMesaj;
    }

    public void setGelMesaj(ArrayList<String> gelMesaj) {
        this.gelMesaj = gelMesaj;
    }

    public ArrayList<Integer> getSay() {
        return say;
    }

    public void setSay(ArrayList<Integer> say) {
        this.say = say;
    }

    public void veriTabaniBaglanti() {
        final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        final String DB_URL = "jdbc:mysql://localhost:3306/chatProject";
        final String USER = "root";
        final String PASS = "573586";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("***********************************Connected.");
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void verKaydet() {
        try {
            String sqlCumle = "insert into Messagges(person,message) values ('" + isimler + "','" + message + "')";
            System.out.println(sqlCumle);
            sqlDurum = (PreparedStatement) conn.prepareStatement(sqlCumle);
            int i = 0;
            i = sqlDurum.executeUpdate();
            if (i == 1) {
                System.out.println(" oldu");
            }
            while (satir.next()) {
                System.out.println(satir.getString(1));
            }

        } catch (SQLException ex) {
            Logger.getLogger(MessageManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sayac() {
        try {
            int i = 0;
            sqlDurum = (PreparedStatement) conn.prepareStatement("select idMessagges from Messagges");
            satir = sqlDurum.executeQuery();
            while (satir.next()) {
                say.add(i++);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MessageManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void gel() {
        try {
            sqlDurum = (PreparedStatement) conn.prepareStatement("select person,message from Messagges");
            satir = sqlDurum.executeQuery();

            while (satir.next()) {
                gelIsim.add(satir.getString(1));
                gelMesaj.add(satir.getString(2));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MessageManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
