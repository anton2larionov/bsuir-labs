package common;

import javax.swing.*;
import java.awt.event.ActionListener;

public class JFileChooserProducer {
    public static ActionListener getDialog(JFrame frame, JTextField textField, int type) {
        return e -> {
            JFileChooser dialog = new JFileChooser();
            dialog.setFileSelectionMode(JFileChooser.FILES_ONLY);
            dialog.setDialogType(type);
            if (dialog.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
                textField.setText(dialog.getSelectedFile().getAbsolutePath());
            }
        };
    }
}
