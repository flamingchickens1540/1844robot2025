package frc.robot.subsystems;

import choreo.auto.AutoFactory;
import choreo.trajectory.SwerveSample;
import com.pathplanner.lib.config.RobotConfig;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Optional;

import static frc.robot.generated.TunerConstants.*;

public class CTREAutoUtils {

    AutoFactory autoFactory;
    public static CommandSwerveDrivetrain drivetrain = new CommandSwerveDrivetrain(DrivetrainConstants, FrontLeft, FrontRight, BackLeft, BackRight);

    public CTREAutoUtils() {
    for(int i = 0; (i<=1); i++){

    }

    boolean isOnRedSide = false;
    Optional<DriverStation.Alliance> alliance = DriverStation.getAlliance();

    if(alliance.isPresent()) {
        isOnRedSide = alliance.orElseThrow() == DriverStation.Alliance.Red;
    } else{
            System.out.println("we are dead!!!");
        }

    autoFactory = new AutoFactory(
            () -> drivetrain.getState().Pose,
            (Pose2d pose2d) -> drivetrain.resetPose(pose2d),
            (SwerveSample sample) -> drivetrain.setVelocityAndRotationalRate(sample.vx/3.28084, sample.vy/3.28084, sample.omega/3.28084,true),
            isOnRedSide,
            drivetrain

    );

}

    public Command orderTrajectory(String trajname){
        return autoFactory.trajectoryCmd(trajname);
    }
    public void test() throws IOException, ParseException {

        final RobotConfig config = RobotConfig.fromGUISettings();
//        new RobotConfig(
//                MASS_KG,
//                TunerConstants.ROBOT_MOI_KGM2,
//                new ModuleConfig(
//                        TunerConstants.FrontLeft.WheelRadius,
//                        4.5,
//                        1.1,
//                        DCMotor.getKrakenX60Foc(4),
//                        TunerConstants.FrontLeft.DriveMotorGearRatio,
//                        TunerConstants.FrontLeft.SlipCurrent,
//                        1),
//                drivetrain.getModuleTranslations());

//        AutoBuilder.configure(
//                ()->drivetrain.getState().Pose,
//                (pose2d)->drivetrain.resetPose(pose2d),
//                ()->new ChassisSpeeds(),
//                (speeds, feedforwards) -> {},
//                new PPHolonomicDriveController( // PPHolonomicController is the built in path following controller for holonomic drive trains
//                        new PIDConstants(5.0, 0.0, 0.0), // Translation PID constants
//                        new PIDConstants(5.0, 0.0, 0.0) // Rotation PID constants
//                ),
//                config,
//                ()->isOnRedSide,
//                drivetrain
//        );

    }
    public void waterSlide(){

    }
}