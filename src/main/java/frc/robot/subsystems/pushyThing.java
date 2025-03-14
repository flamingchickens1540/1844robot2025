package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class pushyThing extends SubsystemBase {
    private final TalonFX pushyThingMotor = new TalonFX(Constants.pushyThing.MOTOR_ID);
    public pushyThing(){
        TalonFXConfiguration config = new TalonFXConfiguration();
        config.Feedback.SensorToMechanismRatio = Constants.Shooter.GEAR_RATIO;
        config.SoftwareLimitSwitch.ForwardSoftLimitEnable = false;
        config.SoftwareLimitSwitch.ReverseSoftLimitEnable = false;
        config.SoftwareLimitSwitch.ForwardSoftLimitThreshold = 0;
        config.SoftwareLimitSwitch.ReverseSoftLimitThreshold = 0;
        config.CurrentLimits.StatorCurrentLimitEnable = false;
        config.CurrentLimits.StatorCurrentLimit = 40;
        config.MotorOutput.NeutralMode = NeutralModeValue.Brake;
    }


    public Command Push(double timeTune, double speedTune) {
        return Commands.startEnd(()->pushyThingMotor.set(speedTune),
        ()->pushyThingMotor.set(0),
            this
        );
    }
}
