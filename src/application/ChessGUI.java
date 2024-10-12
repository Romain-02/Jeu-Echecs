package application;
import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.*;
import java.util.Timer;

public class ChessGUI extends JFrame implements ActionListener{
	
	ArrayList<int[]> ListPossibleMoves;
	int[] LastClick;
	ArrayList<Piece> emptyList = new ArrayList<Piece>();
	char color = 'w', colorOp = 'b'; 
	Color whiteCase = Color.white, blackCase = Color.green, priseColor = Color.red; 
	
	Board Board;
	Boolean boucle;
	
	ArrayList<Piece[][]> boards;
	HashMap<Piece[][], Integer> mapBoard = new HashMap<Piece[][], Integer>();
	int nbBoard = 0;
	
	int[] score = {0, 0}; 
	public enum Joueur {JOUEUR, ORDI};
	Joueur[] paramJoueur = {Joueur.ORDI, Joueur.ORDI};
	final static int DEPTH = 4;
	
	ImageIcon blackBishop = new ImageIcon("Image/blackBishop.PNG");
	ImageIcon whiteBishop = new ImageIcon("Image/whiteBishop.PNG");
	ImageIcon blackKing = new ImageIcon("Image/blackKing.PNG");
	ImageIcon whiteKing = new ImageIcon("Image/whiteKing.PNG");
	ImageIcon blackPawn = new ImageIcon("Image/blackPawn.PNG");
	ImageIcon whitePawn = new ImageIcon("Image/whitePawn.PNG");
	ImageIcon blackQueen = new ImageIcon("Image/blackQueen.PNG");
	ImageIcon whiteQueen = new ImageIcon("Image/whiteQueen.PNG");
	ImageIcon blackKnight = new ImageIcon("Image/blackKnight.PNG");
	ImageIcon whiteKnight = new ImageIcon("Image/whiteKnight.PNG");
	ImageIcon blackRock = new ImageIcon("Image/blackRock.PNG");
	ImageIcon whiteRock = new ImageIcon("Image/whiteRock.PNG");
	ImageIcon greyCircle = new ImageIcon("Image/greyCircle.png");
	ImageIcon boardImg = new ImageIcon("Image/board.png");
	ImageIcon menu = new ImageIcon("Image/menu.jpg");
	ImageIcon imgPromo;

	Action spaceAction;
	Action rightAction;
	Action leftAction;
	
	HashMap<Float, ImageIcon> ImageMap = new HashMap<Float, ImageIcon>();
	
	JButton[] ListButtons = new JButton[64];
	static Timer timer;
	
	JPanel boardPanel;
	int[][] lastMove = new int[][] {{-1, -1},{-1, -1}};
	ArrayList<Integer> priseCase = new ArrayList<Integer>();
	
