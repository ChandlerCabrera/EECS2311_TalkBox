package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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

public class AudioSelectionPanel extends JPanel {
	private JComboBox audioSelection;
	private JList audioList;
	private JButton playButton;
	private JButton setButton;
	private JButton removeset;
	private JButton add_set;
	private SelectionListener selectionListener;
	private PlayListener playListener;
	private SetListener setListener;
	private JCheckBox checkBox;
	private boolean isChecked;
	protected Controller controller;
	private int numofaudioset;
	private DefaultComboBoxModel comboModel;
	ArrayList<String> audioset;
	private RemoveListener removeListener;
	private JButton undo;
	private ClearListener clearListener;
	private AddListener addListener;

	public AudioSelectionPanel() {
		// initialize
		comboModel = new DefaultComboBoxModel();
		audioSelection = new JComboBox();
		audioList = new JList();
		playButton = new JButton("Play");
		setButton = new JButton("Select >>");
		add_set = new JButton("Add Set");
		removeset = new JButton("Remove");
		undo = new JButton("<< Clear");
		checkBox = new JCheckBox();
		setButton.setEnabled(false);
		add_set.setEnabled(false);
		undo.setEnabled(false);
		isChecked = false;
		controller = new Controller();
		audioset = new ArrayList<String>();

		

	

		// Actions
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
				if (isChecked) {
					setButton.setEnabled(isChecked);
					add_set.setEnabled(isChecked);
				} else {
					undo.setEnabled(false);
				}
				
			}

		});
		setButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selection = (String) audioList.getSelectedValue();
				int idx = audioSelection.getSelectedIndex();
				if (setListener != null && selection != null) {
					setListener.setup(idx, selection);
					audioset.add((String) audioList.getSelectedValue());
				}
			}

		});

		add_set.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addAudioSet();
				controller.addAudioSet(new LinkedList<>(audioset));
				audioset.clear();
				checkBox.setSelected(false);
				setButton.setEnabled(false);
				add_set.setEnabled(false);
				undo.setEnabled(false);
				
				if (addListener != null) {
					addListener.clearSetup(true);
				}
			}

		});

		removeset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int n = audioSelection.getSelectedIndex() - 1;
				String file = (String) audioList.getSelectedValue();
				if (removeListener != null && n >= 0) {
					removeListener.setRemoveInfo(n, file);
				}
				

			}

		});
		
		undo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (clearListener != null) {
					clearListener.clear(true);
					audioset.clear();
					undo.setEnabled(false);
				}
				
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
	
	
	
	public void setRemoveListener(RemoveListener listener) {
		this.removeListener = listener;
	}
	public void setPlayListener(PlayListener listener) {
		this.playListener = listener;
	}

	public void setSetListener(SetListener listener) {
		this.setListener = listener;
	}
	
	public void setClearListener(ClearListener listener) {
		this.clearListener = listener;
	}
	
	public void setAddListener(AddListener listener) {
		this.addListener = listener;
	}


	public List<String> getnewaudiolist() {
		return audioset;
	}

	public boolean isChecked() {
		return checkBox.isSelected();
	}

	public void addAudioSet() {
		comboModel.addElement("Audio Set " + numofaudioset);
		numofaudioset++;

	}

	public void refre_audio() {
		comboModel.removeAllElements();
		comboModel.addElement("");
		numofaudioset = 0;
		numofaudioset++;
		for (int i = 0; i < controller.getNumberOfAudioSets(); i++) {
			addAudioSet();
		}
	}

	public void def_audioset() {

		comboModel.addElement("");
		numofaudioset++;
		comboModel.addElement("Audio Set 1");
		numofaudioset++;
		comboModel.addElement("Audio Set 2");
		numofaudioset++;
		comboModel.addElement("Audio Set 3");
		numofaudioset++;
		comboModel.addElement("Audio Set 4");
		numofaudioset++;
		audioSelection.setModel(comboModel);
		audioSelection.setSelectedIndex(0);

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
		gc.insets = new Insets(0, 0, 0, 2);
		add(audioSelection, gc);

		gc.gridx = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.insets = new Insets(0, 0, 0, 0);
		add(audioList, gc);

		/* Next Row */
		gc.gridy++;

		gc.weightx = 1;
		gc.weighty = 0.2;

		gc.gridx = 0;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(playButton, gc);

		/* Next Row */
		gc.weightx = 1;
		gc.weighty = 0.2;

		gc.gridy++;
		gc.gridx = 0;
		gc.insets = new Insets(0, 0, 0, 5);
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(add_set, gc);

		gc.gridx = 1;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(setButton, gc);

		/* Next Row */
		gc.weightx = 1;
		gc.weighty = 0.5;

		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(removeset, gc);
		
		
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		add(undo, gc);

		/* Next Row */
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
		// audioList.setPreferredSize(new Dimension(110, 68));
		audioList.setBorder(BorderFactory.createEtchedBorder());
		//audioList.setSelectedIndex(0);

	}
	
	public void turnOffUndo() {
		undo.setEnabled(false);
	}
	
	public void turnOnUndo() {
		undo.setEnabled(true);
	}

}
