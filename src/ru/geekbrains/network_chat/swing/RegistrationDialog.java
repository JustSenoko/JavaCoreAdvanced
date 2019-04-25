package ru.geekbrains.network_chat.swing;

        import ru.geekbrains.network_chat.Network;
        import ru.geekbrains.network_chat.authorization.AuthException;

        import javax.swing.*;
        import javax.swing.border.LineBorder;
        import java.awt.*;
        import java.io.IOException;

public class RegistrationDialog extends JDialog {

    private Network network;
    private JTextField tfUsername;
    private JTextField tfLogin;
    private JPasswordField pfPassword;
    private JPasswordField pfPasswordRepeat;
    private JLabel lbUsername;
    private JLabel lbLogin;
    private JLabel lbPassword;
    private JLabel lbPasswordRepeat;
    private JButton btnRegistration;
    private JButton btnCancel;

    private JPanel panel;
    private GridBagConstraints cs;

    private boolean registered;

    public RegistrationDialog(JDialog parent, Network network) {
        super(parent, "Логин", true);
        this.network = network;

        panel = new JPanel(new GridBagLayout());
        cs = new GridBagConstraints();

        cs.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        lbUsername = new JLabel("Имя пользователя: ");
        addComponent(lbUsername, 0, row, 1);

        tfUsername = new JTextField(20);
        addComponent(tfUsername, 1, row, 2);

        row++;

        lbLogin = new JLabel("Логин: ");
        addComponent(lbLogin, 0, row, 1);

        tfLogin = new JTextField(20);
        addComponent(tfLogin, 1, row, 2);

        row++;

        lbPassword = new JLabel("Пароль: ");
        addComponent(lbPassword, 0, row, 1);

        pfPassword = new JPasswordField(20);
        addComponent(pfPassword, 1, row, 2);

        row++;

        lbPasswordRepeat = new JLabel("Повторите пароль: ");
        panel.add(lbPasswordRepeat, cs);
        addComponent(lbPasswordRepeat, 0, row, 1);

        pfPasswordRepeat = new JPasswordField(20);
        addComponent(pfPasswordRepeat, 1, row, 2);

        panel.setBorder(new LineBorder(Color.GRAY));

        btnRegistration = new JButton("Регистрация");
        btnCancel = new JButton("Отмена");

        JPanel bp = new JPanel();

        bp.add(btnRegistration);
        btnRegistration.addActionListener(e -> {
            try {
                network.addUser(tfUsername.getText(), tfLogin.getText(), String.valueOf(pfPassword.getPassword()));
                registered = true;
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(RegistrationDialog.this,
                        "Ошибка сети",
                        "Регистрация",
                        JOptionPane.ERROR_MESSAGE);
                return;
            } catch (AuthException ex) {
                JOptionPane.showMessageDialog(RegistrationDialog.this,
                        "Ошибка регистрации",
                        "Регистрация",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            dispose();
        });

        bp.add(btnCancel);
        btnCancel.addActionListener(e -> {
            registered = false;
            dispose();
        });

        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(bp, BorderLayout.PAGE_END);

        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
    }

    private void addComponent(JComponent component, int gridx, int gridy, int gridwidth) {
        cs.gridx = gridx;
        cs.gridy = gridy;
        cs.gridwidth = gridwidth;
        panel.add(component, cs);
    }

    public boolean isRegistered() {
        return registered;
    }
}
