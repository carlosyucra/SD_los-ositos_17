import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JFrame;

public class Game extends JPanel{
	public JFrame ventana;
	public Menu menu;
	public Laberinto izquierda;
	public Opciones derecha;
	public Player jugador;
	public ArrayList<Enemy> enemigos;
	public ArrayList<Thread> hilos;
	public Thread opcionesHilo;
	private int L=25;
	public String nivel="nivel1";
	
	public Game(JFrame ventana,Menu menu){
		this.ventana=ventana;
		this.menu=menu;
		izquierda = new Laberinto(this,L);
		jugador = new Player(this,izquierda,L);
		enemigos = new ArrayList<Enemy>();
		derecha = new Opciones(this,jugador,null);
		hilos = new ArrayList<Thread>();
		
		setLayout(new BorderLayout());
		add(izquierda,BorderLayout.CENTER);
		add(derecha,BorderLayout.EAST);
		
		
		//new Thread(jugador).start();
		nivel1();
	}

	public void iniciar(){
		hilos.get(0).start(); //Enemigo 1
		opcionesHilo=new Thread(derecha); //Las opciones
		opcionesHilo.start();
		jugador.addMovimientos();
	}
	public void nextLevel(){
		if(nivel.equals("nivel1")){
			nivel2();
		}else if(nivel.equals("nivel2")){
			nivel3();
		}else if(nivel.equals("nivel3")){
			nivel4();
		}else if(nivel.equals("nivel4")){
			nivel5();
		}
	}
	public void nivel1(){
		nivel="nivel1";
		//jugador.mover(new PuntoD("a",3,7));
		enemigos.add(new Enemy(this,izquierda,jugador,"m",L));

		hilos.add(new Thread(enemigos.get(0)));
	}
	public void nivel2(){
		nivel="nivel2";
		
		//SE PONE DONDE IRA LA COMIDA ¡¡
		derecha.reiniciar=true;
		izquierda.comida=false;
		izquierda.comidaX=21;
		izquierda.comidaY=3;
		
		//PARA EL MOVIMIENTO DE JUGADOR
		jugador.setTiempo(1);
		jugador.movi=false;
		jugador.mover(new PuntoD("b",3,7));
		jugador.setTiempo(10);
		
		hilos.get(0).stop();  //SE PARA LOS HILOS
		enemigos.clear();		//SE ELIMINAR TODOS LOS ENEMIFOS
		enemigos.add(new Enemy(this,izquierda,jugador,"m",L));
		enemigos.get(0).setTiempo(11);
		enemigos.add(new Enemy(this,izquierda,jugador,"c1",L));
		
		//SE INICIA LOS HILOS "MOVIMIENTOS"
		hilos.clear();
		hilos.add(new Thread(enemigos.get(0)));hilos.get(0).start();
		hilos.add(new Thread(enemigos.get(1)));hilos.get(1).start();
		
		
	}
	public void nivel3(){
		nivel="nivel3";
		
		//EL TIEMPO O EL CONTADOR SE REINICIA CON LA COMIDA
		derecha.tiempoBola=50;		//tiempo para que aparesca la bola
		derecha.tiempoRecoger=60;  //tiempo que tiene para recoger la pelota
		derecha.reiniciar=true;
		izquierda.comida=false;
		izquierda.comidaX=11;
		izquierda.comidaY=3;
		
		//PARAMOS LOS HILOS DE LOS ENEMIGOS
		hilos.get(0).stop();
		hilos.get(1).stop();
		
		//SE VACIA LOS ENEMIGOS
		enemigos.clear();
		
		//SE LLENA DENUEVO EL MAPA Y LA IMAGEN
		izquierda.llenarVertices(Mapas.getMapa(2)[0]);
		izquierda.llenarNodos(Mapas.getMapa(2)[1]);
		izquierda.setImagen("/Imagenes/Laberinto2.png");
		
		//NUEVO LABERINTO TONS NUEVA LONGITUD
		L=50;
		izquierda.setLongitud(50);
		jugador.setLongitud(50);
		
		//LE DAMOS AL JUGADOR SU NUEVA POSICION
		//jugador.mover(new PuntoD("d3",4,3));
		jugador.movi=false;
		jugador.mover(new PuntoD("f",3,3));
		
		//CREAMOS EL ENEMIGO O LOS ENEMIGOS
		enemigos.add(new Enemy(this,izquierda,jugador,"t1",L));
		enemigos.get(0).setTiempo(13);
		//enemigos.get(0).setLongitud(50);
		enemigos.add(new Enemy(this,izquierda,jugador,"x1",L));
		enemigos.get(1).setTiempo(11);
		//enemigos.get(0).setLongitud(50);
		
		//INICIAMOS LOS HILOS DE LOS ENEMIGOS
		hilos.clear();
		hilos.add(new Thread(enemigos.get(0)));hilos.get(0).start();
		hilos.add(new Thread(enemigos.get(1)));hilos.get(1).start();
	}
	public void nivel4(){
		nivel="nivel4";
		//EL TIEMPO O EL CONTADOR SE REINICIA CON LA COMIDA
				derecha.tiempoBola=70;		//tiempo para que aparesca la bola
				derecha.tiempoRecoger=80;  //tiempo que tiene para recoger la pelota
				derecha.reiniciar=true;
				izquierda.comida=false;
				izquierda.comidaX=8;
				izquierda.comidaY=5;
				
				//PARAMOS LOS HILOS DE LOS ENEMIGOS
				hilos.get(0).stop();
				hilos.get(1).stop();
				
				//SE VACIA LOS ENEMIGOS
				enemigos.clear();
				
				//LE DAMOS AL JUGADOR SU NUEVA POSICION
				//jugador.mover(new PuntoD("d3",4,3));
				jugador.movi=false;
				jugador.mover(new PuntoD("z1",15,10));
				
				//CREAMOS EL ENEMIGO O LOS ENEMIGOS
				enemigos.add(new Enemy(this,izquierda,jugador,"p2",L));
				enemigos.get(0).setTiempo(11);
				//enemigos.get(0).setLongitud(50);
				enemigos.add(new Enemy(this,izquierda,jugador,"b1",L));
				enemigos.get(1).setTiempo(11);
				//enemigos.get(0).setLongitud(50);
				enemigos.add(new Enemy(this,izquierda,jugador,"d",L));
				enemigos.get(2).setTiempo(11);
				
				//INICIAMOS LOS HILOS DE LOS ENEMIGOS
				hilos.clear();
				hilos.add(new Thread(enemigos.get(0)));hilos.get(0).start();
				hilos.add(new Thread(enemigos.get(1)));hilos.get(1).start();
				hilos.add(new Thread(enemigos.get(2)));hilos.get(2).start();
	}
	//Nivel para despues :)
	public void nivel5(){
		nivel="nivel5";
	}
	public void cerrarTodo(){
		boolean v=true;
		if(derecha.tiempo==0)
		{	v=false;
			for(Thread h:hilos){
				h.stop();
			}
		}
		else opcionesHilo.stop();
		this.ventana.dispose();
		this.ventana.removeAll();
		menu.setVisible(true);
		if(v)
			for(Thread h:hilos){
			h.stop();
		}else
			opcionesHilo.stop();
		
		
	}

}
