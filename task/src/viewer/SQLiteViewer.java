package viewer;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SQLiteViewer extends JFrame {
    private static JTextField databaseName;
    private static JComboBox comboBox;

    private static JTextArea queryTextArea;
    private static JTable table;
    private static JButton executeButton;


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
        queryTextArea.setEnabled(false);
        add(queryTextArea);

         executeButton = new JButton("Execute");
        executeButton.setName("ExecuteQueryButton");
        executeButton.addActionListener(e -> runQuery());
        executeButton.setEnabled(false);
        add(executeButton);

        MyTableModel tableModel = new MyTableModel();

        table = new JTable(tableModel);
        table.setName("Table");
        tableModel.addTableModelListener(new CustomListener()); //Adds the TableModelListener


        JScrollPane sp = new JScrollPane(table);
        add(sp);

    }

    private static void connectToDatabase() {
        comboBox.removeAllItems();
        try {
            ArrayList<ArrayList<String>> columnAndData = DbConnection.runQuery(databaseName.getText().trim());
            List<ArrayList<String>> listOfTables = columnAndData.subList(1, columnAndData.size());
            ArrayList<String> Tables = new ArrayList<>();
            for (List<String> list : listOfTables) {
                Tables.add(list.get(0));
            }
            Tables.forEach(comboBox::addItem);
            executeButton.setEnabled(true);
            queryTextArea.setEnabled(true);

        }catch (RuntimeException e){
            queryTextArea.setEnabled(false);
            executeButton.setEnabled(false);
            JOptionPane.showMessageDialog(new Frame(), "File doesn't exist!");

        }

    }

    private static void runQuery() {
        try {
        String query = queryTextArea.getText();
        ArrayList<ArrayList<String>> columnAndData = DbConnection.runQuery(databaseName.getText().trim(), query);
        List<ArrayList<String>> data = columnAndData.subList(1, columnAndData.size());

        MyTableModel model = new MyTableModel(columnAndData.get(0), data);
        table.setModel(model);
        model.fireTableDataChanged();
        }catch (RuntimeException e){
            JOptionPane.showMessageDialog(new Frame(), e.getMessage());

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

class MyTableModel extends AbstractTableModel {
    ArrayList<String> columns;
    List<ArrayList<String>> data;


    public MyTableModel() {
        this.columns = new ArrayList<>();
        this.data = new ArrayList<>();
    }

    public MyTableModel(ArrayList<String> columns, List<ArrayList<String>> data) {
        this.data = data;
        this.columns = columns;
//        System.out.println(columns + " " + data);
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columns.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data.get(rowIndex).get(columnIndex);
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columns.get(columnIndex);
    }

}

class CustomListener implements TableModelListener {
    @Override
    public void tableChanged(TableModelEvent e) {
        System.out.println("Table Updated!");
    }
}