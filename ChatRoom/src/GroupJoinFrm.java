import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.DataOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;


public class GroupJoinFrm {

	private JFrame frame;
	private JTextField name;
	private JTextField pwd;
	private JTable groupTable;
	private Client client;

	/**
	 * Launch the application.
	 */
	public  void start() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					initialize();
					frame.setVisible(true);
					Thread.sleep(100);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GroupJoinFrm(Client client) {
		this.client = client;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(GroupJoinFrm.class.getResource("/images/Success.gif")));
		frame.setTitle("\u52A0\u5165\u7FA4");
		frame.setBounds(100, 100, 460, 534);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JLabel label = new JLabel("\u7FA4\u540D\u79F0");
		label.setIcon(new ImageIcon(GroupJoinFrm.class.getResource("/images/tabLeft.PNG")));
		label.setFont(new Font("宋体", Font.ITALIC, 16));

		name = new JTextField();
		name.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("\u5165\u7FA4\u53E3\u4EE4");
		lblNewLabel_1.setIcon(new ImageIcon(GroupJoinFrm.class.getResource("/images/tabLeft2.PNG")));
		lblNewLabel_1.setFont(new Font("宋体", Font.ITALIC, 16));

		pwd = new JTextField();
		pwd.setColumns(10);

		JButton btnNewButton = new JButton("\u7ACB\u5373\u52A0\u5165");
		btnNewButton.setIcon(new ImageIcon(GroupJoinFrm.class.getResource("/images/Success.gif")));
		btnNewButton.setFont(new Font("宋体", Font.ITALIC, 16));

		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (groupTable.getSelectedRow() == -1) return;
				String groupname = (String) groupTable.getValueAt(groupTable.getSelectedRow(),0);
				String password = MD5.encode(pwd.getText());
				try {
					int gid = SQLConnection.groupnameGetId(groupname);
					if (SQLConnection.checkPassword(password, gid)) {
						SQLConnection.addGroup(Main.loginUserid,gid);
						try {
							DataOutputStream dos = new DataOutputStream(client.socket.getOutputStream());
							dos.writeUTF("@addgroup|" + Main.loginUsername + "|" + groupname);
							dos.flush();
						} catch (IOException se) {
							se.printStackTrace();
						}
						JOptionPane.showMessageDialog(null, "加入成功!!!");

						UserMainFrame.updateGroup();
					} else {
						JOptionPane.showMessageDialog(null, "群不存在或口令错误!!!");
					}
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
		});

		JButton btnNewButton_1 = new JButton("\u8FD4\u56DE\u4E0A\u4E00\u7EA7");
		btnNewButton_1.setIcon(new ImageIcon(GroupJoinFrm.class.getResource("/com/sun/javafx/scene/web/skin/Undo_16x16_JFX.png")));
		btnNewButton_1.setFont(new Font("宋体", Font.ITALIC, 16));
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
			}
		});

		JScrollPane scrollPane = new JScrollPane();

		JButton btnNewButton_2 = new JButton("\u641C\u7D22");
		btnNewButton_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {//搜索
				String groupname = name.getText();
				fillGroupTable("select groupname,`desc` from `group` where groupname like '%" + groupname + "%';");
			}
		});
		btnNewButton_2.setFont(new Font("宋体", Font.ITALIC, 16));
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
								.addGap(24)
								.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
										.addGroup(groupLayout.createSequentialGroup()
												.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
														.addComponent(label)
														.addComponent(btnNewButton)
														.addComponent(lblNewLabel_1, Alignment.TRAILING))
												.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
														.addGroup(groupLayout.createSequentialGroup()
																.addGap(17)
																.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
																		.addGroup(groupLayout.createSequentialGroup()
																				.addPreferredGap(ComponentPlacement.RELATED)
																				.addComponent(name, GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE))
																		.addComponent(btnNewButton_1)))
														.addGroup(groupLayout.createSequentialGroup()
																.addGap(18)
																.addComponent(pwd, GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)))
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(btnNewButton_2))
										.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE))
								.addContainerGap())
		);
		groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
								.addGap(32)
								.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(label)
										.addComponent(name, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(btnNewButton_2))
								.addGap(50)
								.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED, 81, Short.MAX_VALUE)
								.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblNewLabel_1)
										.addComponent(pwd, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGap(50)
								.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(btnNewButton_1)
										.addComponent(btnNewButton))
								.addGap(39))
		);

		groupTable = new JTable();
		groupTable.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
						"\u7FA4\u540D\u79F0", "\u7FA4\u63CF\u8FF0"
				}
		) {
			boolean[] columnEditables = new boolean[] {
					true, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		scrollPane.setViewportView(groupTable);
		frame.getContentPane().setLayout(groupLayout);
	}

	public void fillGroupTable(String sql) {
		DefaultTableModel dt = (DefaultTableModel) groupTable.getModel();
		dt.setRowCount(0);
		try {
			Statement s = SQLConnection.creat();
			ResultSet rs = s.executeQuery(sql);
			while (rs.next()) {
				Vector<String> v = new Vector<>();
				v.add(rs.getString("groupname"));
				v.add(rs.getString("desc"));
				dt.addRow(v);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
