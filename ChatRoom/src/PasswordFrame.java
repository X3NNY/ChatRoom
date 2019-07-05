import java.awt.*;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class PasswordFrame {

    private JFrame frame;
    private JTextField originPwd;
    private JPasswordField newPwd;
    private JPasswordField newPwd1;

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
    public PasswordFrame() {
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(PasswordFrame.class.getResource("/images/\u5BC6\u78011.png")));
        frame.setTitle("\u4FEE\u6539\u5BC6\u7801");
        frame.setResizable(false);
        frame.setBounds(100, 100, 290, 309);

        JLabel lblNewLabel = new JLabel("\u539F\u5BC6\u7801");
        lblNewLabel.setIcon(null);
        lblNewLabel.setFont(new Font("宋体", Font.ITALIC, 16));

        originPwd = new JTextField();
        originPwd.setColumns(10);

        JLabel lblNewLabel_1 = new JLabel("\u65B0\u5BC6\u7801");
        lblNewLabel_1.setFont(new Font("宋体", Font.ITALIC, 16));

        JLabel lblNewLabel_2 = new JLabel("\u5BC6\u7801\u786E\u8BA4");
        lblNewLabel_2.setFont(new Font("宋体", Font.ITALIC, 16));

        newPwd = new JPasswordField();

        newPwd1 = new JPasswordField();

        JButton btnNewButton = new JButton("\u786E\u8BA4");
        btnNewButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {//确认
                String oldPass = originPwd.getText();
                String newPass = newPwd.getText();
                String checkPass = newPwd1.getText();
                if (!newPass.equals(checkPass)) {
                    JOptionPane.showMessageDialog(null,"两次密码输入不一致！");
                    return;
                }
                try {
                    User user = new User(Main.loginUsername,oldPass);
                    if (SQLConnection.checkPassword(user)) {
                        SQLConnection.updateUserPassword(user,newPass);
                        JOptionPane.showMessageDialog(null,"密码更改成功！");
                    } else {
                        JOptionPane.showMessageDialog(null,"原密码不正确！");
                    }
                } catch (Exception se) {
                    se.printStackTrace();
                }
            }
        });
        btnNewButton.setIcon(new ImageIcon(PasswordFrame.class.getResource("/images/Success.gif")));
        btnNewButton.setFont(new Font("宋体", Font.ITALIC, 16));

        JButton btnNewButton_1 = new JButton("\u8FD4\u56DE\u4E0A\u4E00\u7EA7");
        btnNewButton_1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {//返回上一级
                frame.dispose();
            }
        });
        btnNewButton_1.setIcon(new ImageIcon(PasswordFrame.class.getResource("/com/sun/javafx/scene/web/skin/Undo_16x16_JFX.png")));
        GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
        groupLayout.setHorizontalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                        .addGroup(groupLayout.createSequentialGroup()
                                                .addComponent(btnNewButton)
                                                .addPreferredGap(ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                                                .addComponent(btnNewButton_1))
                                        .addGroup(groupLayout.createSequentialGroup()
                                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                                        .addComponent(lblNewLabel_2)
                                                        .addComponent(lblNewLabel_1)
                                                        .addComponent(lblNewLabel))
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                                        .addComponent(originPwd, GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
                                                        .addComponent(newPwd, GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
                                                        .addComponent(newPwd1, GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE))))
                                .addContainerGap())
        );
        groupLayout.setVerticalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addGap(39)
                                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(lblNewLabel)
                                        .addComponent(originPwd, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(18)
                                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(lblNewLabel_1)
                                        .addComponent(newPwd, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(18)
                                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(lblNewLabel_2)
                                        .addComponent(newPwd1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(ComponentPlacement.RELATED, 81, Short.MAX_VALUE)
                                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(btnNewButton)
                                        .addComponent(btnNewButton_1))
                                .addGap(23))
        );
        frame.getContentPane().setLayout(groupLayout);
        frame.setLocationRelativeTo(null); //页面居中
    }
}
