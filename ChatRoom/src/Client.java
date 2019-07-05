import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.HashMap;

public class Client extends Thread {
	private int port = 28888;
	private String ip = ""; //服务端IP
	private boolean isRuning = false;
	public static HashMap<String,JTextArea> areaMap = new HashMap<>();
	public Socket socket;
	DataInputStream dis;
	@Override
	public void run() {
		try {
			while (isRuning) {
				String[] tmp = dis.readUTF().split("\\|");
				boolean flag = false;

				if (tmp[0].startsWith("*S")) {
					JTextArea area = areaMap.get(tmp[0].substring(2));
					if (area != null && !socket.isClosed()) {
						area.setText(area.getText() + tmp[1]);
					} else if (areaMap.containsKey(tmp[0].substring(2))) {
						areaMap.remove(tmp[0]);
					}
				} else if (tmp[0].startsWith("&&file&&")) {
					String msg = dis.readUTF();
					String fileName = dis.readUTF();
					File filePath = new File("c://ChatRoomFiles");
					if (!filePath.exists()) {
						filePath.mkdir();
					}
					File file = new File("c://ChatRoomFiles//"+fileName);
					int cont = 1;
					while (file.exists()) {
						file = new File("c://ChatRoomFiles//" + cont + fileName);
						cont++;
					}
					FileOutputStream fos=new FileOutputStream(file.getAbsoluteFile());
					int data;
					while(-1 != (data= dis.readInt())) {
						fos.write(data);
					}
					fos.close();
					JTextArea area = areaMap.get(tmp[1]);
					area.setText(area.getText() + msg);
				} else if (tmp[0].startsWith("#")) {
					String friname = tmp[1].split(":")[2].substring(3);
					JTextArea area = areaMap.get(tmp[0]);
					System.out.println(area);
					if (area != null && !socket.isClosed()) {
						flag = true;
						area.setText(area.getText() + tmp[1]);
					}
					if (!flag && areaMap.containsKey(tmp[0])) areaMap.remove(tmp[0]);
					try {
						SQLConnection.insertMsg(friname, tmp[0].substring(1),flag, tmp[1],tmp[2]);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					String friname = tmp[1].split(":")[2].substring(3);
					JTextArea area = areaMap.get(friname);
					if (area != null && !socket.isClosed()) {
						flag = true;
						area.setText(area.getText() + tmp[1]);
					}
					if (!flag && areaMap.containsKey(tmp[0])) areaMap.remove(tmp[0]);
					try {
						/**
						 * tmp[0] = friend id
						 * tmp[1] = msg -> username1|date friendname:\n msg
						 */
						SQLConnection.insertMsg(friname, tmp[0], tmp[1], flag);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			;
		}
	}


	public Client(User user) {
		try {
			init(user.getUsername());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void init(String username) throws Exception {
		//新建客户端
		socket = new Socket(ip,port);
		isRuning = true;
		dis = new DataInputStream(socket.getInputStream());
		//socket.getOutputStream().write(("321|"+username).getBytes("UTF-8"));
		DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
		dos.writeUTF(username);
		dos.flush();
		//ssMap.put(socket,Main.usernameNow);
		//Main.scNow = socket;
	}

	public void closed() {
		isRuning = false;
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}