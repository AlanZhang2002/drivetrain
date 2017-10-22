package org.usfirst.frc.team8.robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Slider {
	CANTalon sliderCT = new CANTalon(7);
	AnalogPotentiometer pMeter = new AnalogPotentiometer(9);
	DoubleSolenoid spatula = new DoubleSolenoid(10, 10);
	Joysticks joysticks = new Joysticks();
	
	double pMeasure = pMeter.get();
	
	public void Activated() {//Find logic
		if (pMeasure > 0 && pMeasure < 1) {//does the pMeter go between 0 and 1
			sliderCT.set(joysticks.sliderSpeed);
		}
		else {
			sliderCT.set(0);
		}
		
		if (joysticks.spatulaTrigger) {
			spatula.set(Value.kForward);
		}
		else {
			spatula.set(Value.kOff);
		}
	}
}
