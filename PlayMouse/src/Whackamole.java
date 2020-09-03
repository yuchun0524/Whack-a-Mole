import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Whackamole extends GamePanel implements ActionListener {
	private static JButton[] button = new JButton[16];
	Thread thread1, thread2, thread3;
	JLabel scoreLabel = new JLabel();
	int score = 0;
	int time = 30;
	Timer timer;
	String[] imgArray = { "/images/disu.png", "/images/disus.png", "/images/bomb.png" };
	static JLabel jLabel = new JLabel();
	Hole[] hole = new Hole[16];

	public void button_click(Container pane) {
		for (int k = 0; k < 16; k++) {
			button[k] = new JButton();
			button[k].setBounds(60 + 120 * (k % 4), 200 + 120 * (k / 4), 100, 100);
			button[k].setEnabled(false);
			button[k].setVisible(false);
			button[k].addActionListener(this);
			button[k].setBorderPainted(false);
			button[k].setContentAreaFilled(false);
			hole[k] = new Hole(55 + 120 * (k % 4), 238 + 120 * (k / 4));
			pane.add(hole[k].holeLabel);
			pane.add(button[k]);
			SwingUtilities.updateComponentTreeUI(this);
		}
	}

	public Whackamole() {
		jLabel.setText("Time:" + time + " s");
		jLabel.setBounds(100, 20, 200, 200);
		jLabel.setFont(new Font("Dialog", Font.PLAIN, 24));
		jLabel.setForeground(Color.black);
		timer = new Timer(1000, this);
		// timer.addActionListener(this);
		scoreLabel.setText("score:" + score);
		scoreLabel.setBounds(100, 60, 200, 200);
		scoreLabel.setFont(new Font("Dialog", Font.PLAIN, 24));
		scoreLabel.setForeground(Color.black);
		pane.add(jLabel);
		pane.add(scoreLabel);
		button_click(pane);
		try {
			File musicPath = new File("./src/sounds/Breakbeat.wav");
			AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
			Clip clip = AudioSystem.getClip();
			clip.open(audioInput);
			clip.start();
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JButton) {
			((JButton) e.getSource()).setEnabled(false);
			((JButton) e.getSource()).setVisible(false);

			Icon btnIcon = ((JButton) e.getSource()).getIcon();
			String str = btnIcon.toString();
			int index = str.lastIndexOf("/");
			str = str.substring(index + 1);
			if (str.equals("disu.png")) {
				score += 10;
				try {
					File musicPath = new File("./src/sounds/Silencer.wav");
					AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
					Clip clip = AudioSystem.getClip();
					clip.open(audioInput);
					clip.start();
				} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {

				}
			} else if (str.equals("disus.png")) {
				score += 30;
				try {
					File musicPath = new File("./src/sounds/Silencer.wav");
					AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
					Clip clip = AudioSystem.getClip();
					clip.open(audioInput);
					clip.start();
				} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {

				}
			} else if (str.equals("bomb.png")) {
				score -= 20;
				try {
					File bomb = new File("./src/sounds/Explosion1.wav");
					AudioInputStream bombInput = AudioSystem.getAudioInputStream(bomb);
					Clip bombClip = AudioSystem.getClip();
					bombClip.open(bombInput);
					bombClip.start();
				} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {

				}
			}
			scoreLabel.setText("score:" + score);

		} else if (e.getSource() == timer) {
			time--;
			if (time <= 5)
				jLabel.setForeground(Color.red);
			else
				jLabel.setForeground(Color.black);
			jLabel.setText("Time:" + time + " s");
		} else if (e.getSource() instanceof JMenuItem) {
			menuFunction(e);
			if (start) {
				if (newgame) {
					time = 30;
					score = 0;
					jLabel.setForeground(Color.black);
				}
				scoreLabel.setText("score:" + score);
				jLabel.setText("Time:" + time + " s");
				timer.start();
				thread1 = new Thread() {
					public void run() {
						while (time > 0 && start) {
							if (thread1.isInterrupted())
								break;
							int index = (int) (Math.random() * 16);
							if (!button[index].isShowing()) {
								int i = (int) (Math.random() * 3);
								ImageIcon img = new ImageIcon(this.getClass().getResource(imgArray[i]));
								img.setImage(img.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
								button[index].setIcon(img);
								button[index].setEnabled(true);
								button[index].setVisible(true);
								try {
									up_and_down(button[index], index);
									Thread.sleep(1200);
								} catch (InterruptedException e) {
									thread1.interrupt();
								}
								if (button[index].isShowing()) {
									button[index].setEnabled(false);
									button[index].setVisible(false);
								}
							}
							if (time <= 0) {
								timer.stop();
								itemStart.setEnabled(true);
								itemPause.setEnabled(false);
								try {
									File musicPath = new File("./src/sounds/Ding.wav");
									AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
									Clip clip = AudioSystem.getClip();
									clip.open(audioInput);
									clip.start();
								} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {

								}
							}
						}
					}
				};
				thread2 = new Thread() {
					public void run() {
						while (time > 0 && start) {
							if (thread2.isInterrupted())
								break;
							int index = (int) (Math.random() * 16);
							if (!button[index].isShowing()) {
								int i = (int) (Math.random() * 3);
								ImageIcon img = new ImageIcon(this.getClass().getResource(imgArray[i]));
								img.setImage(img.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
								button[index].setIcon(img);
								button[index].setEnabled(true);
								button[index].setVisible(true);
								try {
									up_and_down(button[index], index);
									Thread.sleep(1300);
								} catch (InterruptedException e) {
									thread2.interrupt();
								}
								if (button[index].isShowing()) {
									button[index].setEnabled(false);
									button[index].setVisible(false);
								}
							}
							if (time <= 0) {
								timer.stop();
								itemStart.setEnabled(true);
								itemPause.setEnabled(false);
								try {
									File musicPath = new File("./src/sounds/Ding.wav");
									AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
									Clip clip = AudioSystem.getClip();
									clip.open(audioInput);
									clip.start();
								} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {

								}
							}
						}
					}
				};
				thread3 = new Thread() {
					public void run() {
						while (time > 0 && start) {
							if (thread3.isInterrupted())
								break;
							int index = (int) (Math.random() * 16);
							if (!button[index].isShowing()) {
								int i = (int) (Math.random() * 3);
								ImageIcon img = new ImageIcon(this.getClass().getResource(imgArray[i]));
								img.setImage(img.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
								button[index].setIcon(img);
								button[index].setEnabled(true);
								button[index].setVisible(true);
								try {
									up_and_down(button[index], index);
									Thread.sleep(1500);
								} catch (InterruptedException e) {
									thread3.interrupt();
								}
								if (button[index].isShowing()) {
									button[index].setEnabled(false);
									button[index].setVisible(false);
								}
							}
							if (time <= 0) {
								timer.stop();
								itemStart.setEnabled(true);
								itemPause.setEnabled(false);
								try {
									File musicPath = new File("./src/sounds/Ding.wav");
									AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
									Clip clip = AudioSystem.getClip();
									clip.open(audioInput);
									clip.start();
								} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {

								}
							}
						}
					}
				};
				thread1.start();
				thread2.start();
				thread3.start();
			}
			if (!start) {
				timer.stop();
				thread1.interrupt();
				thread2.interrupt();
				thread3.interrupt();
				try {
					thread1.join();
					thread2.join(); // wait for the thread to stop
					thread3.join();
				} catch (InterruptedException e1) {

				}
			}
		}
	}

	public void up_and_down(JButton btn, int i) {
		Timer timer = new Timer(100, new ActionListener() {
			int count = 0;

			int intiX = btn.getBounds().x;
			int intiY = btn.getBounds().y;
			int x = intiX;
			int y = intiY;

			public void actionPerformed(ActionEvent e) {
				if (count == 0) {
					btn.setLocation(x, y + 18);
				}
				// up
				if (count < 4) {
					y -= 3;
					btn.setLocation(x, y);
				}
				// down
				if (count >= 5) {
					y += 3;
					btn.setLocation(x, y);

				}
				// end
				if (count == 9) {
					((Timer) (e.getSource())).stop();
					btn.setLocation(x, y - 3);
				}
				count++;
			}
		});
		timer.start();
	}

	public static void main(String[] args) {
		Whackamole whackamole = new Whackamole();
	}
}
