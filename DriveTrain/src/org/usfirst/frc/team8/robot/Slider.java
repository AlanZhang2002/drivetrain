package org.usfirst.frc.team8.robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Slider {
	CANTalon sliderCT = new CANTalon(10);
	AnalogInput pMeter = new AnalogInput(0);
	DoubleSolenoid spatula = new DoubleSolenoid(1, 2);
	Joysticks joysticks = new Joysticks();
	
	double pMeasure = pMeter.getValue();
	
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
			spatula.set(Value.kReverse);
		}
	}
}
