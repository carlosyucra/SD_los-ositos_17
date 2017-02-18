import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Menu extends JFrame{
	JPanel salirP=new JPanel();
	JButton salirB=new JButton("Salir");
	JPanel iniciarP=new JPanel();
	JButton iniciarB = new JButton("Iniciar");
	MenuFondo fondo ;
	JPanel creadorP;
	JLabel creador;
	Font creadorF;
	
	//EL TAMAÑO DE LA PANTALLA
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public Menu(){
		setTitle("No Output¡");
		setSize(600,350);
		setResizable(false);
		setLocation(screenSize.width/2-600/2,screenSize.height/2-350/2);
		setLayout(new FlowLayout());
		imagen();
		botones();
		creador();
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}
	public void cambiarTexto(String t){
		fondo.cambiarText(t);
	}
	public void imagen(){
		ImageIcon imgIcon = new ImageIcon(getClass().getResource("/Imagenes/LaberintoD.png"));
		fondo= new MenuFondo(imgIcon.getImage());
		this.getContentPane().add(fondo);
	}
	public void botones(){
		iniciarP.setSize(100,100);
		iniciarB.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {	}
			@Override
			public void mousePressed(MouseEvent e) {
				visible(false);
				nuevo();
			}
			@Override
			public void mouseReleased(MouseEvent e) {			}
			@Override
			public void mouseEntered(MouseEvent e) {			}
			@Override
			public void mouseExited(MouseEvent e) {			}
		});
		iniciarP.add(iniciarB);
		add(iniciarP);
		
		salirP.setSize(100,100);
		salirB.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {	}
			@Override
			public void mousePressed(MouseEvent e) {
				System.exit(0);
			}
			@Override
			public void mouseReleased(MouseEvent e) {			}
			@Override
			public void mouseEntered(MouseEvent e) {			}
			@Override
			public void mouseExited(MouseEvent e) {			}
		});
		salirP.add(salirB);
		add(salirP);
	}
	public void creador(){
		creador=new JLabel("Creado por Carlos Yucra");
		creadorF = new Font("Arial",Font.BOLD,15);
		creadorP=new JPanel();
		creadorP.setFont(creadorF);
		creadorP.setForeground(Color.DARK_GRAY);
		creadorP.setBackground(Color.GRAY);
		creadorP.add(creador);
		add(creadorP);
	}
	public void visible(boolean f){
		setVisible(f);
	}
	public void nuevo(){
		JFrame ventana = new JFrame("No OutPut¡");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		ventana.setVisible(true);
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.setSize(1000,625);
		ventana.setResizable(false);
		ventana.setLocation(screenSize.width/2-1000/2,screenSize.height/2-625/2);
		//ventana.setLocationRelativeTo(null);
		//ventana.setResizable(false);
		Game juego = new Game(ventana,this);
		juego.iniciar();
		ventana.add(juego);
	}
	public static void main(String []args){
		Menu menu=new Menu();
	}
}
