package checkpoint;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class start extends JFrame implements ActionListener{
	public static void main(String[] args) {
		new start("start");
	}
	public start(String start) {
		super();
//		setSize(200,200);
//		//setLayout(new BorderLayout());
//		setResizable(false);
//	    JPanel enter = new JPanel(new FlowLayout(FlowLayout.CENTER,35, 20));
//	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//	    JButton Start = new JButton("Start");
//	    Start.setPreferredSize(new Dimension(65, 28));
//	    Start.addActionListener(this);
//	    enter.add(Start);
//	    JButton Load = new JButton("Load");
//	    Load.setPreferredSize(new Dimension(65, 28));
//	    Load.setActionCommand("startLoad");
//	    Load.addActionListener(this);
//	    enter.add(Load);
//	    JButton Exit = new JButton("Exit");
//	    Exit.setPreferredSize(new Dimension(65, 28));
//	    Exit.setActionCommand("startExit");
//	    Exit.addActionListener(this);
//	    enter.add(Exit);
//	    add(enter);
//	    setVisible(true);
		
		  JFrame error = new JFrame();
		   error.setSize(150,150);
		   error.setResizable(false);
		  
		   error.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		   JPanel errorPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,50, 20));
		   JLabel message = new JLabel("no past record");
		   errorPanel.add(message,BorderLayout.CENTER);
		   JButton ok = new JButton("OK");
		   errorPanel.add(ok,BorderLayout.SOUTH);
		   ok.addActionListener(new ActionListener() {
			   public void actionPerformed(ActionEvent e) {
				   error.dispose();
				   }		   
	        });
		   error.add(errorPanel);
		   error.setVisible(true);
	}
	public void actionPerformed(ActionEvent e) {
		
	}
}
