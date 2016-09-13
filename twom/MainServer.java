package twom;
import java.net.*;
import java.util.concurrent.*;
import java.io.*;

public class MainServer implements Runnable
{
	static ServerSocket server;
	DataInputStream inGame, inChat;
	DataOutputStream outGame, outChat;
	String nome;
	static Socket game, chat;

	private MainServer(Socket ns, Socket ns2) {
		game = ns;
		chat = ns2;
	} 

	public void run(){
		try {
			inGame = new DataInputStream(game.getInputStream());
			outGame = new DataOutputStream(game.getOutputStream());
			inChat = new DataInputStream(chat.getInputStream());
			outChat = new DataOutputStream(chat.getOutputStream());
		}catch(IOException ex){
			
		}

		while(true)
		{
			//CRIANDO O ABRIGO
			//			arenas.criarListaArenas();
			//			(new Thread(new verificaLista(arenas))).start();

			try {
				nome = inGame.readUTF();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			//			/*VARIAVEL PARA O ABRIGO*/arena = /*arenas.criarArena() ABRIGO*/;			
			try {
				outGame.writeUTF(nome);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				inGame.close();
				outGame.close();
				game.close();

				inChat.close();
				outChat.close();
				chat.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}		
	}
	
	public static void main(String[] args) throws IOException
	{
		ExecutorService exec = Executors.newCachedThreadPool();
		server = new ServerSocket(4444);

		while(true)
		{		
			Socket game = server.accept();
			Socket chat = server.accept();
			exec.execute(new MainServer(game, chat));
			if(server.isClosed()) break;
		}		
		System.out.println("Closing Server");
		server.close();
		System.exit(0);
	}
}
