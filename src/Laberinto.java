import javax.swing.ImageIcon;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.swing.JFrame;

public class Laberinto extends JPanel {
	public ArrayList<PuntoD> vertices=new ArrayList<PuntoD>();
	public ArrayList<NodoD> nodos=new ArrayList<NodoD>();
	private Game juego;		
	private int L;
	public String localImagen;
	public boolean comida=false;
	public int comidaX=31;
	public int comidaY=23;
	ImageIcon img;
	
	public Laberinto(Game juego,int L){
		//setSize(800,600);
		this.setPreferredSize(new Dimension(800,600));
		this.juego=juego;
		this.L=L;
		//La imagen de fondo
		localImagen="/Imagenes/Laberinto.png";
		img = new ImageIcon(getClass().getResource(localImagen));
		nivel(1);				//Se inicia en nivel 1 100pre
	}
	public void setImagen(String i){
		img = new ImageIcon(getClass().getResource(i));
	}
	public void nivel(int i){  //Niveles son 1,2,3,4,5,6
		if(vertices!=null)vertices.clear();
		if(nodos!=null)nodos.clear();
		llenarVertices(Mapas.getMapa(i)[0]);
		llenarNodos(Mapas.getMapa(i)[1]);
	}
	public void llenarVertices(String vert) {
		vertices.clear();
		String todo = vert;
		try {
			while (!todo.equals("")) {
				int priComa = todo.indexOf(",");
				int segComa = todo.indexOf(",", priComa+1);
				int punComa = todo.indexOf(";");
				String id=todo.substring(0,priComa);
				int px=Integer.parseInt(todo.substring(priComa+1,segComa));
				int py=Integer.parseInt(todo.substring(segComa+1,punComa));
				vertices.add(new PuntoD(id, px, py));
				todo = todo.substring(punComa+1, todo.length());
			}
		} catch (Exception e) {
			System.out.println("Error llenarVertices: " + e.getMessage()+" "+e);
			System.out.println("Recordar que el laberinto es de forma a,2,5;b,4,5;...;");
			System.exit(0);
		}
	}

	public void llenarNodos(String nodo) {
		nodos.clear();
		String todo = nodo;
		try{
			while(!todo.equals("")){
				int priComa = todo.indexOf(",");
				int punComa = todo.indexOf(";");
				String pri = todo.substring(0,priComa);
				String seg = todo.substring(priComa+1,punComa);
				nodos.add(new NodoD(vertices.get(convertID(pri)),vertices.get(convertID(seg))));
				todo=todo.substring(punComa+1,todo.length());
			}
		}catch(Exception e){
			System.out.println("Error llenarNodos: "+e.getMessage()+" "+e);
			System.out.println("Recordar que los nodos son de la forma a,b;c,a;d,e;...;");
			System.exit(0);
		}
	}

	public int convertID(String id){
		//LO QUE HACE EL PROGRAMA ES DEVOLVER UN NUMERO DE ACUERDO CON LA PALABRA, a=0,b=1,c=2,...
		String[] ids={"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"
				,"a1","b1","c1","d1","e1","f1","g1","h1","i1","j1","k1","l1","m1","n1","o1","p1","q1","r1","s1","t1","u1","v1","w1","x1","y1","z1"
				,"a2","b2","c2","d2","e2","f2","g2","h2","i2","j2","k2","l2","m2","n2","o2","p2","q2","r2","s2","t2","u2","v2","w2","x2","y2","z2"
				,"a3","b3","c3","d3","e3","f3","g3","h3","i3","j3","k3","l3","m3","n3","o3","p3","q3","r3","s3","t3","u3","v3","w3","x3","y3","z3"};
		for(int i=0;i<ids.length;i++){
			if(ids[i].equals(id)){
				return i;
			}
		}
		System.out.println("Error en convertID -1");
		return -1;
	}
	public void setLongitud(int l){
		L=l;
	}
	public void pintarComida(boolean b,int x,int y){
		comida=b;
	}
	public void paint(Graphics g){
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, //Sirve para que no se lagee la imagen
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.drawImage(img.getImage(),0,0,800,600,null);
		setOpaque(false); //EN prueba
		g2d.setColor(Color.GRAY);
		for(NodoD n:nodos){
			g2d.drawLine(n.getPunto1().getX()*L, n.getPunto1().getY()*L, n.getPunto2().getX()*L, n.getPunto2().getY()*L);
		}
		
		if(juego.jugador!=null)juego.jugador.paint(g2d);
		for(int i=0;i<juego.enemigos.size();i++)
			if(juego.enemigos.get(i)!=null)juego.enemigos.get(i).paint(g2d);
		if(comida==true){
			g2d.setColor(Color.BLUE);
			g2d.fillOval(comidaX*L -20/2, comidaY*L-20/2, 20, 20);
		}
	}
	


	

}
