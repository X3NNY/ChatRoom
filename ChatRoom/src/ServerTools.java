import java.util.Vector;

public class ServerTools {

    public static void newgroup(String[] input) {
        ChatTools.svMap.put(input[2],new Vector<>());
        ChatTools.svMap.get(input[2]).add(input[1]);
    }

    public static void addgroup(String[] input) {
        ChatTools.svMap.get(input[2]).add(input[1]);
    }

    public static void delgroup(String[] input) {
        ChatTools.svMap.remove(input[1]);
    }

    public static void exitgroup(String[] input) {
        ChatTools.svMap.get(input[2]).remove(input[1]);
    }

    public static void firegroup(String[] input) {
        ChatTools.svMap.get(input[2]).remove(input[1]);
    }

    public static void pullblack(String[] input) {
        if (!ChatTools.sfMap.containsKey(input[1])) {
            ChatTools.sfMap.put(input[1],new Vector<>());
        }
        ChatTools.sfMap.get(input[1]).add(input[2]);
    }

    public static void unpullblack(String[] input) {
        ChatTools.sfMap.get(input[1]).remove(input[2]);
    }
}
