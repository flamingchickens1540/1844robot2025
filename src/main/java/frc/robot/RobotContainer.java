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



    private void configureBindings() {

        controller.rightBumper().whileTrue(CTREAutoUtils.drivetrain.Zero());//Zero out the drivetrain
        scontroller.axisMagnitudeGreaterThan(0, 0.1).whileTrue(arm.commandMoveSpeed(scontroller));

        /*scontroller.back().and(scontroller.y()).whileTrue(arm.goToL2());
        scontroller.back().and(scontroller.a()).whileTrue(arm.goToL3());
        scontroller.back().and(scontroller.b()).whileTrue(arm.commandToSetpoint(Arm.ArmState.L1_CORAL));
        scontroller.back().and(scontroller.x()).whileTrue(arm.commandToSetpoint(Arm.ArmState.GROUND_ALGAE_INTAKE));
        scontroller.back().and(scontroller.leftBumper()).whileTrue(arm.commandToSetpoint(Arm.ArmState.GROUND_CORAL_INTAKE));
        scontroller.back().and(scontroller.leftTrigger()).whileTrue(arm.commandToSetpoint(Arm.ArmState.HUMAN_PLAYER_INTAKE));
*/
        controller.start().whileTrue(leDs.commandSetToGreen());//Leds
        controller.back().whileTrue(leDs.commandSetToRed());//Leds
        controller.y().whileTrue(leDs.commandSetToPurple());//Leds
        controller.b().whileTrue(leDs.commandSetToRainbow());//Leds


        scontroller.a().whileTrue(endEffectorThing.removeAlgae(1, 1));//remove algea
        scontroller.a().whileFalse(endEffectorThing.Stop());//my bad code remove algea

        scontroller.leftTrigger().whileTrue(shooter.spinFullUntil(3)//shoot
                .andThen(leDs.commandSetToGreen())//shoot
                        .andThen(shooter.spinFullUntil(1)));//shoot
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
        controller.leftBumper().whileTrue(CTREAutoUtils.drivetrain.setVelocityAndRotationalRate(0,0,3.14/2));
    }


    /**
     * Use this to pass the autonomous command to the main {@link} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        Command auto =
                drivetrain.orderTrajectory("Leo Auto 1")
                        .andThen(arm.commandToSetpoint(Arm.ArmState.L1_CORAL))
                                .andThen(endEffectorThing.outputCoral(false,1,3))
                                        .withTimeout(2)
                                                .andThen(drivetrain.orderTrajectory("get out of the way"))
        ;
        return drivetrain.orderTrajectory("Leo auto hates");
    }

}