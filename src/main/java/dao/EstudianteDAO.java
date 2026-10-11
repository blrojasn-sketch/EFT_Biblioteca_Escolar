package dao;

import model.Estudiante;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstudianteDAO {

    private Connection conn;

    public EstudianteDAO() {
        conn = DatabaseConnection
                .getInstance()
                .getConnection();
    }

    public boolean insertar(Estudiante estudiante) {

        try {

            String sql =
                    "INSERT INTO estudiantes(nombre,rut,curso,correo) " +
                            "VALUES(?,?,?,?)";

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setString(1, estudiante.getNombre());
            ps.setString(2, estudiante.getRut());
            ps.setString(3, estudiante.getCurso());
            ps.setString(4, estudiante.getCorreo());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<Estudiante> listar() {

        List<Estudiante> lista =
                new ArrayList<>();

        try {

            String sql =
                    "SELECT * FROM estudiantes";

            Statement st =
                    conn.createStatement();

            ResultSet rs =
                    st.executeQuery(sql);

            while(rs.next()) {

                Estudiante est =
                        new Estudiante();

                est.setId(
                        rs.getInt("id")
                );

                est.setNombre(
                        rs.getString("nombre")
                );

                est.setRut(
                        rs.getString("rut")
                );

                est.setCurso(
                        rs.getString("curso")
                );

                est.setCorreo(
                        rs.getString("correo")
                );

                lista.add(est);
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    public Estudiante buscarPorId(int id) {

        try {

            String sql =
                    "SELECT * FROM estudiantes WHERE id=?";

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setInt(1, id);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                Estudiante estudiante =
                        new Estudiante();

                estudiante.setId(rs.getInt("id"));
                estudiante.setNombre(rs.getString("nombre"));
                estudiante.setRut(rs.getString("rut"));
                estudiante.setCurso(rs.getString("curso"));
                estudiante.setCorreo(rs.getString("correo"));

                return estudiante;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean actualizar(
            Estudiante estudiante) {

        try {

            String sql =
                    "UPDATE estudiantes SET " +
                            "nombre=?, rut=?, curso=?, correo=? " +
                            "WHERE id=?";

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setString(
                    1,
                    estudiante.getNombre());

            ps.setString(
                    2,
                    estudiante.getRut());

            ps.setString(
                    3,
                    estudiante.getCurso());

            ps.setString(
                    4,
                    estudiante.getCorreo());

            ps.setInt(
                    5,
                    estudiante.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean eliminar(int id) {

        try {

            String sql =
                    "DELETE FROM estudiantes WHERE id=?";

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