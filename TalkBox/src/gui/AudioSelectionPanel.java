package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.Border;

import controller.Controller;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import controller.Controller;
public class AudioSelectionPanel extends JPanel {
	private JComboBox audioSelection;
	private JList audioList;
	private JButton playButton;
	private JButton setButton;
	private JButton temp_save;
	private SelectionListener selectionListener;
	private PlayListener playListener;
	private SetListener setListener;
	private JCheckBox checkBox;
	private boolean isChecked;
	protected Controller controller;
	private int numofaudioset;
	private DefaultComboBoxModel comboModel;
	ArrayList<String> audioset;
	private JButton add_set;
	
	public AudioSelectionPanel() {
		//initialize
		audioSelection = new JComboBox();
		audioList = new JList();
		playButton = new JButton("Play"); 
		setButton = new JButton("Set");
		add_set = new JButton("add");
		checkBox = new JCheckBox();
		setButton.setEnabled(false);
		isChecked = false;
		controller = new Controller();
		audioset = new ArrayList<String>();
		
		
		//Setup Combo Box
		comboModel = new DefaultComboBoxModel();
		comboModel.addElement("");
		numofaudioset ++;
		comboModel.addElement("Audio Set 1");
		numofaudioset ++;
		comboModel.addElement("Audio Set 2");
		numofaudioset ++;
		comboModel.addElement("Audio Set 3");
		numofaudioset ++;
		comboModel.addElement("Audio Set 4");
		numofaudioset ++;
		audioSelection.setModel(comboModel);
		audioSelection.setSelectedIndex(0);
		

		
		
		//Actions  
		audioSelection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (selectionListener != null) {
					int selection = audioSelection.getSelectedIndex();

					if (selection > 0) {
						selectionListener.setAudioSelection(selection - 1);
					}
				}
			}
		});
		
		playButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selection = (String) audioList.getSelectedValue();
				int idx = audioSelection.getSelectedIndex();
				if (playListener != null && idx > 0) {
					playListener.setFileName(idx, selection);
				}
			}
			
		});
		
		checkBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean isChecked = checkBox.isSelected();	
				setButton.setEnabled(isChecked);
			}
			
		});
		setButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selection = (String) audioList.getSelectedValue();
				audioset.add((String) audioList.getSelectedValue());
				int idx = audioSelection.getSelectedIndex();
				if (setListener != null) {
					setListener.setup(idx, selection);
				}
			}
			
		});//new arraylist로 잡아주면 되는데 ..
		
		add_set.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addAudioSet();
				controller.addAudioSet(new ArrayList<>(audioset));
				audioset.clear();
			}
			
		});
		
		
			
		Border innerBorder = BorderFactory.createTitledBorder("Select Audio");
		Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
		layoutComponents();
		
		
	}
	
	
	public void setSelectionListener(SelectionListener listener) {
		this.selectionListener = listener;
	}
	
	public void setPlayListener(PlayListener listener) {
		this.playListener = listener;
	}
	
	public void setSetListener(SetListener listener) {
		this.setListener = listener;
	}
	public List<String> getnewaudiolist(){
		return audioset;
	}
	public boolean isChecked() {
		return checkBox.isSelected();
	}
	public void addAudioSet() {
		comboModel.addElement("Audio Set "+ numofaudioset);
		numofaudioset ++;
	
	}
	
	private void layoutComponents() {
		
		setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		
		/* First Row */
		gc.gridy = 0;
		
		gc.weightx = 1;
		gc.weighty = 0.2;
		
		gc.gridx = 0;
		
		gc.fill = GridBagConstraints.NONE;
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(audioSelection, gc);
	
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.insets = new Insets(0,0,0,0);
		add(audioList, gc);
		
		/*Next Row */
		gc.gridy++;
		
		gc.weightx = 1;
		gc.weighty = 2.0;
		
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(playButton, gc);
		
		gc.gridx = 2;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(setButton, gc);
		
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(add_set, gc);


		
		/*Next Row */
		gc.gridy++;
		
		gc.weightx = 1;
		gc.weighty = 0.5;
		
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(new JLabel("Create Custom Audio Set: "), gc);
		
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.insets = new Insets(0, 0, 0, 0);
		add(checkBox, gc);
		
gc.gridy++;
		
		gc.weightx = 1;
		gc.weighty = 0.5;
		
		
		
	}


	public void setJList(String[] audioSet) {
		
		DefaultListModel listModel = new DefaultListModel();
		for (int i = 0; i < audioSet.length; i++) {
			listModel.addElement(audioSet[i]);
		}
		audioList.setModel(listModel);
		//audioList.setPreferredSize(new Dimension(110, 68));
		audioList.setBorder(BorderFactory.createEtchedBorder());
		audioList.setSelectedIndex(0);

	}
	
	
	
}
