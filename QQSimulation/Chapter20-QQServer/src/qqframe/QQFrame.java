package qqframe;

import qqserver.service.UserServerService;

import java.io.IOException;

/*启动服务器
* */
public class QQFrame {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        new UserServerService();
    }
}
