package qqserver.service;

import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/*管理ServerConnectClientThread线程
* */
public class ManageServerConnectClientThread {
    private static ConcurrentHashMap<String, ServerConnectClientThread> hs = new ConcurrentHashMap<String, ServerConnectClientThread>();

    public static void addServerConnectClientThread(String userID, ServerConnectClientThread scct){ //添加<用户名,线程>到HashMap
        hs.put(userID, scct);
    }

    public static ServerConnectClientThread getServerConnectClientThread(String userID){ //根据用户名查找线程
        return hs.get(userID);
    }

    public static String getAllUserID(){ //返回在线用户列表
        String allUserID = "";
        Set set = hs.keySet();
        for (Object key: set) {
            allUserID = allUserID + " " +key;
        }
        return allUserID;
    }

    public static void remove(String userID){
         hs.remove(userID);
    }

    public static ConcurrentHashMap<String, ServerConnectClientThread> getHs(){
        return hs;
    }
}
