package org.usfirst.frc.team8.robot;

import com.ctre.CANTalon;

public class Climber {
	CANTalon climberCT = new CANTalon(9);
	Joysticks joysticks = new Joysticks();
	
	public void Activated() {
		if (joysticks.climbSpeed() > 0)
			climberCT.set(joysticks.climbSpeed());
	}
}
