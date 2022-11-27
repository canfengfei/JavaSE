package QQClient.Service;

/*提供用户相关服务：
 * */

import qqcommon.Message;
import qqcommon.MessageType;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class MessageClientServer {
    private Socket socket;
    private String userID;

    public MessageClientServer(String userID){
        this.userID = userID;
        this.socket = ManageClientConnectServerThread.getClientConnectServerThread(userID).getSocket();
    }

    public void sendMessageToOne(String getter,String content){ //发送私聊信息
        Message ms = new Message();
        ms.setMesType(MessageType.MESSAGE_LOGIN_COMM_MES);
        ms.setSender(userID);
        ms.setGetter(getter);
        ms.setContent(content);
        try {
            System.out.println("用户" + ms.getSender() + "给用户" + ms.getGetter() + "发送信息：" + ms.getContent());
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(ms);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void sendMessageToAll(String content){ //发送私聊信息
        Message ms = new Message();
        ms.setMesType(MessageType.MESSAGE_TO_ALL_MES);
        ms.setSender(userID);
        ms.setContent(content);
        try {
            System.out.println("用户" + ms.getSender() + "给全体用户" +  "发送信息：" + ms.getContent());
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(ms);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
