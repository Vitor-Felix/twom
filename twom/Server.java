import java.net.*;
import java.util.concurrent.*;
import java.io.*;
import java.io.DataInputStream;

public class Server
{
	public static void main(String[] args) throws IOException{
		ExecutorService exec = Executors.newCachedThreadPool();
		ServerSocket s = new ServerSocket(4444);
//		listaArenas arenas = new listaArenas();
		DataInputStream in;
		DataOutputStream out;
//		arenas.criarListaArenas();
//		(new Thread(new verificaLista(arenas))).start();
		int indice = 0;
		int message;
		String nome;
//		No arena;
		while(true) {
			Socket ns = s.accept();
			in = new DataInputStream(ns.getInputStream());
			out = new DataOutputStream(ns.getOutputStream());
			message = in.readInt();
			if(message == 0) {
				in.close();
				out.close();
				ns.close();
			}
			else {

				if(message == 2) {
					synchronized(arenas){
					nome = in.readUTF();	
					arena = arenas.criarArena();	
					indice = arena.setUsuario(nome);		
					out.writeInt(indice);
					exec.execute(new ServerClone(ns, arena, true, indice));
					}
				}
			
				else {
					arena = arenas.getDisponivel();
					if(arena == null || arena.isCheio() || arena.getGameEstado() == 1) {
						out.writeUTF("0");
						in.close();
						out.close();
						ns.close();
					}
					else {
						out.writeUTF("1");
						nome = in.readUTF();
						indice = arena.setUsuario(nome);
						out.writeInt(indice);
						exec.execute(new ServerClone(ns, arena, false, indice));
					}
				
				}	
			}	
			
		}	

	}

}
