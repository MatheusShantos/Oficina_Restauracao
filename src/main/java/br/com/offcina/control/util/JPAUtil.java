/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.offcina.control.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author israel
 */
public class JPAUtil {

    private static EntityManagerFactory factory;
    

    public static EntityManagerFactory entityManagerFactory() {
        if(factory==null){
            factory = Persistence.createEntityManagerFactory("OFFCINA_PU");
        }
        return factory;
    }
}
