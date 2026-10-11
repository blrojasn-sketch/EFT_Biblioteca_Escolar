package view;

import dao.LibroDAO;
import model.Libro;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PanelLibros extends JPanel {

    private JTable tabla;
    private DefaultTableModel modelo;

    private JButton btnAgregar;
    private JButton btnModificar;
    private JButton btnEliminar;
    private JButton btnCargar;

    public PanelLibros() {

        setLayout(new BorderLayout());

        modelo = new DefaultTableModel();

        modelo.addColumn("ID");
        modelo.addColumn("Título");
        modelo.addColumn("Autor");
        modelo.addColumn("ISBN");
        modelo.addColumn("Editorial");
        modelo.addColumn("Stock");

        tabla = new JTable(modelo);

        add(new JScrollPane(tabla), BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();

        btnAgregar = new JButton("Agregar");
        btnModificar = new JButton("Modificar");
        btnEliminar = new JButton("Eliminar");
        btnCargar = new JButton("Cargar Libros");

        panelBotones.add(btnAgregar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnCargar);

        add(panelBotones, BorderLayout.SOUTH);

        btnCargar.addActionListener(
                e -> cargarLibros()
        );

        btnAgregar.addActionListener(
                e -> agregarLibro()
        );

        btnModificar.addActionListener(
                e -> modificarLibro()
        );

        btnEliminar.addActionListener(
                e -> eliminarLibro()
        );

        cargarLibros();
    }

    private void cargarLibros() {

        modelo.setRowCount(0);

        LibroDAO dao = new LibroDAO();

        List<Libro> lista = dao.listar();

        for (Libro libro : lista) {

            modelo.addRow(new Object[]{
                    libro.getId(),
                    libro.getTitulo(),
                    libro.getAutor(),
                    libro.getIsbn(),
                    libro.getEditorial(),
                    libro.getStock()
            });
        }
    }

    private void agregarLibro() {

        try {

            String titulo =
                    JOptionPane.showInputDialog(
                            this,
                            "Título:"
                    );

            String autor =
                    JOptionPane.showInputDialog(
                            this,
                            "Autor:"
                    );

            String isbn =
                    JOptionPane.showInputDialog(
                            this,
                            "ISBN:"
                    );

            String editorial =
                    JOptionPane.showInputDialog(
                            this,
                            "Editorial:"
                    );

            String stockTexto =
                    JOptionPane.showInputDialog(
                            this,
                            "Stock:"
                    );

            int stock =
                    Integer.parseInt(stockTexto);

            Libro libro =
                    new Libro();

            libro.setTitulo(titulo);
            libro.setAutor(autor);
            libro.setIsbn(isbn);
            libro.setEditorial(editorial);
            libro.setStock(stock);

            libro.setIdCategoria(1);

            LibroDAO dao =
                    new LibroDAO();

            if (dao.insertar(libro)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Libro agregado correctamente"
                );

                cargarLibros();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Error al guardar"
                );
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "El stock debe ser un número"
            );
        }
    }

    private void modificarLibro() {

        int fila = tabla.getSelectedRow();

        if (fila == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un libro"
            );

            return;
        }

        int id = Integer.parseInt(
                modelo.getValueAt(fila, 0).toString()
        );

        LibroDAO dao = new LibroDAO();

        Libro libro = dao.buscarPorId(id);

        if (libro == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Libro no encontrado"
            );

            return;
        }

        String titulo = JOptionPane.showInputDialog(
                this,
                "Título:",
                libro.getTitulo()
        );

        String autor = JOptionPane.showInputDialog(
                this,
                "Autor:",
                libro.getAutor()
        );

        String isbn = JOptionPane.showInputDialog(
                this,
                "ISBN:",
                libro.getIsbn()
        );

        String editorial = JOptionPane.showInputDialog(
                this,
                "Editorial:",
                libro.getEditorial()
        );

        int stock = Integer.parseInt(
                JOptionPane.showInputDialog(
                        this,
                        "Stock:",
                        libro.getStock()
                )
        );

        libro.setTitulo(titulo);
        libro.setAutor(autor);
        libro.setIsbn(isbn);
        libro.setEditorial(editorial);
        libro.setStock(stock);

        if (dao.actualizar(libro)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Libro actualizado correctamente"
            );

            cargarLibros();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al actualizar"
            );
        }
    }

    private void eliminarLibro() {

        int fila = tabla.getSelectedRow();

        if (fila == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un libro"
            );

            return;
        }

        int id = Integer.parseInt(
                modelo.getValueAt(fila, 0).toString()
        );

        int opcion = JOptionPane.showConfirmDialog(
                this,
                "¿Desea eliminar el libro seleccionado?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION
        );

        if (opcion == JOptionPane.YES_OPTION) {

            LibroDAO dao = new LibroDAO();

            if (dao.eliminar(id)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Libro eliminado correctamente"
                );

                cargarLibros();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Error al eliminar"
                );
            }
        }
    }
}