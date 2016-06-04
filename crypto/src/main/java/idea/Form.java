package idea;

import javax.swing.*;
import java.awt.event.ActionListener;

import static common.JFileChooserProducer.getDialog;

public class Form extends JFrame {
    private JPanel rootPanel;
    private JButton codeButton;
    private JButton decodeButton;
    private JTextField inFile;
    private JTextField outFile;
    private JButton openButton;
    private JButton saveButton;
    private JTextField key;

    public Form() {
        setTitle("Алгоритм IDEA");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setContentPane(rootPanel);

        openButton.addActionListener(getDialog(this, inFile, JFileChooser.OPEN_DIALOG));
        saveButton.addActionListener(getDialog(this, outFile, JFileChooser.SAVE_DIALOG));

        codeButton.addActionListener(getActionListener(true));
        decodeButton.addActionListener(getActionListener(false));

        pack();
        setVisible(true);
    }

    private ActionListener getActionListener(boolean crypt) {
        return e -> {
            try {
                int[] subKeys = new SubKeys(new StringKey(key.getText())).getKeys(crypt);
                FilesUtil.cryptByIdea(inFile.getText(), outFile.getText(), new IDEA(subKeys));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex, "Ошибка", JOptionPane.ERROR_MESSAGE);
                return;
            }
            JOptionPane.showMessageDialog(this, "Завершено успешно");
        };
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Form::new);
    }
}
