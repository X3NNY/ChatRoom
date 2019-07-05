import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;

import javax.swing.ImageIcon;

import java.awt.Toolkit;
import java.sql.SQLException;
import java.util.Vector;


public class FollowUserFrame {

	private JFrame frame;
	private JTextField selectFriend;
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
	public FollowUserFrame() {
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(FollowUserFrame.class.getResource("/images/11.png")));
		frame.setTitle("\u5173\u6CE8\u7528\u6237");

		selectFriend = new JTextField();
		selectFriend.setColumns(10);


		JButton btnNewButton = new JButton("\u641C\u7D22");
		btnNewButton.setFont(new Font("ËÎÌå", Font.ITALIC, 16));
		btnNewButton.addMouseListener(new MouseAdapter() {		//搜索此人存在否
			@Override
			public void mouseClicked(MouseEvent e) {
				String select = selectFriend.getText();
				try {
					if (SQLConnection.isExistUser(select)) {
						JOptionPane.showMessageDialog(null,"存在此用户！");
					} else {
						JOptionPane.showMessageDialog(null,"不存在此用户！");
					}
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		});

		JButton btnNewButton_1 = new JButton("\u6DFB\u52A0");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {//Ìí¼ÓºÃÓÑ
				int n = JOptionPane.showConfirmDialog(null, "确定加他为好友ta？");
				if(n == 0){
					String select = selectFriend.getText();
					try {
						if (SQLConnection.isExistUser(select)) {
							int fid = SQLConnection.usernameGetId(select);
							SQLConnection.addUser(Main.loginUserid,fid);

							UserMainFrame.updateFriend();

							JOptionPane.showMessageDialog(null,"添加好友成功！");
						} else {
							JOptionPane.showMessageDialog(null,"不存在此用户！");
						}
					} catch (SQLException se) {
						se.printStackTrace();
					}
				}
			}
		});
		btnNewButton_1.setFont(new Font("ËÎÌå", Font.ITALIC, 16));

		JButton btnNewButton_2 = new JButton("\u8FD4\u56DE\u4E0A\u4E00\u7EA7");
		btnNewButton_2.setIcon(new ImageIcon(FollowUserFrame.class.getResource("/com/sun/javafx/scene/web/skin/Undo_16x16_JFX.png")));
		btnNewButton_2.setFont(new Font("ËÎÌå", Font.ITALIC, 16));
		btnNewButton_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
			}
		});
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
								.addGap(54)
								.addComponent(selectFriend, GroupLayout.PREFERRED_SIZE, 279, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btnNewButton)
								.addContainerGap(38, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
								.addGap(57)
								.addComponent(btnNewButton_1)
								.addPreferredGap(ComponentPlacement.RELATED, 176, Short.MAX_VALUE)
								.addComponent(btnNewButton_2)
								.addGap(39))
		);
		groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
								.addGap(41)
								.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(selectFriend, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(btnNewButton))
								.addPreferredGap(ComponentPlacement.RELATED, 134, Short.MAX_VALUE)
								.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
										.addGroup(groupLayout.createSequentialGroup()
												.addComponent(btnNewButton_1)
												.addGap(34))
										.addGroup(groupLayout.createSequentialGroup()
												.addComponent(btnNewButton_2)
												.addGap(31))))
		);
		frame.getContentPane().setLayout(groupLayout);
		frame.setBounds(100, 100, 450, 300);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

}
