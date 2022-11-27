package QQClient.Service;

import qqcommon.Message;
import qqcommon.MessageType;
import qqcommon.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
/*提供用户相关服务：
* */
public class UserClientService {
    private User u;
    private Socket socket;

    //检查用户是否能够登陆成功

    public boolean checkUser(String userID, String passwd) throws IOException, ClassNotFoundException {
        /* 创建用户并向服务器传送登录信息，调用服务器接收处理线程。
        * */
        boolean b = false;
        u = new User(userID,passwd);
        //连接服务器的指定端口
        socket = new Socket(InetAddress.getLocalHost(),9999);
        ObjectOutputStream oOS = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream oIS = new ObjectInputStream(socket.getInputStream());
        oOS.writeObject(u);
        Message ms = (Message)oIS.readObject();
        if(ms.getMesType().equals(MessageType.MESSAGE_LOGIN_SUCCEED)){ //登录成功
            b = true;
            ClientConnectServerThread cctt = new ClientConnectServerThread(socket,userID);
            cctt.start();
            ManageClientConnectServerThread.addClientConnectServerThread(userID,cctt);
        } else { //登录失败
            socket.close();
        }
        return b;
    }

    public void onlineFriendList() throws IOException { //向服务其发送获取在线用户请求
        ClientConnectServerThread mccst = ManageClientConnectServerThread.getClientConnectServerThread(u.getUserID());
        Message msfl = new Message();
        msfl.setMesType(MessageType.MESSAGE_GET_ONLINE_FRIEND);
        ObjectOutputStream oos = new ObjectOutputStream((mccst.socket.getOutputStream()));
        oos.writeObject(msfl);

    }

    public void logout(){
        Message ms = new Message();
        ms.setMesType(MessageType.MESSAGE_CLIENT_EXIT);

        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(ms);
            System.exit(0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
