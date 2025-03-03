// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.*;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.Command;

import java.util.function.BooleanSupplier;


import static frc.robot.generated.TunerConstants.*;



/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
    // The robot's subsystems and commands are defined here...

    //Replace with CommandPS4Controller or CommandJoystick if needed
    public final frc.robot.subsystems.endEffectorThing endEffectorThing = new endEffectorThing();
    public final CommandXboxController controller = new CommandXboxController(0);
    public final frc.robot.subsystems.pushyThing pushyThing = new pushyThing();
    public final frc.robot.subsystems.shooter shooter = new shooter();
    public final Arm arm = new Arm();
    public final CommandXboxController scontroller = new CommandXboxController(1);
    public final XboxController xboxController = new XboxController(0);
    public final CTREAutoUtils drivetrain = new CTREAutoUtils();
    public final LEDs leDs = new LEDs();

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        configureBindings();

//        drivetrain.setDefaultCommand(drivetrain.commandDrive(controller.getHID()));

        // Configure the trigger bindings
        SmartDashboard.putNumber("x", 0);
        SmartDashboard.putNumber("b", 0);
        SmartDashboard.putNumber("y", 0);

    }

    public RobotContainer(CommandSwerveDrivetrain drivetrain) {

    }


    /**
     * Use this method to define your trigger->command mappings. Triggers can be created via the
     * {@link Trigger#Trigger(BooleanSupplier)} constructor with an arbitrary
     * predicate, or via the named factories in {@link
     * CommandGenericHID}'s subclasses for {@link
     * CommandXboxController Xbox}/{@link  CommandPS4Controller
     * PS4} controllers or {@link CommandJoystick Flight
     * joysticks}.
     */
    private void configureBindings() {
        //pose go to
        controller.rightBumper().whileTrue(CTREAutoUtils.drivetrain.Zero());
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
        controller.start().whileTrue(leDs.commandSetToGreen());
        controller.back().whileTrue(leDs.commandSetToRed());
        controller.y().whileTrue(leDs.commandSetToPurple());
        controller.b().whileTrue(leDs.commandSetToRainbow());

        scontroller.y().whileTrue(arm.commandMotionMagicLoc(Rotation2d.fromDegrees(0)));
        scontroller.a().whileTrue(endEffectorThing.removeAlgae(1, 1));
        scontroller.a().whileFalse(endEffectorThing.Stop());
        scontroller.leftTrigger().whileTrue(shooter.spinFullUntil(3)
                .andThen(leDs.commandSetToGreen())
                        .alongWith(shooter.spinFullUntil(1)).withTimeout(1));
        scontroller.leftTrigger().whileFalse(pushyThing.Stop().andThen(leDs.commandSetToRed()));
        scontroller.leftBumper().whileTrue(shooter.intake(3).alongWith(pushyThing.Push(3, 0.1)));
        scontroller.leftBumper().whileFalse(pushyThing.Stop().andThen(shooter.Stop()));
        //controller.rightTrigger().whileTrue(shooter.spinFullUntil(100));
        //controller.a().whileTrue(Commands.print("I work"));
        //controller.leftBumper().whileTrue(DriveTrain.commandTurnAndDrive(1, Rotation2d::new));

        scontroller.rightBumper().whileTrue(endEffectorThing.
                intakeCoral(true, 1, 3));
        scontroller.rightBumper().whileFalse(endEffectorThing.Stop());
        scontroller.rightTrigger().whileTrue(endEffectorThing.outputCoral(false, 1, 3));
        scontroller.rightTrigger().whileFalse(endEffectorThing.Stop());
        CTREAutoUtils.drivetrain.setDefaultCommand(CTREAutoUtils.drivetrain.commandDrive(controller.getHID()));
        //controller.a().whileTrue(arm.commandMotionMagicLoc(Rotation2d.fromDegrees(20)));
        scontroller.x().whileTrue(pushyThing.Push(1, -1));
        scontroller.x().whileFalse(pushyThing.Stop());

    }


    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand(String trajname) {return drivetrain.orderTrajectory(trajname);}
}