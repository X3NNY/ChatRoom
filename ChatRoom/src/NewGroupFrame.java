import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.ImageIcon;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;

import javax.swing.JTextArea;

import com.mysql.jdbc.StringUtils;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.DataOutputStream;
import java.io.IOException;
import java.sql.SQLException;


public class NewGroupFrame {

	private JFrame frame;
	private JTextField groupname;
	private JPasswordField pwd;
	private Client client;
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
	public NewGroupFrame(Client client) {
		this.client = client;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("\u65B0\u5EFA\u7FA4\u7EC4");
		frame.setBounds(100, 100, 460, 497);
		frame.setLocationRelativeTo(null);//在屏幕中设置显示的位置
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JLabel lblNewLabel = new JLabel("\u65B0\u5EFA\u7FA4\u7EC4");
		lblNewLabel.setFont(new Font("宋体", Font.ITALIC, 16));
		lblNewLabel.setIcon(new ImageIcon(NewGroupFrame.class.getResource("/images/tabLeft.PNG")));

		JLabel lblNewLabel_1 = new JLabel("\u8BBE\u7F6E\u7FA4\u540D\u79F0");
		lblNewLabel_1.setFont(new Font("宋体", Font.ITALIC, 16));

		groupname = new JTextField();
		groupname.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("\u8BBE\u7F6E\u5BC6\u7801");
		lblNewLabel_2.setFont(new Font("宋体", Font.ITALIC, 16));

		pwd = new JPasswordField();

		JButton button = new JButton("\u7ACB\u5373\u521B\u5EFA");

		button.setFont(new Font("宋体", Font.ITALIC, 16));

		JButton btnNewButton = new JButton("\u8FD4\u56DE\u4E0A\u4E00\u7EA7");
		btnNewButton.setIcon(new ImageIcon(NewGroupFrame.class.getResource("/com/sun/javafx/scene/web/skin/Undo_16x16_JFX.png")));
		btnNewButton.setFont(new Font("宋体", Font.ITALIC, 16));
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
			}
		});

		JLabel lblNewLabel_3 = new JLabel("\u7FA4\u7B80\u4ECB");
		lblNewLabel_3.setFont(new Font("宋体", Font.ITALIC, 16));

		JTextArea descTxt = new JTextArea();
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
								.addGap(69)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addGroup(groupLayout.createSequentialGroup()
												.addComponent(lblNewLabel_3)
												.addContainerGap())
										.addGroup(groupLayout.createSequentialGroup()
												.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
														.addComponent(button)
														.addComponent(lblNewLabel_1)
														.addComponent(lblNewLabel_2))
												.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
														.addGroup(groupLayout.createSequentialGroup()
																.addGap(30)
																.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
																		.addComponent(lblNewLabel)
																		.addComponent(btnNewButton)))
														.addComponent(groupname, GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
														.addComponent(pwd, 194, 194, Short.MAX_VALUE)
														.addComponent(descTxt, GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE))
												.addGap(92))))
		);
		groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
								.addGap(34)
								.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
										.addGroup(groupLayout.createSequentialGroup()
												.addComponent(lblNewLabel)
												.addGap(57))
										.addGroup(groupLayout.createSequentialGroup()
												.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
														.addComponent(lblNewLabel_1)
														.addComponent(groupname, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
												.addPreferredGap(ComponentPlacement.UNRELATED)))
								.addGap(18)
								.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(pwd, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblNewLabel_2))
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addGroup(groupLayout.createSequentialGroup()
												.addGap(18)
												.addComponent(lblNewLabel_3))
										.addGroup(groupLayout.createSequentialGroup()
												.addGap(38)
												.addComponent(descTxt, GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)))
								.addGap(18)
								.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(button)
										.addComponent(btnNewButton))
								.addGap(47))
		);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {//创建群组
				int n = JOptionPane.showConfirmDialog(null, "是否立即创建群?");
				if(n != 0) return;
				String desc = descTxt.getText();
				String password = pwd.getText();
				String name = groupname.getText();
				try {
					Group group = new Group(name, password, desc, Main.loginUserid);
					group.setId(SQLConnection.groupnameGetId(name));
					if (!StringUtils.isNullOrEmpty(name)) {
						//操作数据库，查询是否可以创建此群
						if (!SQLConnection.isExistGroup(group)) {
							SQLConnection.insert(group);
							group.setId(SQLConnection.groupnameGetId(name));
							SQLConnection.updateGroup(group);
							try {
								DataOutputStream dos = new DataOutputStream(client.socket.getOutputStream());
								dos.writeUTF("@newgroup|" + Main.loginUsername + "|" + group.getGroupname());
								dos.flush();
							} catch (IOException se) {
								se.printStackTrace();
							}
							JOptionPane.showMessageDialog(null, "创建成功!!!");
							UserMainFrame.updateGroup();
						} else {
							JOptionPane.showMessageDialog(null, "创建失败,可能是群已经存在，请重试!!!");
						}
					} else {
						JOptionPane.showMessageDialog(null, "群名称不能为空!!");
					}
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
		});
		frame.getContentPane().setLayout(groupLayout);
	}
}
