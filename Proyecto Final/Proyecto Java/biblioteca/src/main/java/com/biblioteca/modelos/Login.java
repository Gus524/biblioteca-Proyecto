package com.biblioteca.modelos;

import com.biblioteca.utilidades.Encriptar;
import com.biblioteca.dao.ConnectionDB;

public class Login {
    public String user;
    public String pass;
    private ConnectionDB cnn;

    public Login() {
        this.cnn = new ConnectionDB();
    }

    public Login(String user, String pass) {
        this.cnn = new ConnectionDB();
        this.user = user;
        this.pass = pass;
    }

    public Boolean comprobarUser() {
        System.out.println(Encriptar.encrypt(this.pass));
        return (cnn.select("SELECT * FROM Login WHERE user = ? AND pass = ?",
                this.user,
                Encriptar.encrypt(this.pass)) > 0);
    }
}
