/**
 * @author Артур Резник,
 * @version Java. Уровень 1 урок 8+, 29.06.2019
 */
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PassGen extends JFrame{

    PassGen(){

    }
    public static void main(String[] args){
        Init.init();
        //generatePassword(8);
    }
}
class Generate{
    public static String getRandomInt (){
        String result = String.valueOf((int)(Math.random()* 10));
        return result;
    }
    public static String getRandomChar(){
        String[] character = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m",
                "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
        String result = character[(int)(Math.random()*character.length)];
        if (Math.random() < 0.5){
            return result.toUpperCase();
        }
        return result;
    }
    public static String getRandomSym(){
        String[] symbol = {"!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "_", "-", "=", "+"};
        String result = symbol[(int)(Math.random()*symbol.length)];
        return result;
    }
    public static String generatePassword(int passLen){
        ArrayList<String> result = new ArrayList<>();
        String temp = "";
        for (int i = 0; i < passLen; i++){
            int switcher = (int)(Math.random()*3);
            if (switcher == 0){
                temp = getRandomInt();
            } else if (switcher == 1){
                temp = getRandomChar();
            } else {
                temp = getRandomSym();
            }
            result.add(temp);
        }
        StringBuilder sb = new StringBuilder();
        for (String x: result){
            sb.append(x);
        }

        return sb.toString();
    }
}
class Init extends JFrame{
    public static int newLength = 8;
    public static JFrame init(){
        int defaultLength = 8;

        // Frame (main frame) initialization ang configuration
        JFrame frame = new JFrame();
        frame.setTitle("Password Generator");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();
        System.out.println(width);
        Dimension dim = new Dimension((width/4), (height/8));
        if (width == 1980) {
            dim = new Dimension((width/4), (height/5));
        }
        frame.setSize(dim);


        // Text Area
        JPanel textPanel = new JPanel();
        JTextArea passText = new JTextArea(Generate.generatePassword(defaultLength));
        textPanel.add(passText);

        // Button Area
        JPanel buttonPanel = new JPanel();
        // Drop menu 8 to 16
        String[] dropOptions = {"8", "10", "12", "14", "16"};
        JComboBox dropLength = new JComboBox(dropOptions);
        dropLength.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newL = dropLength.getSelectedItem().toString();
                newLength = Integer.parseInt(newL);

            }
        });
        buttonPanel.add(dropLength);
        // New pass generation
        JButton regenerate = new JButton("Generate new");
        regenerate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                passText.setText(Generate.generatePassword(newLength));
            }
        });
        regenerate.setForeground(Color.blue);
        buttonPanel.add(regenerate, BorderLayout.WEST);

        // Copy to clipboard button
        JButton copyToClipboard = new JButton("Copy to clipboard");
        copyToClipboard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Toolkit toolkit = Toolkit.getDefaultToolkit();
                Clipboard clipboard = toolkit.getSystemClipboard();
                StringSelection strSel = new StringSelection(passText.getText());
                clipboard.setContents(strSel, null);
            }
        });
        buttonPanel.add(copyToClipboard, BorderLayout.EAST);
        JLabel empty = new JLabel(" ");


        // Adding text and buttons panels to main panel
        frame.add(empty, BorderLayout.NORTH);
        frame.add(textPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
        return frame;

    }
}