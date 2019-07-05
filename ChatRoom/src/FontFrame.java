import java.awt.*;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.table.DefaultTableModel;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;


public class FontFrame {

	private JFrame frame;
	private JTable fontTable;
	private JTextField sizeField;
	public static HashMap<String,Color> cMap = new HashMap<>();
	int ft = 0;//1-粗体，0-正体，2-斜体
	int fontSize = 12;
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
	public FontFrame() {

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("\u8BBE\u7F6E\u5B57\u4F53");
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(FontFrame.class.getResource("/images/\u5B57\u4F53.png")));
		frame.setBounds(100, 100, 347, 512);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JScrollPane scrollPane = new JScrollPane();
		
		sizeField = new JTextField();
		sizeField.setFont(new Font("宋体", Font.ITALIC, 16));
		sizeField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("\u5B57\u4F53\u5927\u5C0F");
		lblNewLabel.setFont(new Font("宋体", Font.ITALIC, 16));
		
		JButton bold = new JButton("\u7C97\u4F53");
		bold.setFont(new Font("宋体", Font.ITALIC, 16));
		bold.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {//粗体
				ft = 1;
				JOptionPane.showMessageDialog(null,"设置为粗体！");
			}
		});

		JButton btnNewButton_1 = new JButton("\u659C\u4F53");
		btnNewButton_1.setFont(new Font("宋体", Font.ITALIC, 16));
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {//斜体
				ft = 2;
				JOptionPane.showMessageDialog(null,"设置为斜体！");
			}
		});


		JButton btnNewButton_2 = new JButton("\u6B63\u4F53");
		btnNewButton_2.setFont(new Font("宋体", Font.ITALIC, 16));
		btnNewButton_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {//正体
				ft = 0;
				JOptionPane.showMessageDialog(null,"设置为正体！");
			}
		});

		JButton btnNewButton_3 = new JButton("\u786E\u8BA4");
		btnNewButton_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {//确认
				try {
					fontSize = Integer.parseInt(sizeField.getText());
				} catch (Exception se) {
					JOptionPane.showMessageDialog(null,"请重新检查字体大小是否合法！");
					return;
				}
				int n = fontTable.getSelectedRow();
				if (n == -1) {
					return ;
				}
				String co = (String) fontTable.getValueAt(n,0);
				updateUserFont(co);

			}
		});
		btnNewButton_3.setIcon(new ImageIcon(FontFrame.class.getResource("/images/Success.gif")));
		btnNewButton_3.setFont(new Font("宋体", Font.ITALIC, 16));
		
		JButton btnNewButton_4 = new JButton("\u91CD\u7F6E");
		btnNewButton_4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {//恢复默认
				ft = 0;
				fontSize = 12;
				updateUserFont("BLACK");
			}
		});
		btnNewButton_4.setIcon(new ImageIcon(FontFrame.class.getResource("/images/Fail.gif")));
		btnNewButton_4.setFont(new Font("宋体", Font.ITALIC, 16));
		
		JButton btnNewButton_5 = new JButton("\u8FD4\u56DE\u4E0A\u4E00\u7EA7");
		btnNewButton_5.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {//返回上一级
			   frame.dispose();
			}
		});
		btnNewButton_5.setIcon(new ImageIcon(FontFrame.class.getResource("/com/sun/javafx/scene/web/skin/Undo_16x16_JFX.png")));
		btnNewButton_5.setFont(new Font("宋体", Font.ITALIC, 16));
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblNewLabel)
									.addPreferredGap(ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
									.addComponent(sizeField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addComponent(bold, GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
								.addComponent(btnNewButton_2, GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
								.addComponent(btnNewButton_1, GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnNewButton_3)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnNewButton_4)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(btnNewButton_5)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(45)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(sizeField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel))
							.addGap(13)
							.addComponent(bold)
							.addGap(13)
							.addComponent(btnNewButton_2)
							.addGap(12)
							.addComponent(btnNewButton_1)))
					.addGap(85)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNewButton_3)
						.addComponent(btnNewButton_4)
						.addComponent(btnNewButton_5))
					.addContainerGap())
		);
		
		fontTable = new JTable();
		fontTable.setFont(new Font("宋体", Font.ITALIC, 16));
		fontTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"\u5B57\u4F53\u989C\u8272"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		scrollPane.setViewportView(fontTable);


		DefaultTableModel dt = (DefaultTableModel) fontTable.getModel();
		dt.setRowCount(0);
		for (Map.Entry<String,Color> e : cMap.entrySet()) {
			Vector<String> v = new Vector<>();
			v.add(e.getKey());
			dt.addRow(v);
		}
		frame.getContentPane().setLayout(groupLayout);
	}

	private void updateUserFont(String co) {
		try {

			SQLConnection.insertUserFont(Main.loginUsername,co,ft,fontSize);
			Main.fontSize = fontSize;
			Main.co = co;
			Main.ft = ft;
			JOptionPane.showMessageDialog(null,"设置成功！");
		} catch (Exception se) {
			se.printStackTrace();
		}
	}
}
