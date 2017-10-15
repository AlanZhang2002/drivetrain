package org.usfirst.frc.team8.robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.AnalogPotentiometer;

public class Slider {//initialize stuff, create logic and such
	CANTalon sliderCT = new CANTalon(7);
	AnalogPotentiometer pMeter = new AnalogPotentiometer(9);
}
