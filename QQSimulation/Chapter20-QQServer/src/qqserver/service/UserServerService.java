package qqserver.service;

import qqcommon.Message;
import qqcommon.MessageType;
import qqcommon.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
/*完成用户登录验证和注册
* */
public class UserServerService {
    private ServerSocket ss = null;
    private  static HashMap<String, User> validUsers = new HashMap<String, User>();
    static {
        validUsers.put("100",new User("100","123456"));
        validUsers.put("200",new User("200","123456"));
        validUsers.put("300",new User("300","123456"));
    }

    private boolean checkUser(User u){
        if(validUsers.get(u.getUserID()) == null){
            System.out.print("用户不存在，");
            return false;
        } else if(!validUsers.get(u.getUserID()).getPasswd().equals(u.getPasswd())){
            System.out.print("密码错误，");
            return  false;
        } else if(ManageServerConnectClientThread   .getServerConnectClientThread(u.getUserID())!= null){
            System.out.print("用户已经登陆，");
            return false;
        }
            //System.out.println("账号密码正确");
            return true;
        }

    public UserServerService() throws IOException, ClassNotFoundException { //监听客户端连接请求，处理用户登录请求。
        boolean b = true;
        Message ms = new Message();
        ss = new ServerSocket(9999);
        System.out.println("服务器在9999端口监听");
        new Thread(new SendNewsToAllService()).start();
        while(b){
            Socket socket = ss.accept();
            ObjectInputStream oIS = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream oOS = new ObjectOutputStream(socket.getOutputStream());
            User u = (User)oIS.readObject();
            if(checkUser(u)){
                ms.setMesType(MessageType.MESSAGE_LOGIN_SUCCEED);
                oOS.writeObject(ms);
                ServerConnectClientThread scct = new ServerConnectClientThread(socket,u.getUserID());
                scct.start();
                ManageServerConnectClientThread.addServerConnectClientThread(u.getUserID(),scct);
            } else {
                System.out.println("用户" + u.getUserID() + "登陆失败");
                ms.setMesType(MessageType.MESSAGE_LOGIN_FAIL);
                oOS.writeObject(ms);
                socket.close();
            }

        }
        ss.close();
    }

}
