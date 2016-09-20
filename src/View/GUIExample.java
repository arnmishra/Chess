package View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
 
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
 
 
public class GUIExample implements ActionListener{
 
    public GUIExample(){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e) {
            //silently ignore
        }
        JFrame window = new JFrame("Basic Application Example");
        window.setSize(500, 500);
        JPanel myPanel = initializePanel();
        initializeButton(myPanel);
        setUpMenu(window);
        window.setContentPane(myPanel);
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
 
    private void initializeButton(JPanel myPanel) {
        JButton button = new JButton("Click me");
        button.addActionListener(this);
        myPanel.add(button, BorderLayout.SOUTH);
    }
 
    private JPanel initializePanel() {
        JPanel myPanel = new JPanel();
        myPanel.setPreferredSize(new Dimension(500,500));
        myPanel.setLayout(new BorderLayout());
        return myPanel;
    }
 
    private void setUpMenu(JFrame window) {
        JMenuBar menubar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenuItem open = new JMenuItem("Open");
        open.addActionListener(this);
        file.add(open);
        menubar.add(file);
        window.setJMenuBar(menubar);
    }
 
    @Override
    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(null,
                    "I was clicked by "+e.getActionCommand(),
                    "Title here", JOptionPane.INFORMATION_MESSAGE);
    }
 
    public static void main(String[] args) {
        new GUIExample();
    }
}