package dao;

import model.Prestamo;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrestamoDAO {

    private Connection conn;

    public PrestamoDAO() {

        conn = DatabaseConnection
                .getInstance()
                .getConnection();
    }

    // REGISTRAR PRÉSTAMO
    public synchronized boolean registrarPrestamo(
            Prestamo prestamo) {

        try {

            conn.setAutoCommit(false);

            String sqlPrestamo =
                    "INSERT INTO prestamos " +
                            "(id_estudiante, id_libro, fecha_prestamo, fecha_devolucion, devuelto) " +
                            "VALUES (?, ?, ?, ?, ?)";

            PreparedStatement psPrestamo =
                    conn.prepareStatement(sqlPrestamo);

            psPrestamo.setInt(
                    1,
                    prestamo.getIdEstudiante());

            psPrestamo.setInt(
                    2,
                    prestamo.getIdLibro());

            psPrestamo.setDate(
                    3,
                    prestamo.getFechaPrestamo());

            psPrestamo.setDate(
                    4,
                    prestamo.getFechaDevolucion());

            psPrestamo.setBoolean(
                    5,
                    false);

            psPrestamo.executeUpdate();

            String sqlStock =
                    "UPDATE libros " +
                            "SET stock = stock - 1 " +
                            "WHERE id = ? AND stock > 0";

            PreparedStatement psStock =
                    conn.prepareStatement(sqlStock);

            psStock.setInt(
                    1,
                    prestamo.getIdLibro());

            int filas =
                    psStock.executeUpdate();

            if (filas == 0) {

                conn.rollback();
                return false;
            }

            conn.commit();

            return true;

        } catch (Exception e) {

            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            e.printStackTrace();
        }

        return false;
    }

    // LISTAR
    public List<Prestamo> listar() {

        List<Prestamo> lista =
                new ArrayList<>();

        try {

            String sql =
                    "SELECT * FROM prestamos";

            Statement st =
                    conn.createStatement();

            ResultSet rs =
                    st.executeQuery(sql);

            while (rs.next()) {

                Prestamo prestamo =
                        new Prestamo();

                prestamo.setId(
                        rs.getInt("id"));

                prestamo.setIdEstudiante(
                        rs.getInt("id_estudiante"));

                prestamo.setIdLibro(
                        rs.getInt("id_libro"));

                prestamo.setFechaPrestamo(
                        rs.getDate("fecha_prestamo"));

                prestamo.setFechaDevolucion(
                        rs.getDate("fecha_devolucion"));

                prestamo.setDevuelto(
                        rs.getBoolean("devuelto"));

                lista.add(prestamo);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    // BUSCAR POR ID
    public Prestamo buscarPorId(int id) {

        try {

            String sql =
                    "SELECT * FROM prestamos WHERE id=?";

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setInt(1, id);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                Prestamo prestamo =
                        new Prestamo();

                prestamo.setId(
                        rs.getInt("id"));

                prestamo.setIdEstudiante(
                        rs.getInt("id_estudiante"));

                prestamo.setIdLibro(
                        rs.getInt("id_libro"));

                prestamo.setFechaPrestamo(
                        rs.getDate("fecha_prestamo"));

                prestamo.setFechaDevolucion(
                        rs.getDate("fecha_devolucion"));

                prestamo.setDevuelto(
                        rs.getBoolean("devuelto"));

                return prestamo;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // DEVOLVER LIBRO
    public synchronized boolean devolverLibro(
            int idPrestamo) {

        try {

            conn.setAutoCommit(false);

            Prestamo prestamo =
                    buscarPorId(idPrestamo);

            if (prestamo == null) {
                return false;
            }

            String sqlPrestamo =
                    "UPDATE prestamos " +
                            "SET devuelto = true " +
                            "WHERE id=?";

            PreparedStatement psPrestamo =
                    conn.prepareStatement(sqlPrestamo);

            psPrestamo.setInt(
                    1,
                    idPrestamo);

            psPrestamo.executeUpdate();

            String sqlStock =
                    "UPDATE libros " +
                            "SET stock = stock + 1 " +
                            "WHERE id=?";

            PreparedStatement psStock =
                    conn.prepareStatement(sqlStock);

            psStock.setInt(
                    1,
                    prestamo.getIdLibro());

            psStock.executeUpdate();

            conn.commit();

            return true;

        } catch (Exception e) {

            try {
                conn.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            e.printStackTrace();
        }

        return false;
    }
}