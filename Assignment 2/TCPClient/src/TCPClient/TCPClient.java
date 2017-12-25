
package TCPClient;

import java.io.*;
import java.net.*;
import java.util.concurrent.TimeUnit;

class TCPClient {
    public static void main(String argv[]) throws Exception {
        String sentence;
        String modifiedSentence;
        BufferedReader inFromUser = new BufferedReader( new InputStreamReader(System.in));
        Socket clientSocket = new Socket("localhost", 6789);
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        int i=0;
        System.out.println("Please input a string: ");
        while(i<10){
            sentence = inFromUser.readLine();
            outToServer.writeBytes(sentence + '\n');
            modifiedSentence = inFromServer.readLine();
            System.out.println(modifiedSentence);
            if(sentence.equalsIgnoreCase("exit")){
                i=10;
            }
            i++;
        }
        //delay so that server closes socket before client to prevent error
        TimeUnit.SECONDS.sleep(1);
        clientSocket.close();
        System.out.println("Connection Closed.");
    }
}