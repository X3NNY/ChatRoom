
import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server extends Thread{
	public static final int port = 28888;
	public boolean isRunning = false;
	public static HashMap<Socket,String> ssMap = new HashMap<>();
	public ServerSocket sc;

	public static void addToMap(Socket sc, String username) {
		ssMap.put(sc,username);
	}

	public void run() {
		try {
			SQLConnection.updateGroup();
			SQLConnection.updatePullBlack();
			sc = new ServerSocket(port);
			System.out.println("Start on " + port);
			while (true) {
				Socket socket = sc.accept();
				DataInputStream isTmp = new DataInputStream(socket.getInputStream());
				String sTmp = isTmp.readUTF();
				ServerThread ct = new ServerThread(socket,sTmp);
				//传输自己名字
				ChatTools.addClient(sTmp,ct);
				ct.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new Server().start();
	}

	public void closed() {
		try {
			sc.close();
			isRunning = false;
			ssMap.clear();
			for (Map.Entry<String, ServerThread> s : ChatTools.stMap.entrySet()) {
				s.getValue().close();
			}
			ChatTools.stMap.clear();
			ChatTools.svMap.clear();
			ChatTools.sfMap.clear();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}