package relink.guiview;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import relink.control.RE_Control;

public class BoardConnect extends JFrame {

	private JPanel contentPane;
	
	
	private JScrollPane connectionPanel;
	private JPanel buttonPanel;
	
	private JList connectionList;
	private DefaultListModel<String> listModel;
	
	private JButton addButton, editButton, deleteButton, connectButton, disconnectButton;
	
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BoardConnect frame = new BoardConnect(new RE_Control(), new RE_LINK());
					//frame.pack();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public BoardConnect(RE_Control control, RE_LINK link) {
		super("Board Connect");
		addButton = new JButton("Add");
		//this.addButton.setPreferredSize(new Dimension(100,100));
		//this.addButton.setSize(100, 100);
		editButton = new JButton("Edit");
		deleteButton = new JButton("Delete");
		connectButton = new JButton("Connect");
		disconnectButton = new JButton("Disconnect");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setBounds(100, 100, 600, 600);
		this.setPreferredSize(new Dimension(1100,650));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout());
		contentPane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		setContentPane(contentPane);
		
		listModel = new DefaultListModel<String>();
		this.connectionList = new JList(this.listModel);
		this.connectionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		this.connectionPanel = new JScrollPane();
		this.connectionPanel.setViewportView(this.connectionList);
		this.connectionPanel.setMinimumSize(new Dimension(500,500));
		this.connectionPanel.setPreferredSize(new Dimension(500,500));
		this.connectionPanel.setAlignmentX(CENTER_ALIGNMENT);
		this.contentPane.add(connectionPanel, BorderLayout.CENTER);
		//this.contentPane.add(connectionPanel,BorderLayout.BEFORE_FIRST_LINE);
		this.buttonPanel = new JPanel();
		//this.buttonPanel.setPreferredSize(new Dimension(500,500));
		//this.buttonPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.buttonPanel.setLayout(new GridLayout(3, 9));
		for(int i=0; i<9; i++)
		{
			JPanel p = new JPanel();
			//p.setBorder(BorderFactory.createLineBorder(Color.black));
			this.buttonPanel.add(p);
		}
		this.contentPane.add(buttonPanel, BorderLayout.SOUTH);
		this.buttonPanel.add(addButton);
		this.buttonPanel.add(new JPanel());
		this.buttonPanel.add(editButton);
		this.buttonPanel.add(new JPanel());
		this.buttonPanel.add(deleteButton);
		this.buttonPanel.add(new JPanel());
		this.buttonPanel.add(connectButton);
		this.buttonPanel.add(new JPanel());
		this.buttonPanel.add(disconnectButton);
		
		for(int i=0; i<9; i++)
		{
			JPanel p = new JPanel();
			//p.setBorder(BorderFactory.createLineBorder(Color.black));
			this.buttonPanel.add(p);
		}
		//this.contentPane.add(addButton, BorderLayout.WEST);
		//this.contentPane.add(editButton, BorderLayout.CENTER);
		//this.contentPane.add(deleteButton,BorderLayout.EAST);
		//this.contentPane.add(connectButton);
		
		listModel.addElement("evsv");
		this.connectionList.setSelectedIndex(0);
		this.pack();
	}

}