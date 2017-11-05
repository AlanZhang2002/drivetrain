package org.usfirst.frc.team8.robot;

import edu.wpi.first.wpilibj.Joystick;

public class Joysticks {
	Joystick driveStick = new Joystick(0);
	Joystick turnStick = new Joystick(1);
	Joystick sliderStick = new Joystick(2);
	Joystick climbStick = new Joystick(3);
	
	public double sliderSpeed() {
		return -sliderStick.getY();
	}
	public double driveSpeed() {// positive driveSpeed is forwards
		return -driveStick.getY();
	}
	public double turnSpeed() {// positive turnSpeed is right
		return turnStick.getX();
	}
	public double climbSpeed() {
		return -climbStick.getY();
	}
	public boolean spatulaTrigger() {
		return sliderStick.getTrigger();
	}
	
}
