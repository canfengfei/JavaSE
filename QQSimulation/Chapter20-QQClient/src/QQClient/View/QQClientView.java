package QQClient.View;

import QQClient.Service.FileClientService;
import QQClient.Service.MessageClientServer;
import QQClient.Service.UserClientService;
import QQClient.Utils.Utility;

import java.io.IOException;

/*为一位用户提供菜单服务
* */
public class QQClientView {
    private boolean loop = true;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        new QQClientView().mainMenu();
    }

    private void mainMenu() throws IOException, ClassNotFoundException {
        String key;
        while(loop){
            System.out.println("==========欢迎登陆网络通信系统==========");
            System.out.println("\t\t 1 登录系统");
            System.out.println("\t\t 9 退出系统");
            System.out.print("请输入你的选择：");

            key = Utility.readString(1);
            switch(key){
                case "1":
                    //返回功能选择菜单
                    System.out.print("请输入账号：");
                    String userIDTemp = Utility.readString(20);
                    System.out.print("请输入密码：");
                    String passwdTemp = Utility.readString(20);
                    //服务器判断用户是否合法
                    UserClientService ucs = new UserClientService();
                    boolean sl =  ucs.checkUser(userIDTemp,passwdTemp);
                    if(sl){
                        System.out.println("======== 欢迎用户：" + userIDTemp + "登陆成功========");
                        MessageClientServer mcs = new MessageClientServer(userIDTemp);
                        FileClientService fcs = new FileClientService(userIDTemp);
                        while(loop){
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            System.out.println("========== 网络通信二级菜单用户：" + userIDTemp + "==========");
                            System.out.println("\t\t 1 显示用户在线列表");
                            System.out.println("\t\t 2 群发消息");
                            System.out.println("\t\t 3 私聊消息");
                            System.out.println("\t\t 4 发送文件");
                            System.out.println("\t\t 9 退出系统");
                            System.out.print("请输入你的选择：");
                            key = Utility.readString(1);
                            switch(key){
                                case "1":
                                    System.out.println("显示用户在线列表");
                                    ucs.onlineFriendList();
                                    break;
                                case "2":
                                    System.out.println("群发消息");
                                    while(true) {
                                        System.out.print("请输入要发送的信息(输入/exit/退出)：");
                                        String content = Utility.readString(50);
                                        if(content.equals("/exit/")){
                                            break;
                                        }
                                        mcs.sendMessageToAll(content);
                                    }
                                    break;
                                case "3":
                                    System.out.println("私聊消息");
                                    System.out.print("请输入要接收方用户ID：");
                                    String getter = Utility.readString(10);
                                    while(true) {
                                        System.out.print("请输入要发送的信息(输入/exit/退出)：");
                                        String content = Utility.readString(50);
                                        if(content.equals("/exit/")){
                                            break;
                                        }
                                        mcs.sendMessageToOne(getter, content);
                                    }
                                    break;
                                case "4":
                                    System.out.println("发送文件");
                                    System.out.print("请输入发送文件的源路径：");
                                    String src = Utility.readString(50);
                                    System.out.print("请输入文件的目的路径:");
                                    String dest = Utility.readString(50);
                                    System.out.print("请输入接收文件用户的ID:");
                                    String getter1 = Utility.readString(50);
                                    fcs.sendFileToOne(src,dest, getter1);
                                    break;
                                case "9":
                                    loop = false;
                                    ucs.logout();
                                    System.out.println("退出系统");
                                    break;
                                default:
                                    System.out.println("输入错误");
                                    break;
                            }
                        }
                    } else {
                        System.out.println("登陆失败");
                    }
                    break;
                case "2":
                    loop = false;
                    System.out.println("退出系统");
                    break;
            }
        }
    }
}
