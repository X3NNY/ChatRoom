import java.awt.EventQueue;

import javax.swing.*;
import java.awt.Toolkit;
import javax.swing.GroupLayout.Alignment;
import java.awt.Font;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Statement;


public class SystemFrame {

    private JFrame frame;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    SystemFrame window = new SystemFrame();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public SystemFrame() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setTitle("\u7CFB\u7EDF\u516C\u544A");
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(SystemFrame.class.getResource("/images/\u8BBE\u7F6Euser.png")));
        frame.setBounds(100, 100, 376, 411);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel lblNewLabel = new JLabel("\u8BBE\u7F6E\u7CFB\u7EDF\u516C\u544A");
        lblNewLabel.setIcon(new ImageIcon(SystemFrame.class.getResource("/images/\u516C\u544A.png")));
        lblNewLabel.setFont(new Font("Dialog", Font.ITALIC, 16));
        lblNewLabel.setEnabled(true);

        JTextArea adTxt = new JTextArea();

        JButton btnNewButton_2 = new JButton("\u91CD\u7F6E");
        btnNewButton_2.setIcon(new ImageIcon(SystemFrame.class.getResource("/com/sun/javafx/scene/web/skin/Redo_16x16_JFX.png")));
        btnNewButton_2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {//重置
                adTxt.setText("");
            }
        });
        btnNewButton_2.setFont(new Font("宋体", Font.ITALIC, 16));

        JButton btnNewButton_3 = new JButton("\u53D1\u5E03\u516C\u544A");
        btnNewButton_3.setIcon(new ImageIcon(SystemFrame.class.getResource("/images/copy.png")));
        btnNewButton_3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {//发布公告
                String msg = adTxt.getText();
                String sql = "update systemmsg set msg='" + msg + "' where flag=0;";
                try {
                    Statement s = SQLConnection.creat();
                    s.executeUpdate(sql);
                    JOptionPane.showMessageDialog(null,"发布系统通知成功！");
                } catch (Exception se) {
                    se.printStackTrace();
                }
            }
        });
        btnNewButton_3.setFont(new Font("宋体", Font.ITALIC, 16));
        GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
        groupLayout.setHorizontalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                        .addGroup(groupLayout.createSequentialGroup()
                                                .addComponent(adTxt, GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)
                                                .addContainerGap())
                                        .addGroup(groupLayout.createSequentialGroup()
                                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                                        .addGroup(groupLayout.createSequentialGroup()
                                                                .addComponent(btnNewButton_2)
                                                                .addGap(131)
                                                                .addComponent(btnNewButton_3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                        .addComponent(lblNewLabel))
                                                .addContainerGap(17, Short.MAX_VALUE))))
        );
        groupLayout.setVerticalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addGap(25)
                                .addComponent(lblNewLabel)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(adTxt, GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                                .addGap(18)
                                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(btnNewButton_3)
                                        .addComponent(btnNewButton_2))
                                .addContainerGap())
        );
        frame.getContentPane().setLayout(groupLayout);
    }
}
