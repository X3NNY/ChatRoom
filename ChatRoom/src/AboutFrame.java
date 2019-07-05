import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class AboutFrame {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public void start() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					initialize();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AboutFrame() {
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("\u5173\u4E8E");
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(AboutFrame.class.getResource("/images/\u5173\u4E8E\u6211\u4EEC.png")));
		frame.setFont(new Font("Dialog", Font.ITALIC, 16));
		frame.setBounds(100, 100, 413, 542);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JLabel lblNewLabel = new JLabel("\u804A\u5929\u5BA4v3.2");
		lblNewLabel.setFont(new Font("宋体", Font.ITALIC, 16));

		JScrollPane scrollPane = new JScrollPane();

		JButton btnNewButton = new JButton("\u8FD4\u56DE\u4E0A\u4E00\u7EA7");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {//返回上一级
				frame.dispose();
			}
		});
		btnNewButton.setIcon(new ImageIcon(AboutFrame.class.getResource("/com/sun/javafx/scene/web/skin/Undo_16x16_JFX.png")));
		btnNewButton.setFont(new Font("宋体", Font.ITALIC, 16));
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addGroup(groupLayout.createSequentialGroup()
												.addContainerGap()
												.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 377, GroupLayout.PREFERRED_SIZE))
										.addGroup(groupLayout.createSequentialGroup()
												.addGap(124)
												.addComponent(btnNewButton))
										.addGroup(groupLayout.createSequentialGroup()
												.addGap(157)
												.addComponent(lblNewLabel)))
								.addContainerGap(14, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
								.addGap(29)
								.addComponent(lblNewLabel)
								.addGap(18)
								.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 389, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(btnNewButton)
								.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);

		JTextArea txtrSd = new JTextArea();
		txtrSd.setText("此软件由Xenny&Pandora独立开发完成。\n" +
						"采用Java.Swing编写界面并利用Java.Socket进行实时通信，同时在My\n" +
						"sql端存储离线信息。\n" +
						"本软件可以实现在线聊天，离线留言，新建/编辑/加入/退出群，添加/拉\n" +
						"黑/删除好友，可以实现文件收发功能，除此之外，还有一些设置字体，\n" +
						"导出聊天消息等功能。\n" +
						"消耗资源小，功能齐全，操作方便。\n" +
						"感谢你使用本软件！");
		scrollPane.setViewportView(txtrSd);
		frame.getContentPane().setLayout(groupLayout);
		txtrSd.setEditable(false);
	}

}
