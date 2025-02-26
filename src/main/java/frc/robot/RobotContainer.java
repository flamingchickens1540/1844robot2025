// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import static frc.robot.generated.TunerConstants.*;


/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer
{
    // The robot's subsystems and commands are defined here...

    
     //Replace with CommandPS4Controller or CommandJoystick if needed
    public final frc.robot.subsystems.endEffectorThing endEffectorThing = new endEffectorThing();
    public final CommandXboxController controller = new CommandXboxController(0);
    public final indexer indexer = new indexer();
    public final frc.robot.subsystems.shooter shooter = new shooter();
    public final frc.robot.subsystems.Arm arm = new Arm();
    public final CommandSwerveDrivetrain drivetrain;
    
    
    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer()
    {
        configureBindings();
        drivetrain = new CommandSwerveDrivetrain(DrivetrainConstants, FrontLeft,
            FrontRight, BackLeft, BackRight);;
            //this isnt doing anything??
            //what exactly is end effector?
            controller.a().whileTrue(endEffectorThing.runFrontMotor());
            controller.b().whileTrue(endEffectorThing.runBackMotor());

            //trying something below - did not work
            //what is use of intakeCoral function??
            controller.a().whileTrue(endEffectorThing.intakeCoral(false, 0.5, 5));
            //this is to enable drivetrain:
            //drivetrain.setDefaultCommand(drivetrain.commandDrive(controller.getHID()));

        // Configure the trigger bindings
        SmartDashboard.putNumber("x",0);
        SmartDashboard.putNumber("b",0);
        SmartDashboard.putNumber("y",0);

    }

    public RobotContainer(CommandSwerveDrivetrain drivetrain) {
        this.drivetrain = drivetrain;
    }


    /**
     * Use this method to define your trigger->command mappings. Triggers can be created via the
     * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
     * predicate, or via the named factories in {@link
     * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
     * CommandXboxController Xbox}/{@link  edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
     * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
     * joysticks}.
     */
    private void configureBindings() {
        //pose go to


        arm.setDefaultCommand(arm.commandMoveSpeed(controller));
        //controller.x().whileTrue(arm.commandMotionMagicLoc(Rotation2d.fromDegrees(2000)));
       controller.y().whileTrue(arm.commandMotionMagicLoc(Rotation2d.fromDegrees(SmartDashboard.getNumber("y",0))));
       controller.x().whileTrue(arm.commandMotionMagicLoc(Rotation2d.fromDegrees(SmartDashboard.getNumber("b",0))));
        //pose go to
        // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
        System.out.println("button bindings are being worked");

        // Schedule `exampleMethodCommand` when the Xbox controller's B button is pressed,
        // cancelling on release.
        //driverController.b().whileTrue(exampleSubsystem.exampleMethodCommand());
//        controller.a().whileTrue(shooter.spinFullUntil(100).andThen(pushyThing.Push(1,0.5)).alongWith(shooter.spinFullUntil(1)));
//        controller.b().whileTrue(Commands.print("I also work"));
        //new Trigger(()->true).whileTrue(Commands.runOnce(()-> System.out.println("it is Command.print")));
//        controller.a().whileTrue(Commands.runOnce(()->{
//            System.out.println("how abt this?");
//
//        }));
//        Command command = Commands.runOnce(()-> System.out.println("hello world"));
//        controller.b().whileTrue(command);



        //need to figure out button bindings to set indexer into brake mode
        controller.leftTrigger().whileTrue(indexer.Push(-0.5));
    
        controller.rightTrigger().whileTrue(shooter.spinFull());
        //controller.a().whileTrue(Commands.print("I work"));
        controller.leftBumper().whileTrue(DriveTrain.commandTurnAndDrive(1, Rotation2d::new));


        //TunerConstants.DriveTrain.setDefaultCommand(TunerConstants.DriveTrain.commandDrive(controller.getHID()));

    }


    
    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand()
    {
        // An example command will be run in autonomous
        return null;
    }
}
