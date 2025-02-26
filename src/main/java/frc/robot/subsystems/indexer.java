package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class indexer extends SubsystemBase {
    private final TalonFX indexMotor = new TalonFX(Constants.pushyThing.MOTOR_ID);
    public indexer(){
        TalonFXConfiguration config = new TalonFXConfiguration();
        config.Feedback.SensorToMechanismRatio = Constants.Shooter.GEAR_RATIO;
        config.SoftwareLimitSwitch.ForwardSoftLimitEnable = false;
        config.SoftwareLimitSwitch.ReverseSoftLimitEnable = false;
        config.SoftwareLimitSwitch.ForwardSoftLimitThreshold = 0;
        config.SoftwareLimitSwitch.ReverseSoftLimitThreshold = 0;
        config.CurrentLimits.StatorCurrentLimitEnable = false;
        config.CurrentLimits.StatorCurrentLimit = 40;
    }

    public Command Push(double speedTune) {
        return Commands.startEnd(
                ()-> indexMotor.set(speedTune),
                ()-> indexMotor.set(0), this
        );
    }

    //how does NeutralModeValue class work, and does this work?
    public Command setBrakeMode ()
    {
        NeutralModeValue mode = NeutralModeValue.Brake;
        return Commands.runOnce(
                //do we need a timeout??
                ()-> indexMotor.setNeutralMode(mode)
        );
    }

    //are we adding a method to be more specific with indexer and algae pos using Laser CAN???
    public void outtake()
    {}
}
 
