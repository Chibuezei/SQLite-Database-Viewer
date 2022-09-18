package viewer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SQLiteViewer extends JFrame {
    private static JTextField databaseName;
    private static JComboBox comboBox;
    private static ArrayList<String> listOfTables;

    private static JTextArea queryTextArea;


    public SQLiteViewer() {
        super("SQLite Viewer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 900);
        setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
//        setResizable(false);
        components();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void components() {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        topPanel.setBackground(Color.GREEN);
        topPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        add(topPanel);

        databaseName = new JTextField();
        databaseName.setName("FileNameTextField");
        forceSize(databaseName, 420, 35);
        topPanel.add(databaseName);

        JButton openButton = new JButton("Open");
        openButton.setName("OpenFileButton");
        openButton.addActionListener(e -> connectToDatabase());
        topPanel.add(openButton);


        comboBox = new JComboBox();
        comboBox.setName("TablesComboBox");
        forceSize(comboBox, 500, 30);
        comboBox.addActionListener(actionEvent -> setComboBox());
        add(comboBox);

        queryTextArea = new JTextArea();
        queryTextArea.setName("QueryTextArea");
        forceSize(queryTextArea, 420, 35);
        add(queryTextArea);

        JButton executeButton = new JButton("Execute");
        executeButton.setName("ExecuteQueryButton");
        add(executeButton);
    }

    private static void connectToDatabase() {
        comboBox.removeAllItems();
        listOfTables = DbConnection.connect(databaseName.getText().trim());
        if (listOfTables != null) {
            listOfTables.forEach(comboBox::addItem);
        }
    }

    private static void setComboBox() {
        String table = (String) comboBox.getSelectedItem();
        queryTextArea.setText("SELECT * FROM " + table + ";");
    }

    private static void forceSize(JComponent component, int width, int height) {
        Dimension d = new Dimension(width, height);
        component.setMinimumSize(d);
        component.setMaximumSize(d);
        component.setPreferredSize(d);
    }
}
