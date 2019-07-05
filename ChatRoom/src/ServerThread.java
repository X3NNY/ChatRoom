
import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.UUID;

public class ServerThread extends Thread {
    public Socket socket;
    private DataOutputStream os;
    private DataInputStream is;
    private String username;
    public boolean isRuning = false;
    public ServerThread(Socket socket,String username) {
        this.socket = socket;
        this.username = username;
        Server.addToMap(socket,username);
        isRuning = true;
        try {
            is = new DataInputStream(socket.getInputStream());
            os = new DataOutputStream(socket.getOutputStream());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void sendMsgToMe(String msg) {
        try {
            os.writeUTF(msg);
            os.flush();
        } catch (Exception e) {
            String username2 = msg.split("\\|")[0];
            msg = msg.substring(msg.indexOf("|")+1);
            String username1 = msg.split(":")[2].substring(3);//发消息的人
            ChatTools.stMap.remove(username2);
            try {
                /**
                 * tmp[0] = friend id
                 * tmp[1] = msg -> username1|date friendname:msg
                 */
                SQLConnection.insertMsg(username1, username2, msg, false);
            } catch (Exception se) {
                se.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            while (isRuning) {
                String[] input = is.readUTF().split("\\|");
                if (input[0].startsWith("@")) {
                    String opt = input[0].substring(1);
                    if ("addgroup".equals(opt)) {
                        ServerTools.addgroup(input);
                    } else if ("delgroup".equals(opt)) {
                        ServerTools.delgroup(input);
                    } else if ("exitgroup".equals(opt)) {
                        ServerTools.exitgroup(input);
                    } else if ("newgroup".equals(opt)) {
                        ServerTools.newgroup(input);
                    } else if ("firegroup".equals(opt)) {
                        ServerTools.firegroup(input);
                    } else if ("pullblack".equals(opt)) {
                        ServerTools.pullblack(input);
                    } else if ("unpullblack".equals(opt)) {
                        ServerTools.unpullblack(input);
                    }
                } else if (input[0].startsWith("#")) {
                    ChatTools.sendMsgToGroup(input[1].split(" ")[2].replace(":",""), input[0].substring(1), input[2]);
                } else if (input[0].startsWith("&&file&&")){
                    String fileName = is.readUTF();
                    String username = is.readUTF();
                    String friendname = is.readUTF();
                    String tmpName = UUID.randomUUID().toString();
                    FileOutputStream fos = new FileOutputStream("/tmp/"+ tmpName);
                    int data;
                    while(-1  != (data = is.readInt())) {
                        fos.write(data);
                    }
                    ChatTools.sendFileToUser(username,friendname,tmpName,fileName);
                   // textArea.append(fileName+"接收完毕!\n");
                    fos.close();
                } else {
                    //自己名字，好友名字，msg，flag
                    ChatTools.catMsg(username, input[0], input[2], ChatTools.stMap.containsKey(input[0]));
                }
            }
        } catch (IOException e) {
//            e.printStackTrace();
        } finally {
            ;
        }
    }

    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
