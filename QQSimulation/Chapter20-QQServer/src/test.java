import java.io.*;

public class test {
    public static void main(String[] args) throws IOException { //测试节点流能否被多个处理流包装
        FileOutputStream fo1 = new FileOutputStream("D:\\SourceFile.txt");
        FileInputStream fi1 = new FileInputStream("D:\\SourceFile.txt");
        ObjectOutputStream oos1 = new ObjectOutputStream(fo1);
        ObjectInputStream ois1 = new ObjectInputStream(fi1);
        ObjectInputStream ois2 = new ObjectInputStream(fi1);
        System.out.println("OK");
    }
}
