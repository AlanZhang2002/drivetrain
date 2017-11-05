package org.usfirst.frc.team8.robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Slider {
	CANTalon sliderCT = new CANTalon(10);
	AnalogPotentiometer pMeter = new AnalogPotentiometer(0);
	DoubleSolenoid spatula = new DoubleSolenoid(1, 2);
	Joysticks joysticks = new Joysticks();
	
	double pMeasure = pMeter.get();
	
	public void Activated() {//Find logic
		if (pMeasure > 0 && pMeasure < 1) {//does the pMeter go between 0 and 1
			sliderCT.set(joysticks.sliderSpeed());
		}
		else {
			sliderCT.set(0);
		}
		
		if (joysticks.spatulaTrigger()) {
			spatula.set(Value.kForward);
		}
		else {
			if (spatula.get() == Value.kForward) {
				spatula.set(Value.kReverse);
			}
			else {
				spatula.set(Value.kOff);
			}
		}
	}
}
