import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Toolkit;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextArea;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.Statement;


public class GroupADFrame {

	private JFrame frame;
	private String groupName;

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
	public GroupADFrame(String groupName) {
		this.groupName = groupName;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(GroupADFrame.class.getResource("/images/1073830.png")));
		frame.setTitle("\u7FA4\u516C\u544A");
		frame.setBounds(100, 100, 450, 489);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JScrollPane scrollPane = new JScrollPane();

		JButton btnNewButton = new JButton("\u5173\u95ED");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {//关闭
				frame.dispose();
			}
		});
		btnNewButton.setIcon(new ImageIcon(GroupADFrame.class.getResource("/images/Fail.gif")));
		btnNewButton.setFont(new Font("ËÎÌå", Font.ITALIC, 16));
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
								.addGap(26)
								.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 384, GroupLayout.PREFERRED_SIZE)
								.addContainerGap(32, Short.MAX_VALUE))
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
								.addContainerGap(163, Short.MAX_VALUE)
								.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 119, GroupLayout.PREFERRED_SIZE)
								.addGap(160))
		);
		groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
								.addGap(38)
								.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 331, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
								.addComponent(btnNewButton)
								.addGap(24))
		);

		JTextArea textArea = new JTextArea();
		try {
			int gid = SQLConnection.groupnameGetId(groupName);
			textArea.setText(getDesc("select `desc` from `group` where id=" + gid + ";"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		scrollPane.setViewportView(textArea);
		frame.getContentPane().setLayout(groupLayout);
		textArea.setEditable(false);
	}

	private String getDesc(String sql) {
		try {
			Statement s = SQLConnection.creat();
			ResultSet rs = s.executeQuery(sql);
			String tmp = "";
			while (rs.next()) {
				tmp = rs.getString("desc");
			}
			rs.close();
			s.close();
			return tmp;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
}
