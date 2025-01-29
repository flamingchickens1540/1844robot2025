package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.LimelightHelpers;

public class vison extends SubsystemBase {
    public vison() {
        setPoseOffset(Constants.vison.cam1Pose, Constants.vison.cam1Name);
            }

    public void setPoseOffset(Pose3d poseOffsetMeters, String camName) {
        LimelightHelpers.setCameraPose_RobotSpace(camName,
                poseOffsetMeters.getX(),
                poseOffsetMeters.getY(),
                poseOffsetMeters.getZ(),
                Math.toDegrees(poseOffsetMeters.getRotation().getX()),
                Math.toDegrees(poseOffsetMeters.getRotation().getY()),
                Math.toDegrees(poseOffsetMeters.getRotation().getZ()));
    }
}
