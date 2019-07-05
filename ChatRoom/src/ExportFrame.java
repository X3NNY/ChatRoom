import java.awt.EventQueue;

import javax.swing.*;
import java.awt.Toolkit;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;


public class ExportFrame {

    private JFrame frame;
    private JTable friendTable;

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
    public ExportFrame() {
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setResizable(false);
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(ExportFrame.class.getResource("/images/36504.png")));
        frame.setTitle("\u5BFC\u51FA");
        frame.setBounds(100, 100, 263, 516);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JScrollPane scrollPane = new JScrollPane();

        JButton btnNewButton = new JButton("\u5F00\u59CB\u5BFC\u51FA");
        btnNewButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {//开始导出
                int n = friendTable.getSelectedRow();
                if (n == -1) {
                    return;
                }
                String friendName = (String) friendTable.getValueAt(n,0);
                Date d = new Date();
                Format ft = new SimpleDateFormat("YYYY-MM-dd");
                File file = new File("c://ChatRoomFiles//"+ft.format(d)+"-" + Main.loginUsername + "&"+friendName+"的聊天记录.txt");
                try {
                    FileOutputStream fos = new FileOutputStream(file.getAbsoluteFile());
                    int fid = SQLConnection.usernameGetId(friendName);
                    Vector<String> msg = getMessage("select msg from message where (uid="+fid+ " and fid=" + Main.loginUserid + ") or (uid=" + Main.loginUserid + " and fid=" + fid + ");");
                    for (String message : msg) {
                        fos.write(message.getBytes());
                    }
                    fos.flush();
                    fos.close();
                    JOptionPane.showMessageDialog(null,"导出成功，存储在\""+file.getAbsolutePath()+"\"下！");
                } catch (Exception se) {
                    se.printStackTrace();
                }
            }
        });
        btnNewButton.setIcon(new ImageIcon(ExportFrame.class.getResource("/images/\u5BFC\u51FA.png")));
        btnNewButton.setFont(new Font("宋体", Font.ITALIC, 16));

        JButton btnNewButton_1 = new JButton("\u8FD4\u56DE");
        btnNewButton_1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {//返回上一级
                frame.dispose();
            }
        });
        btnNewButton_1.setIcon(new ImageIcon(ExportFrame.class.getResource("/com/sun/javafx/scene/web/skin/Undo_16x16_JFX.png")));
        GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
        groupLayout.setHorizontalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addGap(16)
                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                        .addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
                                                .addComponent(btnNewButton)
                                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                                .addComponent(btnNewButton_1, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(ComponentPlacement.RELATED))
                                        .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 222, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(31, Short.MAX_VALUE))
        );
        groupLayout.setVerticalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addGap(21)
                                .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 368, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(btnNewButton)
                                        .addComponent(btnNewButton_1))
                                .addGap(24))
        );

        friendTable = new JTable();
        friendTable.setModel(new DefaultTableModel(
                new Object[][] {
                },
                new String[] {
                        "\u597D\u53CB\u8BB0\u5F55"
                }
        ) {
            boolean[] columnEditables = new boolean[] {
                    false
            };
            public boolean isCellEditable(int row, int column) {
                return columnEditables[column];
            }
        });
        friendTable.setFont(new Font("宋体", Font.ITALIC, 16));
        scrollPane.setViewportView(friendTable);
        frame.getContentPane().setLayout(groupLayout);

        try {
            fillFriendTable("select u.username from friendtemp f,user u where f.fid = u.id and f.uid = " + Main.loginUserid + ";");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void fillFriendTable(String sql) {
        DefaultTableModel dt = (DefaultTableModel) friendTable.getModel();
        dt.setRowCount(0);
        try {
            Statement s = SQLConnection.creat();
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                Vector<String> v = new Vector<>();
                v.add(rs.getString("username"));
                dt.addRow(v);
            }
            rs.close();
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Vector<String> getMessage(String sql) {
        try {
            Statement s = SQLConnection.creat();
            ResultSet rs = s.executeQuery(sql);
            Vector<String> v = new Vector<>();
            while (rs.next()) {
                v.add(rs.getString("msg"));
            }
            rs.close();
            s.close();
            return v;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
