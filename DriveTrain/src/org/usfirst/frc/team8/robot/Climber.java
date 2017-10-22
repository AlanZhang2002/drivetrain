package org.usfirst.frc.team8.robot;

import com.ctre.CANTalon;

public class Climber {
	CANTalon climberCT = new CANTalon(8);
	Joysticks joysticks = new Joysticks();
	
	public void Activated() {
		climberCT.set(joysticks.climbSpeed);
	}
}
