
package com.SQLDatabase;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import org.omg.CORBA.INTERNAL;



public class AddTable extends javax.swing.JFrame {

    List<JTextField> textList;
    List<JComboBox> comboList;
    List<JCheckBox> isPrimary;
    List<JCheckBox> isForeign;
    List<JComboBox> referenceTables;
    List<JComboBox> referenceKey;
    List<JCheckBox> isFirst;
    
    ResultSet rsset;
    DatabaseConnection db_con;
    DatabaseTable db_tables;

    DefaultComboBoxModel model;
    
    JOptionPane popup = new JOptionPane();
    JPanel holder;
    JScrollPane entryPanel;
    
    public AddTable() {
        initComponents();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        Action action = new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                removeFields();
                addFields();
            }
        };
        
        ColNumberTextField.addActionListener( action );
    }
    
    public void removeFields(){
        
        if(entryPanel != null){
            this.getContentPane().remove(entryPanel);
            textList.clear();
            comboList.clear();
        }
    }
    
    public void addFields(){
        
        
        textList = new ArrayList<>();
        comboList = new ArrayList<>();
        isPrimary = new ArrayList<>();
        isFirst = new ArrayList<>();
        isForeign = new ArrayList<>();
        referenceTables = new ArrayList<>();
        referenceKey = new ArrayList<>();
        
       try{
            
            db_con = new DatabaseConnection();
            db_tables = new DatabaseTable(db_con);
            
            
            
            String tableNames[] = new String[db_tables.getTableNum()];
        
            tableNames = db_tables.getTableNames();
            String colNames[];

            rsset = db_con.getConnection().createStatement().executeQuery("SELECT * FROM " + tableNames[0] + ";");
            ResultSetMetaData tableCols = rsset.getMetaData();
            
            colNames = new String[tableCols.getColumnCount()];
            
            for(int i = 0; i < tableCols.getColumnCount(); i++){
                
                colNames[i] = tableCols.getColumnName(i + 1);
            }
            
            System.out.println(Integer.parseInt(ColNumberTextField.getText()));
            
            holder = new JPanel();
            GridLayout gridLay = new GridLayout(0,9);
            holder.setLayout(gridLay);
            
            for(int i = 0; i < Integer.parseInt(ColNumberTextField.getText()); i++){

                JTextField coltext = new JTextField();
                JCheckBox isPri = new JCheckBox("primary");
                JCheckBox isFi = new JCheckBox("unique");
                JCheckBox isFor = new JCheckBox("foreign");
                JLabel references = new JLabel("References:");
                JLabel as = new JLabel(" as:");
                
                String choiceStr[] = {"INT", "CHAR(255)", "VARCHAR(255)", "TINYTEXT", "TEXT", "BLOB", "MEDIUMTEXT", "LONGTEXT", "MEDIUMBLOB", "LONGBLOB", "ENUM", "SET", "BIGINT", "FLOAT", "DOUBLE", "DECIMAL", "DATE", "DATETIME", "TIMESTAMP", "TIME", "YEAR"};
                
                JComboBox tableReference = new JComboBox(tableNames);
                JComboBox columnReference = new JComboBox(colNames);
                tableReference.setEnabled(false);
                columnReference.setEnabled(false);
                JComboBox colType = new JComboBox(choiceStr);
                
                textList.add(coltext);
                comboList.add(colType);
                isPrimary.add(isPri);
                isForeign.add(isFor);
                referenceTables.add(tableReference);
                referenceKey.add(columnReference);
                isFirst.add(isFi);
                
                isForeign.get(i).addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        
                        if(isForeign.get(isForeign.indexOf(e.getSource())).isSelected()){
                            referenceTables.get(isForeign.indexOf(e.getSource())).setEnabled(true);
                            referenceKey.get(isForeign.indexOf(e.getSource())).setEnabled(true);
                        }
                        else{
                            referenceTables.get(isForeign.indexOf(e.getSource())).setEnabled(false);
                            referenceKey.get(isForeign.indexOf(e.getSource())).setEnabled(false);
                        }
                    }
                });
                
                isPrimary.get(i).addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        
                        int lastSelected = isPrimary.indexOf(e.getSource());
                        
                        for(int i = 0; i < isPrimary.size(); i++){
                            
                            if(i != lastSelected){
                                isPrimary.get(i).setSelected(false);
                            }
                            else{
                                isFirst.get(i).setSelected(false);
                            }
                        }
                        
                    }
                });
                
                isFirst.get(i).addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                            if(isPrimary.get(isFirst.indexOf(e.getSource())).isSelected()){
                                isFirst.get(isFirst.indexOf(e.getSource())).setSelected(false);
                            }
                        
                    }
                });
                
                referenceTables.get(i).addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        
                        String curTable = referenceTables.get(referenceTables.indexOf(e.getSource())).getSelectedItem().toString();
                        
                        try{
                            //db_con = new DatabaseConnection();
                            
                            rsset = db_con.getConnection().createStatement().executeQuery("SELECT * FROM " + curTable + ";");
                            ResultSetMetaData tableCols = rsset.getMetaData();
                            
                            String colNames[] = new String[tableCols.getColumnCount()];
            
                            for(int i = 0; i < tableCols.getColumnCount(); i++){
                
                                    colNames[i] = tableCols.getColumnName(i + 1);
                            }
                            
                            model = new DefaultComboBoxModel(colNames);
                            
                            referenceKey.get(referenceTables.indexOf(e.getSource())).setModel(model);
                            
                        }
                        catch(SQLException ex){
                            popup.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        
                    }
                });
                
                holder.add(coltext);
                holder.add(colType);
                holder.add(isPri);
                holder.add(isFi);
                holder.add(isFor);
                holder.add(references);
                holder.add(tableReference);
                holder.add(as);
                holder.add(columnReference);
                
            
            }
            
            entryPanel = new JScrollPane(holder);
            
            entryPanel.setBounds(this.getWidth()/4, this.getHeight()/2 - this.getHeight()/4 - 13, this.getWidth()/2 + 200, 310);
            holder.setBounds(entryPanel.getWidth()/2 - entryPanel.getWidth()/12, entryPanel.getHeight()/2 - entryPanel.getHeight()/5, entryPanel .getWidth()/2, 195);
            
            entryPanel.revalidate();
            entryPanel.repaint();
            entryPanel.updateUI();
            
            this.add(entryPanel);
            this.revalidate();
            this.validate();
        }
        catch(SQLException ex){
           System.out.println(ex.getErrorCode());
        }
    }
    
    public void createTable(){
        
        String tableName = new String(Add.tableN);
        
        String sqlStr = "CREATE TABLE " + tableName + "(\n";
        
        for(int i = 0; i < textList.size() - 1; i++){
            
            sqlStr += "\t" + textList.get(i).getText() + " " + comboList.get(i).getSelectedItem().toString() + ",\n";
        }
        
        sqlStr += "\t" + textList.get(textList.size() - 1).getText() + " " + comboList.get(textList.size() - 1).getSelectedItem().toString();
        
        int last = 0;
        
        for(int i = 0; i < isFirst.size() - 1; i++){
            
            if(isFirst.get(i).isSelected()){
                
                last = i;
            }
        }
        
        if(last > 0){
            sqlStr += ",\n\tUNIQUE(";
        }
        
        for(int i = 0; i < last - 1; i++){
            
            if(isFirst.get(i).isSelected()){
                
                sqlStr += textList.get(i).getText() + ",";
            }
        }
        
        if(last > 0){
            sqlStr += textList.get(last - 1).getText() + ")";
        }
        
        boolean hasPrimary = false;
        
        for(int i = 0; i < isPrimary.size(); i++){
            
            if(isPrimary.get(i).isSelected()){
                
                hasPrimary = true;
            }
        }
        
        if(hasPrimary){
            sqlStr += ",\n\t PRIMARY KEY(";
        }
        
        for(int i = 0; i < isPrimary.size(); i++){
            
            if(isPrimary.get(i).isSelected()){
                
                sqlStr += textList.get(i).getText() + ")\n";
            }
        }
        
        for(int i = 0; i < isForeign.size(); i++){
            
            if(isForeign.get(i).isSelected()){
                
                sqlStr += ",\n\tFOREIGN KEY (" + textList.get(i).getText() + ") REFERENCES " + referenceTables.get(i).getSelectedItem().toString() + "(" + referenceKey.get(i).getSelectedItem().toString() + ")";
            }
        }
        
        sqlStr += "\n);";
        
        try{
            db_con.getConnection().createStatement().executeUpdate(sqlStr);
        }
        catch(SQLException ex){
            popup.showMessageDialog(null, "Failed to create table", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        System.out.println(sqlStr);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        ColNumberTextField = new javax.swing.JTextField();
        confirmButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Insert Number Of Columns");

        confirmButton.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        confirmButton.setText("Confirm");
        confirmButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(393, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(340, 340, 340))
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(confirmButton, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ColNumberTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addComponent(ColNumberTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(confirmButton, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(207, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void confirmButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmButtonActionPerformed
        createTable();
    }//GEN-LAST:event_confirmButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AddTable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddTable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddTable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddTable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddTable().setVisible(true);
            }
        });
    }
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField ColNumberTextField;
    private javax.swing.JButton confirmButton;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
