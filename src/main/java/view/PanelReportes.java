package view;

import dao.ReporteDAO;

import javax.swing.*;
import java.awt.*;

public class PanelReportes extends JPanel {

    private JTextArea txtReporte;

    private JButton btnMasPrestados;
    private JButton btnHistorial;
    private JButton btnActivos;

    public PanelReportes() {

        setLayout(new BorderLayout());

        txtReporte = new JTextArea();

        add(
                new JScrollPane(txtReporte),
                BorderLayout.CENTER
        );

        JPanel panelBotones = new JPanel();

        btnMasPrestados =
                new JButton("Libros Más Prestados");

        btnHistorial =
                new JButton("Historial");

        btnActivos =
                new JButton("Préstamos Activos");

        panelBotones.add(btnMasPrestados);
        panelBotones.add(btnHistorial);
        panelBotones.add(btnActivos);

        add(panelBotones,
                BorderLayout.NORTH);

        btnMasPrestados.addActionListener(
                e -> mostrarMasPrestados()
        );

        btnHistorial.addActionListener(
                e -> mostrarHistorial()
        );

        btnActivos.addActionListener(
                e -> mostrarActivos()
        );
    }

    private void mostrarMasPrestados() {

        ReporteDAO dao =
                new ReporteDAO();

        txtReporte.setText(
                dao.librosMasPrestados()
        );
    }

    private void mostrarHistorial() {

        ReporteDAO dao =
                new ReporteDAO();

        txtReporte.setText(
                dao.historialEstudiantes()
        );
    }

    private void mostrarActivos() {

        ReporteDAO dao =
                new ReporteDAO();

        txtReporte.setText(
                dao.prestamosActivos()
        );
    }
}