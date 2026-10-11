package view;

import dao.EstudianteDAO;
import model.Estudiante;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PanelEstudiantes extends JPanel {

    private JTable tabla;
    private DefaultTableModel modelo;

    private JButton btnAgregar;
    private JButton btnModificar;
    private JButton btnEliminar;
    private JButton btnCargar;

    public PanelEstudiantes() {

        setLayout(new BorderLayout());

        JLabel titulo = new JLabel(
                "Gestión de Estudiantes",
                SwingConstants.CENTER
        );

        titulo.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        20
                )
        );

        add(titulo, BorderLayout.NORTH);

        modelo = new DefaultTableModel();

        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("RUT");
        modelo.addColumn("Curso");
        modelo.addColumn("Correo");

        tabla = new JTable(modelo);

        add(
                new JScrollPane(tabla),
                BorderLayout.CENTER
        );

        JPanel panelBotones = new JPanel();

        btnAgregar = new JButton("Agregar");
        btnModificar = new JButton("Modificar");
        btnEliminar = new JButton("Eliminar");
        btnCargar = new JButton("Cargar");

        panelBotones.add(btnAgregar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnCargar);

        add(panelBotones, BorderLayout.SOUTH);

        btnCargar.addActionListener(
                e -> cargarEstudiantes()
        );

        btnAgregar.addActionListener(
                e -> agregarEstudiante()
        );

        btnModificar.addActionListener(
                e -> modificarEstudiante()
        );

        btnEliminar.addActionListener(
                e -> eliminarEstudiante()
        );

        cargarEstudiantes();
    }

    private void cargarEstudiantes() {

        modelo.setRowCount(0);

        EstudianteDAO dao = new EstudianteDAO();

        List<Estudiante> lista = dao.listar();

        for (Estudiante est : lista) {

            modelo.addRow(new Object[]{
                    est.getId(),
                    est.getNombre(),
                    est.getRut(),
                    est.getCurso(),
                    est.getCorreo()
            });
        }
    }

    private void agregarEstudiante() {

        String nombre =
                JOptionPane.showInputDialog("Nombre");

        String rut =
                JOptionPane.showInputDialog("RUT");

        String curso =
                JOptionPane.showInputDialog("Curso");

        String correo =
                JOptionPane.showInputDialog("Correo");

        Estudiante estudiante =
                new Estudiante();

        estudiante.setNombre(nombre);
        estudiante.setRut(rut);
        estudiante.setCurso(curso);
        estudiante.setCorreo(correo);

        EstudianteDAO dao =
                new EstudianteDAO();

        if (dao.insertar(estudiante)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Estudiante agregado"
            );

            cargarEstudiantes();
        }
    }

    private void modificarEstudiante() {

        JOptionPane.showMessageDialog(
                this,
                "Modificar pendiente"
        );
    }

    private void eliminarEstudiante() {

        int fila =
                tabla.getSelectedRow();

        if (fila == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un estudiante"
            );

            return;
        }

        int id = Integer.parseInt(
                modelo.getValueAt(
                        fila,
                        0
                ).toString()
        );

        EstudianteDAO dao =
                new EstudianteDAO();

        if (dao.eliminar(id)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Estudiante eliminado"
            );

            cargarEstudiantes();
        }
    }
}