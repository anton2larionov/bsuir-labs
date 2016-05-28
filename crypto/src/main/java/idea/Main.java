package idea;

import javax.swing.*;

public class Main extends JFrame {
    private JPanel rootPanel;
    private JButton codeButton;
    private JButton decodeButton;
    private JTextField inFile;
    private JTextField outFile;
    private JButton openButton;
    private JButton saveButton;
    private JTextField key;

    public Main() {
        setTitle("Алгоритм IDEA");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setContentPane(rootPanel);

        openButton.addActionListener(e -> {
            JFileChooser dialog = new JFileChooser();
            dialog.setFileSelectionMode(JFileChooser.FILES_ONLY);
            dialog.setDialogType(JFileChooser.OPEN_DIALOG);
            if (dialog.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                inFile.setText(dialog.getSelectedFile().getAbsolutePath());
            }
        });

        saveButton.addActionListener(e -> {
            JFileChooser dialog = new JFileChooser();
            dialog.setFileSelectionMode(JFileChooser.FILES_ONLY);
            dialog.setDialogType(JFileChooser.SAVE_DIALOG);

            if (dialog.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                outFile.setText(dialog.getSelectedFile().getAbsolutePath());
            }
        });

        codeButton.addActionListener(e -> {
            try {
                int[] subKeys = new SubKeys(new StringKey(key.getText())).getKeys(true);
                FileEncryption.cryptFile(inFile.getText(), outFile.getText(), new Idea(subKeys));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex, "Ошибка", JOptionPane.ERROR_MESSAGE);
                return;
            }
            JOptionPane.showMessageDialog(this, "Завершено успешно");
        });

        decodeButton.addActionListener(e -> {
            try {
                int[] subKeys = new SubKeys(new StringKey(key.getText())).getKeys(false);
                FileEncryption.decryptFile(inFile.getText(), outFile.getText(), new Idea(subKeys));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex, "Ошибка", JOptionPane.ERROR_MESSAGE);
                return;
            }
            JOptionPane.showMessageDialog(this, "Завершено успешно");
        });

        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}
