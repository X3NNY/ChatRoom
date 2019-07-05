import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.event.*;

import java.awt.Font;

import javax.swing.table.DefaultTableModel;

import com.mysql.jdbc.StringUtils;

import java.awt.Toolkit;
import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;


public class UserMainFrame {

	public JFrame frame;
	public static JTable groupTable;
	public static JTable friendTable;
	public Client client;

	public static JLabel adtxt;
	public static DefaultTableModel fmodel;
	public static DefaultTableModel gmodel;

	private Thread thread = new Thread() {
		@Override
		public void run() {
			while (true) {
				try {
					Thread.sleep(4500);
					fillFriendTable("select u.username,f.flag from friendtemp f,user u where f.fid = u.id and f.uid = " + Main.loginUserid + ";");
					fillGroupTable("select u.groupname from grouptemp f,`group` u where f.gid = u.id and f.uid = " + Main.loginUserid + ";");
					fillSystemText("select msg from systemmsg");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	};

	public static void updateFriend() {
		try {
			String sql = "select u.username,f.flag from friendtemp f,user u where f.fid = u.id and f.uid = " + Main.loginUserid + ";";
			Statement s = SQLConnection.creat();
			ResultSet rs = s.executeQuery(sql);
			String sql2 = "select id from message where flag=0 and fid=" + Main.loginUserid + " and uid=";
			fmodel.setRowCount(0);
			while (rs.next()) {
				Vector<String> v = new Vector<>();
				String tmp = rs.getString("username");

				Statement s2 = SQLConnection.creat();
				ResultSet rs2 = s2.executeQuery(sql2 + SQLConnection.usernameGetId(tmp) + ";");
				if (rs2.next()) {
					tmp = "* " + tmp;
				}
				if (rs.getInt("flag") == 1) {
					tmp = tmp + "[已拉黑]";
				}
				v.add(tmp);
				rs2.close();
				s2.close();
				fmodel.addRow(v);
			}
			s.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void updateGroup() {
		try {
			String sql = "select u.groupname from grouptemp f,`group` u where f.gid = u.id and f.uid = " + Main.loginUserid + ";";
			Statement s = SQLConnection.creat();
			ResultSet rs = s.executeQuery(sql);
			String sql2 = "select id from groupmessage where flag=0 and fid=" + Main.loginUserid + " and gid=";
			gmodel.setRowCount(0);
			while (rs.next()) {
				Vector<String> v = new Vector<>();
				String tmp = rs.getString("groupname");

				Statement s2 = SQLConnection.creat();
				ResultSet rs2 = s2.executeQuery(sql2 + SQLConnection.groupnameGetId(tmp));

				if (rs2.next()) {
					v.add("* " + tmp);
				} else {
					v.add(tmp);
				}
				rs2.close();
				s2.close();
				gmodel.addRow(v);
			}
			s.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void fillSystemText(String sql) {
		try {
			Statement s = SQLConnection.creat();
			ResultSet rs = s.executeQuery(sql);
			if (rs.next()) {
				adtxt.setText(rs.getString("msg"));
			}
			rs.close();
			s.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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
	public UserMainFrame() {
		thread.start();
	}


	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(UserMainFrame.class.getResource("/images/11.png")));
		frame.setFont(new Font("Dialog", Font.PLAIN, 12));
		frame.setTitle(Main.loginUsername+"-\u7528\u6237\u4E3B\u754C\u9762");
		frame.setBounds(100, 100, 527, 453);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JScrollPane scrollPane = new JScrollPane();

		JScrollPane scrollPane_1 = new JScrollPane();

		JLabel lblNewLabel = new JLabel("\u7CFB\u7EDF\u516C\u544A:");
		lblNewLabel.setIcon(new ImageIcon(UserMainFrame.class.getResource("/images/\u516C\u544A(1).png")));
		lblNewLabel.setFont(new Font("宋体", Font.ITALIC, 16));

		adtxt = new JLabel();
		adtxt.setForeground(Color.red);
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
								.addGap(26)
								.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED, 131, Short.MAX_VALUE)
								.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 162, GroupLayout.PREFERRED_SIZE)
								.addGap(36))
						.addGroup(groupLayout.createSequentialGroup()
								.addComponent(lblNewLabel)
								.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(adtxt, GroupLayout.DEFAULT_SIZE, 419, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
								.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblNewLabel)
										.addComponent(adtxt))
								.addGap(5)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
										.addComponent(scrollPane_1, 0, 0, Short.MAX_VALUE)
										.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE))
								.addContainerGap(64, Short.MAX_VALUE))
		);

		friendTable = new JTable();
		friendTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					if (friendTable.getSelectedRow() == -1) return;
					String select = (String) friendTable.getValueAt(friendTable.getSelectedRow(), 0);
					if (StringUtils.isNullOrEmpty(select)) {
						JOptionPane.showMessageDialog(null, "请选择好友后再开始!!");
					} else {
						//私聊...
						FriendRoomFrame ff = new FriendRoomFrame();
						if (select.startsWith("* ")) select = select.substring(2);
						if (select.indexOf('[') != -1) select = select.substring(0,select.indexOf('['));
						ff.currentFriend = select;
						ff.client = client;
						ff.start();
					}
				}
			}
		});
		friendTable.setModel(new DefaultTableModel(
				new Object[][]{
				},
				new String[]{
						"\u597D\u53CB\u5217\u8868"
				}
		) {
			boolean[] columnEditables = new boolean[]{
					false
			};

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		friendTable.setFont(new Font("宋体", Font.ITALIC, 16));
		scrollPane_1.setViewportView(friendTable);

		groupTable = new JTable();
		groupTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					if (groupTable.getSelectedRow() == -1) return;
					String select = (String) groupTable.getValueAt(groupTable.getSelectedRow(), 0);
					if (StringUtils.isNullOrEmpty(select)) {
						JOptionPane.showMessageDialog(null, "请选择聊天群组后再开始!!");
					} else {
						//群聊...
						GroupRoomFrame gf = new GroupRoomFrame();
						if (select.startsWith("* ")) select = select.substring(2);
						gf.currentRoom = select;
						gf.client = client;
						gf.start();
					}
				}
			}
		});
		groupTable.setModel(new DefaultTableModel(
				new Object[][]{
				},
				new String[]{
						"\u7FA4\u540D\u79F0"
				}
		) {
			boolean[] columnEditables = new boolean[]{
					false
			};

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		groupTable.setFont(new Font("宋体", Font.ITALIC, 16));
		scrollPane.setViewportView(groupTable);
		frame.getContentPane().setLayout(groupLayout);

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) { //关闭按钮
				try {
					SQLConnection.updateUserInfo(Main.loginUsername,0);
					frame.dispose();
					client.closed();
				} catch (Exception se) {
					se.printStackTrace();
				}
			}

		});

		JMenu menu = new JMenu("\u7FA4\u64CD\u4F5C");
		menu.setFont(new Font("Microsoft YaHei UI", Font.ITALIC, 16));
		menu.setIcon(new ImageIcon(UserMainFrame.class.getResource("/images/tabLeft.PNG")));
		menuBar.add(menu);

		JMenuItem menuItem = new JMenuItem("\u52A0\u5165\u7FA4");
		menuItem.addActionListener(new ActionListener() { //加入群
			public void actionPerformed(ActionEvent e) {//加入群
				new GroupJoinFrm(client).start();
			}
		});
		menuItem.setIcon(new ImageIcon(UserMainFrame.class.getResource("/images/copy.png")));
		menuItem.setFont(new Font("Microsoft YaHei UI", Font.ITALIC, 12));

		menu.add(menuItem);

		JMenuItem menuItem_1 = new JMenuItem("\u5220\u9664/\u4FEE\u6539\u7FA4");
		menuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {//操作群
				GroupOpersFrame df = new GroupOpersFrame(client);
				df.start();
			}
		});
		menuItem_1.setIcon(new ImageIcon(UserMainFrame.class.getResource("/images/close.png")));
		menuItem_1.setFont(new Font("Microsoft YaHei UI", Font.ITALIC, 12));

		menu.add(menuItem_1);

		JMenuItem menuItem_3 = new JMenuItem("\u65B0\u5EFA\u7FA4");
		ImageIcon ic = new ImageIcon(UserMainFrame.class.getResource("/images/\u661F\u661F.png"));
		menuItem_3.setIcon(ic);
		ic.setImage(ic.getImage().getScaledInstance(20, 20,
				Image.SCALE_DEFAULT));
		menuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {//新建群
				NewGroupFrame nf = new NewGroupFrame(client);
				nf.start();
			}
		});
		menuItem_3.setFont(new Font("Microsoft YaHei UI", Font.ITALIC, 12));
		menu.add(menuItem_3);

		JMenu menu_1 = new JMenu("\u7528\u6237\u64CD\u4F5C");
		menu_1.setFont(new Font("Microsoft YaHei UI", Font.ITALIC, 16));
		menu_1.setIcon(new ImageIcon(UserMainFrame.class.getResource("/images/tabLeft2.PNG")));
		menuBar.add(menu_1);

		JMenuItem menuItem_4 = new JMenuItem("\u65B0\u52A0\u597D\u53CB");
		menuItem_4.addActionListener(new ActionListener() { //添加好友
			public void actionPerformed(ActionEvent e) {//新加好友
				new FollowUserFrame().start();
			}
		});
		menuItem_4.setIcon(new ImageIcon(UserMainFrame.class.getResource("/images/copy.png")));
		menuItem_4.setFont(new Font("Microsoft YaHei UI", Font.ITALIC, 12));
		menu_1.add(menuItem_4);

		JMenuItem menuItem_5 = new JMenuItem("\u4FEE\u6539\u597D\u53CB");
		menuItem_5.addActionListener(new ActionListener() { //删除好友
			public void actionPerformed(ActionEvent e) {//删除好友
				new DelFriendFrame(client).start();
			}
		});
		menuItem_5.setIcon(new ImageIcon(UserMainFrame.class.getResource("/images/close.png")));
		menuItem_5.setFont(new Font("Microsoft YaHei UI", Font.ITALIC, 12));
		menu_1.add(menuItem_5);

		JMenuItem mntmNewMenuItem_3 = new JMenuItem("\u4FEE\u6539\u5BC6\u7801");
		mntmNewMenuItem_3.setIcon(new ImageIcon(UserMainFrame.class.getResource("/images/\u5BC6\u78011.png")));
		mntmNewMenuItem_3.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
		mntmNewMenuItem_3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new PasswordFrame().start();
			}
		});
		menu_1.add(mntmNewMenuItem_3);

		JMenuItem menuItem_6 = new JMenuItem("\u6CE8\u9500\u7528\u6237");
		menuItem_6.setFont(new Font("Microsoft YaHei UI", Font.ITALIC, 16));
		menuItem_6.setIcon(new ImageIcon(UserMainFrame.class.getResource("/com/sun/javafx/scene/control/skin/caspian/images/capslock-icon.png")));
		menuItem_6.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {//返回上一级
				try {
					SQLConnection.updateUserInfo(Main.loginUsername, 0);
					frame.dispose();
					Main.start();
				} catch (Exception se) {
					se.printStackTrace();
				}
			}
		});
		menuBar.add(menuItem_6);

		JMenu mnNewMenu = new JMenu("\u5176\u5B83");
		mnNewMenu.setIcon(new ImageIcon(UserMainFrame.class.getResource("/images/\u8BBE\u7F6Euser.png")));
		mnNewMenu.setFont(new Font("Microsoft YaHei UI", Font.ITALIC, 15));
		menuBar.add(mnNewMenu);

		JMenuItem mntmNewMenuItem = new JMenuItem("\u5BFC\u51FA");
		mntmNewMenuItem.addActionListener(new ActionListener() { //导出聊天
			public void actionPerformed(ActionEvent e) {//导出
				new ExportFrame().start();
			}
		});
		mntmNewMenuItem.setIcon(new ImageIcon(UserMainFrame.class.getResource("/images/\u5BFC\u51FA.png")));
		mntmNewMenuItem.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
		mnNewMenu.add(mntmNewMenuItem);

		JMenuItem mntmNewMenuItem_1 = new JMenuItem("\u5B57\u4F53");
		mntmNewMenuItem_1.addActionListener(new ActionListener() { //设置字体
			public void actionPerformed(ActionEvent e) {//字体
				new FontFrame().start();
			}
		});
		mntmNewMenuItem_1.setIcon(new ImageIcon(UserMainFrame.class.getResource("/images/\u5B57\u4F53.png")));
		mntmNewMenuItem_1.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
		mnNewMenu.add(mntmNewMenuItem_1);

		JMenuItem mntmNewMenuItem_2 = new JMenuItem("\u5173\u4E8E");
		mntmNewMenuItem_2.addActionListener(new ActionListener() { //关于
			public void actionPerformed(ActionEvent e) {//About
				new AboutFrame().start();
			}
		});
		mntmNewMenuItem_2.setIcon(new ImageIcon(UserMainFrame.class.getResource("/images/\u5173\u4E8E\u6211\u4EEC.png")));
		mntmNewMenuItem_2.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
		mnNewMenu.add(mntmNewMenuItem_2);
		gmodel = (DefaultTableModel) groupTable.getModel();
		fmodel = (DefaultTableModel) friendTable.getModel();
		fillFriendTable("select u.username,f.flag from friendtemp f,user u where f.fid = u.id and f.uid = " + Main.loginUserid + ";");
		fillGroupTable("select u.groupname from grouptemp f,`group` u where f.gid = u.id and f.uid = " + Main.loginUserid + ";");
		fillSystemText("select msg from systemmsg");
	}

	public void fillGroupTable(String sql) {
		//查询数据库,向表中填充数据
		try {
			Statement s = SQLConnection.creat();
			ResultSet rs = s.executeQuery(sql);
			String sql2 = "select id from groupmessage where flag=0 and fid=" + Main.loginUserid + " and gid=";
			gmodel.setRowCount(0);
			while (rs.next()) {
				Vector<String> v = new Vector<>();
				String tmp = rs.getString("groupname");

				Statement s2 = SQLConnection.creat();
				ResultSet rs2 = s2.executeQuery(sql2 + SQLConnection.groupnameGetId(tmp));

				if (rs2.next()) {
					v.add("* " + tmp);
				} else {
					v.add(tmp);
				}
				rs2.close();
				s2.close();
				gmodel.addRow(v);
			}
			rs.close();
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void fillFriendTable(String sql) {
		//查询数据库,向表中填充数据
		try {
			Statement s = SQLConnection.creat();
			ResultSet rs = s.executeQuery(sql);
			String sql2 = "select id from message where flag=0 and fid=" + Main.loginUserid + " and uid=";
			fmodel.setRowCount(0);
			while (rs.next()) {
				Vector<String> v = new Vector<>();
				String tmp = rs.getString("username");

				Statement s2 = SQLConnection.creat();
				ResultSet rs2 = s2.executeQuery(sql2 + SQLConnection.usernameGetId(tmp) + ";");
				if (rs2.next()) {
					tmp = "* " + tmp;
				}
				if (rs.getInt("flag") == 1) {
					tmp = tmp + "[已拉黑]";
				}
				v.add(tmp);
				rs2.close();
				s2.close();
				fmodel.addRow(v);
			}
			rs.close();
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
