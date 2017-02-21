import javax.swing.ImageIcon;
import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JFrame;

//Idea cada personaje debe de tener su propio mapa osea aqui poner:
//ArrayList<PuntoD> vertices
public class Player {
	private ArrayList<PuntoD>vertices=new ArrayList<PuntoD>();
	private ArrayList<NodoD>nodos;
	private Game juego;
	private Laberinto laberinto;
	public PuntoD punto; //Punto de este jugador
	private int L;
	private int posX;
	private int posY;
	private int grosor=40;
	private int tiempo=10;
	public boolean movi=true; //si el objeto si se mueve es false sino es true 
	public PuntoD dirige;		//Hasta donde se dirige es para que lo sepa el enemigo
	private ImageIcon img;
        private String personajeNombre;
        
	public Player(Game juego,Laberinto lab,int L, String personajeNombre){
		this.juego=juego;
		this.laberinto=lab;
		nodos=lab.nodos;
		this.L=L;
                this.personajeNombre = personajeNombre;
		nuevosVertices(lab.vertices);
		punto=getPunto("a").clone();
		dirige=this.punto;
		//img = new ImageIcon(getClass().getResource("/Imagenes/jugador.png"));
		img = new ImageIcon(getClass().getResource("/Imagenes/"+this.personajeNombre));
		posX=punto.getX()*L-grosor/2;
		posY=punto.getY()*L-grosor/2;
                
		//addMovimientos();
	}
	public void addMovimientos(){
		juego.izquierda.setFocusable(true);
		juego.izquierda.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}
			@Override
			public void keyReleased(KeyEvent e) {
//				//keyRelease(e);
			}
			@Override
			public void keyPressed(KeyEvent e) {
				if(movi==true)
				caminar(e);
			}
		});
	}
	public void mover(final PuntoD hasta){	
		dirige=hasta;
		movi=false;
		Runnable miRunnable = new Runnable(){
	      public void run(){
	        
	        int caminoX=(punto.getX()-hasta.getX())*L;
			int caminoY=(punto.getY()-hasta.getY())*L;
			posX=punto.getX();posX=(posX*L)-grosor/2;
			posY=punto.getY();posY=(posY*L)-grosor/2;
			while(caminoX!=0 || caminoY!=0){movi=false;
				if(caminoX<0){caminoX++;posX++;}
				if(caminoX>0){caminoX--;posX--;}
				if(caminoY<0){caminoY++;posY++;}
				if(caminoY>0){caminoY--;posY--;}
			      try {
					juego.izquierda.repaint();
					Thread.sleep(tiempo);
			      }catch (InterruptedException e){
			    	  System.out.println("Error en player");
			        e.printStackTrace();
			        Thread.currentThread().interrupt();
			      }
			}
			movi=true;
			punto.setX((posX+grosor)/L);
			punto.setY((posY+grosor)/L);
			punto.setID(hasta.getID());
	      }
	     };
	    Thread hilo = new Thread (miRunnable);
	    hilo.start();
	}
	public void caminar(KeyEvent e){ // Se recive la accion para ejecutar el movimientos
		for(PuntoD p:getAdyacentes()){
			int difX=punto.getX()-p.getX();
			int difY=punto.getY()-p.getY();
			if(movi==true && e.getKeyCode() == KeyEvent.VK_LEFT && difX>0 || e.getKeyCode() == KeyEvent.VK_RIGHT && difX<0
					|| e.getKeyCode() == KeyEvent.VK_UP && difY>0 || e.getKeyCode() == KeyEvent.VK_DOWN && difY <0){
				//izquierda();
				//return p;
				mover(p);break;
			}
		}
	}
	public void nuevosVertices(ArrayList<PuntoD> ver){	
		//Son nuevos porque cada enemigo - jugador tienes sus propios predecesores
		//Estamos independisando al personaje
		for(PuntoD p:ver){
			vertices.add(new PuntoD(p.getID(),p.getX(),p.getY()));
		}
	}
	public PuntoD getPunto(String id){
		for(PuntoD p:vertices){
			if(p.getID().equals(id))
				return p;
		}
		System.out.println("No encontrado el punto con id="+id);
		return null;
	}
	public ArrayList<PuntoD> getAdyacentes(){
		ArrayList<PuntoD> adyacentes = new ArrayList<PuntoD>();
		for(NodoD n: nodos){
			if(n.getPunto1().equals(punto))adyacentes.add(n.getPunto2());
			if(n.getPunto2().equals(punto))adyacentes.add(n.getPunto1());
		}
		return adyacentes;
	}
	public void setPos(int x,int y){
		posX=x*L-grosor/2;
		posY=y*L-grosor/2;
	}
	public void setLongitud(int l){
		L=l;
	}
	public void setTiempo(int t){
		tiempo = t;
	}
	public Rectangle getBounds(){
		return new Rectangle(posX, posY, grosor/2, grosor/2);
	}
	public void paint(Graphics2D g){
		g.drawImage(img.getImage(),posX, posY, grosor, grosor,null);
		if(juego.izquierda.comida==true && (juego.izquierda.comidaX*L==posX+grosor/2 && juego.izquierda.comidaY*L==posY+grosor/2)){
			juego.nextLevel();
		}
	}


}
