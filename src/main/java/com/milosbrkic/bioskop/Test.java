/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.milosbrkic.bioskop;

/**
 *
 * @author milos
 */
public class Test {

    public static void main(String[] args) {
        try {
            Class.forName("com.postgresql.jdbc.Driver");
            System.out.println("ima");
        } catch (ClassNotFoundException e) {
            System.out.println("nema");
        }
    }
}
