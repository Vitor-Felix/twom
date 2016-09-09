import java.net.*;
import java.util.concurrent.*;
import java.io.*;
public class ServerClone implements Runnable
{
	private Socket ns;
//	private No arena;
	private DataOutputStream out;
	private DataInputStream in;
	private String message;
	private boolean adm;
	private int indice;
	private boolean inGame;
	private boolean ok;
	private String podeSair;
	private boolean gameOver;

	public ServerClone(Socket ns) throws IOException{
		this.ns = ns;
		gameOver = false;
		out = new DataOutputStream(ns.getOutputStream());
		in = new DataInputStream(ns.getInputStream());
		(new Thread(new Atualizador())).start();
	}

	public void run() {
		try{
//			out.writeInt(arena.getId());
			if(adm){
				while((arena.getGameEstado() == 0 || !ok) && inGame) {
					synchronized(arena) {
					try{
						while(arena.condition[indice] == 0) {arena.wait();}
					}catch(InterruptedException e) {}
					arena.condition[indice]--;
					for(int i = 0;i<6;++i) {
						out.writeUTF(arena.getUsuario(i));
						out.writeUTF("" + arena.getEstado(i));
						if(podeSair.equals("podeSair") || podeSair.equals("podeVoltar")) {inGame = false;} 
						out.writeUTF(podeSair);
					}
					}
				}
				synchronized(arena) {
					if(inGame) {
					out.writeUTF("ok");
					}
					arena.condition[indice] = 1;
				}
				
				while(arena.getGameEstado() == 1 && inGame) {
					synchronized(arena) {
						try{
							while(arena.condition[indice] == 0) {arena.wait();}
						}catch(InterruptedException e) {}
						arena.condition[indice]--;
					for(int i = 0;i<6;++i) {
						
						out.writeUTF(arena.getUsuario(i));
						out.writeUTF("" + arena.getVida(i));
						out.writeUTF("" + arena.getMadeira(i));
						out.writeUTF("" + arena.getFerro(i));
						out.writeUTF("" + arena.getComida(i));
						out.writeUTF("" + arena.getPedra(i));
						out.writeUTF("" + arena.getDefesa(i));
						out.writeUTF("" + arena.getAtaque(i));
						gameOver = arena.isGameOver();
						if(podeSair.equals("podeSair") || podeSair.equals("podeVoltar") || gameOver) {inGame = false;} 
						out.writeUTF("" + gameOver);
						out.writeUTF(podeSair);
					}
					}	
				}
			}
			else{
				while((arena.getGameEstado() == 0 || !arena.getEstado(indice) || !ok) && inGame) {
					synchronized(arena) {
						try{
							while(arena.condition[indice] == 0) {arena.wait();}
						}catch(InterruptedException e) {}
						arena.condition[indice]--;
					for(int i = 0;i<6;++i) {
						out.writeUTF(arena.getUsuario(i));
						out.writeUTF("" + arena.getEstado(i));
						out.writeUTF("" + arena.checar());
						if(podeSair.equals("podeSair") || podeSair.equals("podeVoltar")) {inGame = false;} 
						out.writeUTF(podeSair);
					}	
					}
				}
				synchronized(arena) {
					if(inGame) {
					out.writeUTF("ok");
					}
					arena.condition[indice] = 2;
					arena.notifyAll();
				}
				while(arena.getGameEstado() == 1 && inGame) {
					synchronized(arena) {
						try{
							while(arena.condition[indice] == 0) {arena.wait();}
						}catch(InterruptedException e) {}
						arena.condition[indice]--;
					for(int i = 0;i<6;++i) {
						out.writeUTF(arena.getUsuario(i));
						out.writeUTF("" + arena.getVida(i));
						out.writeUTF("" + arena.getMadeira(i));
						out.writeUTF("" + arena.getFerro(i));
						out.writeUTF("" + arena.getComida(i));
						out.writeUTF("" + arena.getPedra(i));
						out.writeUTF("" + arena.getDefesa(i));
						out.writeUTF("" + arena.getAtaque(i));
						gameOver = arena.isGameOver();
						if(podeSair.equals("podeSair") || podeSair.equals("podeVoltar") || gameOver) {inGame = false;} 
						out.writeUTF("" + gameOver);
						out.writeUTF(podeSair);
					}
					}
				}	
			}
			if(gameOver) {
				arena.setSair(indice);
			}
			
			ns.close();
			in.close();
			out.close();
			
		}catch(IOException e) {}
		
	}

