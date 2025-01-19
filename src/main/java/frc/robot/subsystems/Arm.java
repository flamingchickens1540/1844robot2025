package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
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
    double offSet = 0;
    public Arm() {
        TalonFXConfiguration config = new TalonFXConfiguration();
        config.Feedback.SensorToMechanismRatio = Constants.Arm.GEAR_RATIO;
        config.SoftwareLimitSwitch.ForwardSoftLimitEnable = false;
        config.SoftwareLimitSwitch.ReverseSoftLimitEnable = false;
        config.SoftwareLimitSwitch.ForwardSoftLimitThreshold = 0;
        config.SoftwareLimitSwitch.ReverseSoftLimitThreshold = 0;
        config.CurrentLimits.StatorCurrentLimitEnable = false;
        config.CurrentLimits.StatorCurrentLimit = 40;

        // in init function


// set slot 0 gains
        var slot0Configs = config.Slot0;
        slot0Configs.kS = 0.25; // Add 0.25 V output to overcome static friction
        slot0Configs.kV = 0.12; // A velocity target of 1 rps results in 0.12 V output
        slot0Configs.kA = 0.01; // An acceleration of 1 rps/s requires 0.01 V output
        slot0Configs.kP = 4.8; // A position error of 2.5 rotations results in 12 V output
        slot0Configs.kI = 0; // no output for integrated error
        slot0Configs.kD = 0.1; // A velocity error of 1 rps results in 0.1 V output

// set Motion Magic settings
        var motionMagicConfigs = config.MotionMagic;
        motionMagicConfigs.MotionMagicCruiseVelocity = 5; // Target cruise velocity of 80 rps
        motionMagicConfigs.MotionMagicAcceleration = 50; // Target acceleration of 160 rps/s (0.5 seconds)
        motionMagicConfigs.MotionMagicJerk = 1600; // Target jerk of 1600 rps/s/s (0.1 seconds)


        armMotor.getConfigurator().apply(config);
        armMotor.setPosition(0);
        armMotor.setNeutralMode(NeutralModeValue.Brake);

    }


    public Command commandMoveSpeed(XboxController controller) {
        return Commands.run(
                () -> {
                    double value = (controller.getLeftY()/3);
                    armMotor.set(value);
                    System.out.println(value);
                },
                this
        );
    }
    public Command commandMotionMagicLoc(Rotation2d loc) {
        return Commands.run(
                () -> armMotor.setControl(new MotionMagicVoltage(loc.getRotations()))
        ).until(
                ()-> (Math.abs(armMotor.getPosition().refresh().getValueAsDouble() + offSet - loc.getRotations())<=0.001)
        );
    }

    public Command commandBunnyDrop(){
        return Commands.run(
                ()->{
                    commandMotionMagicLoc(Rotation2d.fromDegrees(55));
                }
        );
    }
    public Command commandStrartPos(){
        return Commands.run(
                ()->{
                    commandMotionMagicLoc(Rotation2d.fromDegrees(0));
                }
        );
    }
    public Command commandSetToZero(){
        //offSet = armMotor.getPosition().refresh().getValueAsDouble();
        return Commands.run(
                ()->armMotor.setPosition(0));
    }
}
