import com.mysql.jdbc.StringUtils;

import java.awt.EventQueue;

import javax.swing.*;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;


public class GroupRoomFrame {

	private JFrame frame;
	private JTable groupMemberTable;
	public Client client;
	public  String currentRoom;//当前聊天室
	private JTextArea showMessage;
	private JTextArea sendMessage;
	private int gid;
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
	public GroupRoomFrame() {
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		try {
			gid = SQLConnection.groupnameGetId(currentRoom);
		} catch (Exception e) {
			e.printStackTrace();
		}

		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(GroupRoomFrame.class.getResource("/images/tabLeft.PNG")));
		frame.setFont(new Font("Dialog", Font.ITALIC, 16));
		frame.setTitle(currentRoom+"-\u7FA4\u804A\u5929\u5BA4");
		frame.setBounds(100, 100, 758, 542);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JScrollPane scrollPane = new JScrollPane();

		sendMessage = new JTextArea();

		sendMessage.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.isControlDown() && e.getKeyCode() == 10) {
					sendMessageToGroup();
				}
			}
		});

		JScrollPane scrollPane_1 = new JScrollPane();

		JLabel lblNewLabel = new JLabel("\u7FA4\u6210\u5458");
		lblNewLabel.setIcon(new ImageIcon(GroupRoomFrame.class.getResource("/images/ToolbarFace.png")));
		lblNewLabel.setFont(new Font("宋体", Font.ITALIC, 16));

		JButton btnNewButton = new JButton("\u7FA4\u516C\u544A");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {//显示群公告
				new GroupADFrame(currentRoom).start();
			}
		});
		btnNewButton.setIcon(new ImageIcon(GroupRoomFrame.class.getResource("/images/\u516C\u544A.png")));
		btnNewButton.setFont(new Font("宋体", Font.ITALIC, 16));

		JButton btnNewButton_1 = new JButton("\u6DFB\u52A0\u597D\u53CB");
		btnNewButton_1.setFont(new Font("宋体", Font.ITALIC, 16));
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {//添加好友
				if (groupMemberTable.getSelectedRow() == -1) return;
				String addUserName = (String) groupMemberTable.getValueAt(groupMemberTable.getSelectedRow(), 0);//得到对方用户名
				if (addUserName.equals(Main.loginUsername)) {
					JOptionPane.showMessageDialog(null,"您不能添加自己为好友！");
					return ;
				}
				//操作数据库
				try {
					int fid = SQLConnection.usernameGetId(addUserName);
					SQLConnection.addUser(Main.loginUserid,fid);
				} catch (SQLException se) {
					se.printStackTrace();
				}
				JOptionPane.showMessageDialog(null, "已经添加该用户");
			}
		});
		btnNewButton_1.setIcon(new ImageIcon(GroupRoomFrame.class.getResource("/images/sysTray.png")));

		JButton btnNewButton_2 = new JButton("\u8E22\u4EBA");
		btnNewButton_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {//
				if (groupMemberTable.getSelectedRow() == -1) return;
				String addUserName = (String) groupMemberTable.getValueAt(groupMemberTable.getSelectedRow(), 0);//得到对方用户名
				//操作数据库
				try {
					if (Main.loginUsername.equals(addUserName)) {
						JOptionPane.showMessageDialog(null,"您不能踢出自己！");
						return;
					}
					if(!SQLConnection.isGroupAdmin(gid,Main.loginUserid)) {
						JOptionPane.showMessageDialog(null,"你不是群主，不能踢人！");
						return;
					}
					SQLConnection.deleteUser(gid,SQLConnection.usernameGetId(addUserName));
					JOptionPane.showMessageDialog(null,addUserName+"已被踢出！");

					try {
						DataOutputStream dos = new DataOutputStream(client.socket.getOutputStream());
						dos.writeUTF("@firegroup|" + addUserName + "|" + currentRoom);
						dos.flush();
					} catch (IOException se) {
						se.printStackTrace();
					}
					fillTable("select u.username from grouptemp f,user u where f.uid = u.id and f.gid = " + gid + ";");//填充群成员列表
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
		});
		btnNewButton_2.setIcon(new ImageIcon(GroupRoomFrame.class.getResource("/images/Fail.gif")));
		btnNewButton_2.setFont(new Font("宋体", Font.ITALIC, 16));

		JButton btnNewButton_3 = new JButton("\u53D1\u9001");
		btnNewButton_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {//发送群消息
				sendMessageToGroup();
			}
		});
		btnNewButton_3.setIcon(new ImageIcon(GroupRoomFrame.class.getResource("/images/ToolbarFont.png")));
		btnNewButton_3.setFont(new Font("宋体", Font.ITALIC, 16));

		JButton btnNewButton_4 = new JButton("\u6E05\u7A7A");
		btnNewButton_4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {//清空
				showMessage.setText("");
			}
		});
		btnNewButton_4.setIcon(new ImageIcon(GroupRoomFrame.class.getResource("/images/Fail.gif")));
		btnNewButton_4.setFont(new Font("宋体", Font.ITALIC, 16));

		JButton btnNewButton_5 = new JButton("\u8FD4\u56DE");
		btnNewButton_5.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {//返回上一级
				Client.areaMap.remove("#"+currentRoom);
				frame.dispose();
			}
		});
		btnNewButton_5.setIcon(new ImageIcon(GroupRoomFrame.class.getResource("/com/sun/javafx/scene/web/skin/Undo_16x16_JFX.png")));
		btnNewButton_5.setFont(new Font("宋体", Font.ITALIC, 16));
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
								.addContainerGap()
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(sendMessage)
										.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 399, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addGroup(groupLayout.createSequentialGroup()
												.addGap(81)
												.addComponent(lblNewLabel))
										.addGroup(groupLayout.createSequentialGroup()
												.addGap(36)
												.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
														.addGroup(groupLayout.createSequentialGroup()
																.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
																		.addComponent(btnNewButton_3, GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
																		.addComponent(btnNewButton_1, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE))
																.addGap(8)
																.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
																		.addComponent(btnNewButton_4, GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
																		.addComponent(btnNewButton_5, GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)))
														.addGroup(groupLayout.createSequentialGroup()
																.addGap(18)
																.addComponent(scrollPane_1, 0, 0, Short.MAX_VALUE))
														.addGroup(groupLayout.createSequentialGroup()
																.addComponent(btnNewButton)
																.addPreferredGap(ComponentPlacement.RELATED)
																.addComponent(btnNewButton_2, GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)))))
								.addContainerGap())
		);
		groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
								.addContainerGap()
								.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 307, GroupLayout.PREFERRED_SIZE)
										.addGroup(groupLayout.createSequentialGroup()
												.addComponent(lblNewLabel)
												.addGap(8)
												.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 263, GroupLayout.PREFERRED_SIZE)))
								.addGap(18)
								.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
										.addGroup(groupLayout.createSequentialGroup()
												.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
														.addComponent(btnNewButton)
														.addComponent(btnNewButton_2, GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE))
												.addGap(18)
												.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
														.addGroup(groupLayout.createSequentialGroup()
																.addComponent(btnNewButton_1)
																.addPreferredGap(ComponentPlacement.UNRELATED)
																.addComponent(btnNewButton_3, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE))
														.addGroup(groupLayout.createSequentialGroup()
																.addComponent(btnNewButton_5, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
																.addGap(18)
																.addComponent(btnNewButton_4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
										.addComponent(sendMessage, GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE))
								.addContainerGap())
		);

		showMessage = new JTextArea();
		scrollPane.setViewportView(showMessage);

		//用户字体
		showMessage.setFont(new Font("宋体",Main.ft,Main.fontSize));
		showMessage.setForeground(FontFrame.cMap.get(Main.co));

		groupMemberTable = new JTable();
		groupMemberTable.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
						"\u7528\u6237\u540D"
				}
		) {
			Class[] columnTypes = new Class[] {
					String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
					false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});

		scrollPane_1.setViewportView(groupMemberTable);
		groupMemberTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {//添加好友
				if(e.getClickCount() == 2){//如果鼠标双击,则询问是否添加好友
					int judge = JOptionPane.showConfirmDialog(null, "是否将ta添加为好友？");
					if(judge == 0){
						if (groupMemberTable.getSelectedRow() == -1) return;
						String addUserName = (String) groupMemberTable.getValueAt(groupMemberTable.getSelectedRow(), 0);//得到对方用户名
						if (addUserName.equals(Main.loginUsername)) {
							JOptionPane.showMessageDialog(null,"您不能添加自己为好友！");
							return ;
						}
						//操作数据库
						try {
							int fid = SQLConnection.usernameGetId(addUserName);
							SQLConnection.addUser(Main.loginUserid,fid);
						} catch (SQLException se) {
							se.printStackTrace();
						}
						JOptionPane.showMessageDialog(null, "已经添加该用户");
					}
				}
			}
		});

		frame.getContentPane().setLayout(groupLayout);

		showUnreadMsg();

		if (!Client.areaMap.containsKey("#"+currentRoom) || Client.areaMap.get("#"+currentRoom) == null) {
			Client.areaMap.put("#"+currentRoom,showMessage);
		}
		fillTable("select u.username from grouptemp f,user u where f.uid = u.id and f.gid = " + gid + ";");//填充群成员列表
		showMessage.setEditable(false);

	}

	private void sendMessageToGroup() {
		String send = sendMessage.getText();
		if(StringUtils.isNullOrEmpty(send)){
			JOptionPane.showMessageDialog(null, "消息不能为空!!!");
		}else{
			//client发送消息,操作数据库
			Date d = new Date();
			try {
				if (!Client.areaMap.containsKey("#"+currentRoom) || Client.areaMap.get("#"+currentRoom) == null) {
					Client.areaMap.put("#"+currentRoom,showMessage);
				}
				DataOutputStream bos = new DataOutputStream(client.socket.getOutputStream());

				//#groupname|data username: \r\n\t|msg
				bos.writeUTF("#" + currentRoom + "|" + Main.df.format(d) + " " + Server.ssMap.get(client.socket) + ": \r\n\t|"  + send + "\r\n");
				bos.flush();

			} catch (Exception se) {
				se.printStackTrace();
			}
			sendMessage.setText("");
		}
	}

	private void fillTable(String sql){
		DefaultTableModel dt = (DefaultTableModel) groupMemberTable.getModel();
		dt.setRowCount(0);
		try {
			Statement s = SQLConnection.creat();
			ResultSet rs = s.executeQuery(sql);
			//查询数据库,向表中填充数据
			while (rs.next()) {
				Vector<String> v = new Vector<>();
				v.add(rs.getString("username"));
				dt.addRow(v);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void showUnreadMsg() {
		try {
			String sql = "select * from groupmessage where flag=0 and gid=" + SQLConnection.groupnameGetId(currentRoom) + " and fid=" + Main.loginUserid + ";";
			Statement s = SQLConnection.creat();
			ResultSet rs = s.executeQuery(sql);
			StringBuffer msg = new StringBuffer();
			while (rs.next()) {
				if (msg.length() == 0) msg.append(rs.getString("msg"));
				else msg.append("\r\n" + rs.getString("msg"));
			}
			showMessage.setText(showMessage.getText()+msg);
			rs.close();
			s.close();

			String sql2 = "update groupmessage set flag=1 where flag=0 and gid=" + SQLConnection.groupnameGetId(currentRoom) + " and fid=" + Main.loginUserid + ";";
			Statement s2 = SQLConnection.creat();
			s2.executeUpdate(sql2);
			s2.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
