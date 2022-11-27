package QQClient.Service;

import java.util.HashMap;
/*将创建的ClientConnectServerThread加入HashMap,进行管理。
* */
public class ManageClientConnectServerThread {
    private static HashMap<String, ClientConnectServerThread> hs = new HashMap<String, ClientConnectServerThread>();

    public static void addClientConnectServerThread(String userID,ClientConnectServerThread ccst){ //添加<用户名，线程>到HashMap
        hs.put(userID, ccst);
    }

    public static ClientConnectServerThread getClientConnectServerThread(String userID){  //根据用户名查找线程
        return hs.get(userID);
    }
}
