// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import choreo.Choreo;
import choreo.trajectory.Trajectory;
import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.geometry.Rotation2d;

import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import java.nio.file.Path;
import java.util.function.Supplier;


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
    public final frc.robot.subsystems.pushyThing pushyThing = new pushyThing();
    public final frc.robot.subsystems.shooter shooter = new shooter();
    public final frc.robot.subsystems.Arm arm = new Arm();
    public final CommandSwerveDrivetrain drivetrain;
    public final CommandXboxController scontroller = new CommandXboxController(1);
    
    
    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer()
    {
        configureBindings();
        drivetrain = new CommandSwerveDrivetrain(DrivetrainConstants, FrontLeft,
            FrontRight, BackLeft, BackRight);;
//        drivetrain.setDefaultCommand(drivetrain.commandDrive(controller.getHID()));

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
        controller.rightBumper().whileTrue(DriveTrain.Zero());
        arm.setDefaultCommand(arm.commandMoveSpeed(scontroller));
        //controller.x().whileTrue(arm.commandMotionMagicLoc(Rotation2d.fromDegrees(2000)));
        //controller.y().whileTrue(arm.commandMotionMagicLoc(Rotation2d.fromDegrees(SmartDashboard.getNumber("y",0))));
        //controller.b().whileTrue(arm.commandMotionMagicLoc(Rotation2d.fromDegrees(SmartDashboard.getNumber("b",0))));
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



        scontroller.leftTrigger().whileTrue(shooter.spinFullUntil(3)
                .andThen(pushyThing.Push(1, -0.5)
                        .alongWith(shooter.spinFullUntil(1)).withTimeout(1)));
        scontroller.leftBumper().whileTrue(shooter.intake(3).alongWith(pushyThing.Push(3,0.2)));
        //controller.rightTrigger().whileTrue(shooter.spinFullUntil(100));
        //controller.a().whileTrue(Commands.print("I work"));
        //controller.leftBumper().whileTrue(DriveTrain.commandTurnAndDrive(1, Rotation2d::new));

        scontroller.rightBumper().whileTrue(endEffectorThing.intakeCoral(true,0.9,3));
        scontroller.rightBumper().whileFalse(endEffectorThing.Stop());
        scontroller.rightTrigger().whileTrue(endEffectorThing.outputCoral(false,0.9,3));
        scontroller.rightTrigger().whileFalse(endEffectorThing.Stop());
        TunerConstants.DriveTrain.setDefaultCommand(TunerConstants.DriveTrain.commandDrive(controller.getHID()));

    }


    
    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
//        try {
//            // Load the trajectory file
//            Trajectory trajectory = Choreo.loadTrajectory(Filesystem.getDeployDirectory().toPath().resolve(trajname).toString()).get();
//
//            // Loop through trajectory points and send speeds to the swerve drive
//            for (int i = 0; i <= trajectory.getTotalTime(); i++) {
//                double[] doubleMatrix =TrajectoryUtils.getVelocitiesAtTime(trajectory,i);
//                return DriveTrain.setVelocityAndRotationalRate(doubleMatrix[0],doubleMatrix[1],doubleMatrix[2]);
//            }
//
//
//                 // Command the robot to follow the trajectory
//
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }

        return drivetrain.moveABitForward(1);
    }
}