    public ChessGUI() {
    	
    	ListPossibleMoves = new ArrayList<int[]>();
    	LastClick = new int[2];
    	LastClick[0] = -1;
    	LastClick[1] = -1;
    	Board = new Board(0, true, emptyList, emptyList);
    	
    	ImageMap.put((float) (-5), blackRock);
    	ImageMap.put((float) (5), whiteRock);
    	ImageMap.put((float) (-3), blackKnight);
    	ImageMap.put((float) (3), whiteKnight);
    	ImageMap.put((float) (-3.1), blackBishop);
    	ImageMap.put((float) (3.1), whiteBishop);
    	ImageMap.put((float) (-9), blackQueen);
    	ImageMap.put((float) (9), whiteQueen);
    	ImageMap.put((float) (-100), blackKing);
    	ImageMap.put((float) (100), whiteKing);
    	ImageMap.put((float) (-1), blackPawn);
    	ImageMap.put((float) (1), whitePawn);
        setTitle("Chess Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800);
        setLocationRelativeTo(null);
        
        printMenu();
    }

    public static void main(String[] args) {
    	
        SwingUtilities.invokeLater(() -> {
            ChessGUI gui = new ChessGUI();
            gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gui.setVisible(true);
        });
    }

	public void printGameOver(char c){

		remove(boardPanel);
		boardPanel = new JPanel(new GridLayout(8, 8)){
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				// Dessiner l'image en arrière-plan du panneau
				g.drawImage(resizeImage(boardImg, 800, 800).getImage(), 0, 0, getWidth(), getHeight(), this);
			}
		};
		
		add(boardPanel);

		Board = new Board(0, true, emptyList, emptyList);
		lastMove = new int[][]{{-1, -1}, {-1, -1}};

		JLabel label = new JLabel("Les " + (c == 'w' ? "blancs" : "noires") + " ont gagnés");
		label.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 68));
		label.setForeground(new Color(10, 10, 10));

		boardPanel.add(label, BorderLayout.NORTH);
		this.update(getGraphics());
		color = 'w';

		try {
			Thread.sleep(5000);
			remove(boardPanel);
			printMenu();
		} catch (Exception e) {
			// TODO: handle exception
		}
		remove(boardPanel);
		printMenu();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	
		if(paramJoueur[0] == Joueur.ORDI && paramJoueur[1] == Joueur.ORDI) {
			actionOrdi();
		} else {
			int compt = 0;
			for(JButton b : ListButtons) {
				compt += 1;
				//si on a appuyé sur ce bouton
				if(e.getSource() == b) {
					int x = (int) Math.floor((compt -1) / 8);
					int y = (compt -1) % 8;
					//on clique sur une de nos pièces
					if(Board.getBoard()[x][y] != null && Board.getBoard()[x][y].getColor() == color) {
						//on clique pour la première fois
						if(ListPossibleMoves.isEmpty()) {
							LastClick[0] = x;
							LastClick[1] = y;
							ListPossibleMoves = Board.getLegalMovesPiece(Board.getBoard()[x][y].getColor(), x, y);
							priseCase = new ArrayList<Integer>();
							for(int[] coord : ListPossibleMoves) {
								if(Board.getBoard()[coord[0]][coord[1]] == null) {
									ListButtons[coord[0] * 8 + coord[1]].setIcon(greyCircle);
								}else if(Board.getBoard()[coord[0]][coord[1]].getColor() == colorOp) {
									boardPanel.getComponent(coord[0] * 8 + coord[1]).setBackground(priseColor);
									priseCase.add(coord[0] * 8 + coord[1]);
								}
							}
							//on clique une nouvelle fois sur une de nos pièces
						} else {
							for(int[] coord : ListPossibleMoves) {
								if(Board.getBoard()[coord[0]][coord[1]] == null) {
									ListButtons[coord[0] * 8 + coord[1]].setIcon(null);
								}
							}
							ListPossibleMoves = new ArrayList<int[]>();
							clearPriseCase();
							
							//si c'est une nouvelle pièce
							if(x != LastClick[0] || LastClick[1] != y) {
								ListPossibleMoves = Board.getLegalMovesPiece(Board.getBoard()[x][y].getColor(), x, y);
								LastClick[0] = x;
								LastClick[1] = y;
								for(int[] coord : ListPossibleMoves) {
									if(Board.getBoard()[coord[0]][coord[1]] == null) {
										ListButtons[coord[0] * 8 + coord[1]].setIcon(greyCircle);
									}else if(Board.getBoard()[coord[0]][coord[1]].getColor() == colorOp) {
										boardPanel.getComponent(coord[0] * 8 + coord[1]).setBackground(priseColor);
										priseCase.add(coord[0] * 8 + coord[1]);
									}
								}
							}
							
						}					
						
					} else {
						int[] Case = {x, y};
						for(int[] c : ListPossibleMoves) {
							int[] coord2 = new int[] {c[0], c[1]};
							if(Arrays.equals(coord2, Case)) {
								
								
								if(c.length == 10) {                       // cas du Rock
									Board.movePiece(LastClick, c);
									b.setIcon(ListButtons[LastClick[0] * 8 + LastClick[1]].getIcon());
									ListButtons[c[2] * 8 + c[3]].setIcon(ListButtons[c[4] * 8 + c[5]].getIcon());
									ListButtons[c[4] * 8 + c[5]].setIcon(null);
								} else if(c.length == 4){
									Board.movePiece(LastClick, c);
									b.setIcon(ListButtons[x * 8 + y].getIcon());
									ListButtons[c[0] * 8 + c[1]].setIcon(null);
									ListButtons[c[2] * 8 + c[3]].setIcon(null);
								}
								//Jaunissement des cases du dernier coup
								printLastMove(new int[][] {LastClick, coord2}, lastMove);
								lastMove = new int[][] {new int[] {LastClick[0], LastClick[1]}, coord2};
								clearPriseCase();
								
								Board.movePiece(LastClick, Case);
								b.setIcon(ListButtons[LastClick[0] * 8 + LastClick[1]].getIcon());
								ListButtons[LastClick[0] * 8 + LastClick[1]].setIcon(null);
								LastClick[0] = -1;
								LastClick[1] = -1;
								for(int[] coord : ListPossibleMoves) {
									if(Board.getBoard()[coord[0]][coord[1]] == null) {
										ListButtons[coord[0] * 8 + coord[1]].setIcon(null);
									}
								}
								ListPossibleMoves = new ArrayList<int[]>();
								
								for(int i = 0; i < 8; i ++) {
									imgPromo = color == 'w' ? whiteQueen : blackQueen;
									if(Board.getBoard()[0][i] != null && Board.getBoard()[0][i].getName() == "Pawn") {
										Board.getList(color).remove(Board.getBoard()[0][i]);
										Board.getBoard()[0][i] =  new Queen(Board.getBoard()[0][i].getColor(), 0, i, false, false, false, "Queen");
										ListButtons[i].setIcon(resizeImage(imgPromo, 80, 80));
										Board.getList(color).add(Board.getBoard()[0][i]);
									}
								}
								
								
								this.update(getGraphics());
								boards.add(Board.copyBoard(Board.getBoard()));
								nbBoard++;

								if(Board.checkmate(colorOp)) {
									printGameOver(color);
								}
								
								if(paramJoueur[1] == Joueur.ORDI) {
									playOrdi(colorOp);
									nbBoard++;
								} else {
									/*afficheBoard(Board.getBoard(), color == 'w');*/
								}

								if(Board.checkmate(color)) {
									printGameOver(color == 'w' ? 'b' : 'w');
								}
								
								/*Board.showBoard();*/
								for(int i = 0; i < 8; i ++) {
									imgPromo = color == 'w' ? blackQueen : whiteQueen;
									if(Board.getBoard()[7][i] != null && Board.getBoard()[7][i].getName() == "Pawn") {
										Board.getList(colorOp).remove(Board.getBoard()[7][i]);
										Board.getBoard()[7][i] =  new Queen(Board.getBoard()[7][i].getColor(), 7, i, false, false, false, "Queen");
										ListButtons[i + 56].setIcon(resizeImage(imgPromo, 80, 80));
										Board.getList(colorOp).add(Board.getBoard()[7][i]);
									}
								}
								
								if(paramJoueur[0] == paramJoueur[1]) {
									color = color == 'w' ? 'b' : 'w';
								}
								this.update(getGraphics());
							}
						}
				}
			}
			}
		}
	}
	
	public ImageIcon resizeImage(ImageIcon img, int width, int height) {
		Image image = img.getImage(); // transform it 
		Image newImg = image.getScaledInstance(width, height,  Image.SCALE_SMOOTH); // scale it the smooth way
		return new ImageIcon(newImg);  // transform it back
	}
	
	//jaunissement des cases du dernier coup et on enlève celle de l'avant dernier coup
	public void printLastMove(int[][] move, int[][] lastMove) {
		//on enlève le jaune de l'avant dernier coup
		Color[] tabColor = new Color[] {whiteCase, blackCase};
		if(lastMove[0][0] != -1) {
			boardPanel.getComponent(lastMove[0][0] * 8 + lastMove[0][1]).setBackground(tabColor[(lastMove[0][0] + (lastMove[0][1]%2)) % 2]);
			boardPanel.getComponent(lastMove[1][0] * 8 + lastMove[1][1]).setBackground(tabColor[(lastMove[1][0] + (lastMove[1][1]%2)) % 2]);
			
		}
		//on ajoute le jaune du dernier coup
		/*if(paramJoueur[0] == paramJoueur[1] && paramJoueur[0] == Joueur.JOUEUR){
			boardPanel.getComponent(63 - (move[0][0] * 8 + move[0][1])).setBackground(Color.yellow);
			boardPanel.getComponent(63 - (move[1][0] * 8 + move[1][1])).setBackground(Color.yellow);
		}else{ */
			boardPanel.getComponent(move[0][0] * 8 + move[0][1]).setBackground(Color.yellow);
			boardPanel.getComponent(move[1][0] * 8 + move[1][1]).setBackground(Color.yellow);
		/* }*/
		
	}
	
	public void clearPriseCase() {
		Color[] tabColor = new Color[] {whiteCase, blackCase};
		for(int coord : priseCase) {
			boardPanel.getComponent(coord).setBackground(tabColor[(coord/8 + (coord%2)) % 2]);
		}
	}
	
	public void initializeBoard() {
		int compt = 0;

		boards = new ArrayList<Piece[][]>();
		boardPanel = new JPanel(new GridLayout(8, 8));
		
		String[][] startingPosition = {
	            {"-5", "-3", "-3.1", "-9", "-100", "-3.1", "-3", "-5"},
	            {"-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1"},
	            {"", "", "", "", "", "", "", ""},
	            {"", "", "", "", "", "", "", ""},
	            {"", "", "", "", "", "", "", ""},
	            {"", "", "", "", "", "", "", ""},
	            {"1", "1", "1", "1", "1", "1", "1", "1"},
	            {"5", "3", "3.1", "9", "100", "3.1", "3", "5"}
	        };
        
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                JPanel square = new JPanel(new BorderLayout());
                
                JButton button = new JButton();
                button.setOpaque(false);
                button.setContentAreaFilled(false);
                button.setBorderPainted(false);
                button.addActionListener(this);
                button.setFocusPainted(false);
                
                if(startingPosition[row][col] != "") {
	                button.setIcon(resizeImage(ImageMap.get(Float.parseFloat(startingPosition[row][col])), 80, 80));
                }
                
                ListButtons[compt] = button;
                square.add(button);
                
                if ((row + col) % 2 == 0) {
                    square.setBackground(whiteCase);
                } else {
                    square.setBackground(blackCase);
                }
                boardPanel.add(square);
                compt += 1;
            }
        }

		spaceAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
				boucle = true;
            	for(int i = 0; i < 10; i++) {
            		while(boucle) actionOrdi();
            	}
            }
        };
        leftAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	System.out.println("left : " + (nbBoard-1));
            	if(nbBoard > 1) {
                	nbBoard--;
                	afficheBoard(boards.get(nbBoard-1));
                } 
            }
        };
        rightAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	System.out.println("right : " + (nbBoard+1));
                if(nbBoard == boards.size()) 
                	actionOrdi();
                else {
                	nbBoard++;
                	afficheBoard(boards.get(nbBoard-1));
                }
            }
        };

        boardPanel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "LEFT");
        boardPanel.getActionMap().put("LEFT", leftAction);
        
        boardPanel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "RIGHT");
        boardPanel.getActionMap().put("RIGHT", rightAction);
        
        boardPanel.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "SPACE");
        boardPanel.getActionMap().put("SPACE", spaceAction);
        
        add(boardPanel);
		this.update(getGraphics());
	}
	
	public void playOrdi(char color) {
		int[] minmax = Board.minmax(color, DEPTH, -1000, 1000, color=='w');
		int[] coordBot1 = {minmax[1], minmax[2]};
		int[] coordBot2 = {minmax[3], minmax[4]};
		
		//Jaunissement des cases du dernier coup
		printLastMove(new int[][] {coordBot1, coordBot2}, lastMove);
		lastMove = new int[][] {new int[] {coordBot1[0], coordBot1[1]}, new int[] {coordBot2[0], coordBot2[1]}};
		
		Board.movePiece(coordBot1, coordBot2);
		ListButtons[minmax[3] * 8 + minmax[4]].setIcon(ListButtons[minmax[1] * 8 + minmax[2]].getIcon());
		ListButtons[minmax[1] * 8 + minmax[2]].setIcon(null);
	}
	
	public void afficheBoard(Piece[][] board) {
		HashMap<String, Double> map = new HashMap<String, Double>(){{put("Pawn", 1.0); put("Knight", 3.0); put("Bishop", 3.1); put("King", 100.0); put("Queen", 9.0); put("Rock", 5.0);}};
		for(int i = 0; i < ListButtons.length; i++) {
			if(board[i/8][i%8] != null)
				ListButtons[i].setIcon(resizeImage(ImageMap.get(Float.parseFloat((map.get(board[i/8][i%8].getName()) * (board[i/8][i%8].getColor()=='w'?1:-1)) + "")), 80, 80));
			else
				ListButtons[i].setIcon(null);
		}
		this.update(getGraphics());
	}

	public void afficheBoard(Piece[][] board, boolean b) {
		HashMap<String, Double> map = new HashMap<String, Double>(){{put("Pawn", 1.0); put("Knight", 3.0); put("Bishop", 3.1); put("King", 100.0); put("Queen", 9.0); put("Rock", 5.0);}};

		for(int i = 0; i < 64; i++){
			if(board[i/8][i%8] != null)
				ListButtons[b ? 63 - i: i].setIcon(resizeImage(ImageMap.get(Float.parseFloat((map.get(board[i/8][i%8].getName()) * (board[i/8][i%8].getColor()=='w'?1:-1)) + "")), 80, 80));
			else
				ListButtons[b ? 63 - i: i].setIcon(null);
		}
		this.update(getGraphics());
	}
	
	public void actionOrdi() {
		
		playOrdi(color);
		this.update(getGraphics());
		color = color == 'w' ? 'b' : 'w';
		
		boards.add(Board.copyBoard(Board.getBoard()));
		nbBoard++;
		if(Board.checkmate(color) || Board.pat(color) || this.nullSamePosition()) {
			printGameOver(color == 'w' ? 'b' : 'w');
			boucle = false;
		}
		
	}
	
	public boolean nullSamePosition() {
		for(Piece[][] board : mapBoard.keySet()) {
			if(Board.equalBoard(board)) {
				if(mapBoard.get(board) == 2) return true;
				else {
					mapBoard.replace(board, (mapBoard.get(board)+1)); 
					return false; 
				}
			}
		}
		
		mapBoard.put(Board.copyBoard(Board.getBoard()), 1);
		return false;
	}

	public static void invisibleButton(JButton b){
		b.setBorder(null);
		b.setFocusPainted(false);
		b.setPressedIcon(null);
		b.setRolloverIcon(null);
		b.setRolloverEnabled(false);
		b.setRolloverSelectedIcon(null);
		b.setSelectedIcon(null);
		b.setBorderPainted(false);
		
		Font maPolice = new Font("Arial", Font.BOLD | Font.ITALIC, 75);
		b.setFont(maPolice);
		b.setForeground(new Color(200, 200, 200));

	}

	public void printMenu(){
		setSize(1200, 800);
		JPanel buttonPanel = new JPanel(new GridLayout(3, 1));

		JButton playerPlayerButton = new JButton("PvP"); invisibleButton(playerPlayerButton); playerPlayerButton.setBackground(new Color(0, 0, 0));playerPlayerButton.setPreferredSize(new Dimension(280, 125));
		JButton playerOrdiButton = new JButton("PvO"); invisibleButton(playerOrdiButton); playerOrdiButton.setBackground(new Color(100, 20, 20));
		JButton ordiOrdiButton = new JButton("OvO"); invisibleButton(ordiOrdiButton); ordiOrdiButton.setBackground(new Color(0, 120, 150));

		JPanel myPanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				// Dessiner l'image en arrière-plan du panneau
				g.drawImage(resizeImage(menu, 1200, 800).getImage(), 0, 0, getWidth(), getHeight(), this);
				}
			};

		JLabel label = new JLabel("Choisissez le mode de jeu !");
		label.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 68));
		label.setForeground(new Color(180, 180, 180));

		myPanel.add(label);

		myPanel.add(buttonPanel);
		myPanel.setBackground(new Color(0, 0, 0, 0));

		buttonPanel.add(playerPlayerButton); buttonPanel.add(playerOrdiButton); buttonPanel.add(ordiOrdiButton);
		
		myPanel.add(buttonPanel);

		add(myPanel);

		playerPlayerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Code à exécuter lorsque le bouton est cliqué
				paramJoueur[0] = Joueur.JOUEUR;
				paramJoueur[1] = Joueur.JOUEUR;
				remove(myPanel);
				setSize(800, 800);
				initializeBoard();
			}
		});

		playerOrdiButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Code à exécuter lorsque le bouton est cliqué
				paramJoueur[0] = Joueur.JOUEUR;
				paramJoueur[1] = Joueur.ORDI;
				remove(myPanel);
				setSize(800, 800);
				initializeBoard();
			}
		});

		ordiOrdiButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Code à exécuter lorsque le bouton est cliqué
				paramJoueur[0] = Joueur.ORDI;
				paramJoueur[1] = Joueur.ORDI;
				remove(myPanel);
				setSize(800, 800);
				initializeBoard();
			}
		});
	}
}