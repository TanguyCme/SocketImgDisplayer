import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.awt.color.*;
import javax.imageio.*;


public class Client {
  //Function wich change a byte array to a image
  private static BufferedImage createRGBImage(byte[] bytes, int width, int height) {
    DataBufferByte buffer = new DataBufferByte(bytes, bytes.length);
    ColorModel cm = new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB), new int[]{8, 8, 8}, false, false, Transparency.OPAQUE, DataBuffer.TYPE_BYTE);
    return new BufferedImage(cm, Raster.createInterleavedRaster(buffer, width, height, width * 3, 3, new int[]{0, 1, 2}, null), false, null);
  }

  public static void main(String[] args) throws IOException {
        Socket socket = null;
        String host = "127.0.0.1";

        socket = new Socket(InetAddress.getLocalHost(), 22222);

        DataInputStream dIn = new DataInputStream(socket.getInputStream());

        byte[] recievedMsg;

        int length = dIn.readInt();
        try {
          if (length >0) {
            recievedMsg = new byte[length];
            dIn.readFully(recievedMsg,0,recievedMsg.length);

            //Part wich write the byte array in a file
            FileOutputStream fos = new FileOutputStream(new File("toto.txt"));
            fos.write(recievedMsg);
            fos.close();

            //Part wich convert and display the image
            BufferedImage recievedImg = createRGBImage(recievedMsg,640,480);
            try {
              ImageIO.write(recievedImg, "BMP", new File("toto.png"));
              JFrame frame = new JFrame();
              frame.getContentPane().setLayout(new FlowLayout());
              frame.getContentPane().add(new JLabel(new ImageIcon(recievedImg)));
              frame.pack();
              frame.setVisible(true);
            }
            finally {
              //close the inputStream
              dIn.close();
            }

          }
        }catch(IOException e){
          e.printStackTrace();
        }
        socket.close();
    }
}
