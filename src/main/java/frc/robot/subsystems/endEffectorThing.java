package frc.robot.subsystems;

import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class endEffectorThing extends SubsystemBase{
    private final SparkMax largeEndEffectorThingMotor = new SparkMax(Constants.largeEndEffectorThing.MOTOR_ID, SparkLowLevel.MotorType.kBrushless);
    public final SparkMax smallEndEffectorThingMotor = new SparkMax(Constants.smallEndEffectorThing.MOTOR_ID1, SparkLowLevel.MotorType.kBrushless);
    
    public endEffectorThing(){
        SparkMaxConfig largeEndEffectorThingMotorConfig =new SparkMaxConfig();
        largeEndEffectorThingMotorConfig.smartCurrentLimit(40);
        largeEndEffectorThingMotorConfig.idleMode(SparkBaseConfig.IdleMode.kCoast);
        largeEndEffectorThingMotor.configure(largeEndEffectorThingMotorConfig, SparkBase.ResetMode.kResetSafeParameters, SparkBase.PersistMode.kPersistParameters);
    }
    
    
    public Command removeAlgae(double speed, double time){
        return Commands.run(()->runBoth(0,speed),
                this

        ,
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