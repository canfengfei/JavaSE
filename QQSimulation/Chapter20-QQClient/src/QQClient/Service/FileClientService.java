package QQClient.Service;

import qqcommon.Message;
import qqcommon.MessageType;

import java.io.*;
import java.net.Socket;

public class FileClientService {
    private Socket socket;
    private String userID;

    public FileClientService(String userID){
        this.userID = userID;
        this.socket = ManageClientConnectServerThread.getClientConnectServerThread(userID).getSocket();
    }

    public void sendFileToOne(String src, String dest, String getter){
        Message ms = new Message();
        ms.setMesType(MessageType.MESSAGE_FILE_MESSAGE);
        ms.setDest(dest);
        ms.setGetter(getter);
        ms.setSender(userID);

        try {
            byte [] file = new byte[(int)new File(src).length()];
            FileInputStream istream = new FileInputStream(new File(src));
            istream.read(file);
            ms.setFile(file);
            ms.setFileLength(file.length);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(ms);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
