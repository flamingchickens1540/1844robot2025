package frc.robot.subsystems;

import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.ForwardLimitSourceValue;
import com.ctre.phoenix6.signals.ForwardLimitValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.math.geometry.Rotation2d;
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

    double offSet = 0;

    public final StatusSignal<ForwardLimitValue> limit;

    public Arm() {
        config.Feedback.SensorToMechanismRatio = Constants.Arm.GEAR_RATIO;
        config.SoftwareLimitSwitch.ForwardSoftLimitEnable = false;
        config.SoftwareLimitSwitch.ReverseSoftLimitEnable = false;
        config.SoftwareLimitSwitch.ForwardSoftLimitThreshold = 23.3;
        config.SoftwareLimitSwitch.ReverseSoftLimitThreshold = -24.6;
        config.CurrentLimits.StatorCurrentLimitEnable = true;
        config.CurrentLimits.StatorCurrentLimit = 80;
        // in init function

        SmartDashboard.putNumber("p",4.8);
// set slot 0 gains
        var slot0Configs = config.Slot0;
        slot0Configs.kS = 0; // Add 0.25 V output to overcome static friction
        slot0Configs.kV = 0; // A velocity target of 1 rps results in 0.12 V output
        slot0Configs.kA = 0; // An acceleration of 1 rps/s requires 0.01 V output
        //slot0Configs.kP = SmartDashboard.getNumber("p",0); // A position error of 2.5 rotations results in 12 V output
        slot0Configs.kP = 2;
        slot0Configs.kI = 0; // no output for integrated error
        slot0Configs.kD = 0; // A velocity error of 1 rps results in 0.1 V output

// set Motion Magic settings
        var motionMagicConfigs = config.MotionMagic;
        motionMagicConfigs.MotionMagicCruiseVelocity = 5; // Target cruise velocity of 80 rps
        motionMagicConfigs.MotionMagicAcceleration = 50; // Target acceleration of 160 rps/s (0.5 seconds)
        motionMagicConfigs.MotionMagicJerk = 1600; // Target jerk of 1600 rps/s/s (0.1 seconds)


        armMotor.getConfigurator().apply(config);
        armMotor1.getConfigurator().apply(config);
        armMotor1.setControl(new Follower(Constants.Arm.MOTOR_ID, true));

        armMotor.setPosition(0);
        armMotor1.setPosition(0);
        armMotor.setNeutralMode(NeutralModeValue.Brake);
        limit = armMotor.getForwardLimit();
    }


    public Command commandMoveSpeed(CommandXboxController controller) {
        return Commands.run(
                () -> {
                    double value = (controller.getLeftY()/3);
                    armMotor.setVoltage(value*12);
                    //System.out.println(value);
                },
                this
        );
    }
    public Command
    commandMotionMagicLoc(Rotation2d loc) {
        config.Slot0.kP = 0.5;
        armMotor.getConfigurator().apply(config);
        armMotor1.getConfigurator().apply(config);
        return Commands.runOnce(

                ()-> armMotor.setControl(new MotionMagicVoltage(loc.getRotations()))
        ).andThen(Commands.waitUntil(
                ()-> (Math.abs(armMotor.getPosition().refresh().getValueAsDouble() + offSet - loc.getRotations())<=0.001)
        ));
    }


    public Command commandSetToZero(){
        //offSet = armMotor.getPosition().refresh().getValueAsDouble();
        return Commands.run(
                ()->armMotor.setPosition(0));
    }

    @Override
    public void periodic() {
        System.out.println("ArmPos: "+armMotor.getPosition().refresh().getValueAsDouble());
        System.out.println("otherArmPos: "+armMotor1.getPosition().refresh().getValueAsDouble());
        System.out.println("p: "+armMotor);
    }
}
