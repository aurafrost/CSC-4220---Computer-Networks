
package TCPServer;

import java.io.*;
import java.net.*;

class TCPServer {
    public static void main(String argv[]) throws Exception {
        String clientSentence;
        String capitalizedSentence;
        String query="      Please input next string: ";
        String end="      String limit reached.";
        ServerSocket welcomeSocket = new ServerSocket(6789);
        
        boolean active=true;
        int i=0;
        Socket connectionSocket = welcomeSocket.accept();
        BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
        DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
        System.out.println("Server is running...");
        while(active && i<9) {
            clientSentence = inFromClient.readLine();
            if(clientSentence.equalsIgnoreCase("exit")){
                outToClient.writeBytes("Exit requested. Connection Closed."+'\n');
                welcomeSocket.close();
                i=10;
            }
            System.out.println("Received: " + clientSentence);
            capitalizedSentence = clientSentence.toUpperCase();
            outToClient.writeBytes("From Server: "+capitalizedSentence+query+'\n');
            i++;
        }
        if(i==9){
            clientSentence = inFromClient.readLine();
            if(clientSentence.equalsIgnoreCase("exit")){
                outToClient.writeBytes("Exit requested. Connection Closed."+'\n');
                welcomeSocket.close();
            }
            System.out.println("Received: " + clientSentence);
            capitalizedSentence = clientSentence.toUpperCase();
            outToClient.writeBytes("From Server: "+capitalizedSentence+end+'\n');
            i++;
        }
        welcomeSocket.close();
        System.out.println("Connection Closed.");
    }
}