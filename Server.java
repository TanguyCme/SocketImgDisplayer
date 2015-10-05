import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.awt.color.*;
import javax.imageio.*;

public class Server {
    public static void main(String[] args) throws IOException {
        Socket serverSocket = new ServerSocket(22222).accept();

        //Create a Byte Array
        byte[] msg = extractBytes("I79o1.jpg");
        //Create a DataOutputStream wich ill exchange data with the Client
        while(true){
          DataOutputStream dOut = new DataOutputStream(serverSocket.getOutputStream());
          //Then write the message to the Client
          dOut.writeInt(msg.length);
          dOut.write(msg);
        }
    }

    //function wich will create a byte array from a image
    public static byte[] extractBytes (String imageName) throws IOException {
     // open image
     File imgPath = new File(imageName);
     BufferedImage bufferedImage = ImageIO.read(imgPath);

     // get DataBufferBytes from Raster
     WritableRaster raster = bufferedImage .getRaster();
     DataBufferByte data   = (DataBufferByte) raster.getDataBuffer();

     return ( data.getData() );
  }
}
