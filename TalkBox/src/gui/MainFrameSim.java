package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import controller.Controller;
import utils.Stereo;

public class MainFrameSim extends JFrame {
	
	
	private Stereo audioPlayer;
	private int idx;
	private Map<Integer, String> map = new HashMap<>();
	private JPanel buttonPanel;
	private boolean swap1Pressed = false;
	private boolean swap2Pressed = false;
	private boolean soundPlayed = false;
	private Controller controller;
	private JLabel label = new JLabel();

	public MainFrameSim(Controller controller) {
		super("TalkBox Simulator");
		setVisible(false);
		setLayout(new BorderLayout());
		setJMenuBar(createMenuBar());
		this.controller= controller;
		
		
		audioPlayer = new Stereo();
	}
	
	
	public void setIdx(int idx) {
		this.idx = idx;
	}
	
	public void showIt() {
		setSize(500,600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public void setButtons(int idx) {
		if (this.buttonPanel != null) {
			this.remove(buttonPanel);
			this.idx = idx;
		}
		label.setText("Audio Set " + (idx + 1));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(label, BorderLayout.NORTH);
		String[][] audioFileSet = controller.getFileNames();
		String[] audioSet = audioFileSet[idx];
		JPanel panel = new JPanel();
		this.buttonPanel = panel;
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		Border innerBorder = BorderFactory.createTitledBorder("Play Audio");
		Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		panel.setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
		
		for(int i = 0; i < audioSet.length; i++) {
			String file = audioSet[i];
			JButton add = new JButton(file);
			add.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JButton eventButton = (JButton) e.getSource();
					play(eventButton);
					
				}
				
			});
			panel.add(add);
			
		}
	
		
		this.add(panel, BorderLayout.CENTER);
	}
	
	public void setSwapButtons() {
		int numberOfAudioSets = controller.getNumberOfAudioSets();
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		Border innerBorder = BorderFactory.createTitledBorder("Swap Audio Set");
		Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		panel.setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
		this.add(panel, BorderLayout.SOUTH);
			
		JButton swap1 = new JButton("1");
		swap1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton button = (JButton) e.getSource();
				int idx = Integer.parseInt(button.getText());
				idx--;
				setButtons(idx);
				swap1Pressed = true;
				swap2Pressed = false;
				MainFrameSim.this.revalidate();
				MainFrameSim.this.repaint();
			}
			
		});
		
		JButton swap2 = new JButton("Swap");
		swap2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton button = (JButton) e.getSource();
				int swapIdx = 2;
				swapIdx--;
				if (swap1Pressed || !swap2Pressed) {
					setButtons(swapIdx);
					swap1Pressed = false;
					swap2Pressed = true;
				} else {
					
					if (MainFrameSim.this.idx == (numberOfAudioSets - 1)) {
						MainFrameSim.this.idx = -1;
					}
					setButtons(++MainFrameSim.this.idx);
				
				}
				MainFrameSim.this.revalidate();
				MainFrameSim.this.repaint();
			}
			
		});
		
		panel.add(swap1);
		panel.add(swap2);
	}
	
	private void play(JButton button) {
		String path = controller.getPath().toString();
		String completePath = path + "\\" + button.getText(); //**
		audioPlayer.playMusic(button.getText());
		soundPlayed = true;
	}
	
	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		
		JMenu fileMenu = new JMenu("File");
		JMenuItem exitItem = new JMenuItem("Exit");
		fileMenu.add(exitItem);
		menuBar.add(fileMenu);
		exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int action = JOptionPane.showConfirmDialog(MainFrameSim.this, "Exit?", "Yes", JOptionPane.OK_CANCEL_OPTION);
				if (action == JOptionPane.OK_OPTION) {
					System.exit(0);
				}
				
			}
		});
		
		return menuBar;
	}
	
	public boolean getSoundPlayed() {
		return soundPlayed;
	}
	
}
