
package HTTPServer;

import java.io.*;
import java.net.*;
import java.net.ServerSocket;

public class HTTPServer {
    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(8000);
        System.out.println("Listening for connection on port 8000...");
        Boolean serverStatus=true;
        FileInputStream fileIn;
        OutputStream httpResponse;
        String fileName;
        
        while (serverStatus){
            //create socket on client request
            Socket client = server.accept();
            BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
            DataOutputStream output = new DataOutputStream(client.getOutputStream());
            
            try{
                //receive http request from client
                fileName = input.readLine();
                System.out.println("HTTP Request=" + fileName);
                try{
                    //parse request and try to get file requested
                    //separate 1st header elements into array
                    String[]data=fileName.split(" ");
                    fileName=data[1];
                    //find and print file name
                    fileName=fileName.substring(1);
                    System.out.println("File Name: "+fileName);
                    File toSend = new File(fileName);
                    String location=toSend.getAbsolutePath();
                    System.out.println("File Location: "+location);
                    //create http response to send file to client
                    fileIn = new FileInputStream(location);
                    httpResponse = client.getOutputStream();
                    //send over tcp
                    httpResponse.write(fileIn.read());
                    httpResponse.flush();
                    System.out.println("File Successfully Delivered.");
                }
                //catch for missing file
                catch (FileNotFoundException e){
                    String missing="File Not Found";
                    System.err.println(missing);
                    String errResponse = "HTTP/1.1 404 OK\r\n\r\n"+missing;
                    client.getOutputStream().write(errResponse.getBytes("UTF-8"));
                }
            }
            //catch all just in case
            catch (Exception e){
                System.err.println("Server error.");
            }
        }
    }
}