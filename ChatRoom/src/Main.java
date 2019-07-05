

import java.awt.*;

import javax.swing.*;


import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.event.*;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Main {
    private JFrame frame;
    private JTextField username;
    private JPasswordField password;
    public static String loginUsername;
    public static int loginUserid;
    public static DateFormat df = new SimpleDateFormat("YYYY-MM-dd HH:mm");

    public static int ft;
    public static int fontSize;
    public static String co;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Main window = new Main();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public static void start() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Main window = new Main();
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
    public Main() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        
        FontFrame.cMap.put("BLUE",Color.BLUE);
        FontFrame.cMap.put("RED",Color.RED);
        FontFrame.cMap.put("WHITE",Color.WHITE);
        FontFrame.cMap.put("BLACK",Color.BLACK);
        FontFrame.cMap.put("GREEN",Color.GREEN);
        FontFrame.cMap.put("PINK",Color.PINK);
        FontFrame.cMap.put("CYAN",Color.CYAN);
        FontFrame.cMap.put("GRAY",Color.GRAY);
        FontFrame.cMap.put("ORANGE",Color.ORANGE);
        FontFrame.cMap.put("YELLOW",Color.YELLOW);
        FontFrame.cMap.put("MAGENTA",Color.MAGENTA);
        FontFrame.cMap.put("LIGHTGRAY",Color.lightGray);
        
        //改变系统默认字体
        Font font = new Font("Dialog",Font.PLAIN,12);
        java.util.Enumeration keys = UIManager.getDefaults().keys();
        while(keys.hasMoreElements()){
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if(value instanceof javax.swing.plaf.FontUIResource)
                UIManager.put(key, font);
        }
        frame = new JFrame();
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/images/\u767B\u5F55.png")));

        frame.setFont(new Font("Dialog", Font.PLAIN, 12));
        frame.setTitle("\u7528\u6237\u6CE8\u518C/\u767B\u5F55");
        frame.getContentPane().setForeground(Color.LIGHT_GRAY);
        frame.getContentPane().setBackground(UIManager.getColor("Button.background"));
        frame.setFont(new Font("Dialog", Font.PLAIN, 12));
        frame.setTitle("\u7528\u6237\u6CE8\u518C/\u767B\u5F55");
        frame.getContentPane().setForeground(Color.LIGHT_GRAY);
        frame.getContentPane().setBackground(UIManager.getColor("Button.background"));

        JLabel lblNewLabel = new JLabel("\u7528\u6237\u6CE8\u518C/\u767B\u5F55");
        lblNewLabel.setFont(new Font("宋体", Font.BOLD, 16));
        lblNewLabel.setIcon(new ImageIcon(Main.class.getResource("/images/11.png")));

        JLabel lblNewLabel_1 = new JLabel("\u7528\u6237\u540D");
        lblNewLabel.setIcon(new ImageIcon(Main.class.getResource("/images/11.png")));

        lblNewLabel_1.setIcon(new ImageIcon(Main.class.getResource("/images/\u7528\u6237\u540D.png")));
        lblNewLabel_1.setFont(new Font("宋体", Font.BOLD, 16));

        username = new JTextField();
        username.setFont(new Font("宋体", Font.PLAIN, 16));
        username.setColumns(10);

        username.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {//用户名监听
                if (e.getKeyCode() == 10) {
                    userLogin();
                }
            }
        });

        JLabel lblNewLabel_2 = new JLabel("\u5BC6  \u7801");
        lblNewLabel_2.setIcon(new ImageIcon(Main.class.getResource("/images/\u5BC6\u7801.png")));
        lblNewLabel_2.setFont(new Font("宋体", Font.BOLD, 16));

        JButton registerButton = new JButton("\u6CE8\u518C");
        registerButton.setIcon(new ImageIcon(Main.class.getResource("/images/\u6CE8\u518C\u767B\u5F55.png")));
        registerButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {//鼠标点击注册
                userRegister();
            }
        });
        registerButton.setFont(new Font("宋体", Font.BOLD, 16));

        JButton loginButton = new JButton("\u767B\u5F55");
        loginButton.setIcon(new ImageIcon(Main.class.getResource("/images/\u767B\u5F55\u5BC6\u7801.png")));
        loginButton.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == 10) {
                    userLogin();
                }

            }
        });
        loginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {//鼠标点击登录
                userLogin();
            }
        });
        loginButton.setFont(new Font("宋体", Font.BOLD, 16));

        password = new JPasswordField();
        password.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {//用户密码监听
                if (e.getKeyCode() == 10) {
                    userLogin();
                }
            }
        });

        GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
        groupLayout.setHorizontalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addGap(109)
                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                        .addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
                                                .addComponent(lblNewLabel_2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(lblNewLabel_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addComponent(registerButton))
                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                        .addGroup(groupLayout.createSequentialGroup()
                                                .addGap(35)
                                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                                        .addComponent(username, GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE)
                                                        .addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 167, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(password, GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE))
                                                .addContainerGap(33, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(groupLayout.createSequentialGroup()
                                                .addGap(101)
                                                .addComponent(loginButton)
                                                .addContainerGap())))
        );
        groupLayout.setVerticalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addGap(48)
                                .addComponent(lblNewLabel)
                                .addGap(63)
                                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(lblNewLabel_1)
                                        .addComponent(username, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(lblNewLabel_2)
                                        .addComponent(password, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(72)
                                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(registerButton)
                                        .addComponent(loginButton))
                                .addGap(45))
        );
        frame.getContentPane().setLayout(groupLayout);
        frame.setBackground(Color.WHITE);
        frame.setForeground(Color.WHITE);
        frame.setBounds(100, 100, 519, 424);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    }

    private void userRegister() {
        String name = username.getText();
        String pwd = password.getText();

        if (!validString.isValid(name)) {
            JOptionPane.showMessageDialog(null,"用户名包含非法字符,比如into,*,|等\n" +
                    "请检查后再次注册");
            return ;
        }
        //访问数据库
        User user = new User(name,pwd);
        try {
            if (SQLConnection.isExistUser(user)) {
                JOptionPane.showMessageDialog(null, "用户已存在!!!");
            } else {
                JOptionPane.showMessageDialog(null, "注册成功!!!");
                SQLConnection.insert(user);
            }
        } catch (SQLException se){
            se.printStackTrace();
        }
    }

    private void userLogin() {
        String name = username.getText();
        String pwd = password.getText();
        if (!validString.isValid(name)) {
            JOptionPane.showMessageDialog(null,"用户名包含非法字符,比如into,*,|等\n请检查后再次注册");
            return ;
        }
        //访问数据库...
        User user = new User(name,pwd);
        try {
            if (SQLConnection.checkPassword(user)) {
                if (SQLConnection.isUserLogin(name)) {
                    JOptionPane.showMessageDialog(null,"用户已经登陆！");
                    return ;
                }
                Client client = new Client(user);
                Server.addToMap(client.socket,user.getUsername());
                frame.dispose();
                UserMainFrame umf = new UserMainFrame();
                umf.client = client;
                umf.start();
                client.start();

                //init
                Main.loginUsername = name;
                Main.loginUserid = SQLConnection.usernameGetId(name);
                try {
                    Object[] otmp = SQLConnection.getUserFont(Main.loginUsername);
                    ft = (int) otmp[0];
                    fontSize = (int) otmp[1];
                    co = (String) otmp[2];
                } catch (Exception e) {
                    e.printStackTrace();
                }
                SQLConnection.updateUserInfo(name,1);
            } else {
                JOptionPane.showMessageDialog(null, "密码或用户名错误!!!");
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }
}