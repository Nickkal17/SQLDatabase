
package com.SQLDatabase;

import com.sun.corba.se.spi.orb.OperationFactory;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Nick
 */
public class Load extends javax.swing.JFrame {

    JComboBox queries;
    ResultSet rsset;
    DatabaseConnection db_con;
    DatabaseGUI db_gui;
    DatabaseGUI frame;
    JTable table = new JTable();
    JScrollPane DisplayPanel;    
    JOptionPane popup = new JOptionPane();
    String fieldText[] = {"Table_name", "Salary", "Department_name", "Product_price", "Years", "Department_name", "Manager_id"};
        
    public Load() {
        initComponents();
        
        queryText.setText(fieldText[0]);
        createViewCheckBox.setEnabled(false);
        viewNameText.setEnabled(false);
        
        queryText.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(queryText.getText().isEmpty() || queryText.getText().equals(fieldText[queries.getSelectedIndex()])){
                    queryText.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(queryText.getText().isEmpty()){
                    queryText.setText(fieldText[queries.getSelectedIndex()]);
                }
            }
        });
        
        viewNameText.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(viewNameText.getText().isEmpty() || viewNameText.getText().equals("View_name")){
                    viewNameText.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(viewNameText.getText().isEmpty()){
                    viewNameText.setText("View_name");
                }
            }
        });
        
        createViewCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                if(createViewCheckBox.isSelected()){
                    viewNameText.setEnabled(true);
                }
                else{
                   viewNameText.setEnabled(false);
                }
            }
        });

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        initEntries();
        initTableView();
        
        queries.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               
                
                if(queries.getSelectedIndex() == 0){
                    createViewCheckBox.setEnabled(false);
                }
                else{
                    createViewCheckBox.setEnabled(true);
                }
                
                queryText.setText(fieldText[queries.getSelectedIndex()]);
                
                
            }
        });
    }
    
    public void executeQuery() {
        
        db_gui = new DatabaseGUI();
        
        try{
            switch(queries.getSelectedIndex()){
            
            case(0): {
                
                destroyDispPanel();
 
                rsset = db_con.getConnection().createStatement().executeQuery("SELECT * FROM " + queryText.getText() + ";");
                System.out.println(queryText.getText());
                DefaultTableModel model = DatabaseGUI.buildTableModel(rsset);
                
                createDispTable(model);
                
                break;
            }
            case(1):{
                
                destroyDispPanel();
                String sqlQuery = "SELECT E.f_name, E.l_name, S.salary\n "
                                + "FROM Employees E, Salaries S\n"
                                + "WHERE E.id = S.id And S.salary >  " + Integer.parseInt(queryText.getText()) + ";";
                
                System.out.println(sqlQuery);
                rsset = db_con.getConnection().createStatement().executeQuery(sqlQuery);
                
                DefaultTableModel model = DatabaseGUI.buildTableModel(rsset);
                
                if(createViewCheckBox.isSelected()){
                    String viewQuery = "CREATE VIEW " + viewNameText.getText() + " AS\n"
                                     + "SELECT E.f_name, E.l_name, S.salary\n "
                                     + "FROM Employees E, Salaries S\n"
                                     + "WHERE E.id = S.id And S.salary >  " + Integer.parseInt(queryText.getText()) + ";";
                    
                    db_con.getConnection().createStatement().executeUpdate(viewQuery);
                    popup.showMessageDialog(null, "View has been created", "Done", JOptionPane.INFORMATION_MESSAGE);
                }
                
                createDispTable(model);
                break;
            }
            
            case(2):{
                
                destroyDispPanel();
                String sqlQuery = "SELECT Dw.d_work\n "
                                + "FROM Department_work Dw\n "
                                + "WHERE Dw.d_name = '" + queryText.getText() + "';";
                
                System.out.println(sqlQuery);
                rsset = db_con.getConnection().createStatement().executeQuery(sqlQuery);
                
                DefaultTableModel model = DatabaseGUI.buildTableModel(rsset);
                
                if(createViewCheckBox.isSelected()){
                    String viewQuery = "CREATE VIEW " + viewNameText.getText() + " AS\n"
                                     + "SELECT Dw.d_work\n "
                                     + "FROM Department_work Dw\n "
                                     + "WHERE Dw.d_name = '" + queryText.getText() + "';";
                    
                    db_con.getConnection().createStatement().executeUpdate(viewQuery);
                    popup.showMessageDialog(null, "View has been created", "Done", JOptionPane.INFORMATION_MESSAGE);
                }
                
                createDispTable(model);
                
                break;
            }
            
            case(3):{
                
                destroyDispPanel();
                String sqlQuery = "SELECT p_price\n "
                                + "FROM Products\n "
                                + "WHERE p_name = '" + queryText.getText() + "';";
                
                rsset = db_con.getConnection().createStatement().executeQuery(sqlQuery);
                
                DefaultTableModel model = DatabaseGUI.buildTableModel(rsset);
                
                if(createViewCheckBox.isSelected()){
                    String viewQuery = "CREATE VIEW " + viewNameText.getText() + " AS\n"
                                     + "SELECT p_price\n "
                                     + "FROM Products\n "
                                     + "WHERE p_name = '" + queryText.getText() + "';";
                    
                    db_con.getConnection().createStatement().executeUpdate(viewQuery);
                    popup.showMessageDialog(null, "View has been created", "Done", JOptionPane.INFORMATION_MESSAGE);
                }
                
                createDispTable(model);
                
                break;
            }
            case(4):{
                
                destroyDispPanel();
                
                String  sqlQuery  = "SELECT *\n"
                                  + "FROM employees\n"  
                                  + "WHERE (year(CURRENT_DATE) - year(hire_date)) > " + Integer.parseInt(queryText.getText()) + ";";
                
                rsset = db_con.getConnection().createStatement().executeQuery(sqlQuery);
                
                DefaultTableModel model = DatabaseGUI.buildTableModel(rsset);
                
                if(createViewCheckBox.isSelected()){
                    String viewQuery = "CREATE VIEW " + viewNameText.getText() + " AS\n"
                                     + "SELECT *\n"
                                     + "FROM employees\n"  
                                     + "WHERE (year(CURRENT_DATE) - year(hire_date)) > " + Integer.parseInt(queryText.getText()) + ";";
                    
                    db_con.getConnection().createStatement().executeUpdate(viewQuery);
                    popup.showMessageDialog(null, "View has been created", "Done", JOptionPane.INFORMATION_MESSAGE);
                }
                
                createDispTable(model);
                
                break;
                
            }
            case(5):{
                
                destroyDispPanel();
                String  sqlQuery  = "SELECT E.f_name , E.l_name , E.department , D.location, D.supervisor_id\n"
                                  + "FROM employees E\n"  
                                  + "JOIN departments D\n"
                                  + "ON E.department = D.d_name\n"
                                  + "AND E.department = '" + queryText.getText() + "';";
                
                rsset = db_con.getConnection().createStatement().executeQuery(sqlQuery);
                
                DefaultTableModel model = DatabaseGUI.buildTableModel(rsset);
                
                if(createViewCheckBox.isSelected()){
                    String viewQuery = "CREATE VIEW " + viewNameText.getText() + " AS\n"
                                     + "SELECT E.f_name , E.l_name , E.department , D.location, D.supervisor_id\n"
                                     + "FROM employees E\n"  
                                     + "JOIN departments D\n"
                                     + "ON E.department = D.d_name\n"
                                     + "AND E.department = '" + queryText.getText() + "';";
                    
                    db_con.getConnection().createStatement().executeUpdate(viewQuery);
                    popup.showMessageDialog(null, "View has been created", "Done", JOptionPane.INFORMATION_MESSAGE);
                }
                
                createDispTable(model);
                
                break;
                
            }
            case(6):{
                destroyDispPanel();
                String  sqlQuery  = "SELECT E.id, E.f_name, E.l_name,E.gender, E.b_date, E.phone_num, E.e_mail, E.address, E.department\n"
                                  + "FROM employees E, departments D\n"  
                                  + "WHERE D.d_name = E.department AND d.supervisor_id = " + Integer.parseInt(queryText.getText()) + ";";
                
                rsset = db_con.getConnection().createStatement().executeQuery(sqlQuery);
                
                DefaultTableModel model = DatabaseGUI.buildTableModel(rsset);
                
                if(createViewCheckBox.isSelected()){
                    
                    String viewQuery = "CREATE VIEW " + viewNameText.getText() + " AS\n"
                                     + "SELECT E.id, E.f_name, E.l_name,E.gender, E.b_date, E.phone_num, E.e_mail, E.address, E.department\n"
                                     + "FROM employees E, departments D\n"  
                                     + "WHERE D.d_name = E.department AND d.supervisor_id = " + Integer.parseInt(queryText.getText()) + ";";
                    
                    db_con.getConnection().createStatement().executeUpdate(viewQuery);
                    popup.showMessageDialog(null, "View has been created", "Done", JOptionPane.INFORMATION_MESSAGE);
                }
                
                createDispTable(model);
                
                break;
            }
            
        }
        }
        catch(SQLException ex){
            
            popup.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        
    }
    
    public void initTableView(){
        
        DisplayPanel = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        DisplayPanel.setVisible(true);
                
        table.setFillsViewportHeight(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                
        DisplayPanel.setBounds(0, this.getHeight()/2 - 70, this.getWidth() - 7, this.getHeight() / 2 + 40);
                
                //DatabaseGUI.gui.setVisible(false);
                
        DisplayPanel.revalidate();
        DisplayPanel.validate();
        DisplayPanel.repaint();
        DisplayPanel.updateUI();

        this.add(DisplayPanel);
        this.revalidate();
        this.validate();
    }
    
    public void createDispTable(DefaultTableModel model){
        
        table.setModel(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                
        DisplayPanel = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        DisplayPanel.setVisible(true);
                
        table.setFillsViewportHeight(true);
                
        DisplayPanel.setBounds(0, this.getHeight()/2 - 70, this.getWidth() - 7, this.getHeight() / 2 + 40);
                
        DisplayPanel.revalidate();
        DisplayPanel.validate();
        DisplayPanel.repaint();
        DisplayPanel.updateUI();

        this.add(DisplayPanel);
        this.revalidate();
        this.validate();
    }
    
    public void destroyDispPanel(){
        
        if(DisplayPanel != null){
             this.getContentPane().remove(DisplayPanel);
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

        jLabel1 = new javax.swing.JLabel();
        queryText = new javax.swing.JTextField();
        executeButton = new javax.swing.JButton();
        createViewCheckBox = new javax.swing.JCheckBox();
        viewNameText = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Load");
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("    Select A Premade Query");

        queryText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                queryTextActionPerformed(evt);
            }
        });

        executeButton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        executeButton.setText("Execute");
        executeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                executeButtonActionPerformed(evt);
            }
        });

        createViewCheckBox.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        createViewCheckBox.setText("Create As View");
        createViewCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createViewCheckBoxActionPerformed(evt);
            }
        });

        viewNameText.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        viewNameText.setText("View_name");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(278, 278, 278)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(152, 152, 152)
                        .addComponent(executeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(134, 134, 134)
                        .addComponent(createViewCheckBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                        .addComponent(viewNameText, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(queryText, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(56, 56, 56))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addComponent(queryText, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(executeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(createViewCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(viewNameText, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(361, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void queryTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_queryTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_queryTextActionPerformed

    private void executeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_executeButtonActionPerformed
        executeQuery();
    }//GEN-LAST:event_executeButtonActionPerformed

    private void createViewCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createViewCheckBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_createViewCheckBoxActionPerformed

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
            java.util.logging.Logger.getLogger(Load.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Load.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Load.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Load.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Load().setVisible(true);
            }
        });
    }
    
    public void initEntries(){

        String queryChoices[] = {"List all data from table:", "List the employees with salary more than:", "List the projects of the department:", "Display the price of the product:", "List the employees whose experience is more than:", "List the employees and supervisor of the department:", "List the employees whose manager is:"};
        
        queries = new JComboBox(queryChoices);
        queries.setBounds(50, 113, 320, 40);
        
        this.add(queries);
        
        this.revalidate();
        this.validate();
        this.repaint();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox createViewCheckBox;
    private javax.swing.JButton executeButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextField queryText;
    private javax.swing.JTextField viewNameText;
    // End of variables declaration//GEN-END:variables
}
