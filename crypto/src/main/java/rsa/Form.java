package rsa;

import javax.swing.*;

import static common.JFileChooserProducer.getDialog;

public class Form extends JFrame {
    private JPanel rootPanel;
    private JTextField inFile;
    private JButton openButton;
    private JTextField outFile;
    private JButton saveButton;
    private JTextField p, q;
    private JTextField r, d;
    private JButton run1, run2;

    public Form() {
        setTitle("Алгоритм RSA");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setContentPane(rootPanel);

        openButton.addActionListener(getDialog(this, inFile, JFileChooser.OPEN_DIALOG));
        saveButton.addActionListener(getDialog(this, outFile, JFileChooser.SAVE_DIALOG));

        run1.addActionListener(e -> {
            try {
                RsaKey rsaKey = new RsaKey(getInt(p), getInt(q));

                new RsaEncrypt(rsaKey.getR(), rsaKey.getE())
                        .encryptFile(inFile.getText(), outFile.getText());

                JOptionPane.showMessageDialog(this, rsaKey, "Завершено успешно", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex, "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        });

        run2.addActionListener(e -> {
            try {
                new RsaDecrypt(getInt(r), getInt(d)).decryptFile(inFile.getText(), outFile.getText());
                JOptionPane.showMessageDialog(this, "Завершено успешно");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex, "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        });

        pack();
        setVisible(true);
    }

    private static int getInt(JTextField field) {
        return Integer.parseInt(field.getText().trim());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Form::new);
    }
}
