package checkpoint;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class A1063343_GUI extends JFrame implements ActionListener {
	//// TODO: GUI ////
	public static final int width = 700;
	public static final int height = 700;
	private JLabel player[] = new JLabel[5], playermoney[] = new JLabel[5], round, turn, display_dicenumLabel,
			imgLabel[] = new JLabel[20];
	private JButton diceLabel;
	private JPanel characterInfo;
	private static BufferedImage[] image = new BufferedImage[4];
	private static int[] centerX = { 585, 465, 375, 285, 195, 85, 85, 85, 85, 85, 85, 195, 285, 375, 465, 585, 585, 585,
			585, 585 };
	private static int[] centerY = { 610, 610, 610, 610, 610, 610, 495, 405, 315, 225, 100, 100, 100, 100, 100, 100,
			225, 315, 405, 495 };
	private static int[][] picture = new int[100][1];
	int goX, goY;
	private int[] characterX, characterY,locationX,locationY;
	private static int number, location, playerNumber;
	private static boolean startNew;
	public static void Start() {
		JFrame gameStart = new JFrame();
		gameStart.setSize(200,200);
		gameStart.setResizable(false);
		gameStart.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    JPanel enter = new JPanel(new FlowLayout(FlowLayout.CENTER,35, 20));	    
	    JButton Start = new JButton("Start");
	    Start.setPreferredSize(new Dimension(65, 28));
	    enter.add(Start);
	    Start.addActionListener(new ActionListener() {
			   public void actionPerformed(ActionEvent e) {
				   try {
					   startNew=false;
					   BufferedWriter writeCharacter = new BufferedWriter(new FileWriter("Character.txt"));
					   writeCharacter.write("Round:1,Turn:1\n0,1,2000,1,Character_1.png\n"
							   +"0,2,2000,1,Character_2.png\n0,3,2000,1,Character_3.png\n"
							   +"0,4,2000,1,Character_4.png");
					   BufferedWriter writeLand = new BufferedWriter(new FileWriter("Land.txt"));
					   writeLand.write("LOCATION_NUMBER, owner\n1,0\n2,0\n3,0\n4,0\n6,0\n7,0\n8,0\n9,0\n"
							   +"11,0\n12,0\n13,0\n14,0\n16,0\n17,0\n18,0\n19,0");
					   writeCharacter.flush();
					   writeCharacter.close();
					   writeLand.flush();
					   writeLand.close();
					   A1063343_Checkpoint6.Load("Character.txt", "Land.txt");
					   gameStart.dispose();
				       A1063343_GUI Monopoly = new A1063343_GUI();
					   Monopoly.setVisible(true); 
				   }catch(IOException e2) {
					   System.out.println(e2.getMessage());
				   }
			   }
			  });
	    
	    JButton Load = new JButton("Load");
	    Load.setPreferredSize(new Dimension(65, 28));
	    Load.setActionCommand("startLoad");
	    enter.add(Load);
	    Load.addActionListener(new ActionListener() {
			   public void actionPerformed(ActionEvent e) {
				   try {
					   A1063343_Checkpoint6.Load("Character.txt", "Land.txt");	
					   gameStart.dispose();
				       A1063343_GUI Monopoly = new A1063343_GUI();
					   Monopoly.setVisible(true);
				   }catch(IOException e2){
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
			   }
			  });
	    
	    JButton Exit = new JButton("Exit");
	    Exit.setPreferredSize(new Dimension(65, 28));
	    Exit.setActionCommand("startExit");
	    Exit.addActionListener(new ActionListener() {
			   public void actionPerformed(ActionEvent e) {
				   System.exit(0);
			   }
			  });
	    enter.add(Exit);
	    gameStart.add(enter);
	    gameStart.setVisible(true);
	   
	}
	
	
	public A1063343_GUI() {
		super("Monopoly");
		int a, b, c, d, e;
		setSize(width, height);
		setResizable(false);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel allPanel = new JPanel(new BorderLayout());
		JPanel topPanel = new JPanel();

		topPanel.setLayout(new BorderLayout());
		JPanel topPanel_1 = new JPanel();
		topPanel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 0));
		JButton Save = new JButton("Save");
		Save.setPreferredSize(new java.awt.Dimension(73, 20));
		Save.addActionListener(this);

		topPanel_1.setBackground(Color.white);
		topPanel_1.add(Save);
		JButton Load = new JButton("Load");
		Load.setPreferredSize(new java.awt.Dimension(73, 20));
		Load.addActionListener(this);
		topPanel_1.add(Load);
		characterInfo = new JPanel(new GridLayout(2, 4));
		characterInfo.setBackground(Color.white);
		player = new JLabel[A1063343_Checkpoint6.CharacterArray.size()];
		playermoney = new JLabel[A1063343_Checkpoint6.CharacterArray.size()];
		for (a = 0; a < A1063343_Checkpoint6.CharacterArray.size(); a++) {
			String number = Integer.toString(A1063343_Checkpoint6.CharacterArray.get(a).CHARACTER_NUMBER);
			player[a] = new JLabel("  Character" + number + "  ");
			player[a].setFont(new Font("Consolas", Font.BOLD, 15));
			characterInfo.add(player[a]);
		}
		for (a = 0; a < A1063343_Checkpoint6.CharacterArray.size(); a++) {
			String money = Integer.toString(A1063343_Checkpoint6.CharacterArray.get(a).money);
			playermoney[a] = new JLabel(money, 0);
			playermoney[a].setFont(new Font("Consolas", Font.BOLD, 15));
			characterInfo.add(playermoney[a]);
		}
		topPanel_1.add(characterInfo);
		topPanel.add(topPanel_1, BorderLayout.NORTH);
		JPanel topPanel_2 = new JPanel();
		topPanel_2.setBackground(Color.white);
		topPanel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		ImageIcon img10 = new ImageIcon("10.png");
		JLabel imgLabel10 = new JLabel(img10);
		topPanel_2.add(imgLabel10);
		for (b = 11; b <= 14; b++) {
			ImageIcon img = new ImageIcon(b + ".png");
			imgLabel[b] = new JLabel() {
				public void paintComponent(Graphics g) {
					g.drawImage(img.getImage(), 0, 0, null);
					super.paintComponent(g);
				}
			};
			for (int i = 0; i < A1063343_Checkpoint6.LandArray.size(); i++) {
				if (A1063343_Checkpoint6.LandArray.get(i).PLACE_NUMBER == b) {
					if (A1063343_Checkpoint6.LandArray.get(i).owner != 0) {
						imgLabel[b].setText(Integer.toString(A1063343_Checkpoint6.LandArray.get(i).owner));
					}
				}
			}
			imgLabel[b].setPreferredSize(new Dimension(89, 120));
			imgLabel[b].setFont(new Font("Consolas", Font.BOLD, 40));
			imgLabel[b].setHorizontalAlignment(display_dicenumLabel.CENTER);
			imgLabel[b].setVerticalAlignment(display_dicenumLabel.CENTER);

			topPanel_2.add(imgLabel[b]);
		}
		ImageIcon img15 = new ImageIcon("15.png");
		JLabel imgLabel15 = new JLabel(img15);
		topPanel_2.add(imgLabel15);
		topPanel.add(topPanel_2, BorderLayout.SOUTH);
		allPanel.add(topPanel, BorderLayout.NORTH);

		JPanel secondPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 38, 0));
		secondPanel.setBackground(Color.white);
		JPanel leftPanel = new JPanel(new BorderLayout());
		leftPanel.setLayout(new GridLayout(4, 1));
		leftPanel.setBackground(Color.white);

		for (c = 9; c >= 6; c--) {
			ImageIcon img = new ImageIcon(c + ".png");
			imgLabel[c] = new JLabel() {
				public void paintComponent(Graphics g) {
					g.drawImage(img.getImage(), 0, 0, null);
					super.paintComponent(g);
				}
			};
			for (int i = 0; i < A1063343_Checkpoint6.LandArray.size(); i++) {
				if (A1063343_Checkpoint6.LandArray.get(i).PLACE_NUMBER == c) {
					if (A1063343_Checkpoint6.LandArray.get(i).owner != 0) {
						imgLabel[c].setText(Integer.toString(A1063343_Checkpoint6.LandArray.get(i).owner));
					}
				}
			}
			imgLabel[c].setPreferredSize(new Dimension(119, 91));
			imgLabel[c].setFont(new Font("Consolas", Font.BOLD, 40));
			imgLabel[c].setHorizontalAlignment(display_dicenumLabel.CENTER);
			imgLabel[c].setVerticalAlignment(display_dicenumLabel.CENTER);

			leftPanel.add(imgLabel[c]);
		}
		secondPanel.add(leftPanel);
		JPanel centerPanel = new JPanel(new BorderLayout());
		centerPanel.setBackground(Color.white);
		ImageIcon title = new ImageIcon("title.png");
		JLabel titleLabel = new JLabel(title);
		centerPanel.add(titleLabel, BorderLayout.NORTH);

		JPanel center = new JPanel(new FlowLayout());
		center.setBackground(Color.white);
		ImageIcon dice = new ImageIcon("Dice.png");
		diceLabel = new JButton(dice);
		diceLabel.setBorder(null);
		diceLabel.setContentAreaFilled(false);
		diceLabel.setActionCommand("dice");
		diceLabel.addActionListener(this);

		center.add(diceLabel);

		JPanel locationRound = new JPanel(new GridLayout(2, 1));
		locationRound.setBackground(Color.white);
		ImageIcon display_dicenum = new ImageIcon("display_dicenum.png");
		display_dicenumLabel = new JLabel() {
			public void paintComponent(Graphics g) {
				g.drawImage(display_dicenum.getImage(), 0, 0, null);
				super.paintComponent(g);
			}
		};
		display_dicenumLabel.setText("0");
		display_dicenumLabel.setFont(new Font("Consolas", Font.BOLD, 50));
		display_dicenumLabel.setPreferredSize(new Dimension(111, 100));
		display_dicenumLabel.setHorizontalAlignment(display_dicenumLabel.CENTER);
		display_dicenumLabel.setVerticalAlignment(display_dicenumLabel.CENTER);

		locationRound.add(display_dicenumLabel);

		round = new JLabel("Round " + A1063343_Checkpoint6.k);
		round.setBackground(Color.white);
		round.setFont(new Font("Consolas", Font.BOLD, 25));
		locationRound.add(round);

		while (A1063343_Checkpoint6.CharacterArray.get(A1063343_Checkpoint6.i).status <= 0) {
			if (A1063343_Checkpoint6.i == A1063343_Checkpoint6.CharacterArray.size() - 1) {
				A1063343_Checkpoint6.i = 0;
				A1063343_Checkpoint6.k++;
		//		System.out.println("i" + A1063343_Checkpoint5.i + "k" + A1063343_Checkpoint5.k);
				round.setText("Round " + A1063343_Checkpoint6.k);
				for (int x = 0; x < A1063343_Checkpoint6.CharacterArray.size(); x++) {
					A1063343_Checkpoint6.CharacterArray.get(x).status += 1;
				}
			} else {
				A1063343_Checkpoint6.i++;
			}
		}
		// System.out.println("i:" + A1063343_Checkpoint5.i);

		center.add(locationRound);
		centerPanel.add(center, BorderLayout.CENTER);
		JPanel turnleft = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
		turnleft.setBackground(Color.white);
		turn = new JLabel(
				"Turn Character " + A1063343_Checkpoint6.CharacterArray.get(A1063343_Checkpoint6.i).CHARACTER_NUMBER);
		turn.setFont(new Font("Consolas", Font.BOLD, 27));
		turnleft.add(turn);
		centerPanel.add(turnleft, BorderLayout.SOUTH);
		secondPanel.add(centerPanel);

		JPanel rightPanel = new JPanel(new BorderLayout());
		rightPanel.setBackground(Color.white);
		rightPanel.setLayout(new GridLayout(4, 1));

		for (d = 16; d <= 19; d++) {
			ImageIcon img = new ImageIcon(d + ".png");
			imgLabel[d] = new JLabel() {
				public void paintComponent(Graphics g) {
					g.drawImage(img.getImage(), 0, 0, null);
					super.paintComponent(g);
				}
			};

			for (int i = 0; i < A1063343_Checkpoint6.LandArray.size(); i++) {
				if (A1063343_Checkpoint6.LandArray.get(i).PLACE_NUMBER == d) {
					if (A1063343_Checkpoint6.LandArray.get(i).owner != 0) {
						imgLabel[d].setText(Integer.toString(A1063343_Checkpoint6.LandArray.get(i).owner));
					}
				}
			}
			imgLabel[d].setPreferredSize(new Dimension(119, 91));
			imgLabel[d].setFont(new Font("Consolas", Font.BOLD, 40));
			imgLabel[d].setHorizontalAlignment(display_dicenumLabel.CENTER);
			imgLabel[d].setVerticalAlignment(display_dicenumLabel.CENTER);

			rightPanel.add(imgLabel[d]);
		}
		secondPanel.add(rightPanel);
		allPanel.add(secondPanel, BorderLayout.CENTER);
		JPanel lastPanel = new JPanel(new BorderLayout());
		lastPanel.setBackground(Color.white);
		JPanel lastmap = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		lastmap.setBackground(Color.white);

		ImageIcon img5 = new ImageIcon("5.png");
		JLabel imgLabel5 = new JLabel(img5);
		lastmap.add(imgLabel5);
		for (e = 4; e >= 1; e--) {
			ImageIcon img = new ImageIcon(e + ".png");
			imgLabel[e] = new JLabel() {
				public void paintComponent(Graphics g) {
					g.drawImage(img.getImage(), 0, 0, null);
					super.paintComponent(g);
				}
			};
			for (int i = 0; i < A1063343_Checkpoint6.LandArray.size(); i++) {
				if (A1063343_Checkpoint6.LandArray.get(i).PLACE_NUMBER == e) {
					if (A1063343_Checkpoint6.LandArray.get(i).owner != 0) {
						imgLabel[e].setText(Integer.toString(A1063343_Checkpoint6.LandArray.get(i).owner));
					}
				}
			}
			imgLabel[e].setPreferredSize(new Dimension(89, 119));
			imgLabel[e].setFont(new Font("Consolas", Font.BOLD, 40));
			imgLabel[e].setHorizontalAlignment(display_dicenumLabel.CENTER);
			imgLabel[e].setVerticalAlignment(display_dicenumLabel.CENTER);

			lastmap.add(imgLabel[e]);
		}
		ImageIcon img0 = new ImageIcon("0.png");
		JLabel imgLabel0 = new JLabel(img0);
		lastmap.add(imgLabel0);

		lastPanel.add(lastmap, BorderLayout.NORTH);
		JPanel exitPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
		exitPanel.setBackground(Color.white);
		JButton ExitBotton = new JButton("Exit");
		ExitBotton.setPreferredSize(new java.awt.Dimension(80, 21));
		exitPanel.add(ExitBotton);
		ExitBotton.addActionListener(this);
		lastPanel.add(exitPanel, BorderLayout.SOUTH);
		allPanel.add(lastPanel, BorderLayout.SOUTH);
		add(allPanel);

		try {
			for (int l = 0; l < A1063343_Checkpoint6.CharacterArray.size(); l++) {
				image[l] = ImageIO.read(new File(A1063343_Checkpoint6.CharacterArray.get(l).IMAGE_FILENAME));
				picture[A1063343_Checkpoint6.CharacterArray.get(l).CHARACTER_NUMBER - 1][0] = l;

			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		characterX = new int[4];
		characterY = new int[4];
		for (int l = 0; l < A1063343_Checkpoint6.CharacterArray.size(); l++) {
			characterX[l] = centerX[A1063343_Checkpoint6.CharacterArray.get(l).location];
			characterY[l] = centerY[A1063343_Checkpoint6.CharacterArray.get(l).location];

		}

	}

	public void actionPerformed(ActionEvent e) {
		String buttonString = e.getActionCommand();
		if (buttonString.equals("Save")) {
			try {
				A1063343_Checkpoint6.Save("Character.txt", "Land.txt");
				startNew=true;
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} else if (buttonString.equals("Load")) {
			try {
				// setVisible(false);
				dispose();
				A1063343_Checkpoint6.Load("Character.txt", "Land.txt");
				A1063343_GUI Monopoly2 = new A1063343_GUI();
				Monopoly2.setVisible(true);

			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} else if (buttonString.equals("dice")) {
			location = A1063343_Checkpoint6.CharacterArray.get(A1063343_Checkpoint6.i).location;
			A1063343_Checkpoint6.Random();
			playerNumber = A1063343_Checkpoint6.CharacterArray.get(A1063343_Checkpoint6.i).CHARACTER_NUMBER;
			number = A1063343_Checkpoint6.CharacterArray.get(A1063343_Checkpoint6.i).CHARACTER_NUMBER - 1;
			moving m = new moving();
			m.start();
			display_dicenumLabel.setText(Integer.toString(A1063343_Checkpoint6.j));
		} else if (buttonString.equals("Exit")) {
			if(startNew==false) {
				File CharacterTxt = new File("Character.txt");
				File LandTxt = new File("Land.txt");
				CharacterTxt.delete();
				LandTxt.delete();
				System.exit(0);
			}else {
				System.exit(0);
			}
			
		}
	}

	public void paint(Graphics g) {
		super.paint(g);	
		int locationX[] = { characterX[0] - 15, characterX[1] + 20, characterX[2] - 15, characterX[3] + 20 };
        int locationY[] = { characterY[0] - 15, characterY[1] - 15, characterY[2] + 17, characterY[3] + 17 };
		for (int i = 0; i < A1063343_Checkpoint6.CharacterArray.size(); i++) {
			g.drawImage(image[i], locationX[i], locationY[i], null);
		}
	}

	private class moving extends Thread {
		int x, y, tolocation = A1063343_Checkpoint6.j + location, totalX, totalY, total, distance, sleepTime;
		int start = tolocation % 20 - location;

		public void run() {
			diceLabel.setEnabled(false);
			goX = centerX[location];
			goY = centerY[location];
			total = location;
			while (total < tolocation) {
				totalX = centerX[(total + 1) % 20] - centerX[total % 20];
				totalY = centerY[(total + 1) % 20] - centerY[total % 20];
				if (totalX < 0) {
					totalX = totalX * (-1);
				}
				if (totalY < 0) {
					totalY = totalY * (-1);
				}
				total++;
				distance = distance + (totalX + totalY);
			}
			if (A1063343_Checkpoint6.j >= 1 && A1063343_Checkpoint6.j <= 3) {
				sleepTime = 2000 / distance;
			} else {
				sleepTime = 3000 / distance;
			}

			while (location < tolocation) {
				// System.out.println("goX:" + goX + "goY" + goY);
				x = centerX[(location + 1) % 20] - centerX[location % 20];
				y = centerY[(location + 1) % 20] - centerY[location % 20];
				if (x > 0) {
					while (goX < centerX[(location + 1) % 20]) {
						goX += 1;
						characterX[picture[number][0]] = goX; 
						doNothing(sleepTime);
						repaint();
					}
				} else if (x < 0) {
					while (goX > centerX[(location + 1) % 20]) {
						goX -= 1;
						characterX[picture[number][0]] = goX;
						doNothing(sleepTime);
						repaint();
					}
				} else if (y > 0) {
					while (goY < centerY[(location + 1) % 20]) {
						goY += 1;
						characterY[picture[number][0]] = goY;
						doNothing(sleepTime);
						repaint();
					}
				} else if (y < 0) {
					while (goY > centerY[(location + 1) % 20]) {
						goY -= 1;
						characterY[picture[number][0]] = goY;
						doNothing(sleepTime);
						repaint();
					}
				}
				location++;
				// System.out.println("goX:" + goX + "goY" + goY);
			}
			BuyLand(tolocation % 20);

			if (A1063343_Checkpoint6.i == A1063343_Checkpoint6.CharacterArray.size() - 1) {
				A1063343_Checkpoint6.i = 0;
				A1063343_Checkpoint6.k++;
				for (x = 0; x < A1063343_Checkpoint6.CharacterArray.size(); x++) {
					A1063343_Checkpoint6.CharacterArray.get(x).status += 1;
				}
				while (A1063343_Checkpoint6.CharacterArray.get(A1063343_Checkpoint6.i).status <= 0) {
					if (A1063343_Checkpoint6.i == A1063343_Checkpoint6.CharacterArray.size() - 1) {
						A1063343_Checkpoint6.i = 0;
						A1063343_Checkpoint6.k++;
						round.setText("Round " + A1063343_Checkpoint6.k);
						for (x = 0; x < A1063343_Checkpoint6.CharacterArray.size(); x++) {
							A1063343_Checkpoint6.CharacterArray.get(x).status += 1;
						}
					} else {
						A1063343_Checkpoint6.i++;
					}
				}
				round.setText("Round " + A1063343_Checkpoint6.k);
				turn.setText("Turn Character "
						+ A1063343_Checkpoint6.CharacterArray.get(A1063343_Checkpoint6.i).CHARACTER_NUMBER);
			} else {
				A1063343_Checkpoint6.i++;
				while (A1063343_Checkpoint6.CharacterArray.get(A1063343_Checkpoint6.i).status <= 0) {
					if (A1063343_Checkpoint6.i == A1063343_Checkpoint6.CharacterArray.size() - 1) {
						A1063343_Checkpoint6.i = 0;
						A1063343_Checkpoint6.k++;
						round.setText("Round " + A1063343_Checkpoint6.k);
						for (x = 0; x < A1063343_Checkpoint6.CharacterArray.size(); x++) {
							A1063343_Checkpoint6.CharacterArray.get(x).status += 1;
						}
					} else {
						A1063343_Checkpoint6.i++;
					}
				}
				turn.setText("Turn Character "
						+ A1063343_Checkpoint6.CharacterArray.get(A1063343_Checkpoint6.i).CHARACTER_NUMBER);

			}

			diceLabel.setEnabled(true);
		}

		public void doNothing(int milliseconds) {
			try {
				sleep(milliseconds);
			} catch (InterruptedException e) {
				System.out.println("unexcepted interrupt");
				System.exit(0);
			}
		}

		public void BuyLand(int landNumber) {
			int playerindex = A1063343_Checkpoint6.i;
			if (start < 0) {
				for (int i = 0; i < A1063343_Checkpoint6.CharacterArray.size(); i++) {
					if (A1063343_Checkpoint6.CharacterArray.get(i).CHARACTER_NUMBER == playerNumber) {
						A1063343_Checkpoint6.CharacterArray.get(i).money += 2000;
						String m = Integer.toString(A1063343_Checkpoint6.CharacterArray.get(i).money);
						playermoney[i].setText(m);
					}
				}
			}
			for (int i = 0; i < A1063343_Checkpoint6.LandArray.size(); i++) {
				if (A1063343_Checkpoint6.LandArray.get(i).PLACE_NUMBER == landNumber) {
					if (A1063343_Checkpoint6.LandArray.get(i).owner == 0) {
						if (A1063343_Checkpoint6.CharacterArray.get(playerindex).money > A1063343_Checkpoint6.LandArray
								.get(i).LAND_PRICE) {
							JPanel panel = new JPanel();
							panel.setBackground(null);
							panel.setLayout(new GridLayout(2, 1));
							JLabel buyLand = new JLabel("Do you want to buy?");
							JLabel price = new JLabel("$" + A1063343_Checkpoint6.LandArray.get(i).LAND_PRICE);
							buyLand.setHorizontalAlignment(buyLand.CENTER);
							buyLand.setVerticalAlignment(buyLand.CENTER);
							price.setHorizontalAlignment(price.CENTER);
							price.setVerticalAlignment(price.CENTER);
							panel.add(buyLand);
							panel.add(price);
							int buy = JOptionPane.showConfirmDialog(A1063343_GUI.this, panel, "buy or not !!",
									JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
							if (buy == 0) {
								A1063343_Checkpoint6.LandArray.get(i).owner = playerNumber;
								imgLabel[landNumber].setText(Integer.toString(playerNumber));
								A1063343_Checkpoint6.CharacterArray
										.get(playerindex).money -= A1063343_Checkpoint6.LandArray.get(i).LAND_PRICE;
								playermoney[playerindex].setText(
										Integer.toString(A1063343_Checkpoint6.CharacterArray.get(playerindex).money));
								repaint();
							} else {
								JOptionPane.getRootFrame().dispose();
								// System.out.print("1");
							}
						}
					} else if (A1063343_Checkpoint6.LandArray.get(i).owner != playerNumber) {

						JOptionPane.showMessageDialog(A1063343_GUI.this,
								String.format("This place is owned by " + "Character"
										+ A1063343_Checkpoint6.LandArray.get(i).owner + " Character" + playerNumber
										+ " need to pay for $" + A1063343_Checkpoint6.LandArray.get(i).TOLLS),
								"Pay tolls", JOptionPane.PLAIN_MESSAGE);
						for (int j = 0; j < A1063343_Checkpoint6.CharacterArray.size(); j++) {
							if (A1063343_Checkpoint6.CharacterArray.get(j).CHARACTER_NUMBER == playerNumber) {
								A1063343_Checkpoint6.CharacterArray.get(j).money -= A1063343_Checkpoint6.LandArray
										.get(i).TOLLS;
								String m = Integer.toString(A1063343_Checkpoint6.CharacterArray.get(j).money);
								playermoney[j].setText(m);
							} else if (A1063343_Checkpoint6.CharacterArray
									.get(j).CHARACTER_NUMBER == A1063343_Checkpoint6.LandArray.get(i).owner) {
								A1063343_Checkpoint6.CharacterArray.get(j).money += A1063343_Checkpoint6.LandArray
										.get(i).TOLLS;
								playermoney[j]
										.setText(Integer.toString(A1063343_Checkpoint6.CharacterArray.get(j).money));
							}
						}
					}

				}
			}
		}

	}

}
