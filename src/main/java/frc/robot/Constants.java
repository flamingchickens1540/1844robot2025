// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.geometry.Pose3d;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants
{
    public static final boolean isTuningMode = true;
    
    public static class OperatorConstants
    {
        public static final int DRIVER_CONTROLLER_PORT = 0;

    }
    public static class Shooter{
        public static final int MOTOR_ID = 0;
        public static final double GEAR_RATIO = 1;
    }
    public static class pushyThing{
        public static final int MOTOR_ID = 9;
    }
    public static class Arm{
        public static final int MOTOR_ID =21;
        public static final int MOTOR_ID2 = 51;
        public static final int SENSOR_ID = 0; //todo
        public static final double SENSOR_TO_PIVOT = 3/1; 
        public static final double MOTOR_TO_SENSOR = 3/1*3/1*4/1*3/1; 
        
    }
    public static class smallEndEffectorThing {
        public static final int MOTOR_ID = 0;
    }
    public static class largeEndEffectorThing {
        public static final int MOTOR_ID = 0;
    }
    public static class vison {
        public static String cam1Name = "limelight-cam1";
        public static Pose3d cam1Pose = new Pose3d();
    }
}
