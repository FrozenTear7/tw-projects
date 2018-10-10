package lab1.ex2;

import java.util.ArrayList;

public class Buffer {

    private ArrayList<String> msgList;

    public Buffer () {
        this.msgList = new ArrayList<>();
    }

    public String take() {
        return msgList.remove(msgList.size() - 1);
    }

    public void put(String msg) {
        msgList.add(msg);
    }
}
