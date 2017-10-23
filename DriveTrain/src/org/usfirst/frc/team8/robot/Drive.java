package org.usfirst.frc.team8.robot;

import com.ctre.CANTalon;

public class Drive {
	CANTalon left1 = new CANTalon(1);
	CANTalon left2 = new CANTalon(2);
	CANTalon left3 = new CANTalon(3);
	CANTalon right1 = new CANTalon(4);
	CANTalon right2 = new CANTalon(5);
	CANTalon right3 = new CANTalon(6);
	
	Joysticks joysticks = new Joysticks();
	
	public void Activated() {
		double turnSpeed = joysticks.turnSpeed();//pos values = left turn
		double driveSpeed = joysticks.driveSpeed();
		
		left1.set(driveSpeed + turnSpeed);
		left2.set(driveSpeed + turnSpeed);
		left3.set(driveSpeed + turnSpeed);
		right1.set(-driveSpeed + turnSpeed);//basically driveSpeed - turnSpeed, but reversed
		right2.set(-driveSpeed + turnSpeed);
		right3.set(-driveSpeed + turnSpeed);
	} 
	
}
