package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.TorqueCurrentFOC;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class endEffectorThing extends SubsystemBase{
    private final TalonFX largeEndEffectorThingMotor = new TalonFX(Constants.largeEndEffectorThing.MOTOR_ID);//black wheels
    public final TalonFX smallEndEffectorThingMotor = new TalonFX(Constants.smallEndEffectorThing.MOTOR_ID1);

    public endEffectorThing(){
        TalonFXConfiguration config =new TalonFXConfiguration();
        config.CurrentLimits.StatorCurrentLimit = 40;
        config.MotorOutput.NeutralMode = NeutralModeValue.Brake;

        largeEndEffectorThingMotor.getConfigurator().apply(config);
        smallEndEffectorThingMotor.getConfigurator().apply(config);    }
    
    
    public Command removeAlgae(double speed, double time){
        return Commands.run(()->runBoth(0,speed),
                this

        ,
                this);
    

    }

    public Command stay(){
        return Commands.startEnd(()->
        {
                largeEndEffectorThingMotor.setControl(new TorqueCurrentFOC(20));
                smallEndEffectorThingMotor.setControl(new TorqueCurrentFOC(20));
        }, () -> {
                largeEndEffectorThingMotor.set(0);
                smallEndEffectorThingMotor.set(0);        
        },
        
        this);


    }

    public Command outputCoral(boolean inverted, double speed, double time){
        System.out.println("I work");
        if (inverted){
            return Commands.run(()->runBoth(speed,speed),
                            this
                    )
                    .andThen(
                            Commands.run(()->runBoth(0,0),
                                    this)
                    );
        }
        else {
            return Commands.run(()->runBoth(speed,speed),
                            this
                    )
                    .andThen(
                            Commands.run(()->runBoth(0,0),
                                    this)
                    );
        }
    }
    public Command intakeCoral(boolean inverted, double speed, double time){
        if (inverted){
            return Commands.run(()->runBoth(-speed,-speed),
                    this
            )
                    .andThen(
                            Commands.run(()->runBoth(0,0),
                                    this)
                    );
        }
        else {
            return Commands.run(()->runBoth(-speed,-speed),
                            this
                    )

                    .andThen(
                            Commands.run(()->runBoth(0,0),
                                    this)
                    );
        }
}
    public Command Debug(double speed){
        return Commands.run(
                ()->runBoth(speed,-speed),
                this
        );

    }
    public Command Stop(){
        return Commands.run(()->runBoth(0,0),
                this);
    }
    public void runBoth(double speedbig,double speedsmall){
        largeEndEffectorThingMotor.setVoltage(speedbig*12);
        smallEndEffectorThingMotor.setVoltage(speedsmall*12);
    }
}