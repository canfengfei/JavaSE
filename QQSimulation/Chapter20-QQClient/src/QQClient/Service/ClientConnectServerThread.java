package QQClient.Service;

import QQClient.Utils.Utility;
import qqcommon.Message;
import qqcommon.MessageType;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/* 维护客户端信息传递类登陆成功的Socket，同时不断接受服务器端信息，并处理。
 * */
public class ClientConnectServerThread extends Thread{
    public Socket socket;
    public String userID;

    public ClientConnectServerThread(Socket socket,String userID) {
        this.socket = socket;
        this.userID = userID;
    }

    public void run(){
        while(true){
            Message ms;
            ObjectInputStream oIS = null;
            try {
                oIS = new ObjectInputStream(socket.getInputStream());
                ms = (Message) oIS.readObject();
                //判断Message类型并进行对应处理
                System.out.println("客户端" + userID + "与服务器连接");
                if(ms.getMesType().equals(MessageType.MESSAGE_RET_ONLINE_FRIEND)){
                    String [] flc = ms.getContent().split(" ");
                    for (int i = 1; i < flc.length; i++) {
                            System.out.println("用户：" + flc[i]);
                    }
                } else if(ms.getMesType().equals(MessageType.MESSAGE_LOGIN_COMM_MES)){  //类型为信息，输出信息，回复
                    System.out.println("用户" + ms.getSender() + "给用户" + ms.getGetter() + "发送信息：" + ms.getContent());
//                    System.out.print("是否回复(输入Y/N): ");
//                    String choose = Utility.readString(100);
//                    while(true) {
//                        if (choose.equals("Y") || choose.equals("y")) {
//                            MessageClientServer mcs = new MessageClientServer(userID);
//                            while (true) {
//                                System.out.print("请输入要发送的信息(输入/exit/退出)：");
//                                String content = Utility.readString(50);
//                                if (content.equals("/exit/")) {
//                                    break;
//                                }
//                                mcs.sendMessageToOne(ms.getSender(), content);
//                            }
//                            break;
//                        } else if (!(choose.equals("N") || choose.equals("n"))) {
//                            System.out.println("请重新输入");
//                        } else {
//                            break;
//                        }
//                    }
                } else if(ms.getMesType().equals(MessageType.MESSAGE_TO_ALL_MES)){
                    System.out.println("用户" + ms.getSender() + "给全体用户发送信息：" + ms.getContent());
                } else if(ms.getMesType().equals(MessageType.MESSAGE_FILE_MESSAGE)){
                    File picture = new File(ms.getDest());
                    FileOutputStream fos = new FileOutputStream(picture);
                    fos.write(ms.getFile(),0,ms.getFileLength());
                    System.out.println("已接受来自" + ms.getSender() + "的信息");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public Socket getSocket(){
        return socket;
    }
}
