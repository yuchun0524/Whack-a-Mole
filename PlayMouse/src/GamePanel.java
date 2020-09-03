import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class GamePanel extends JFrame implements ActionListener {

	static final int WIDTH = 600;
	static final int HEIGHT = 800;
	public static boolean start = false;
	public static boolean newgame = false;
	JMenuItem itemStart = new JMenuItem("Start");
	JMenuItem itemPause = new JMenuItem("Pause");
	JMenuItem itemContinue = new JMenuItem("Continue");
	JMenuItem itemExit = new JMenuItem("Exit");
	Container pane = getContentPane();
	// initialize panel and menu bar

	public GamePanel() {
		setSize(WIDTH, HEIGHT);
		setLocationRelativeTo(null);
		this.getContentPane().setLayout(null);
		setbackground();
		setMeun();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("打地鼠");
		this.setResizable(false);
		this.setVisible(true);
		itemPause.setEnabled(false);
		JOptionPane.showMessageDialog(this, "遊戲說明：\n左上角點擊Game可出現選單，分別有開始、暫停、繼續與離開的功能。\n用滑鼠點擊地鼠可得分，點擊炸彈會扣分，時間限制為30秒。\n若需重新開始，需等待遊戲時間到之後，再點選start");
	}

	// setting background into least layer
	private void setbackground() {
		((JPanel) (this.getContentPane())).setOpaque(false);
		ImageIcon bgImage = new ImageIcon(this.getClass().getResource("/images/background.jpg"));
		bgImage.setImage(bgImage.getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_DEFAULT));
		JLabel bgLabel = new JLabel(bgImage);
		bgLabel.setBounds(0, 0, bgImage.getIconWidth(), bgImage.getIconHeight());
		this.getLayeredPane().add(bgLabel, new Integer(Integer.MIN_VALUE));
	}

	// setting menubar
	private void setMeun() {
		JMenuBar menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		JMenu game = new JMenu("Game");
		itemStart.setActionCommand("new");
		itemStart.addActionListener(this);
		itemPause.setActionCommand("pause");
		itemPause.addActionListener(this);
		itemPause.setEnabled(true);
		itemContinue.setActionCommand("continue");
		itemContinue.addActionListener(this);
		itemContinue.setEnabled(false);
		itemExit.setActionCommand("exit");
		itemExit.addActionListener(this);
		game.add(itemStart);
		game.add(itemPause);
		game.add(itemContinue);
		game.add(itemExit);
		menuBar.add(game);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JMenuItem)
			menuFunction(e);
	}

	// setting game mode
	public void menuFunction(ActionEvent e) {
		if (e.getActionCommand().equalsIgnoreCase("new")) {
			// start a new game
			start = true;
			newgame = true;
			itemStart.setEnabled(false);
			itemPause.setEnabled(true);
		} else if (e.getActionCommand().equalsIgnoreCase("pause")) {
			// timer need to stop
			itemPause.setEnabled(false);
			itemContinue.setEnabled(true);
			start = false;
			newgame = false;
		} else if (e.getActionCommand().equalsIgnoreCase("continue")) {
			// timer need to continue
			itemPause.setEnabled(true);
			itemContinue.setEnabled(false);
			start = true;
			newgame = false;
		} else if (e.getActionCommand().equalsIgnoreCase("exit")) {
			System.exit(EXIT_ON_CLOSE);
		}
	}
}