package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import java.util.function.Supplier;

public class shooter extends SubsystemBase {
    private final TalonFX shooterMotor = new TalonFX(Constants.Shooter.MOTOR_ID);
    public shooter(){
        TalonFXConfiguration config = new TalonFXConfiguration();
        config.Feedback.SensorToMechanismRatio = Constants.Shooter.GEAR_RATIO;
        config.SoftwareLimitSwitch.ForwardSoftLimitEnable = false;
        config.SoftwareLimitSwitch.ReverseSoftLimitEnable = false;
        config.SoftwareLimitSwitch.ForwardSoftLimitThreshold = 0;
        config.SoftwareLimitSwitch.ReverseSoftLimitThreshold = 0;
        config.CurrentLimits.StatorCurrentLimitEnable = true;
        config.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;
        config.CurrentLimits.StatorCurrentLimit = 80;
        config.Slot0.kP = 0.2;
        config.Slot0.kA = 4.49;
        config.Slot0.kV = 0.12;
        shooterMotor.getConfigurator().apply(config);
        SmartDashboard.setDefaultNumber("shooterVelocity", 3000);
    }
    public Command spinFull(){

        return Commands.startEnd(
                ()->setMotorVelocity(SmartDashboard.getNumber("shooterVelocity", 3000)),
                ()->shooterMotor.set(0),
                this);
    }


    private void setMotorVelocity(double velocityRPM){
        shooterMotor.setControl(
                new VelocityVoltage(
                        velocityRPM/60.0).withSlot(0));
    }
}
