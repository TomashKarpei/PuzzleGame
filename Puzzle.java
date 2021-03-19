import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Puzzle extends JFrame implements ActionListener {

    private JPanel centerPanel;
    private JButton button;
    private JLabel label;
    private Image source;
    private Image image;
    int[][] pos;
    int width, height;

    public Puzzle() {
    	
    	//deklarowanie pozycji dla czesci obrazu
        pos = new int[][] {
                            {0, 1, 2}, 
                            {3, 4, 5},
                            {6, 7, 8} 
                            //{8, 9, 10, 11},
                            //{12, 13, 14, 15}
                        };


        centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(3, 3, 0, 0));
        
        //Dodajemy zdjecie jako tlo
        ImageIcon img = new ImageIcon("D:\\prey.jpg");
        source = img.getImage();

        //deklarujemy rozmiary zdjecia
        width = img.getIconWidth();
        height = img.getIconHeight();


        add(Box.createRigidArea(new Dimension(0, 4)), BorderLayout.NORTH);    
        add(centerPanel, BorderLayout.CENTER);

        //tworzenie 11 przyciskow, dzielimy obrazy na czesci i dodajemy czesci obrazu do tych przyciskow
        for ( int i = 0; i < 3; i++) {
            for ( int j = 0; j < 3; j++) {
                if ( j == 2 && i == 2) {
                    label = new JLabel("");
                    centerPanel.add(label);
                } else {
                    button = new JButton();
                    button.addActionListener(this);
                    centerPanel.add(button);
                    image = createImage(new FilteredImageSource(source.getSource(),
                    		
                    //napisac new CropImageFilter(i*width/3, j*height/3, (width/3)+1, height/3))); zamiast tego, co nizej, zeby zaczac od zakonczonego pazla
                        new CropImageFilter(i*width/3, j*height/3, (width/3)+1, height/3)));
                    button.setIcon(new ImageIcon(image));
                }
            }
        }

        //ustawiamy rozmiar okienka, nazwe i konfigurujemy
        setSize(1920, 1080);
        setTitle("Puzzle");
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        
    }
    

    public static void main(String[] args) {

        new Puzzle();

    }

    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        Dimension size = button.getSize();

        //dodajemy wspolrzêdne przyciskow
        int labelX = label.getX();
        int labelY = label.getY();
        int buttonX = button.getX();
        int buttonY = button.getY();
        //otrzymujemy indeks pzycisku w tablicy pozycji przyciskow
        int buttonPosX = buttonX / size.width;
        int buttonPosY = buttonY / size.height;
        int buttonIndex = pos[buttonPosY][buttonPosX];


        //polecenia umozliwiaja przesuwanie pustego okna bez pojawiania sie bledow
        if (labelX == buttonX && (labelY - buttonY) == size.height ) {

             int labelIndex = buttonIndex + 3;

             centerPanel.remove(buttonIndex);
             centerPanel.add(label, buttonIndex);
             centerPanel.add(button,labelIndex);
             centerPanel.validate();
        }

        if (labelX == buttonX && (labelY - buttonY) == -size.height ) {

             int labelIndex = buttonIndex - 3;
             centerPanel.remove(labelIndex);
             centerPanel.add(button,labelIndex);
             centerPanel.add(label, buttonIndex);
             centerPanel.validate();
        }

        if (labelY == buttonY && (labelX - buttonX) == size.width ) {

             int labelIndex = buttonIndex + 1;

             centerPanel.remove(buttonIndex);
             centerPanel.add(label, buttonIndex);
             centerPanel.add(button,labelIndex);
             centerPanel.validate();
        }

        if (labelY == buttonY && (labelX - buttonX) == -size.width ) {

             int labelIndex = buttonIndex - 1;

             centerPanel.remove(buttonIndex);
             centerPanel.add(label, labelIndex);
             centerPanel.add(button,labelIndex);
             centerPanel.validate();
        }
    }
}