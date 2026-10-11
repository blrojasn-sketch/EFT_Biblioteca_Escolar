package dao;

import model.Usuario;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UsuarioDAO {

    private Connection conn =
            DatabaseConnection
                    .getInstance()
                    .getConnection();

    public Usuario login(String correo,
                         String contrasena) {

        try {

            String sql =
                    "SELECT * FROM usuarios " +
                            "WHERE correo=? " +
                            "AND contrasena=?";

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ps.setString(1, correo);
            ps.setString(2, contrasena);

            ResultSet rs =
                    ps.executeQuery();

            if(rs.next()) {

                Usuario usuario =
                        new Usuario();

                usuario.setId(
                        rs.getInt("id")
                );

                usuario.setNombre(
                        rs.getString("nombre")
                );

                usuario.setRut(
                        rs.getString("rut")
                );

                usuario.setCorreo(
                        rs.getString("correo")
                );

                usuario.setContrasena(
                        rs.getString("contrasena")
                );

                usuario.setRol(
                        rs.getString("rol")
                );

                return usuario;
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;
    }
}