import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.InputStream;

public class Opciones extends JPanel implements Runnable{
	private Game juego;
	private Player jugador;
	private Enemy enemigo;
	private JPanel marcador;
	private Font marcadorF ;
	private JLabel marcadorL;
	private JPanel imagen;
	private JLabel imagenL;
	private JButton salir;
	public int tiempoBola=30; //Es el tiempo para el objetivo
	public int tiempoRecoger=30;
	public int tiempo;
	public boolean ultimoSegundo=false;
	public boolean reiniciar = false;
	public Clip sonido;
	public Clip perder;
	
	public Opciones(Game juego,Player jugador,Enemy enemigo){
		//setSize(200,600);
		setPreferredSize(new Dimension(200,600)); //Es mejor que el setSize(); :)
		setLayout(new FlowLayout());
		setBackground(Color.BLACK);
		this.juego=juego;
		this.jugador=jugador;
		this.enemigo=enemigo;
		
		//new Thread(this).start();
	}
	public void cargarSonido(){
		try {
			InputStream path=getClass().getResourceAsStream("/Sonidos/perder.wav");
			perder=AudioSystem.getClip();
			perder.open(AudioSystem.getAudioInputStream(path));
		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			System.out.println("Error al cargar sonido error");e.printStackTrace();
		}
		
	}
	public void botones(){
		salir=new JButton("Salir");
		salir.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub	
			}
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				//JOptionPane.showMessageDialog(null,"Adios¡¡¡¡...");
				//juego.hilo.get("enemigo1").stop();
				System.exit(0);
				
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				juego.setFocusable(true);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
			}
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
			}});
		add(salir);
	}
	public void marcador(){
		marcadorF = new Font("Arial",Font.BOLD,20);
		marcador=new JPanel();
		marcador.setPreferredSize(new Dimension(200,50));
		marcadorL=new JLabel("Tiempo: "+tiempoBola);
		marcadorL.setFont(marcadorF);
		marcadorL.setForeground(Color.DARK_GRAY);
		marcador.setBackground(Color.GRAY);
		marcador.add(marcadorL);
		add(marcador);
	}
	public void imagen() {
		imagen=new JPanel();
		imagenL=new JLabel();
		imagenL.setSize(200, 220);
		ImageIcon imgIcon = new ImageIcon(getClass().getResource("/Imagenes/jugador.png"));
        Image imgEscalada = imgIcon.getImage().getScaledInstance(imagenL.getWidth(),
                imagenL.getHeight(), Image.SCALE_SMOOTH);
        Icon iconoEscalado = new ImageIcon(imgEscalada);
        imagenL.setIcon(iconoEscalado);
        imagen.add(imagenL);
		
		//imagen.add(imagenL);
		add(imagen);
	}
	public void caraEnojado(){
		ImageIcon imgIcon = new ImageIcon(getClass().getResource("/Imagenes/jugadorEnojado.png"));
        Image imgEscalada = imgIcon.getImage().getScaledInstance(imagenL.getWidth(),
                imagenL.getHeight(), Image.SCALE_SMOOTH);
        Icon iconoEscalado = new ImageIcon(imgEscalada);
        imagenL.setIcon(iconoEscalado);
	}
	public void caraNormal(){
		ImageIcon imgIcon = new ImageIcon(getClass().getResource("/Imagenes/jugador.png"));
        Image imgEscalada = imgIcon.getImage().getScaledInstance(imagenL.getWidth(),
                imagenL.getHeight(), Image.SCALE_SMOOTH);
        Icon iconoEscalado = new ImageIcon(imgEscalada);
        imagenL.setIcon(iconoEscalado);
	}
//	public void paint(Graphics g){
//		g.setColor(Color.GRAY);
//		g.fillRect(800,0,200,600);
//		g.setColor(Color.GREEN);
//		g.drawString("Opciones :"+ i, 800, 10);
//	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		marcador();
		imagen();
		botones();
		cargarSonido();
		int repeticionesReloj=1;
		try{
			
				sonido=AudioSystem.getClip();
				sonido.open(AudioSystem.getAudioInputStream(getClass().getResourceAsStream("/Sonidos/reloj1.wav")));
		}catch(Exception e){
			System.out.println("Error en la carga de sonido "+e.getMessage());
		}
		while (true) {
			reiniciar = false;
			ultimoSegundo = false;
			caraNormal();
			repeticionesReloj=1;
			tiempo = tiempoBola;
			int tiempoR = tiempoRecoger;
			while (true) {
				if(reiniciar == true)break;
				try {
					marcadorL.setText("Tiempo: " + tiempo);
					if (tiempo == 0 && ultimoSegundo == false) {
						juego.izquierda.pintarComida(true, 0, 0);
						tiempo = tiempoR;
						ultimoSegundo=true;
					}else if(tiempo == 0){
						sonido.close();
						perder.close();
						juego.menu.cambiarTexto("Fuera de tiempo");
						juego.cerrarTodo();
					}if(ultimoSegundo==true && tiempo == 10){
						caraEnojado();
						repeticionesReloj=2;
					}if(ultimoSegundo== true && tiempo == 5){
						repeticionesReloj=3;
					}
					sonido.loop(repeticionesReloj);
					Thread.sleep(1000);
					tiempo--;
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
		}
	}

}
