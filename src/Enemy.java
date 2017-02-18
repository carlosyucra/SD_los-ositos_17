import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.JFrame;

//Idea cada personaje debe de tener su propio mapa osea aqui poner:
//ArrayList<PuntoD> vertices
public class Enemy implements Runnable{
	private ArrayList<PuntoD>vertices;
	private ArrayList<NodoD>nodos;
	private Game juego;
	private Laberinto laberinto;
	private PuntoD punto; //Punto de este enemigo
	private Player jugador;
	private IA inteli=new IA();
	private int L;
	private int posX;
	private int posY;
	private int grosor=40;
	private int tiempo=15;
	private PuntoD objetivo;
	private ImageIcon img;
	
	public Enemy(Game juego,Laberinto lab,Player jugador,String ini,int L){
		this.juego=juego;
		this.laberinto=lab;
		this.jugador=jugador;
		this.L=L;
		nuevosVertices(lab.vertices);
		nuevosNodos(lab.nodos);
		punto=getPunto(ini).clone();
		posX=punto.getX()*L-grosor/2;
		posY=punto.getY()*L-grosor/2;
		img = new ImageIcon(getClass().getResource("/Imagenes/enemigo.png"));
		
	}
	
	public void nuevosVertices(ArrayList<PuntoD> ver){	
		//Son nuevos porque cada enemigo - jugador tienes sus propios predecesores
		//Estamos independisando al personaje
		vertices=new ArrayList<PuntoD>();
		for(PuntoD p:ver){
			vertices.add(new PuntoD(p.getID(),p.getX(),p.getY()));
		}
	}
	public void nuevosNodos(ArrayList<NodoD> nod){
		nodos=new ArrayList<NodoD>();
		for(NodoD p:nod){		//INICIALIZAMOS LOS NODOS
			nodos.add(
					new NodoD(vertices.get(convertID(p.getPunto1().getID())),
							vertices.get(convertID(p.getPunto2().getID()))));
		}
	}
	public void mover(PuntoD hasta){
		if(hasta == null)hasta=jugador.dirige;
		int caminoX=(punto.getX()-hasta.getX())*L;
		int caminoY=(punto.getY()-hasta.getY())*L;
		posX=punto.getX();posX=(posX*L)-grosor/2;
		posY=punto.getY();posY=(posY*L)-grosor/2;
		while(caminoX!=0 || caminoY!=0){
			if(caminoX<0){caminoX++;posX++;}
			if(caminoX>0){caminoX--;posX--;}
			if(caminoY<0){caminoY++;posY++;}
			if(caminoY>0){caminoY--;posY--;}
			juego.izquierda.repaint();
			try{
				Thread.sleep(tiempo);
			}catch(InterruptedException e){
				System.out.println("Error bola1 "+e.getMessage());
			}
		}
		punto.setX((posX+grosor)/L);
		punto.setY((posY+grosor)/L);
		punto.setID(hasta.getID());
	}
	

	public Rectangle getBounds(){
		//Este lo dibujo a la mitad para que se vea mas realizta
		//ya que si los cuadrados son muy grandes entonces la colizion se dara incluso
		//sin tocarse las dos imagenes "caras"
		return new Rectangle(posX,posY,grosor/2,grosor/2);
	}
	
	public PuntoD getPunto(String id){
		for(PuntoD p:vertices){
			if(p.getID().equals(id))
				return p;
		}
		System.out.println("No encontrado el punto con id="+id);
		return null;
	}
	public void setTiempo(int t){
		this.tiempo=t;
	}
	public int convertID(String id){
		//LO QUE HACE EL PROGRAMA ES DEVOLVER UN NUMERO DE ACUERDO CON LA PALABRA, a=0,b=1,c=2,...
		int r=-1;
		String[] ids={"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"
				,"a1","b1","c1","d1","e1","f1","g1","h1","i1","j1","k1","l1","m1","n1","o1","p1","q1","r1","s1","t1","u1","v1","w1","x1","y1","z1"
				,"a2","b2","c2","d2","e2","f2","g2","h2","i2","j2","k2","l2","m2","n2","o2","p2","q2","r2","s2","t2","u2","v2","w2","x2","y2","z2"
				,"a3","b3","c3","d3","e3","f3","g3","h3","i3","j3","k3","l3","m3","n3","o3","p3","q3","r3","s3","t3","u3","v3","w3","x3","y3","z3"};
		for(int i=0;i<ids.length;i++){
			if(ids[i].equals(id)){
				r=i;
				break;
			}
		}
		//System.out.println("No se encontra el id en convertID");
		return r;
	}
	public void setLongitud(int l){
		L=l;
	}
	public void paint(Graphics2D g){
		g.drawImage(img.getImage(),posX, posY, grosor, grosor,null);
		if(jugador.getBounds().intersects(getBounds())){
			juego.derecha.perder.loop(1);
			juego.derecha.sonido.close();
			juego.derecha.perder.close();
			juego.menu.cambiarTexto("Perdio");;
			juego.cerrarTodo();
		}
	}
	public void perseguir(){
		for(PuntoD p:inteli.getCamino(vertices,nodos,punto,jugador.punto)){
			mover(p);
			if(!objetivo.equals(jugador.dirige))break; //Si el objetivo es nuevo se hace nueva ruta por eso se rompe
		}
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			objetivo=jugador.dirige;
			perseguir();}

	}
}
