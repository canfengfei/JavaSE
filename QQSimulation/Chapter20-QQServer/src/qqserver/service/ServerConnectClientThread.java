package qqserver.service;

import qqcommon.Message;
import qqcommon.MessageType;

import java.io.*;
import java.net.Socket;
/*
保存socket。
持续接收对应客户端传来的消息，并调用响应
* */
public class ServerConnectClientThread extends Thread{
    private Socket socket;
    private String userID;
    public ServerConnectClientThread(Socket socket,String userID) {
        this.socket = socket;
        this.userID = userID;
    }

    public void run(){  //持续接收客户端传来的消息
        System.out.println("服务器和用户" + userID + "保持通讯" );
        while(true){
            try {
                ObjectInputStream oIS = new ObjectInputStream(socket.getInputStream());
                Message ms = (Message)oIS.readObject();
                //判断消息类型进行对应处理
                if(ms.getMesType().equals(MessageType.MESSAGE_GET_ONLINE_FRIEND)){
                    System.out.println("用户" + userID + "请求在线列表");
                    Message msfl = new Message();
                    msfl.setMesType(MessageType.MESSAGE_RET_ONLINE_FRIEND);
                    msfl.setContent(ManageServerConnectClientThread.getAllUserID());
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(msfl);
                } else if(ms.getMesType().equals(MessageType.MESSAGE_CLIENT_EXIT)){
                    socket.close();
                    ManageServerConnectClientThread.remove(userID);
                    System.out.println("服务器终止与用户：" + userID + "的通讯");
                    break;
                } else if(ms.getMesType().equals(MessageType.MESSAGE_LOGIN_COMM_MES)){
                    if(ManageServerConnectClientThread.getServerConnectClientThread(ms.getGetter())==null){
                        System.out.println("用户不在线，无法发送Message");
                    }
                    Socket socketGetter = ManageServerConnectClientThread.getServerConnectClientThread(ms.getGetter()).getSocket();
                    ObjectOutputStream oos = new ObjectOutputStream(socketGetter.getOutputStream());
                    oos.writeObject(ms);
                } else if(ms.getMesType().equals(MessageType.MESSAGE_TO_ALL_MES)){
                    String [] flc = ManageServerConnectClientThread.getAllUserID().split(" ");
                    for (int i = 1; i < flc.length; i++) {
                        if(ms.getSender().equals(flc[i])){
                            continue;
                        }
                        Socket socketGetter = ManageServerConnectClientThread.getServerConnectClientThread(flc[i]).getSocket();
                        ObjectOutputStream oos = new ObjectOutputStream(socketGetter.getOutputStream());
                        oos.writeObject(ms);
                    }
                } else if(ms.getMesType().equals(MessageType.MESSAGE_FILE_MESSAGE)){
                    if(ManageServerConnectClientThread.getServerConnectClientThread(ms.getGetter())==null){
                        System.out.println("用户不在线，无法发送Message");
                    } else {Socket socketGetter = ManageServerConnectClientThread.getServerConnectClientThread(ms.getGetter()).getSocket();
                        ObjectOutputStream oos = new ObjectOutputStream(socketGetter.getOutputStream());
                        oos.writeObject(ms);}
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Socket getSocket() {
        return socket;
    }
}
