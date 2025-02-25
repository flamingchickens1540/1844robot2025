// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import choreo.Choreo;
import com.ctre.phoenix6.swerve.SwerveDrivetrain;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.endEffectorThing;
import frc.robot.subsystems.pushyThing;
import frc.robot.subsystems.shooter;

import java.nio.file.Path;


/**
 * The methods in this class are called automatically corresponding to each mode, as described in
 * the TimedRobot documentation. If you change the name of this class or the package after creating
 * this project, you must also update the Main.java file in the project.
 */
class AutoChoices {

    private static SendableChooser<String> example;
    private static SendableChooser<String> start;
    private static SendableChooser<String> next1;
    private static SendableChooser<String> end;

    public AutoChoices() {
        // Initialize the SendableChooser
        example = new SendableChooser<>();
        start = new SendableChooser<>();
        next1 = new SendableChooser<>();
        // Set default option and add additional options
        example.setDefaultOption("nothing", "Default");
        example.addOption("example", "exampleAuto");
        start.setDefaultOption("Nothing","nothing");
        start.addOption("Buddy Auto","buddy_auto");
        next1.setDefaultOption("Nothing","nothing");
        next1.setDefaultOption("Score Reef","score_reef");


        // Add the chooser to the SmartDashboard
        SmartDashboard.putData("Auto Choices", example);
    }

    // Method to get the selected option

    public static String example()  {
        return example.getSelected();
    }

}
public class Robot extends TimedRobot
{
    private Command autonomousCommand;
    private final RobotContainer robotContainer;



    /**
     * This method is run when the robot is first started up and should be used for any
     * initialization code.
     */
    public Robot()
    {
        // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
        // autonomous chooser on the dashboard.

        robotContainer = new RobotContainer();
        System.out.println("Instantiating robot");
        Pose2d robotPosFromCam = new Pose2d();

    }
    
    
    /**
     * This method is called every 20 ms, no matter the mode. Use this for items like diagnostics
     * that you want ran during disabled, autonomous, teleoperated and test.
     *
     * <p>This runs after the mode specific periodic methods, but before LiveWindow and
     * SmartDashboard integrated updating.
     */
    @Override
    public void robotPeriodic()
    {
        //TunerConstants.DriveTrain.setDefaultCommand(TunerConstants.DriveTrain.commandDrive(controller.getHID()));
        Commands.print("Doing anything at all");
//        if (controller.a().getAsBoolean()){
//            System.out.println("btton works!(yay)");
//        }
        CommandScheduler.getInstance().run();
        //System.out.println(LimelightHelpers.getBotPose2d(Constants.vison.firstLimelight));
 }
    
    
    /** This method is called once each time the robot enters Disabled mode. */
    @Override
    public void disabledInit() {}
    
    
    @Override
    public void disabledPeriodic() {}
    
    
    /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
    @Override
    public void autonomousInit()
    {

        autonomousCommand = robotContainer.getAutonomousCommand("Leo Auto 1.traj",time);

        // schedule the autonomous command (example)
        if (autonomousCommand != null)
        {
            autonomousCommand.schedule();
        }
    }
    
    int time;
    /** This method is called periodically during autonomous. */
    @Override
    public void autonomousPeriodic() {
//        time ++;
//        autonomousCommand = robotContainer.getAutonomousCommand("Leo Auto 1",time);
//        autonomousCommand.schedule();
    }
    
    
    @Override
    public void teleopInit()
    {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null)
        {
            autonomousCommand.cancel();
        }
//        robotContainer.controller.x().whileTrue(robotContainer.arm.commandMotionMagicLoc(20)));
//        robotContainer.controller.b().whileTrue(robotContainer.arm.commandMotionMagicLoc(Rotation2d.fromDegrees(SmartDashboard.getNumber("b",0))));
//        robotContainer.controller.y().whileTrue(robotContainer.arm.commandMotionMagicLoc(Rotation2d.fromDegrees(SmartDashboard.getNumber("y",0))));


    }
    
    /** This method is called periodically during operator control. */
    @Override
    public void teleopPeriodic() {
        //Commands.runOnce(()->{
//            System.out.println("Name: " + TunerConstants.DriveTrain.getName());
       // });
        //System.out.println("I work");
    }
    
    
    @Override
    public void testInit()
    {
        // Cancels all running commands at the start of test mode.
        CommandScheduler.getInstance().cancelAll();
    }
    
    
    /** This method is called periodically during test mode. */
    @Override
    public void testPeriodic() {}
    
    
    /** This method is called once when the robot is first started up. */
    @Override
    public void simulationInit() {}
    
    
    /** This method is called periodically whilst in simulation. */
    @Override
    public void simulationPeriodic() {}
}
