package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class EndEffectorThing extends SubsystemBase{
    public final SparkMax smallEndEffectorThingMotor = new SparkMax(Constants.smallEndEffectorThing.MOTOR_ID, SparkLowLevel.MotorType.kBrushless);
    public final SparkMax largeEndEffectorThingMotor = new SparkMax(Constants.largeEndEffectorThing.MOTOR_ID, SparkLowLevel.MotorType.kBrushless);
    public Command removeAlgae(double speed, double time){
        return Commands.parallel(
                Commands.runOnce(()->largeEndEffectorThingMotor.set(speed)),
                Commands.runOnce(()->smallEndEffectorThingMotor.set(speed))
        ).withTimeout(time).andThen(Commands.runOnce(()->largeEndEffectorThingMotor.set(0)),
                Commands.runOnce(()->smallEndEffectorThingMotor.set(0)));


    }
    public Command outputCoral(boolean isInverted, double speed, double time){
        if (isInverted){
            return Commands.parallel(
                            Commands.runOnce(()->largeEndEffectorThingMotor.set(-speed/100)),
                            Commands.runOnce(()->smallEndEffectorThingMotor.set(speed/100))
                    ).withTimeout(time)
                    .andThen(
                            Commands.runOnce(
                                    ()->
                                    {largeEndEffectorThingMotor.set(0);
                                        smallEndEffectorThingMotor.set(0);
                                    })
                    );
        }
        else {
            return Commands.parallel(
                    Commands.runOnce(()->largeEndEffectorThingMotor.set(speed/100)),
                    Commands.runOnce(()->smallEndEffectorThingMotor.set(-speed/100))
            ).withTimeout(time)
                    .andThen(
                            Commands.runOnce(
                            ()->
                            {largeEndEffectorThingMotor.set(0);
                            smallEndEffectorThingMotor.set(0);
                            })
                    );
        }
    }
    public Command intakeCoral(boolean isInverted, double speed, double time) {
        if (isInverted) {
            return Commands.parallel(
                            Commands.runOnce(() -> largeEndEffectorThingMotor.set(-speed/100)),
                            Commands.runOnce(() -> smallEndEffectorThingMotor.set(speed/100))
                    ).withTimeout(time)
                    .andThen(
                            Commands.runOnce(
                                    () ->
                                    {
                                        largeEndEffectorThingMotor.set(0);
                                        smallEndEffectorThingMotor.set(0);
                                    })
                    );
        } else {
            return Commands.parallel(
                            Commands.runOnce(() -> largeEndEffectorThingMotor.set(speed/100)),
                            Commands.runOnce(() -> smallEndEffectorThingMotor.set(-speed/100))
                    ).withTimeout(time)
                    .andThen(
                            Commands.runOnce(
                                    () ->
                                    {
                                        largeEndEffectorThingMotor.set(0);
                                        smallEndEffectorThingMotor.set(0);
                                    })
                    );
        }
    }
    public Command intakeAlgea(double smallSpeed, double largeSpeed, double timeSpinning){
        return Commands.run(
                ()-> {
                    smallEndEffectorThingMotor.set(smallSpeed/100);
                    largeEndEffectorThingMotor.set(largeSpeed/100);
                }
        ).withTimeout(timeSpinning).andThen(
                ()->{
                    smallEndEffectorThingMotor.set(smallSpeed/100);
                    largeEndEffectorThingMotor.set(largeSpeed/100);
                }
        );
    }
}
