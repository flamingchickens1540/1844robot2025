package frc.robot.subsystems;

import choreo.trajectory.SwerveSample;
import choreo.trajectory.Trajectory;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;

import java.util.Optional;

public class TrajectoryUtils {
    public static double[] getVelocitiesAtTime(Trajectory<SwerveSample> trajectory, double timestamp) {
        Optional<SwerveSample> sampleOpt = getSampleAtTime(trajectory, timestamp);
        if (sampleOpt.isPresent()) {
            SwerveSample sample = sampleOpt.get();
            double vx = sample.vx;
            double vy = sample.vy;
            double omega = sample.omega;
            return new double[]{vx,vy,omega};
        } else {
            return new double[]{0,0,0};
        }
    }

    private static Optional<SwerveSample> getSampleAtTime(Trajectory<SwerveSample> trajectory, double timestamp) {
        DriverStation.Alliance alliance = DriverStation.getAlliance().get();
        var mirrorForRedAlliance = false;
        switch (alliance){
            case Red:
                mirrorForRedAlliance = true;
            break;
            case Blue:
                 mirrorForRedAlliance = false;
        }
        return trajectory.sampleAt(timestamp, mirrorForRedAlliance);
    }

}