package qqcommon;

public interface MessageType {
    String MESSAGE_LOGIN_SUCCEED = "1";     //登陆成功
    String MESSAGE_LOGIN_FAIL = "2";        //登陆失败
    String MESSAGE_LOGIN_COMM_MES = "3";    //私聊消息
    String MESSAGE_GET_ONLINE_FRIEND = "4"; //请求获取用户在线列表
    String MESSAGE_RET_ONLINE_FRIEND = "5";  //返回用户在线列表
    String MESSAGE_CLIENT_EXIT = "6";        //退出客户端
    String MESSAGE_TO_ALL_MES = "7";         //群发消息
    String MESSAGE_FILE_MESSAGE = "8";       //文件消息
}
