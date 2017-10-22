package org.usfirst.frc.team8.robot;

import edu.wpi.first.wpilibj.Joystick;

public class Joysticks {
	Joystick driveStick = new Joystick(1);
	Joystick turnStick = new Joystick(2);
	Joystick sliderStick = new Joystick(3);
	Joystick climbStick = new Joystick(4);
	
	double driveSpeed = - driveStick.getY();
	double turnSpeed = turnStick.getX();//works if positive values = left turn
	double sliderSpeed = - sliderStick.getY();
	double climbSpeed = - climbStick.getY();
	
}
