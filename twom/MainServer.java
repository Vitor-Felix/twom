package twom;
import java.net.*;
import java.util.concurrent.*;
import java.io.*;
import java.io.DataInputStream;

public class MainServer
{
	public static void main(String[] args) throws IOException
	{
		ExecutorService exec = Executors.newCachedThreadPool();
		ServerSocket s = new ServerSocket(4444);
		
		DataInputStream inGame, inChat;
		DataOutputStream outGame, outChat;

		String nome;
		Socket game = s.accept();
		Socket chat = s.accept();

		//INSTANCIANDO O ABRIGO
		//			listaArenas arenas = new listaArenas();

		inGame = new DataInputStream(game.getInputStream());
		outGame = new DataOutputStream(game.getOutputStream());
		inChat = new DataInputStream(chat.getInputStream());
		outChat = new DataOutputStream(chat.getOutputStream());

		while(true)
		{
			//CRIANDO O ABRIGO
			//			arenas.criarListaArenas();
			//			(new Thread(new verificaLista(arenas))).start();

			nome = inGame.readUTF();	
			/*VARIAVEL PARA O ABRIGO*/arena = /*arenas.criarArena() ABRIGO*/;			
			outGame.writeUTF(nome);
			exec.execute(new ServerClone(game));
			inGame.close();
			outGame.close();
			game.close();
			inChat.close();
			outChat.close();
			chat.close();
		}		
	}
}
