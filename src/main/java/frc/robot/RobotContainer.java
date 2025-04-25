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
 * "declarative" paradigm, very little robot logic should actually be handled in the
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
    // The robot's subsystems and commands are defined here...

    //Replace with CommandPS4Controller or CommandJoystick if needed
    public final endEffectorThing endEffectorThing = new endEffectorThing();
    public final CommandXboxController controller = new CommandXboxController(0);
    public final pushyThing pushyThing = new pushyThing();
    public final shooter shooter = new shooter();
    public final Arm arm = new Arm();
    public final CommandXboxController scontroller = new CommandXboxController(1);
    public final CommandXboxController controllerBuddy = new CommandXboxController(2);
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



    private void configureBindings() {

        controller.rightTrigger().toggleOnTrue(CTREAutoUtils.drivetrain.commandBuddyDrive(controllerBuddy.getHID()));
        controller.rightTrigger().toggleOnTrue(null).and(controllerBuddy.rightBumper()).whileTrue(CTREAutoUtils.drivetrain.Zero());
        controller.leftTrigger().toggleOnTrue(null).and(controllerBuddy.axisMagnitudeGreaterThan(XboxController.Axis.kLeftY.value, 0.1)).whileTrue(arm.commandMoveSpeedBuddy(scontroller));
        //controller.leftTrigger().toggleOnTrue(null).and(controllerBuddy.povDown()).whileTrue(arm.commandToSetpoint(Rotation2d.fromDegrees(116.5)));
        //controller.leftTrigger().toggleOnTrue(null).and(controllerBuddy.povLeft()).whileTrue(arm.commandToSetpoint(Rotation2d.fromDegrees(153.2)));
        //controller.leftTrigger().toggleOnTrue(null).and(controllerBuddy.povRight()).whileTrue(arm.commandToSetpoint(Rotation2d.fromDegrees(125.8)));
        controller.leftTrigger().toggleOnTrue(null).and(controllerBuddy.a()).whileTrue(endEffectorThing.removeAlgae(1, 1));
        controller.leftTrigger().toggleOnTrue(null).and(controllerBuddy.a()).whileFalse(endEffectorThing.Stop());
        controller.leftTrigger().toggleOnTrue(null).and(controllerBuddy.rightStick()).whileTrue(endEffectorThing.stay());
        controller.leftTrigger().toggleOnTrue(null).and(controllerBuddy.leftTrigger()).whileTrue(Commands.parallel(Commands.sequence(shooter.intake(1), shooter.spinFullUntilBuddy()), pushyThing.Push(0, 0.1), Commands.waitSeconds(4).andThen(leDs.commandSetToGreen())));
        controller.leftTrigger().toggleOnTrue(null).and(controllerBuddy.leftTrigger()).whileFalse(leDs.commandSetToRed());
        controller.leftTrigger().toggleOnTrue(null).and(controllerBuddy.leftBumper()).whileTrue(shooter.intake(3).alongWith(pushyThing.Push(3, 0.1)));
        //controller.leftTrigger().toggleOnTrue(null).and(controllerBuddy.rightBumper()).whileTrue(endEffectorThing.intakeCoral(true, 1, 3));
        //controller.leftTrigger().toggleOnTrue(null).and(controllerBuddy.rightBumper()).whileFalse(endEffectorThing.Stop());
        //controller.leftTrigger().toggleOnTrue(null).and(controllerBuddy.rightTrigger()).whileTrue(endEffectorThing.outputCoral(false, 1, 3));
        //controller.leftTrigger().toggleOnTrue(null).and(controllerBuddy.rightTrigger()).whileFalse(endEffectorThing.Stop());
        controller.leftTrigger().toggleOnTrue(null).and(controllerBuddy.x()).whileTrue(pushyThing.Push(1, -1));












        controller.rightBumper().whileTrue(CTREAutoUtils.drivetrain.Zero());//Zero out the drivetrain
        CTREAutoUtils.drivetrain.setDefaultCommand(CTREAutoUtils.drivetrain.commandDrive(controller.getHID()));

        controller.start().whileTrue(leDs.commandSetToGreen());//Leds
        controller.back().whileTrue(leDs.commandSetToRed());//Leds
        controller.y().whileTrue(leDs.commandSetToPurple());//Leds
        controller.b().whileTrue(leDs.commandSetToRainbow());//Leds

        scontroller.axisMagnitudeGreaterThan(XboxController.Axis.kLeftY.value, 0.1).whileTrue(arm.commandMoveSpeed(scontroller));


    scontroller.povDown().whileTrue(arm.commandToSetpoint(Rotation2d.fromDegrees(116.5)));//algae shooting
    scontroller.povLeft().whileTrue(arm.commandToSetpoint(Rotation2d.fromDegrees(153.2)));//algae intake
    scontroller.povRight().whileTrue(arm.commandToSetpoint(Rotation2d.fromDegrees(125.8)));//coral intake




        scontroller.a().whileTrue(endEffectorThing.removeAlgae(1, 1));//remove algea
        scontroller.a().whileFalse(endEffectorThing.Stop());//my bad code remove algea
        scontroller.rightStick().whileTrue(endEffectorThing.stay());

        scontroller.leftTrigger().whileTrue(

                Commands.parallel(
                        Commands.sequence(shooter.intake(1), shooter.spinFullUntil()),
                         pushyThing.Push(0, 0.1), 
                Commands.waitSeconds(4).andThen(leDs.commandSetToGreen())));

        scontroller.leftTrigger().whileFalse(leDs.commandSetToRed());//my bad code shoot

        scontroller.leftBumper().whileTrue(shooter.intake(3).alongWith(pushyThing.Push(3, 0.1)));//a thing
        //scontroller.leftBumper().whileFalse(pushyThing.Stop().andThen(shooter.Stop()));//my bad code a thing

        scontroller.rightBumper().whileTrue(endEffectorThing.intakeCoral(true, 1, 3));//intake coral
        scontroller.rightBumper().whileFalse(endEffectorThing.Stop());//my bad code intake coral

        scontroller.rightTrigger().whileTrue(endEffectorThing.outputCoral(false, 1, 3));//score coral
        scontroller.rightTrigger().whileFalse(endEffectorThing.Stop());//my bad code score coral

        //move the drivetrain
        scontroller.x().whileTrue(pushyThing.Push(1, -1));//a thing
        controller.leftBumper().whileTrue(CTREAutoUtils.drivetrain.setVelocityAndRotationalRate(0,0,3.14/2));
    }


    /**
     * Use this to pass the autonomous command to the main {@link} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        Command auto =
                drivetrain.orderTrajectory("Leo Auto 2")
                        //.andThen(arm.commandToSetpoint(Arm.ArmState.L2_CORAL))
                                .andThen(endEffectorThing.intakeCoral(false,1,3))
                                        .withTimeout(2)
                                                .andThen(drivetrain.orderTrajectory("get out of the way"))
        ;
        return CTREAutoUtils.drivetrain.moveABitForward(1);
    }

}