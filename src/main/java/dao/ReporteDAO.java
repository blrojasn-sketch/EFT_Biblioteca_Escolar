package dao;

import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ReporteDAO {

    private Connection conn;

    public ReporteDAO() {
        conn = DatabaseConnection
                .getInstance()
                .getConnection();
    }

    // LIBROS MÁS PRESTADOS
    public String librosMasPrestados() {

        StringBuilder sb = new StringBuilder();

        try {

            String sql =
                    "SELECT l.titulo, COUNT(*) cantidad " +
                            "FROM prestamos p " +
                            "INNER JOIN libros l ON p.id_libro = l.id " +
                            "GROUP BY l.titulo " +
                            "ORDER BY cantidad DESC";

            Statement st = conn.createStatement();

            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {

                sb.append("Libro: ")
                        .append(rs.getString("titulo"))
                        .append(" | Cantidad: ")
                        .append(rs.getInt("cantidad"))
                        .append("\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    // HISTORIAL POR ESTUDIANTE
    public String historialEstudiantes() {

        StringBuilder sb = new StringBuilder();

        try {

            String sql =
                    "SELECT e.nombre, l.titulo, p.fecha_prestamo " +
                            "FROM prestamos p " +
                            "INNER JOIN estudiantes e " +
                            "ON p.id_estudiante = e.id " +
                            "INNER JOIN libros l " +
                            "ON p.id_libro = l.id";

            Statement st = conn.createStatement();

            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {

                sb.append("Estudiante: ")
                        .append(rs.getString("nombre"))
                        .append(" | Libro: ")
                        .append(rs.getString("titulo"))
                        .append(" | Fecha: ")
                        .append(rs.getDate("fecha_prestamo"))
                        .append("\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    // PRESTAMOS ACTIVOS
    public String prestamosActivos() {

        StringBuilder sb = new StringBuilder();

        try {

            String sql =
                    "SELECT * FROM prestamos " +
                            "WHERE devuelto = 0";

            Statement st = conn.createStatement();

            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {

                sb.append("Préstamo ID: ")
                        .append(rs.getInt("id"))
                        .append(" | Estudiante: ")
                        .append(rs.getInt("id_estudiante"))
                        .append(" | Libro: ")
                        .append(rs.getInt("id_libro"))
                        .append("\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return sb.toString();
    }
}