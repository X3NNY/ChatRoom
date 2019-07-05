import java.awt.EventQueue;
import java.io.DataOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;

import java.awt.Font;


import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;

import java.awt.Color;

import javax.swing.JTextArea;
import javax.swing.ImageIcon;

import java.awt.Toolkit;


public class GroupOpersFrame {

	private JFrame frame;
	private JTable groups;
	private  Client client;
	private HashMap<String,String> ssMap = new HashMap<>();
	/**
	 * Launch the application.
	 */
	public void start() {
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
	public GroupOpersFrame(Client client) {
		this.client = client;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(GroupOpersFrame.class.getResource("/images/tabLeft.PNG")));
		frame.setTitle("\u7FA4\u7EC4\u64CD\u4F5C");
		frame.setBounds(100, 100, 456, 592);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JScrollPane plane = new JScrollPane();

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u8868\u5355\u64CD\u4F5C", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));

		JButton btnNewButton_1 = new JButton("\u4FEE\u6539");

		btnNewButton_1.setIcon(new ImageIcon(GroupOpersFrame.class.getResource("/images/copy.png")));
		btnNewButton_1.setFont(new Font("宋体", Font.ITALIC, 16));

		JButton btnNewButton_2 = new JButton("\u9000\u51FA");

		btnNewButton_2.setIcon(new ImageIcon(GroupOpersFrame.class.getResource("/images/close.png")));
		btnNewButton_2.setFont(new Font("宋体", Font.ITALIC, 16));

		JButton btnNewButton_3 = new JButton("\u8FD4\u56DE\u4E0A\u4E00\u7EA7");
		btnNewButton_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
			}
		});
		btnNewButton_3.setIcon(new ImageIcon(GroupOpersFrame.class.getResource("/com/sun/javafx/scene/web/skin/Undo_16x16_JFX.png")));
		btnNewButton_3.setFont(new Font("宋体", Font.ITALIC, 16));
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
								.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
										.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
												.addContainerGap()
												.addComponent(btnNewButton_1, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
												.addGap(35)
												.addComponent(btnNewButton_2, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(btnNewButton_3))
										.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
												.addGap(30)
												.addComponent(plane, 0, 0, Short.MAX_VALUE))
										.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
												.addContainerGap()
												.addComponent(panel, GroupLayout.PREFERRED_SIZE, 395, GroupLayout.PREFERRED_SIZE)))
								.addContainerGap(179, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
								.addGap(38)
								.addComponent(plane, GroupLayout.PREFERRED_SIZE, 233, GroupLayout.PREFERRED_SIZE)
								.addGap(18)
								.addComponent(panel, GroupLayout.PREFERRED_SIZE, 171, GroupLayout.PREFERRED_SIZE)
								.addGap(39)
								.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(btnNewButton_2)
										.addComponent(btnNewButton_3)
										.addComponent(btnNewButton_1))
								.addContainerGap(27, Short.MAX_VALUE))
		);

		JLabel label_2 = new JLabel("\u7FA4\u63CF\u8FF0");
		label_2.setFont(new Font("宋体", Font.ITALIC, 16));

		JTextArea updateDesc = new JTextArea();
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
				gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
								.addComponent(label_2)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(updateDesc, GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE)
								.addContainerGap())
		);
		gl_panel.setVerticalGroup(
				gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
								.addContainerGap()
								.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
										.addComponent(label_2)
										.addComponent(updateDesc, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE))
								.addContainerGap(112, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);

		groups = new JTable();
		groups.setFont(new Font("宋体", Font.ITALIC, 16));
		groups.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int row = groups.getSelectedRow();
				updateDesc.setText(ssMap.get((String) groups.getValueAt(row, 0)));
			}
		});
		groups.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
						"\u7FA4\u540D\u79F0"
				}
		) {
			boolean[] columnEditables = new boolean[] {
					false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {//修改操作
				int n = groups.getSelectedRow();
				String desc = updateDesc.getText();
				if (n == -1) {
					JOptionPane.showMessageDialog(null,"请先选中群组！");
				} else {
					//是否有权限,是,操作数据库,否 : JOptionPane.showMessageDialog(null, "无此权限!!!");
					String name =(String) groups.getValueAt(n,0);
					try {
						int gid = SQLConnection.groupnameGetId(name);
						if (SQLConnection.isGroupAdmin(gid, Main.loginUserid)) {
							SQLConnection.updateGroupData(name,desc);
							JOptionPane.showMessageDialog(null,name +"群修改成功!!!");
						} else {
							JOptionPane.showMessageDialog(null, "无此权限!!!");
						}
					} catch (SQLException se) {
						se.printStackTrace();
					}
					try {
						fillTable();//从Map<socket,String> 中获取对象
					} catch (Exception se) {
						se.printStackTrace();
					}
				}
			}
		});
		btnNewButton_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {//删除群组
				int judge = JOptionPane.showConfirmDialog(null, "是否确定退出/解散此群?");
				if(judge == 0){
					if (groups.getSelectedRow() == -1) return;
					String name = (String) groups.getValueAt(groups.getSelectedRow(),0);
					//如果存在
					//1.自己退群
					//2.如果自己是群主，解散群
					try {
						int gid = SQLConnection.groupnameGetId(name);
						if (SQLConnection.isGroupAdmin(gid, Main.loginUserid)) {
							SQLConnection.dismissGroup(gid);
							try {
								DataOutputStream dos = new DataOutputStream(client.socket.getOutputStream());
								dos.writeUTF("@delgroup|" + name);
								dos.flush();
							} catch (IOException se) {
								se.printStackTrace();
							}
							JOptionPane.showMessageDialog(null,name+"群成功解散！");
						} else {
							SQLConnection.exitGroup(gid, Main.loginUserid);
							try {
								DataOutputStream dos = new DataOutputStream(client.socket.getOutputStream());
								dos.writeUTF("@exitgroup|" + Main.loginUserid + "|" + name);
								dos.flush();
							} catch (IOException se) {
								se.printStackTrace();
							}
							JOptionPane.showMessageDialog(null,name+"群成功退出！");
						}
						UserMainFrame.updateGroup();
						try {
							fillTable();//从Map<socket,String> 中获取对象
						} catch (Exception se) {
							se.printStackTrace();
						}
					} catch (SQLException se) {
						se.printStackTrace();
					}
				}
			}
		});
		plane.setViewportView(groups);
		frame.getContentPane().setLayout(groupLayout);
		try {
			fillTable();//从Map<socket,String> 中获取对象
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void fillTable(){
		DefaultTableModel dt = (DefaultTableModel) groups.getModel();
		dt.setRowCount(0);
		String sql = "select gid from grouptemp where uid=" + Main.loginUserid + ";";
		try {
			Statement s = SQLConnection.creat();
			ResultSet rs = s.executeQuery(sql);
			//查询数据库,向表中填充数据
			while (rs.next()) {
				Vector<String> v = new Vector<>();
				String sql2 = "select groupname,`desc` from `group` where id=" + rs.getInt("gid") + ";";
				Statement s2 = SQLConnection.creat();
				ResultSet rs2 = s2.executeQuery(sql2);
				if (rs2.next()) {
					v.add(rs2.getString("groupname"));
					ssMap.put(rs2.getString("groupname"), rs2.getString("desc"));
				}
				rs2.close();
				s2.close();
				dt.addRow(v);
			}
			rs.close();
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
