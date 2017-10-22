package org.usfirst.frc.team8.robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Slider {
	CANTalon sliderCT = new CANTalon(7);
	AnalogPotentiometer pMeter = new AnalogPotentiometer(9);
	DoubleSolenoid dSolenoid = new DoubleSolenoid(10, 10);
	Joysticks joysticks = new Joysticks();
	
	double pMeasure = pMeter.get();
	
	public void Activated() {//Find logic
		if (pMeasure > 0 && pMeasure < 1) {
			sliderCT.set(joysticks.sliderSpeed);
		}
		else {
			sliderCT.set(0);
		}
	}
}
