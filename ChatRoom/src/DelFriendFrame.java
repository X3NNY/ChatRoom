import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.Toolkit;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ImageIcon;


public class DelFriendFrame {

    private JFrame frame;
    private JTable delTable;
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
                    //Thread.sleep(100);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public DelFriendFrame(Client client) {
        this.client = client;
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }
        });
        frame.setFont(new Font("Dialog", Font.ITALIC, 16));
        frame.setTitle("\u4FEE\u6539\u597D\u53CB");
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(DelFriendFrame.class.getResource("/images/11.png")));
        frame.setBounds(100, 100, 248, 499);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JScrollPane scrollPane = new JScrollPane();

        JButton btnNewButton = new JButton("\u70B9\u51FB\u5220\u9664");
        btnNewButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {//删除好友
                int n = delTable.getSelectedRow();
                if(n == -1) return;
                String friendName = (String) delTable.getValueAt(n,0);
                //操作数据库... 一个一个删
                try {
                    if (friendName.indexOf('[') != -1) friendName = friendName.substring(0,friendName.indexOf('['));
                    UserMainFrame.updateFriend();
                    SQLConnection.deleteFriend(Main.loginUsername,friendName);
                    SQLConnection.deleteFriend(friendName,Main.loginUsername);
                    try {
                        int uid = SQLConnection.usernameGetId(Main.loginUsername);
                        fillFriendTable("select u.username,f.flag from friendtemp f,user u where f.fid = u.id and f.uid = " + uid + ";");
                    } catch (SQLException sse) {
                        sse.printStackTrace();
                    }
                    JOptionPane.showMessageDialog(null, "删除好友成功!!!");
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            }
        });
        btnNewButton.setFont(new Font("宋体", Font.ITALIC, 16));

        JButton btnNewButton_1 = new JButton("\u8FD4\u56DE");
        btnNewButton_1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
            }
        });
        btnNewButton_1.setIcon(new ImageIcon(DelFriendFrame.class.getResource("/com/sun/javafx/scene/web/skin/Undo_16x16_JFX.png")));
        btnNewButton_1.setFont(new Font("宋体", Font.ITALIC, 16));

        JButton btnNewButton_2 = new JButton("\u62C9\u9ED1");

        btnNewButton_2.setFont(new Font("宋体", Font.ITALIC, 16));

        JButton btnNewButton_3 = new JButton("\u89E3\u9664\u62C9\u9ED1");
        btnNewButton_2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {//拉黑好友
                int n = delTable.getSelectedRow();
                if(n == -1) return;
                String friendName = (String) delTable.getValueAt(n,0);
                try {
                    if (friendName.indexOf('[') != -1) {
                        JOptionPane.showMessageDialog(null, "已经拉黑！");
                        return;
                    }
                    UserMainFrame.updateFriend();
                    SQLConnection.pullBlack(Main.loginUsername,friendName);
                    DataOutputStream dos = new DataOutputStream(client.socket.getOutputStream());
                    dos.writeUTF("@pullblack|" + Main.loginUsername + "|" + friendName);
                    dos.flush();
                    delTable.setValueAt(friendName+"[已拉黑]",n,0);
                } catch (SQLException se) {
                    se.printStackTrace();
                } catch (IOException se) {
                    se.printStackTrace();
                }
            }
        });
        btnNewButton_3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {//解除拉黑
                int n = delTable.getSelectedRow();
                if(n == -1) return;
                String friendName = (String) delTable.getValueAt(n,0);
                try {
                    if (friendName.indexOf('[') == -1) {
                        JOptionPane.showMessageDialog(null,"改好友未被拉黑！");
                        return;
                    }
                    friendName = friendName.substring(0,friendName.indexOf('['));
                    SQLConnection.unPullBlack(Main.loginUsername,friendName);
                    UserMainFrame.updateFriend();
                    DataOutputStream dos = new DataOutputStream(client.socket.getOutputStream());
                    dos.writeUTF("@unpullblack|" + Main.loginUsername + "|" + friendName);
                    delTable.setValueAt(friendName,n,0);
                } catch (Exception se) {
                    se.printStackTrace();
                }
            }
        });
        btnNewButton_3.setFont(new Font("宋体", Font.ITALIC, 16));
        GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
        groupLayout.setHorizontalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
                                        .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
                                        .addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
                                                .addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
                                                        .addComponent(btnNewButton_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(btnNewButton, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
                                                        .addComponent(btnNewButton_2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(btnNewButton_3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                .addGap(24))
        );
        groupLayout.setVerticalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 350, GroupLayout.PREFERRED_SIZE)
                                .addGap(18)
                                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(btnNewButton)
                                        .addComponent(btnNewButton_2))
                                .addGap(18)
                                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(btnNewButton_1)
                                        .addComponent(btnNewButton_3))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        delTable = new JTable();
        delTable.setModel(new DefaultTableModel(
                new Object[][] {
                },
                new String[] {
                        "\u597D\u53CB\u540D\u79F0"
                }
        ) {
            boolean[] columnEditables = new boolean[] {
                    false
            };
            public boolean isCellEditable(int row, int column) {
                return columnEditables[column];
            }
        });
        scrollPane.setViewportView(delTable);
        frame.getContentPane().setLayout(groupLayout);
        fillFriendTable("select u.username,f.flag from friendtemp f,user u where f.fid=u.id and f.uid=" + Main.loginUserid + ";");
    }
    private void fillFriendTable(String sql){
        DefaultTableModel dt = (DefaultTableModel) delTable.getModel();
        dt.setRowCount(0);
        //查询数据库,向表中填充数据
        try {
            Statement s = SQLConnection.creat();
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                Vector<String> v = new Vector<>();
                String tmp = rs.getString("username");
                if (rs.getInt("flag")!=0) {
                    tmp = tmp + "[已拉黑]";
                }
                v.add(tmp);
                dt.addRow(v);
            }
            rs.close();
            s.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
