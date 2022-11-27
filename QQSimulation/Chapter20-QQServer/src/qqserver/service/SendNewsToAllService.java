package qqserver.service;

import Utils.Utility;
import qqcommon.Message;
import qqcommon.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

public class SendNewsToAllService implements Runnable {
    public void run(){
        while(true) {
            System.out.println("请输入要推送的新闻(exit退出)：");
            String news = Utility.readString(100);
            if(news.equals("exit")){
                break;
            }
            Iterator iterator1 = ManageServerConnectClientThread.getHs().values().iterator();
            Message ms = new Message();
            ms.setSender("服务器");
            ms.setContent(news);
            ms.setMesType(MessageType.MESSAGE_TO_ALL_MES);
            ms.setSendTime(new Date().toString());
            while (iterator1.hasNext()) {
                Socket socket = ((ServerConnectClientThread) iterator1.next()).getSocket();
                try {
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(ms);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
