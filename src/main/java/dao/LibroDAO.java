package dao;

import model.Libro;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibroDAO {

    private Connection conn;

    public LibroDAO() {
        conn = DatabaseConnection
                .getInstance()
                .getConnection();
    }

    // INSERTAR
    public boolean insertar(Libro libro) {

        try {

            String sql =
                    "INSERT INTO libros " +
                            "(titulo, autor, isbn, editorial, stock, id_categoria) " +
                            "VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setString(1, libro.getTitulo());
            ps.setString(2, libro.getAutor());
            ps.setString(3, libro.getIsbn());
            ps.setString(4, libro.getEditorial());
            ps.setInt(5, libro.getStock());
            ps.setInt(6, libro.getIdCategoria());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // LISTAR
    public List<Libro> listar() {

        List<Libro> lista = new ArrayList<>();

        try {

            String sql = "SELECT * FROM libros";

            Statement st = conn.createStatement();

            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {

                Libro libro = new Libro();

                libro.setId(rs.getInt("id"));
                libro.setTitulo(rs.getString("titulo"));
                libro.setAutor(rs.getString("autor"));
                libro.setIsbn(rs.getString("isbn"));
                libro.setEditorial(rs.getString("editorial"));
                libro.setStock(rs.getInt("stock"));
                libro.setIdCategoria(rs.getInt("id_categoria"));

                lista.add(libro);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // BUSCAR POR ID
    public Libro buscarPorId(int id) {

        try {

            String sql =
                    "SELECT * FROM libros WHERE id = ?";

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Libro libro = new Libro();

                libro.setId(rs.getInt("id"));
                libro.setTitulo(rs.getString("titulo"));
                libro.setAutor(rs.getString("autor"));
                libro.setIsbn(rs.getString("isbn"));
                libro.setEditorial(rs.getString("editorial"));
                libro.setStock(rs.getInt("stock"));
                libro.setIdCategoria(rs.getInt("id_categoria"));

                return libro;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // ACTUALIZAR
    public boolean actualizar(Libro libro) {

        try {

            String sql =
                    "UPDATE libros SET " +
                            "titulo=?, " +
                            "autor=?, " +
                            "isbn=?, " +
                            "editorial=?, " +
                            "stock=?, " +
                            "id_categoria=? " +
                            "WHERE id=?";

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setString(1, libro.getTitulo());
            ps.setString(2, libro.getAutor());
            ps.setString(3, libro.getIsbn());
            ps.setString(4, libro.getEditorial());
            ps.setInt(5, libro.getStock());
            ps.setInt(6, libro.getIdCategoria());
            ps.setInt(7, libro.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // ELIMINAR
    public boolean eliminar(int id) {

        try {

            String sql =
                    "DELETE FROM libros WHERE id = ?";

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}