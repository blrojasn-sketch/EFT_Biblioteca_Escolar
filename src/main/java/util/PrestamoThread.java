package util;

import dao.PrestamoDAO;
import model.Prestamo;

public class PrestamoThread extends Thread {

    private PrestamoDAO dao;
    private Prestamo prestamo;

    public PrestamoThread(
            PrestamoDAO dao,
            Prestamo prestamo) {

        this.dao = dao;
        this.prestamo = prestamo;
    }

    @Override
    public void run() {

        dao.registrarPrestamo(
                prestamo
        );
    }
}