import javax.swing.JOptionPane;

/**
 *
 * @author laptop
 */
public class Welcome extends javax.swing.JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Creates new form Welcome
     */
    String user_name;
    
    public Welcome() {
        initComponents();
    }
    
    private void initComponents() {

        Register = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Register.setText("Register");
        Register.setActionCommand("Register");
        Register.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RegisterActionPerformed(evt);
            }
        });

        jButton2.setText("Login");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(Register)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 142, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addGap(54, 54, 54))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Register)
                    .addComponent(jButton2))
                .addContainerGap(50, Short.MAX_VALUE))
        );

        pack();
    }

    // register
    private void RegisterActionPerformed(java.awt.event.ActionEvent evt) {
        user_name = JOptionPane.showInputDialog("Please Enter your name!");
        if (user_name == null || user_name.equals("")) return;
        Client client = new Client();
        String response = client.register_user(user_name);
        if (response.contains("404")) {
        	JOptionPane.showMessageDialog(null, "This user has registred before! Try another name, please!", "Error", JOptionPane.ERROR_MESSAGE);
        	return;
        } else if (response.contains("200")) {
	        EVotingSystem register = new EVotingSystem(user_name, client);
	        register.setTitle(user_name);
		    this.setVisible(false);
	        register.setVisible(true);
        }
    }

    // login
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        user_name = JOptionPane.showInputDialog("Please Enter your name!");
        if (user_name == null || user_name.equals("")) return;
        Client client = new Client();
        String response = client.login(user_name);
        if (response.contains("404")) {
        	JOptionPane.showMessageDialog(null, "User NOT FOUND", "Error", JOptionPane.ERROR_MESSAGE);
        	return;
        } else if (response.contains("200")) {
        	EVotingSystem login = new EVotingSystem(user_name, client);
		    login.setTitle(user_name);
		    this.setVisible(false);
		    login.setVisible(true);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
    	
    	try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Welcome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Welcome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Welcome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Welcome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Welcome().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify
    private javax.swing.JButton Register;
    private javax.swing.JButton jButton2;
    // End of variables declaration
}
