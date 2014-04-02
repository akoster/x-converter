package xcon.project.hotel.db;


import java.io.File;
import java.io.RandomAccessFile;
import java.net.URL;

public class FileStubUser {

    public static void main(String[] args) throws Exception {

        URL resourceUrl = FileStubUser.class.getResource("/atm.properties");
        File resourceFile = new FileStub(resourceUrl.toURI().getPath());
        RandomAccessFile resourceRAF = new RandomAccessFile(resourceFile, "rw");
        String line = resourceRAF.readLine();
        System.out.println("Read: " + line);
    }
}
