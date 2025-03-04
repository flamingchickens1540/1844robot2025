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

        controller.rightBumper().whileTrue(CTREAutoUtils.drivetrain.Zero());//Zero out the drivetrain

        arm.setDefaultCommand(arm.commandMoveSpeed(scontroller));//manual arm control

        controller.start().whileTrue(leDs.commandSetToGreen());//Leds
        controller.back().whileTrue(leDs.commandSetToRed());//Leds
        controller.y().whileTrue(leDs.commandSetToPurple());//Leds
        controller.b().whileTrue(leDs.commandSetToRainbow());//Leds

        scontroller.y().whileTrue(arm.commandMotionMagicLoc(Rotation2d.fromDegrees(0)));//arm to zero

        scontroller.a().whileTrue(endEffectorThing.removeAlgae(1, 1));//remove algea
        scontroller.a().whileFalse(endEffectorThing.Stop());//my bad code remove algea

        scontroller.leftTrigger().whileTrue(shooter.spinFullUntil(3)//shoot
                .andThen(leDs.commandSetToGreen())//shoot
                        .alongWith(shooter.spinFullUntil(1)).withTimeout(1));//shoot
        scontroller.leftTrigger().whileFalse(pushyThing.Stop().andThen(leDs.commandSetToRed()));//my bad code shoot

        scontroller.leftBumper().whileTrue(shooter.intake(3).alongWith(pushyThing.Push(3, 0.1)));//a thing
        scontroller.leftBumper().whileFalse(pushyThing.Stop().andThen(shooter.Stop()));//my bad code a thing

        scontroller.rightBumper().whileTrue(endEffectorThing.intakeCoral(true, 1, 3));//intake coral
        scontroller.rightBumper().whileFalse(endEffectorThing.Stop());//my bad code intake coral

        scontroller.rightTrigger().whileTrue(endEffectorThing.outputCoral(false, 1, 3));//score coral
        scontroller.rightTrigger().whileFalse(endEffectorThing.Stop());//my bad code score coral

        CTREAutoUtils.drivetrain.setDefaultCommand(CTREAutoUtils.drivetrain.commandDrive(controller.getHID()));//move the drivetrain
        scontroller.x().whileTrue(pushyThing.Push(1, -1));//a thing
        scontroller.x().whileFalse(pushyThing.Stop());//my bad code a thing

    }


    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        Command auto = Commands.run(
                ()->drivetrain.orderTrajectory(AutoChoices.getLeoAuto())
                        .andThen(arm.commandMotionMagicLoc(Rotation2d.fromDegrees(20)))
                                .andThen(endEffectorThing.outputCoral(false,1,3))
                                        .withTimeout(2)
                                                .andThen(drivetrain.orderTrajectory("get out of the way"))
        );
        return auto;
    }
}