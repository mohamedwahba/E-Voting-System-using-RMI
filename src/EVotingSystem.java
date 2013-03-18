

public class EVotingSystem extends javax.swing.JFrame {

	private static final long serialVersionUID = 1L;

	String user_name, candidate;
	Client client;

	public EVotingSystem() {
		client = new Client();
		initComponents();
	}

	public EVotingSystem(String user_name, Client client) {
		this.client = client;
		this.user_name = user_name;
		initComponents();
	}

	private void initComponents() {
		work = new javax.swing.JPanel();
		jScrollPane1 = new javax.swing.JScrollPane();
		area = new javax.swing.JTextArea();
		jButton1 = new javax.swing.JButton();
		candidates = new javax.swing.JComboBox();
		vote_log = new javax.swing.JLabel();
		jButton2 = new javax.swing.JButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

		area.setColumns(20);
		area.setRows(5);
		jScrollPane1.setViewportView(area);

		jButton1.setText("Vote");
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton1ActionPerformed(evt);
			}
		});

		String[] data = client.getCandidates(user_name); // null check
		candidates.setModel(new javax.swing.DefaultComboBoxModel(data));

		jButton2.setText("View Results");
		jButton2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton2ActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout workLayout = new javax.swing.GroupLayout(work);
		work.setLayout(workLayout);
		workLayout
				.setHorizontalGroup(workLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								workLayout
										.createSequentialGroup()
										.addGroup(
												workLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																workLayout
																		.createSequentialGroup()
																		.addContainerGap()
																		.addGroup(
																				workLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								vote_log,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								Short.MAX_VALUE)
																						.addComponent(
																								jScrollPane1,
																								javax.swing.GroupLayout.Alignment.TRAILING)
																						.addGroup(
																								workLayout
																										.createSequentialGroup()
																										.addComponent(
																												candidates,
																												0,
																												javax.swing.GroupLayout.DEFAULT_SIZE,
																												Short.MAX_VALUE)
																										.addGap(37,
																												37,
																												37)
																										.addComponent(
																												jButton1))))
														.addGroup(
																workLayout
																		.createSequentialGroup()
																		.addGap(89,
																				89,
																				89)
																		.addComponent(
																				jButton2,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				130,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addGap(0,
																				122,
																				Short.MAX_VALUE)))
										.addContainerGap()));
		workLayout
				.setVerticalGroup(workLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								workLayout
										.createSequentialGroup()
										.addGap(43, 43, 43)
										.addGroup(
												workLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(jButton1)
														.addComponent(
																candidates,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												vote_log,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												28,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jButton2,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												28,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jScrollPane1,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												208,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addContainerGap(51, Short.MAX_VALUE)));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				javax.swing.GroupLayout.Alignment.TRAILING,
				layout.createSequentialGroup()
						.addContainerGap(95, Short.MAX_VALUE)
						.addComponent(work,
								javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(89, 89, 89)));
		layout.setVerticalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(work,
								javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)));

		pack();
	}

	// vote
	private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
		candidate = (String) candidates.getSelectedItem();
		String response = client.recordVote(candidate, user_name);
		if (response.contains("404")) {
			vote_log.setText("You've voted, before!");
		} else {
			vote_log.setText("You've voted successfully to candidate: "
					+ candidate);
		}
	}

	// view results.
	private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
		String response = client.getListOfVoteResult(user_name);
		if (response.contains("404")) {
			area.setText("You must vote first to view the vote result!");
		} else {
			area.setText(response);
			new Thread() {
				public void run() {
					while (true) {
						String response = client.getListOfVoteResult(user_name);
						area.setText(response);
						try {
							sleep(5000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				};
			}.start();
		}
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[]) {

		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager
					.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(EVotingSystem.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(EVotingSystem.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(EVotingSystem.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(EVotingSystem.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		}
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new EVotingSystem().setVisible(true);
			}
		});
	}

	// Variables declaration - do not modify
	private javax.swing.JTextArea area;
	private javax.swing.JComboBox candidates;
	private javax.swing.JButton jButton1;
	private javax.swing.JButton jButton2;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JLabel vote_log;
	private javax.swing.JPanel work;
	// End of variables declaration
}
