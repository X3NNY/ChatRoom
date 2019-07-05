import java.awt.EventQueue;

import java.awt.Toolkit;
import java.awt.Font;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import com.mysql.jdbc.StringUtils;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class FriendRoomFrame {

	private JFrame frame;
	public String currentFriend;//当前会话好友
	public Client client;
	private JTextArea showMessage;
	private JTextArea sendMessage;
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
	public FriendRoomFrame() {
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setFont(new Font("宋体", Font.ITALIC, 16));

		JScrollPane scrollPane = new JScrollPane();

		sendMessage = new JTextArea();

		sendMessage.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.isControlDown() && e.getKeyCode() == 10) {
					sendMessageToFriend();
				}
			}
		});

		JButton btnNewButton = new JButton("\u53D1\u9001\u4FE1\u606F");
		btnNewButton.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.isControlDown() && e.getKeyCode() == 10) {
					sendMessageToFriend();
				}
			}
		});

		btnNewButton.setIcon(new ImageIcon(FriendRoomFrame.class.getResource("/images/ToolbarFont.png")));
		btnNewButton.setFont(new Font("宋体", Font.ITALIC, 16));

		JButton btnNewButton_1 = new JButton("\u8FD4\u56DE\u4E0A\u4E00\u7EA7");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {//返回上一级
				Client.areaMap.remove(currentFriend);
				frame.dispose();

			}
		});
		btnNewButton_1.setIcon(new ImageIcon(FriendRoomFrame.class.getResource("/com/sun/javafx/scene/web/skin/Undo_16x16_JFX.png")));
		btnNewButton_1.setFont(new Font("宋体", Font.ITALIC, 16));

		JButton btnNewButton_2 = new JButton("\u6E05\u7A7A");

		btnNewButton_2.setIcon(new ImageIcon(FriendRoomFrame.class.getResource("/images/Fail.gif")));
		btnNewButton_2.setFont(new Font("宋体", Font.ITALIC, 16));

		JButton button = new JButton("\u53D1\u9001\u6587\u4EF6");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {//发送文件
				JFileChooser chooser = new JFileChooser();
				int flag = chooser.showOpenDialog(frame);
				if (flag == JFileChooser.APPROVE_OPTION) {
					String filePath = chooser.getSelectedFile().getAbsolutePath();
					String fileName = chooser.getSelectedFile().getName();
					try {
						DataOutputStream out = new DataOutputStream(client.socket.getOutputStream());
						out.writeUTF("&&file&&");
						out.writeUTF(fileName);
						out.writeUTF(Main.loginUsername);
						out.writeUTF(currentFriend);
						FileInputStream fileInputStream = new FileInputStream(filePath);
						int data;
						while (-1 != (data = fileInputStream.read())) {
							out.writeInt(data);
						}
						out.writeInt(data);
						out.flush();
						fileInputStream.close();

						Date d = new Date();
						DateFormat df = new SimpleDateFormat("YYYY-MM-dd HH:mm");

						showMessage.setText(showMessage.getText() + df.format(d) + " " + Main.loginUsername + ": \r\n\t发送文件中...\r\n");
					} catch (Exception se) {
						se.printStackTrace();
					}

				}
			}
		});
		button.setIcon(new ImageIcon(FriendRoomFrame.class.getResource("/images/\u4E0A\u4F20\u6587\u4EF6.png")));
		button.setFont(new Font("宋体", Font.ITALIC, 16));

		JButton btnNewButton_3 = new JButton("\u6253\u5F00\u6587\u4EF6\u5939");
		btnNewButton_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {//打开文件夹
				File file = new File("C://ChatRoomFiles");
				if (!file.exists()) {
					file.mkdir();
				}
				try {
					java.awt.Desktop.getDesktop().open(file.getAbsoluteFile());
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		});
		btnNewButton_3.setIcon(new ImageIcon(FriendRoomFrame.class.getResource("/images/\u6587\u4EF6(1).png")));
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
								.addContainerGap()
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(sendMessage, GroupLayout.DEFAULT_SIZE, 363, Short.MAX_VALUE)
										.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 363, Short.MAX_VALUE)
										.addGroup(groupLayout.createSequentialGroup()
												.addComponent(btnNewButton)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(btnNewButton_2, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(btnNewButton_1))
										.addGroup(groupLayout.createSequentialGroup()
												.addComponent(button, GroupLayout.PREFERRED_SIZE, 165, GroupLayout.PREFERRED_SIZE)
												.addGap(18)
												.addComponent(btnNewButton_3, GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)))
								.addContainerGap())
		);
		groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
								.addContainerGap()
								.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 316, GroupLayout.PREFERRED_SIZE)
								.addGap(5)
								.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(button)
										.addComponent(btnNewButton_3))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(sendMessage, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(btnNewButton)
										.addComponent(btnNewButton_2)
										.addComponent(btnNewButton_1))
								.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);

		showMessage = new JTextArea();

		showMessage.setFont(new Font("宋体",Main.ft,Main.fontSize));
		showMessage.setForeground(FontFrame.cMap.get(Main.co));

		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {//发送消息
				sendMessageToFriend();
			}
		});

		//显示未读消息
		showUnreadMsg();

		scrollPane.setViewportView(showMessage);
		btnNewButton_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {//清空重置
				showMessage.setText("");
			}
		});
		scrollPane.setViewportView(showMessage);
		frame.getContentPane().setLayout(groupLayout);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(FriendRoomFrame.class.getResource("/images/11.png")));
		frame.setTitle(currentFriend+"-\u597D\u53CB\u79C1\u804A");
		frame.setBounds(100, 100, 396, 583);
		if (!Client.areaMap.containsKey(currentFriend) || Client.areaMap.get(currentFriend) == null) {
			Client.areaMap.put(currentFriend,showMessage);
		}
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		showMessage.setEditable(false);
	}

	private void sendMessageToFriend() {
		String send = sendMessage.getText();
		if(StringUtils.isNullOrEmpty(send)){
			JOptionPane.showMessageDialog(null, "消息不能为空!!!");
		}else{
			//client发送消息,操作数据库
			Date d = new Date();
			try {
				DataOutputStream bos = new DataOutputStream(client.socket.getOutputStream());
				//friendname|date username: \r\n\t|msg
				bos.writeUTF(currentFriend + "|" + Main.df.format(d) + " " + Main.loginUsername + ": \r\n\t|"  + send + "\r\n");
				bos.flush();

			} catch (Exception se) {
				se.printStackTrace();
			}
			showMessage.setText(showMessage.getText() + Main.df.format(d) + " " + Main.loginUsername + ": \r\n\t"  + send + "\r\n");
			sendMessage.setText("");
		}
	}

	public void showUnreadMsg() {
		try {
			int fid = SQLConnection.usernameGetId(currentFriend);
			String sql = "select * from message where flag=0 and fid=" + Main.loginUserid + " and uid=" + fid + ";";
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

			String sql2 = "update message set flag=1 where flag=0 and fid=" + Main.loginUserid + " and uid=" + fid + ";";
			Statement s2 = SQLConnection.creat();
			s2.executeUpdate(sql2);
			s2.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
