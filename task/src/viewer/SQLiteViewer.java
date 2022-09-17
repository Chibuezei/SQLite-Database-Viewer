package viewer;

import javax.swing.*;
import java.awt.*;

public class SQLiteViewer extends JFrame {

    public SQLiteViewer() {
        super("SQLite Viewer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 900);
        setLayout(new BorderLayout());
        setResizable(false);
        components();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    private void components() {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        topPanel.setBackground(Color.GREEN);
        topPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        add(topPanel, BorderLayout.NORTH);

        JTextField fileName = new JTextField();
        fileName.setName("FileNameTextField");
        forceSize(fileName, 420, 35);
        topPanel.add(fileName);

        JButton saveButton = new JButton("Open");
        saveButton.setName("OpenFileButton");
//        saveButton.addActionListener(e -> openAction());
        topPanel.add(saveButton);
        topPanel.setVisible(true);


    }
    private static void forceSize(JComponent component, int width, int height) {
        Dimension d = new Dimension(width, height);
        component.setMinimumSize(d);
        component.setMaximumSize(d);
        component.setPreferredSize(d);
    }
    }
