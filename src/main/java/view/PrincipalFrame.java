package view;

import model.Usuario;

import javax.swing.*;

public class PrincipalFrame extends JFrame {

    public PrincipalFrame(Usuario usuario) {

        setTitle("Sistema Biblioteca Escolar");

        setSize(1000, 600);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabs = new JTabbedPane();

        // Pestaña Libros
        tabs.addTab(
                "Libros",
                new PanelLibros()
        );

        // Pestaña Estudiantes
        tabs.addTab(
                "Estudiantes",
                new PanelEstudiantes()
        );

        // Pestaña Préstamos
        tabs.addTab(
                "Préstamos",
                new PanelPrestamos()
        );

        // Pestaña Reportes
        tabs.addTab(
                "Reportes",
                new PanelReportes()
        );

        // Restricciones para estudiantes
        if (usuario.getRol()
                .equalsIgnoreCase("estudiante")) {

            tabs.setEnabledAt(0, false);
            tabs.setEnabledAt(1, false);
        }

        add(tabs);

        setVisible(true);
    }
}