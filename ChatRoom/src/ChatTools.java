import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class ChatTools {
    //private static List<ServerThread> stList = new ArrayList();
    public static HashMap<String,ServerThread> stMap = new HashMap<>();  //《用户名，线程》
    public static HashMap<String,Vector<String>> svMap = new HashMap<>(); //《群名字，群成员》

    public static HashMap<String,Vector<String>> sfMap = new HashMap<>(); //《用户名，拉黑表》
    private ChatTools() {

    }

    public static void addToPullBlack(String username, String friendname) {
        if (!sfMap.containsKey(username)) {
            sfMap.put(username,new Vector<>());
        }
        sfMap.get(username).add(friendname);
    }

    public static void sendFileToUser(String username, String friendname, String nowName, String fileName) {
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("YYYY-MM-dd HH:mm");
        String time = df.format(date);
        if (!stMap.containsKey(friendname)) {
            sendMsgToUser(username,"*S" + friendname + "|" + time + " 系统提示: \r\n\t" + "发送失败！对方不在线\r\n");
        } else {
            try {
                FileInputStream is = new FileInputStream(new File("/tmp/" + nowName));
                ServerThread st = stMap.get(friendname);
                DataOutputStream dos = new DataOutputStream(st.socket.getOutputStream());
                dos.writeUTF("&&file&&|" + username);
                dos.writeUTF(time + " "+ username + ": \r\n\t我向你发送了一个文件！请打开文件夹查看\r\n");
                dos.writeUTF(fileName);
                int data;
                while (-1 != (data = is.read())) {
                    dos.writeInt(data);
                }
                dos.writeInt(data);
                dos.flush();
                sendMsgToUser(username,"*S" + friendname + "|" + time + " 系统提示: \r\n\t" + "发送成功！对方已接收\r\n");
                is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        File file = new File("/tmp/"+nowName);
        file.delete();
    }

    public static boolean catMsg(String username1, String username2, String msg, boolean flag) {
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        String time = df.format(date);
        msg = time + " " + username1 + ": \r\n\t" + msg;
        if (ChatTools.sfMap.containsKey(username2) && ChatTools.sfMap.get(username2).contains(username1)) {
            return false;
        }
        if (flag) {
            sendMsgToUser(username2, username2 + "|" + msg);
        } else {
            try {
                /**
                 * tmp[0] = friend id
                 * tmp[1] = msg -> username1|date friendname:msg
                 */
                SQLConnection.insertMsg(username1, username2, msg, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }
    public static boolean sendMsgToGroup(String username, String groupname,String msg) {
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        String time = df.format(date);
        msg = "#" + groupname + "|" + time + " " + username + ": \r\n\t" + msg;
        for (String fname : svMap.get(groupname)) {
            if (stMap.containsKey(fname) && !stMap.get(fname).socket.isClosed()) {
                stMap.get(fname).sendMsgToMe(msg+"|"+fname);
            } else {
                if (stMap.containsKey(fname)) {
                    stMap.get(fname).isRuning = false;
                    stMap.get(fname).close();
                    stMap.remove(fname);
                }
                try {
                    SQLConnection.insertMsg(username, groupname,false, msg.substring(msg.indexOf("|")+1),fname);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }
    /** 取得保存处理线程对象的队列 */
    public static  HashMap<String,ServerThread> getAllThread() {
        return stMap;
    }

    public static void addClient(String username,ServerThread ct) {
        //新人上线
        stMap.put(username,ct);

    }

    ///发消息给某人
    public static void sendMsgToUser(String username,String msg) {
        stMap.get(username).sendMsgToMe(msg);
    }
}
