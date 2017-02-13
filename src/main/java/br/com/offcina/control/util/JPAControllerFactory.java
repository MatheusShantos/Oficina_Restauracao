/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.offcina.control.util;

import br.com.offcina.control.cargo.CargoJpaController;

/**
 *
 * @author israel
 */
public class JPAControllerFactory {

    private static CargoJpaController cargoController;

    public static CargoJpaController cargoController() {
        if (cargoController == null) {
            cargoController = new CargoJpaController(JPAUtil.entityManagerFactory());
        }
        return cargoController;
    }
}
