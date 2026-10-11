package view;

import dao.EstudianteDAO;
import dao.LibroDAO;
import dao.PrestamoDAO;

import model.Estudiante;
import model.Libro;
import model.Prestamo;

import util.PrestamoThread;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class PanelPrestamos extends JPanel {

    private JComboBox<Estudiante> cboEstudiantes;
    private JComboBox<Libro> cboLibros;

    private JButton btnRegistrar;
    private JButton btnDevolver;
    private JButton btnCargar;

    private JTable tabla;
    private DefaultTableModel modelo;

    public PanelPrestamos() {

        setLayout(new BorderLayout());

        JLabel titulo = new JLabel(
                "📖 Gestión de Préstamos",
                SwingConstants.CENTER
        );

        titulo.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        24
                )
        );

        titulo.setForeground(
                new Color(25, 118, 210)
        );

        add(titulo, BorderLayout.NORTH);

        JPanel panelFormulario =
                new JPanel(
                        new GridLayout(3, 2, 10, 10)
                );

        panelFormulario.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        10,
                        10,
                        10
                )
        );

        panelFormulario.add(
                new JLabel("Estudiante:")
        );

        cboEstudiantes =
                new JComboBox<>();

        panelFormulario.add(
                cboEstudiantes
        );

        panelFormulario.add(
                new JLabel("Libro:")
        );

        cboLibros =
                new JComboBox<>();

        panelFormulario.add(
                cboLibros
        );

        btnRegistrar =
                new JButton(
                        "Registrar Préstamo"
                );

        btnRegistrar.setBackground(
                new Color(76, 175, 80)
        );

        btnRegistrar.setForeground(
                Color.WHITE
        );

        panelFormulario.add(
                btnRegistrar
        );

        add(
                panelFormulario,
                BorderLayout.WEST
        );

        modelo =
                new DefaultTableModel();

        modelo.addColumn("ID");
        modelo.addColumn("Estudiante");
        modelo.addColumn("Libro");
        modelo.addColumn("Fecha Préstamo");
        modelo.addColumn("Fecha Devolución");
        modelo.addColumn("Devuelto");

        tabla =
                new JTable(modelo);

        tabla.setRowHeight(28);

        tabla.getTableHeader().setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        add(
                new JScrollPane(tabla),
                BorderLayout.CENTER
        );

        JPanel panelBotones =
                new JPanel();

        btnDevolver =
                new JButton("Devolver");

        btnCargar =
                new JButton("Cargar");

        btnDevolver.setBackground(
                new Color(255, 152, 0)
        );

        btnDevolver.setForeground(
                Color.WHITE
        );

        btnCargar.setBackground(
                new Color(33, 150, 243)
        );

        btnCargar.setForeground(
                Color.WHITE
        );

        panelBotones.add(btnDevolver);
        panelBotones.add(btnCargar);

        add(
                panelBotones,
                BorderLayout.SOUTH
        );

        cargarEstudiantes();
        cargarLibros();
        cargarPrestamos();

        btnRegistrar.addActionListener(
                e -> registrarPrestamo()
        );

        btnDevolver.addActionListener(
                e -> devolverLibro()
        );

        btnCargar.addActionListener(
                e -> cargarPrestamos()
        );
    }

    private void cargarEstudiantes() {

        cboEstudiantes.removeAllItems();

        EstudianteDAO dao =
                new EstudianteDAO();

        for (Estudiante est : dao.listar()) {

            cboEstudiantes.addItem(est);
        }
    }

    private void cargarLibros() {

        cboLibros.removeAllItems();

        LibroDAO dao =
                new LibroDAO();

        for (Libro libro : dao.listar()) {

            cboLibros.addItem(libro);
        }
    }
    private void cargarPrestamos() {

        modelo.setRowCount(0);

        PrestamoDAO dao = new PrestamoDAO();

        List<Prestamo> lista = dao.listar();

        for (Prestamo p : lista) {

            modelo.addRow(new Object[]{
                    p.getId(),
                    p.getIdEstudiante(),
                    p.getIdLibro(),
                    p.getFechaPrestamo(),
                    p.getFechaDevolucion(),
                    p.isDevuelto()
            });
        }
    }
    private void registrarPrestamo() {

        try {

            Estudiante estudiante =
                    (Estudiante) cboEstudiantes.getSelectedItem();

            Libro libro =
                    (Libro) cboLibros.getSelectedItem();

            if (estudiante == null || libro == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Seleccione estudiante y libro"
                );

                return;
            }

            Prestamo prestamo =
                    new Prestamo();

            prestamo.setIdEstudiante(
                    estudiante.getId()
            );

            prestamo.setIdLibro(
                    libro.getId()
            );

            prestamo.setFechaPrestamo(
                    Date.valueOf(LocalDate.now())
            );

            prestamo.setFechaDevolucion(
                    Date.valueOf(
                            LocalDate.now().plusDays(7)
                    )
            );

            prestamo.setDevuelto(false);

            PrestamoDAO dao =
                    new PrestamoDAO();

            PrestamoThread hilo =
                    new PrestamoThread(
                            dao,
                            prestamo
                    );

            hilo.start();

            JOptionPane.showMessageDialog(
                    this,
                    "Préstamo registrado"
            );

            cargarPrestamos();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al registrar préstamo"
            );

            e.printStackTrace();
        }
    }
    private void devolverLibro() {

        int fila =
                tabla.getSelectedRow();

        if (fila == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un préstamo"
            );

            return;
        }

        int idPrestamo =
                Integer.parseInt(
                        modelo.getValueAt(
                                fila,
                                0
                        ).toString()
                );

        PrestamoDAO dao =
                new PrestamoDAO();

        if (dao.devolverLibro(idPrestamo)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Libro devuelto"
            );

            cargarPrestamos();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al devolver"
            );
        }
    }
}