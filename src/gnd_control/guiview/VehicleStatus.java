package gnd_control.guiview;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import com.MAVLink.enums.MAV_TYPE;

import gnd_control.model.GPosition;
import gnd_control.model.Vehicle;
import gnd_control.model.VehicleStateListener;

/**
 * This panel shows the user the status of the current vehicle
 *
 */
public class VehicleStatus extends JPanel implements VehicleStateListener {
	private JButton armButton;
	private JProgressBar batteryStatusBar;
	private static final int PROGRESS_MAX=100;
	private SpringLayout layout;
	private JLabel groundSpeedLabel=new JLabel("Ground Speed(km/h)");
	private JLabel altitudeLabel = new JLabel("Altitude(m)");
	private JTextField groundSpeedField;
	private JTextField altitudeField;
	
	private JComboBox<String> modeBox;
	private static final String[] MODES = {"Stabilize","Acro","Alt Hold","Auto","Guided","Loiter","RTL","Circle","Position",
			"Land"};
	private static final String ARMED="<html><font color=red>Armed</font></html>";
	private static final String DISARMED = "<html><font color=green>Disarmed</font></html>";
	private static final String DISCONNECTED="<html><font color=blue>Disconnected</font></html>";
	
	private GND_Control_GUI_HUB hub;
	private StatusListener actionListener;
	
	public VehicleStatus(GND_Control_GUI_HUB hub)
	{
		this.hub=hub;
		layout = new SpringLayout();
		armButton = new JButton(DISCONNECTED);
		armButton.setEnabled(false);
		batteryStatusBar = new JProgressBar();
		batteryStatusBar.setMaximum(PROGRESS_MAX);
		batteryStatusBar.setForeground(Color.RED);
		batteryStatusBar.setBorder(BorderFactory.createLineBorder(Color.black));
		batteryStatusBar.setOrientation(JProgressBar.HORIZONTAL);
		batteryStatusBar.setStringPainted(true);
		batteryStatusBar.setValue(0);
		batteryStatusBar.setString("Battery Level: "+this.batteryStatusBar.getValue());
		
		
		actionListener=new StatusListener();
		this.setLayout(layout);
		
		this.add(armButton);
		armButton.addActionListener(actionListener);
		layout.putConstraint(SpringLayout.WEST, armButton, 50, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, armButton, 50, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.EAST, armButton, -50, SpringLayout.EAST, this);
		
		modeBox = new JComboBox<String>();
		modeBox.setEnabled(false);
		this.add(modeBox);
		modeBox.addActionListener(actionListener);
		layout.putConstraint(SpringLayout.WEST, modeBox, 50, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, modeBox, 50, SpringLayout.SOUTH, armButton);
		layout.putConstraint(SpringLayout.EAST, modeBox, -50, SpringLayout.EAST, this);
		
		this.add(batteryStatusBar);
		layout.putConstraint(SpringLayout.WEST, batteryStatusBar, 50, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, batteryStatusBar, 50, SpringLayout.SOUTH, modeBox);
		layout.putConstraint(SpringLayout.EAST, batteryStatusBar, -50, SpringLayout.EAST, this);
		
		this.add(altitudeLabel);
		layout.putConstraint(SpringLayout.WEST, altitudeLabel, 50, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, altitudeLabel, 50, SpringLayout.SOUTH, batteryStatusBar);
		
		this.add(groundSpeedLabel);
		layout.putConstraint(SpringLayout.WEST, groundSpeedLabel, 50, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, groundSpeedLabel, 50, SpringLayout.SOUTH, altitudeLabel);
		
		this.altitudeField=new JTextField();
		this.altitudeField.setText("0.0");
		this.altitudeField.setEditable(false);
		this.add(altitudeField);
		layout.putConstraint(SpringLayout.WEST, altitudeField, 250, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, altitudeField, 50, SpringLayout.SOUTH, batteryStatusBar);
		layout.putConstraint(SpringLayout.EAST, altitudeField, -50, SpringLayout.EAST, this);
		
		this.groundSpeedField=new JTextField();
		this.groundSpeedField.setText("0.0");
		this.groundSpeedField.setEditable(false);
		this.add(groundSpeedField);
		layout.putConstraint(SpringLayout.WEST, groundSpeedField, 250, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, groundSpeedField, 50, SpringLayout.SOUTH, altitudeLabel);
		layout.putConstraint(SpringLayout.EAST, groundSpeedField, -50, SpringLayout.EAST, this);
		
		
		if(this.hub!=null)
		if(hub.control.isVehicleConnected())
		{
			if(hub.control.isVehicleArmed())
			{
				this.armButton.setEnabled(true);
				this.armButton.setText(ARMED);
				
			}
			else
			{
				this.armButton.setEnabled(true);
				this.armButton.setText(DISARMED);
			}
			this.batteryStatusBar.setString("Battery Level "+hub.control.getVehicleBatteryLevel());
			this.groundSpeedField.setText(""+hub.control.getVehicleGroundSpeed());
			this.altitudeField.setText(""+hub.control.getVehicleAltitude());
			
		}
		else
		{
			this.armButton.setEnabled(false);
			this.armButton.setText(DISCONNECTED);
			this.batteryStatusBar.setValue(0);
			this.groundSpeedField.setText("0.0");
			this.altitudeField.setText("0.0");
		}
		
		
		//hub.control.addVehicleListener(this);
		
		this.setBorder(BorderFactory.createTitledBorder("Vehicle Status"));
		this.setPreferredSize(new Dimension(400,350));
		this.setMinimumSize(new Dimension(400,350));
		this.setMaximumSize(new Dimension(400,350));
		
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			JFrame f = new JFrame();
			f.add(new VehicleStatus(null));
			f.pack();
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			f.setVisible(true);
			
	}


