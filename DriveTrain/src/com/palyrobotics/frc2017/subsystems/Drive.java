package com.palyrobotics.frc2017.subsystems;

import org.usfirst.frc.team8.robot.Joysticks;

import com.ctre.CANTalon;
import com.palyrobotics.frc2017.config.Commands;
import com.palyrobotics.frc2017.config.Constants;
import com.palyrobotics.frc2017.config.Gains;
import com.palyrobotics.frc2017.config.RobotState;
import com.palyrobotics.frc2017.config.dashboard.DashboardManager;
import com.palyrobotics.frc2017.config.dashboard.DashboardValue;
import com.palyrobotics.frc2017.subsystems.controllers.*;
import com.palyrobotics.frc2017.util.Pose;
import com.palyrobotics.frc2017.util.archive.CheesyDriveHelper;
import com.palyrobotics.frc2017.util.archive.DriveSignal;
import com.team254.lib.trajectory.Path;

/**
 * Represents the drivetrain
 * Uses controllers or cheesydrivehelper/proportionaldrivehelper to calculate DriveSignal
 * @author Nihar
 */
public class Drive extends Subsystem {
	private static Drive instance = new Drive();
	public static Drive getInstance() {
		return instance;
	}

	/* Various control states for the drivetrain
	 * Chezy - chezy drive with joystick values, offboard - can talon offboard loop
	 * on board - control loop calculated in code, open loop - use drive outputs passed in through commands
	 * neutral - do nothing
	 */
	public enum DriveState {CHEZY, OFF_BOARD_CONTROLLER, ON_BOARD_CONTROLLER, OPEN_LOOP, NEUTRAL}
	private DriveState mState = DriveState.NEUTRAL;

	// Helper class to calculate teleop output
	private CheesyDriveHelper mCDH = new CheesyDriveHelper();

	private Drive.DriveController mController = null;
	// Used for off board controllers to be called only once
	private boolean newController = false;

	// Encoder DPP
	private final double kInchesPerTick;
	private final double kWheelbaseWidth; // Get from CAD
	private final double kTurnSlipFactor; // Measure empirically
	public final double kInchesToTicks;

	// Cache poses to not be allocating at 200Hz
	private Pose mCachedPose = new Pose(0, 0, 0, 0, 0, 0, 0, 0);
	// Cached robot state, updated by looper
	private RobotState mCachedRobotState;
	// Stores output
	private DriveSignal mSignal = DriveSignal.getNeutralSignal();

	private DashboardValue motors;
	
	private DashboardValue leftEncoder;
	private DashboardValue rightEncoder;
	
	private Drive() {
		super("Drive");
		if (Constants.kRobotName == Constants.RobotName.DERICA) {
			kWheelbaseWidth = 22.0;
			kTurnSlipFactor = 1.2;
			kInchesPerTick = 0.07033622;
			kInchesToTicks = 1400 / (2 * 3.1415 * 3.5);
		} else if (Constants.kRobotName == Constants.RobotName.STEIK) {
			kWheelbaseWidth = 0;
			kTurnSlipFactor = 0;
			kInchesPerTick = 1/Constants.kDriveTicksPerInch;
			kInchesToTicks = Constants.kDriveTicksPerInch;
		} else {
			kWheelbaseWidth = 0;
			kTurnSlipFactor = 0;
			kInchesPerTick = 1/Constants.kDriveTicksPerInch;
			kInchesToTicks = Constants.kDriveTicksPerInch;
		}
		
		motors = new DashboardValue("driveSpeedUpdate");
		
		leftEncoder = new DashboardValue("leftdriveencoder");
		rightEncoder = new DashboardValue("rightdriveencoder");
	}

	/**
	 * @return DriveSignal
	 */
	public DriveSignal getDriveSignal() {
		return mSignal;
	}

	@Override
	public void start() {
		setNeutral();
	}

