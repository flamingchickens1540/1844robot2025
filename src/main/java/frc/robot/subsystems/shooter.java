package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import java.util.function.Supplier;

public class Shooter extends SubsystemBase {
    private final TalonFX shooterMotor1 = new TalonFX(Constants.Shooter.MOTOR1_ID);
    private final TalonFX shooterMotor2 = new TalonFX(Constants.Shooter.MOTOR2_ID);
    public Shooter()
    {
        TalonFXConfiguration config = new TalonFXConfiguration();
        config.Feedback.SensorToMechanismRatio = Constants.Shooter.GEAR_RATIO;
        config.SoftwareLimitSwitch.ForwardSoftLimitEnable = false;
        config.SoftwareLimitSwitch.ReverseSoftLimitEnable = false;
        config.SoftwareLimitSwitch.ForwardSoftLimitThreshold = 0;
        config.SoftwareLimitSwitch.ReverseSoftLimitThreshold = 0;
        config.CurrentLimits.StatorCurrentLimitEnable = false;
        config.CurrentLimits.StatorCurrentLimit = 40;
        
        //is this good?
        this.shooterMotor1.getConfigurator().apply(config);
        this.shooterMotor2.getConfigurator().apply(config);

        //safety settings
        var currentConfiguration = new CurrentLimitsConfigs();
        currentConfiguration.StatorCurrentLimitEnable = true;
        currentConfiguration.StatorCurrentLimit = /* what should top velocity be? */ 80;

        //should i refresh before applying these configs?

        this.shooterMotor1.getConfigurator().refresh(currentConfiguration);
        this.shooterMotor2.getConfigurator().refresh(currentConfiguration);
        this.shooterMotor1.getConfigurator().apply(currentConfiguration);
        this.shooterMotor2.getConfigurator().apply(currentConfiguration);

    }

    //blue fly wheels, bigger diameter (what should speeds be?)
    public Command shoot1Command(double speed1)
    {
        return Commands.startEnd(
            ()->shooterMotor1.set(speed1),
            ()->shooterMotor1.set(0.0));

    }

    //green fly wheels, smaller diameter (what should speeds be?)
    public Command shoot2Command(double speed2)
    {
        return Commands.startEnd(
            ()->shooterMotor2.set(speed2), 
            ()->shooterMotor2.set(0.0));

    }


    public Command spinFullUntil(double time){

        return Commands.startEnd(
                ()->setMotorVelocity(6_000),
                ()->shooterMotor1.set(0)
        ).withTimeout(time);

    }


    private void setMotorVelocity(double velocityRPM){
        shooterMotor1.setControl(
                new VelocityVoltage(
                        velocityRPM/60.0));
    }
}