	@Override
	public void armedChanged(boolean armed) {
		// TODO Auto-generated method stub
		if(this.hub==null)
			return;
		if(this.hub.control.isVehicleConnected())
			if(armed)
			{
				this.armButton.setEnabled(true);
				this.armButton.setText(ARMED);
			}
			else
			{
				this.armButton.setEnabled(true);
				this.armButton.setText(DISARMED);
			}
	}


	@Override
	public void locationChange(GPosition location) {
		// TODO Auto-generated method stub
		double alt = location.getAltitude();
		this.altitudeField.setText(alt+"");
	}


	@Override
	public void batteryLevelChange(double level) {
		// TODO Auto-generated method stub
		System.out.println("Battery level change "+level);
		int l = (int)level;
		if(l>=75)
		{
			this.batteryStatusBar.setForeground(Color.GREEN);
			this.batteryStatusBar.setValue((int)level);
			this.batteryStatusBar.setString("Battery Level "+level);
		}
		else if(l<75 && l>=50)
		{
			this.batteryStatusBar.setForeground(Color.YELLOW);
			this.batteryStatusBar.setValue((int)level);
			this.batteryStatusBar.setString("Battery Level "+level);
		}
		else
		{
			this.batteryStatusBar.setForeground(Color.RED);
			this.batteryStatusBar.setValue((int)level);
			this.batteryStatusBar.setString("Battery Level "+level);
		}
	}


	@Override
	public void connectedChanged(boolean connected) {
		// TODO Auto-generated method stub
		if(!connected)
		{
			this.armButton.setEnabled(false);
			this.armButton.setText(DISCONNECTED);
			this.batteryStatusBar.setString("Battery Level: N/A");
			this.batteryStatusBar.setValue(0);
			this.altitudeField.setText("0.0");
			this.groundSpeedField.setText("0.0");
		}
		else
		{
			this.armButton.setEnabled(true);
			if(hub.control.isVehicleArmed())
			{
				this.armButton.setEnabled(true);
				this.armButton.setText(ARMED);
			}
			else
			{
				this.armButton.setEnabled(true);
				this.armButton.setText(DISARMED);
			}
			// get values
			this.altitudeField.setText(""+hub.control.getVehicleAltitude());
			this.groundSpeedField.setText(""+hub.control.getVehicleGroundSpeed());
			this.batteryStatusBar.setString("Battery Level: "+hub.control.getVehicleBatteryLevel());
			
			modeBox.setEnabled(true);
			modeBox.removeAllItems();
			switch(hub.control.getVehicleType())
			{
				case MAV_TYPE.MAV_TYPE_FIXED_WING:break;
				default: 
					int j=-1;
					for(int i=0;i<Vehicle.COPTER_MODES.length;i++)
					{
						modeBox.addItem(Vehicle.COPTER_MODES[i]);
						if(Vehicle.COPTER_MODES[i].compareToIgnoreCase(hub.control.getVehicleMode())==0)
							j=i;
					}
					if(j>=0)
						modeBox.setSelectedIndex(j);
					break;
			}
		}
	}


	@Override
	public void flightModeChanged(String mode) {
		// TODO Auto-generated method stub
		modeBox.setEnabled(true);
		modeBox.removeAllItems();
		switch(hub.control.getVehicleType())
		{
			case MAV_TYPE.MAV_TYPE_FIXED_WING:break;
			default: 
				int j=-1;
				for(int i=0;i<Vehicle.COPTER_MODES.length;i++)
				{
					modeBox.addItem(Vehicle.COPTER_MODES[i]);
					if(Vehicle.COPTER_MODES[i].compareToIgnoreCase(mode)==0)
						j=i;
				}
				if(j>=0)
					modeBox.setSelectedIndex(j);
				break;
		}
		
	}
	
	protected class StatusListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource()==armButton)
			{
				if(hub.control.isVehicleArmed())
				{
					hub.control.disarmVehicle();
				}
				else
				{
					hub.control.armVehicle();
				}
			}
			else if(e.getSource()==modeBox)
			{
				hub.control.changeMode((String)modeBox.getSelectedItem());
			}
		}
		
	}

}
