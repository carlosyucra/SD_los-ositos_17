import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JPanel;

public class MenuFondo extends JPanel{
    private Image img;
    private String mensaje="No OutPut¡";
    public MenuFondo(Image img) {
        this.img = img;
        Dimension dimension = new Dimension(img.getWidth(null),img.getHeight(null));
        this.setPreferredSize(dimension);
        this.setMaximumSize(dimension);
        this.setMaximumSize(dimension);
        this.setSize(dimension);
        this.setLayout(null);
    }
    
    public void cambiarText(String t){
    	mensaje = t;
    }
    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(img, 0, 0,null);
        g.setColor(Color.DARK_GRAY);
        g.setFont(new Font("Arial",Font.BOLD,40));
        g.drawString(mensaje, 200,130);
    }
}
    
    
    