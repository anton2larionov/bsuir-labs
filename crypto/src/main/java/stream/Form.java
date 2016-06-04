package stream;

import stream.lfsr.Geffe;
import stream.lfsr.LFSR;
import stream.lfsr.StringRegister;
import stream.rc4.RC4;
import stream.rc4.StringRC4Key;

import javax.swing.*;

import java.awt.event.ActionListener;

import static common.JFileChooserProducer.getDialog;

public class Form extends JFrame {
    private JPanel rootPanel;
    private JButton codeButton;
    private JTextField inFile;
    private JTextField outFile;
    private JButton openButton;
    private JButton saveButton;
    private JComboBox methods;
    private JCheckBox printKey;

    private JTextField lfsr1, lfsr2, lfsr3;
    private JTextField rc4;

    public Form() {
        setTitle("Потоковые алгоритмы");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setContentPane(rootPanel);

        openButton.addActionListener(getDialog(this, inFile, JFileChooser.OPEN_DIALOG));
        saveButton.addActionListener(getDialog(this, outFile, JFileChooser.SAVE_DIALOG));

        codeButton.addActionListener(getActionListener());

        pack();
        setVisible(true);
    }

    private ActionListener getActionListener() {
        return e -> {
            try {
                FilesUtil.crypt(
                        inFile.getText(), outFile.getText(),
                        getKeyIterator(), printKey.isSelected());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex, "Ошибка", JOptionPane.ERROR_MESSAGE);
                return;
            }
            JOptionPane.showMessageDialog(this, "Завершено успешно");
        };
    }

    private KeyIterator getKeyIterator() {
        switch (methods.getSelectedIndex()) {
            case 0: return new LFSR(new StringRegister(lfsr1.getText()));
            case 1: return new Geffe(
                    new LFSR(new StringRegister(lfsr1.getText())),
                    new LFSR(new StringRegister(lfsr2.getText())),
                    new LFSR(new StringRegister(lfsr3.getText()))
            );
            case 2: return new RC4(new StringRC4Key(rc4.getText()));
        }
        return () -> 0;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Form::new);
    }
}