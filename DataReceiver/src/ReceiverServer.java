import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ReceiverServer {
	private static Socket socket;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ServerSocket serverSocket = null;
		try {
			if (args == null || args.length < 2) {
				System.out.println("No Port Number/Output File provided");
				System.exit(0);
			}
			int port = Integer.parseInt(args[0]);
			String outputFileName = args[1];
			serverSocket = new ServerSocket(port);
			System.out.println("Server Started and listening to the port " + port);

			// Server is running always. This is done using this while(true)
			// loop
			while (true) {
				// Reading the message from the client
				socket = serverSocket.accept();
				InputStream is = socket.getInputStream();
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr);
				String data = br.readLine();
				System.out.println("Message received from client is " + data);

				BufferedWriter writer = null;

				try {
					writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFileName, true)));
					writer.newLine();
					writer.append(data);
				} catch (IOException ex) {
					ex.printStackTrace();
				} finally {
					try {
						writer.close();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				socket.close();
				serverSocket.close();
			} catch (Exception e) {
			}
		}

	}

}
