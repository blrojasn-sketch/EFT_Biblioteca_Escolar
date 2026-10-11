package view;

import dao.UsuarioDAO;
import model.Usuario;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private JTextField txtCorreo;
    private JPasswordField txtClave;
    private JButton btnIngresar;

    public LoginFrame() {

        setTitle("Biblioteca Escolar");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setBackground(new Color(245, 245, 245));
        panelPrincipal.setLayout(new BorderLayout());

        JLabel titulo = new JLabel(
                "📚 Biblioteca Escolar",
                SwingConstants.CENTER
        );

        titulo.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        26
                )
        );

        titulo.setForeground(
                new Color(
                        25,
                        118,
                        210
                )
        );

        panelPrincipal.add(
                titulo,
                BorderLayout.NORTH
        );

        JPanel formulario = new JPanel();

        formulario.setBackground(
                new Color(245, 245, 245)
        );

        formulario.setLayout(
                new GridLayout(3, 2, 10, 10)
        );

        formulario.setBorder(
                BorderFactory.createEmptyBorder(
                        40,
                        50,
                        40,
                        50
                )
        );

        formulario.add(
                new JLabel("Correo:")
        );

        txtCorreo = new JTextField();
        formulario.add(txtCorreo);

        formulario.add(
                new JLabel("Contraseña:")
        );

        txtClave = new JPasswordField();
        formulario.add(txtClave);

        formulario.add(new JLabel());

        btnIngresar =
                new JButton("Ingresar");

        btnIngresar.setBackground(
                new Color(
                        25,
                        118,
                        210
                )
        );

        btnIngresar.setForeground(
                Color.WHITE
        );

        btnIngresar.setFocusPainted(false);

        btnIngresar.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        formulario.add(btnIngresar);

        panelPrincipal.add(
                formulario,
                BorderLayout.CENTER
        );

        add(panelPrincipal);

        btnIngresar.addActionListener(
                e -> login()
        );
    }

    private void login() {

        String correo =
                txtCorreo.getText();

        String clave =
                new String(
                        txtClave.getPassword()
                );

        UsuarioDAO dao =
                new UsuarioDAO();

        Usuario usuario =
                dao.login(
                        correo,
                        clave
                );

        if (usuario != null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Bienvenido " +
                            usuario.getNombre()
            );

            new PrincipalFrame(usuario);

            dispose();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Credenciales incorrectas"
            );
        }
    }
}