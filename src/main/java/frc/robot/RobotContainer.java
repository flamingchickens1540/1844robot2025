// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.*;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;

import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;

public class RobotContainer {
    private double MaxSpeed = TunerConstants.kSpeedAt12Volts.in(MetersPerSecond); // kSpeedAt12Volts desired top speed
    private double MaxAngularRate = RotationsPerSecond.of(0.75).in(RadiansPerSecond); // 3/4 of a rotation per second max angular velocity

    /* Setting up bindings for necessary control of the swerve drive platform */
    private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
            .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors
    private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
    private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();

    private final Telemetry logger = new Telemetry(MaxSpeed);

    private final CommandXboxController joystick = new CommandXboxController(0);

    public final CommandSwerveDrivetrain drivetrain = TunerConstants.createDrivetrain();

    public RobotContainer() {
        configureBindings();
        drivetrain = new CommandSwerveDrivetrain(DrivetrainConstants, FrontLeft,
            FrontRight, BackLeft, BackRight);;
            //this isnt doing anything??
            //what exactly is end effector?
            controller.a().whileTrue(endEffectorThing.runFrontMotor());

            controller.leftBumper().whileTrue(arm.commandToSetpoint(Rotation2d.fromDegrees(0)));
//        drivetrain.setDefaultCommand(drivetrain.commandDrive(controller.getHID()));

            //trying something below - did not work
            //what is use of intakeCoral function??
            controller.a().whileTrue(endEffectorThing.intakeCoral(false, 0.5, 5));
            //this is to enable drivetrain:

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
       controller. y().whileTrue(arm.commandToSetpoint(Rotation2d.fromDegrees(SmartDashboard.getNumber("y",0))));
       controller.x().whileTrue(arm.commandToSetpoint(Rotation2d.fromDegrees(SmartDashboard.getNumber("b",0))));
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



        controller.leftTrigger().whileTrue(pushyThing.Push(-0.5));
    
        controller.rightTrigger().whileTrue(shooter.spinFull());
        //controller.a().whileTrue(Commands.print("I work"));




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
