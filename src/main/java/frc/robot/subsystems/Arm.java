package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants;



public class Arm extends SubsystemBase {
    private final TalonFX armMotor = new TalonFX(Constants.Arm.MOTOR_ID);
    private final TalonFX armMotor1 = new TalonFX(Constants.Arm.MOTOR_ID2);
    private final TalonFXConfiguration config = new TalonFXConfiguration();
    private final DutyCycleEncoder encoder = new DutyCycleEncoder(0);



    public Arm() {
        config.Feedback.SensorToMechanismRatio = Constants.Arm.GEAR_RATIO;
        config.SoftwareLimitSwitch.ForwardSoftLimitEnable = false;
        config.SoftwareLimitSwitch.ReverseSoftLimitEnable = false;
        config.SoftwareLimitSwitch.ForwardSoftLimitThreshold = 23.3;
        config.SoftwareLimitSwitch.ReverseSoftLimitThreshold = -24.6;
        config.CurrentLimits.StatorCurrentLimitEnable = false;
        config.CurrentLimits.StatorCurrentLimit = 5;
        // in init function

        SmartDashboard.putNumber("p",4.8);
// set slot 0 gains
        var slot0Configs = config.Slot0;
        slot0Configs.kS = 0.25; // Add 0.25 V output to overcome static friction
        slot0Configs.kV = 1; // A velocity target of 1 rps results in 0.12 V output
        slot0Configs.kA = 0.01; // An acceleration of 1 rps/s requires 0.01 V output
        //slot0Configs.kP = SmartDashboard.getNumber("p",0); // A position error of 2.5 rotations results in 12 V output
        slot0Configs.kP = 65;
        slot0Configs.kI = 7; // no output for integrated error6
        slot0Configs.kD = 0.1; // A velocity error of 1 rps results in 0.1 V output

// set Motion Magic settings
        var motionMagicConfigs = config.MotionMagic;
        motionMagicConfigs.MotionMagicCruiseVelocity = 5; // Target cruise velocity of 80 rps
        motionMagicConfigs.MotionMagicAcceleration = 50; // Target acceleration of 160 rps/s (0.5 seconds)
        motionMagicConfigs.MotionMagicJerk = 1600; // Target jerk of 1600 rps/s/s (0.1 seconds)

        config.MotorOutput.NeutralMode = NeutralModeValue.Brake;

        armMotor.getConfigurator().apply(config);
        armMotor1.getConfigurator().apply(config);
        armMotor1.setControl(new Follower(Constants.Arm.MOTOR_ID, true));

        armMotor.setPosition(encoder.get()/3);
        armMotor1.setPosition(encoder.get()/3);
    }


    public Command commandMoveSpeed(CommandXboxController controller) {
        return Commands.run(
                () -> {
                    double value = (controller.getLeftY()/3.8);
                    armMotor.setVoltage(value*12);
                    //System.out.println(value);
                },
                this
        );
    }
    public Command commandMotionMagicLoc(Rotation2d loc) {
        return Commands.startEnd(
                ()-> armMotor.setControl(new MotionMagicVoltage(loc.getRotations())),
                ()->armMotor.setVoltage(0),
                this
        ).until(
                ()-> (Math.abs(armMotor.getPosition().getValueAsDouble() - loc.getRotations())<=0.001)
        );
    }


    public Command commandSetToZero(){
        //offSet = armMotor.getPosition().refresh().getValueAsDouble();
        return Commands.run(
                ()->armMotor.setPosition(0));
    }

    @Override
    public void periodic() {
//          System.out.println("ArmPos: " + armMotor.getPosition().getValueAsDouble()*360);
//        System.out.println("otherArmPos: "+armMotor1.getPosition().refresh().getValueAsDouble());
//        System.out.println("p: "+armMotor);
    }
}