	/**
	 * Updates the drivetrain and its DriveSignal
	 * Pass in the newest RobotState
	 */
	@Override
	public void update(Commands commands, RobotState state) {
		mCachedRobotState = state;
		mCachedPose = state.drivePose.copy();
		boolean mIsNewState = !(mState == commands.wantedDriveState);
		mState = commands.wantedDriveState;
		
		switch(mState) {
			case CHEZY:
				/*
				setDriveOutputs(mCDH.cheesyDrive(commands, mCachedRobotState)); // cheesyDrive returns a DriveSignal
				break;
				*/
				
				/*
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
					
					left1.set(driveSpeed + turnSpeed);// when left side goes faster and right side goes slower when turning, it turns right
					left2.set(driveSpeed + turnSpeed);
					left3.set(driveSpeed + turnSpeed);
					right1.set(-driveSpeed + turnSpeed);//basically driveSpeed - turnSpeed, but reversed
					right2.set(-driveSpeed + turnSpeed);
					right3.set(-driveSpeed + turnSpeed);
				} 
				*/
				
				
				break;
			case OFF_BOARD_CONTROLLER:
				/*
				if (mController == null) {
					setDriveOutputs(DriveSignal.getNeutralSignal());
					System.err.println("No offboard controller to use!");
					break;
				}
				setDriveOutputs(mController.update(mCachedRobotState));
				break;
				*/
				break;
			case ON_BOARD_CONTROLLER:
				/*
				if (mController == null) {
					System.err.println("No onboard controller to use!");
					commands.wantedDriveState = DriveState.NEUTRAL;
				} else {
					setDriveOutputs(mController.update(mCachedRobotState));
				}
				break;
				*/
				break;
			case OPEN_LOOP:
				/*
				if (commands.robotSetpoints.drivePowerSetpoint.isPresent()) {
					setDriveOutputs(commands.robotSetpoints.drivePowerSetpoint.get());
				}
				break;
				*/
				break;
			case NEUTRAL:
				/*
				if(!newController && mIsNewState) {
					resetController();
				}
				setDriveOutputs(DriveSignal.getNeutralSignal());
				
				if(mCachedRobotState.gamePeriod.equals(RobotState.GamePeriod.TELEOP)) {
					if(mIsNewState) {
						resetController();
					}
					commands.wantedDriveState = DriveState.CHEZY;
				}
				break;
				*/
				break;
		}
		
		mIsNewState = false;
		mState = commands.wantedDriveState;
		
		leftEncoder.updateValue(state.drivePose.leftEnc);
		rightEncoder.updateValue(state.drivePose.rightEnc);
		
		DashboardManager.getInstance().publishKVPair(leftEncoder);
		DashboardManager.getInstance().publishKVPair(rightEncoder);

		motors.updateValue(state.drivePose.leftSpeed + ", " + state.drivePose.rightSpeed);
		DashboardManager.getInstance().publishKVPair(motors);
	}

	@Override
	public void stop() {
	}

	private void setDriveOutputs(DriveSignal signal) {
		mSignal = signal;
	}

	/**
	 * Used when external reset of drivetrain is desired
	 */
	public void setNeutral() {
		mController = null;
		mState = DriveState.NEUTRAL;
		setDriveOutputs(DriveSignal.getNeutralSignal());
	}

	public void setCANTalonController(DriveSignal signal) {
		mController = new CANTalonDriveController(signal);
		newController = true;
	}

	public void setTurnAngleSetpoint(double heading) {
		mController = new BangBangTurnAngleController(mCachedPose, heading);
		newController = true;
	}
	
	public void setTurnAngleEncoderSetpoint(double angle) {
		System.out.println("Encoder angle "+angle);
		mController = new EncoderTurnAngleController(mCachedPose, angle);
		newController = true;
	}
	
	public void setGyroMotionMagicTurnAngleSetpoint(double angle) {
		mController = new GyroMotionMagicTurnAngleController(mCachedPose, angle);
		newController = true;
	}

	/**
	 * Motion profile hype
	 * @param path Path to follow
	 * @param useGyro Should correct heading using gyro or not
	 */
	public void setTrajectoryController(Path path, Gains.TrajectoryGains gains, boolean useGyro, boolean inverted) {
		mController = new TrajectoryFollowingController(path, gains, useGyro, inverted);
		newController = true;
	}

	public void setDriveStraight(double distance) {
		mController = new DriveStraightController(mCachedPose, distance);
		newController = true;
	}
	public void setTimedDrive(double voltage, double time) {
		mController = new TimedDriveController(voltage, time);
		newController = true;
	}
	
	// Wipes current controller
	public void resetController() {
		mController = null;
	}

	/**
	 * @return The pose according to the current sensor state
	 */
	public Pose getPose() {
		// If drivetrain has not had first update yet, return initial robot pose of 0,0,0,0,0,0
		if(mCachedRobotState == null) {
			return new Pose(0,0,0,0,0,0,0,0);
		}
		return mCachedPose;
	}

	public Drive.DriveController getController() {
		return mController;
	}

	public boolean controllerOnTarget() {
		return (mController==null || mController.onTarget());
	}

	public boolean hasController() {
		return mController != null;
	}

	public interface DriveController {
		DriveSignal update(RobotState state);

		Pose getSetpoint();

		boolean onTarget();
	}

	@Override
	public String getStatus() {
		return "Drive State: " + mState + "\nOutput Control Mode: " + mSignal.leftMotor.getControlMode() +
				"\nLeft Setpoint: " + mSignal.leftMotor.getSetpoint() + "\nRight Setpoint: " + mSignal.rightMotor.getSetpoint() +
				"\nLeft Enc: "+mCachedPose.leftEnc + "\nRight Enc: "+mCachedPose.rightEnc+
				"\nGyro: "+mCachedPose.heading+"\n";
	}
}