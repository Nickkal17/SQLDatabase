
package com.SQLDatabase;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.Box;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;


public class Add extends javax.swing.JFrame{

    List<JCheckBox> checkList;
    List<JTextField> textList;
    int lastSelected;
    
    public static String tableN;
    public static String viewN;
    
    static final int CHARACTER = 12;
    static final int DATE = 91;
    
    JOptionPane popup = new JOptionPane();
    ResultSet rsset;
    DatabaseConnection db_con;
    DatabaseTable db_tables;
    
    
    Box box =  Box.createVerticalBox();
    JScrollPane entryPanel;
    
    public Add() {
        initComponents();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        AddCheckBoxes();
    }
    
    public void AddCheckBoxes(){
        
        checkList = new ArrayList<>();
        
        db_con = new DatabaseConnection();
        db_tables = new DatabaseTable(db_con);

        String tableNames[] = new String[db_tables.getTableNum()];
        
        tableNames = db_tables.getTableNames();
        
        Box box = Box.createVerticalBox();
        
        for(int i = 0; i < db_tables.getTableNum(); i++){
            System.out.println(tableNames[i]);
            JCheckBox checkb = new JCheckBox(tableNames[i], false);
            checkList.add(checkb);
            checkb.setVisible(true);
            box.add(checkList.get(i));
            checkList.get(i).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                     checkBoxEv(e);
                }
            });
   
        }
        
        JScrollPane pane = new JScrollPane(box);
        pane.setBounds(0, this.getHeight()/2 - 65, this.getWidth()/2, 190);
        
        pane.revalidate();
        pane.repaint();
        pane.updateUI();
        
        this.add(pane);
    }
    
     private void checkBoxEv(java.awt.event.ActionEvent evt){
         
         removeEntryFields();
         
         lastSelected = checkList.indexOf(evt.getSource());
         
         for(int i=0; i < db_tables.getTableNum(); i++){
             
             if(i != lastSelected){
                 checkList.get(i).setSelected(false);
             }
         }
         
         updateEntryFields();
     }
    
    public void removeEntryFields(){
                
        if(entryPanel != null){
            
           this.getContentPane().remove(entryPanel);
           box.removeAll();
           textList.clear();
        }
        
    }
    
    public void updateEntryFields(){
        
        String tableName = checkList.get(lastSelected).getText();
        textList = new ArrayList<>();
        
        
        try{
            rsset = db_con.getConnection().createStatement().executeQuery("SELECT * FROM " + tableName + ";");
            ResultSetMetaData tableCols = rsset.getMetaData();
            
            System.out.println(tableCols.getColumnCount());

            
            for(int i = 1; i <= tableCols.getColumnCount(); i++){
                String colName = tableCols.getColumnName(i);
                System.out.println(colName);
                JTextField coltext = new JTextField();
                textList.add(coltext);
                coltext.setText(colName);
               
                textList.get(i-1).addFocusListener(new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {
                        if(coltext.getText().isEmpty() || coltext.getText().equals(colName)){
                            coltext.setText("");
                        }
                    }

                    @Override
                    public void focusLost(FocusEvent e) {
                        if(coltext.getText().isEmpty()){
                            coltext.setText(colName);
                        }
                    }
                });

                box.add(coltext);
                box.revalidate();
                
                
            }
            
            entryPanel = new JScrollPane(box);
        
            entryPanel.setBounds(this.getWidth()/2, this.getHeight()/2 - 65 , this.getWidth()/2, 190);
            
            entryPanel.revalidate();
            entryPanel.repaint();
            entryPanel.updateUI();
            
            this.add(entryPanel);
            this.revalidate();
            this.validate();
        }
        catch(SQLException ex){
           popup.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
   
    }
    
    
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Add_Table = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        AddEntryButton = new javax.swing.JButton();
        tableNameTextField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Add");
        setResizable(false);

        Add_Table.setText("Add");
        Add_Table.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Add_TableActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("  Add New Table");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("    Add New Entry");

        AddEntryButton.setText("Add Entry");
        AddEntryButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddEntryButtonActionPerformed(evt);
            }
        });

        tableNameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tableNameTextFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(127, 127, 127)
                .addComponent(Add_Table, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(tableNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 209, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(AddEntryButton, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(323, 323, 323))
            .addGroup(layout.createSequentialGroup()
                .addGap(296, 296, 296)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(305, 305, 305))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addComponent(Add_Table, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addComponent(tableNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 201, Short.MAX_VALUE)
                .addComponent(AddEntryButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Add_TableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Add_TableActionPerformed
        
        if(tableNameTextField.getText() != null && !tableNameTextField.getText().isEmpty() && !tableNameTextField.getText().trim().isEmpty()){
            tableN = tableNameTextField.getText();
            AddTable a = new AddTable();
            a.setVisible(true);
        }
        
    }//GEN-LAST:event_Add_TableActionPerformed

    
    private void AddEntryButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddEntryButtonActionPerformed
        addNewEntry();
    }//GEN-LAST:event_AddEntryButtonActionPerformed

    private void tableNameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tableNameTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tableNameTextFieldActionPerformed
    
    public void addNewEntry(){
        
        
        
        String tableName = checkList.get(lastSelected).getText();
        String sqlQuery = "INSERT INTO " + tableName + "(";
        
        try{
            
            ResultSetMetaData tableCols = rsset.getMetaData();
            
            for (int i = 1; i <= textList.size() - 1; i++) {
		sqlQuery += tableCols.getColumnName(i) + ", ";
                System.out.println(tableCols.getColumnName(i) + ":" + tableCols.getColumnType(i));
            }
            
            sqlQuery += tableCols.getColumnName(textList.size()) + ")" + "\nVALUES (";
            System.out.println(tableCols.getColumnName(textList.size()) + ":" + tableCols.getColumnType(textList.size()));
            
            for (int i = 0; i < textList.size() - 1; i++) {
                
                if(tableCols.getColumnType(i+1) == CHARACTER || tableCols.getColumnType(i+1) == DATE){
                    sqlQuery += "'" + textList.get(i).getText() + "'" + ", ";
                }
                else{
                    sqlQuery += textList.get(i).getText() + ", ";
                }
            }
            
            if(tableCols.getColumnType(textList.size()) == CHARACTER || tableCols.getColumnType(textList.size()) == DATE){
                sqlQuery += "'" + textList.get(textList.size() - 1).getText() + "'" + ");";
            }
            else{
                sqlQuery += textList.get(textList.size() - 1).getText() + ");";
            }
            
            System.out.println(sqlQuery);
            db_con.getConnection().createStatement().executeUpdate(sqlQuery);
            
            popup.showMessageDialog(null, "Entry Added", "Done", JOptionPane.INFORMATION_MESSAGE);
            System.out.println(sqlQuery);
            
        } 
        catch(SQLException ex){
            popup.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println(ex.getMessage());
        }
        
    }
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
            java.util.logging.Logger.getLogger(Add.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Add.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Add.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Add.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Add().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddEntryButton;
    private javax.swing.JButton Add_Table;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField tableNameTextField;
    // End of variables declaration//GEN-END:variables
}