	public void setOk() {
		synchronized(arena) {
			ok = true;
			arena.setCondition();
			arena.notifyAll();
		}
	}
	private class Atualizador implements Runnable{
    
    	String esc = null;
    
   		public void run() {
   			    
        	while(true){

        		try{
   					esc = in.readUTF();

   				}catch(IOException e) {} 
			    

        		switch(esc) {

            	case "0":
                	//codigo
            		arena.setSair(indice);
            		podeSair = "podeVoltar";
                	break;            
            	case "1":
                	//codigo
            		arena.setPronto();
            		arena.setGameEstado(1);
                	break;
            	case "2":
                	//codigo
            		arena.setSair(indice);
            		podeSair = "podeSair";
                	break;
            	case "3":
                	//codigo
            		arena.setSair(indice);
            		podeSair = "podeSair";
                	break;
            	case "4":
                	//codigo
            		arena.setEstado(indice);
                	break;
            	case "5":
                	//codigo
            		arena.setSair(indice);
            		podeSair = "podeVoltar";
                	break;
            	case "6":
                	//codigo
            		arena.setVida(indice);
            		arena.setSair(indice);
            		podeSair = "podeVoltar";
                	break;
            	case "aP1":
                	//codigo
            		arena.ataque(indice,0);
            		if(arena.getVida(0) <= 0) {arena.setMorto(0); arena.setVida(0);}
                	break;
            	case "aP2":
                	//codigo
            		arena.ataque(indice,1);
            		if(arena.getVida(1) <= 0) {arena.setMorto(1);arena.setVida(1);}
                	break;
            	case "aP3":
                	//codigo
            		arena.ataque(indice,2);
            		if(arena.getVida(2) <= 0) {arena.setMorto(2);arena.setVida(2);}
                	break;  
            	case "aP4":
                	//codigo
            		arena.ataque(indice,3);
            		if(arena.getVida(3) <= 0) {arena.setMorto(3);arena.setVida(3);}
                	break;            
            	case "aP5":
                	//codigo
            		arena.ataque(indice,4);
            		if(arena.getVida(4) <= 0) {arena.setMorto(4);arena.setVida(4);}
                	break;            
            	case "aP6":
                	//codigo
            		arena.ataque(indice,5);
            		if(arena.getVida(5) <= 0) {arena.setMorto(5);arena.setVida(5);}
                	break;
            	case "mP":
                	//codigo
            		arena.setMuralha(indice);
                	break;
            	case "tAP":
                	//codigo
            		arena.setTorreDeArq(indice);
                	break;
            	case "gP":
                	//codigo
            		arena.setGuarnicao(indice);
                	break;
            	case "feP":
                	//codigo
            		arena.setFerraria(indice);
                	break;
                case "pP":
                	//codigo
            		arena.setPedreira(indice);
                	break;
                case "faP":
                	//codigo
            		arena.setFazenda(indice);
                	break;		
            	case "sP":
                	//codigo
            		arena.setSerraria(indice);
                	break;
            	case "mFP":
                	//codigo
            		arena.setMinaDeFerro(indice);
                	break;
            	case "arP":
                	//codigo
            		arena.setArmazem(indice);
                	break;
            	case "soP":
                	//codigo
            		arena.setSoldado(indice);
                	break;
            	case "caP":
                	//codigo
            		arena.setCavaleiro(indice);
                	break;
            	case "arqP":
                	//codigo
            		arena.setArqueiro(indice);
                	break;
            	case "catP":
                	//codigo
            		arena.setCatapulta(indice);
                	break;
            	case "ttP":
                	//codigo
            		arena.setTransportadores(indice);
               		break;
               	case "ok":
                	//codigo
            		setOk();
               		break;   	    
               
            	default:  
            		
        		}
        	}    
    	}
	}

}