package org.usfirst.frc.team8.robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Slider {
	CANTalon sliderCT = new CANTalon(10);
	AnalogPotentiometer pMeter = new AnalogPotentiometer(3);
	DoubleSolenoid spatula = new DoubleSolenoid(1, 2);
	Joysticks joysticks = new Joysticks();
	
	public double getPosition() {
		return pMeter.get();
	}
	
	public void Activated() {//Find logic
		
		double position = getPosition();
		
		if (position > 0.53 && position < 0.8) {// 0.82 and 0.515, debugging rn
			sliderCT.set(joysticks.sliderSpeed());
			System.out.println(position);
		}
		else {
			if (position < 0.53 && joysticks.sliderSpeed() < 0) {
				sliderCT.set(joysticks.sliderSpeed());
			}
			else if (position > 0.8 && joysticks.sliderSpeed() > 0) {
				sliderCT.set(joysticks.sliderSpeed());
			}
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
