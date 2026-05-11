package org.example.noten.util;

import org.mindrot.jbcrypt.BCrypt;

public class GenerarHash {
    public static void main(String[] args) {
        String pasahitza = "1234";
        String hash = BCrypt.hashpw(pasahitza, BCrypt.gensalt(12));
        System.out.println(hash);
    }
}