package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants;

public class Arm extends SubsystemBase {

    public enum ArmState {
        STOW(() -> SmartDashboard.getNumber("Arm/Setpoint/Stow", 16)),
        GROUND_CORAL_INTAKE(() -> SmartDashboard.getNumber("Arm/Setpoint/GroundCoralIntake", 38.3)),
        GROUND_ALGAE_INTAKE(() -> SmartDashboard.getNumber("Arm/Setpoint/GroundAlgaeIntake", 29.4)),
        L1_CORAL(() -> SmartDashboard.getNumber("Arm/Setpoint/L1Coral", 25.2)),
        HUMAN_PLAYER_INTAKE(() -> SmartDashboard.getNumber("Arm/Setpoint/HumanPlayerIntake", -8.8));

        /*
         * Algae ground : 29.4
         * Coral Ground : 38.3
         * Coral L1: 25.2
         * Coral L2: 13.5
         * Coral L3: -1.14
         * Algae shooting : 31.5
         * Station intake: -8.8
         **/

        public final DoubleSupplier positionDegrees;

        ArmState(DoubleSupplier positionDegrees) {
            this.positionDegrees = positionDegrees;
        }

        public Rotation2d position() {
            return Rotation2d.fromDegrees(positionDegrees.getAsDouble());
        }
    }



    private final TalonFX armMotor = new TalonFX(Constants.Arm.MOTOR_ID2);
    private final TalonFX armMotor1 = new TalonFX(Constants.Arm.MOTOR_ID);
    private final TalonFXConfiguration config = new TalonFXConfiguration();
    private final MotionMagicVoltage positionCtrlReq;
    private Rotation2d setpoint = Rotation2d.kZero;
    private final DutyCycleEncoder encoder = new DutyCycleEncoder(0);
    //public final StatusSignal<ForwardLimitValue> limit;

    public Arm() {


        armMotor1.setControl(new Follower(Constants.Arm.MOTOR_ID, true));
        config.Feedback.SensorToMechanismRatio = Constants.Arm.GEAR_RATIO;

        config.SoftwareLimitSwitch.ForwardSoftLimitEnable = false;
        config.SoftwareLimitSwitch.ReverseSoftLimitEnable = false;
        config.SoftwareLimitSwitch.ForwardSoftLimitThreshold = 23.3;
        config.SoftwareLimitSwitch.ReverseSoftLimitThreshold = -24.6;
        config.CurrentLimits.StatorCurrentLimitEnable = true;
        config.CurrentLimits.StatorCurrentLimit = 80;
        config.MotorOutput.NeutralMode = NeutralModeValue.Brake;
        //need to invert
        // in init function


        SmartDashboard.putNumber("p",4.8);
// set slot 0 gains
        var slot0Configs = config.Slot0;
        slot0Configs.kS = 0.25; // Add 0.25 V output to overcome static friction
        slot0Configs.kV = 0.12; // A velocity target of 1 rps results in 0.12 V output
        slot0Configs.kA = 0.01; // An acceleration of 1 rps/s requires 0.01 V output
        slot0Configs.kP = 65; // A position error of 2.5 rotations results in 12 V output
        slot0Configs.kI = 7;// no output for integrated error
        slot0Configs.kD = 0.1; //velocity error of 1 rps results in 0.1 V output

// set Motion Magic settings
        var motionMagicConfigs = config.MotionMagic;
        motionMagicConfigs.MotionMagicCruiseVelocity = 5; // Target cruise velocity of 80 rps
        motionMagicConfigs.MotionMagicAcceleration = 50; // Target acceleration of 160 rps/s (0.5 seconds)
        motionMagicConfigs.MotionMagicJerk = 1600; // Target jerk of 1600 rps/s/s (0.1 seconds)

        config.MotorOutput.NeutralMode = NeutralModeValue.Brake;

        armMotor.getConfigurator().apply(config);
        armMotor.setPosition(encoder.get()/3);
        armMotor1.setPosition(encoder.get()/3);
        armMotor.setNeutralMode(NeutralModeValue.Brake);
        armMotor1.setNeutralMode(NeutralModeValue.Brake);

        positionCtrlReq = new MotionMagicVoltage(0).withSlot(0);

        SmartDashboard.putNumber("Arm/Setpoint/Stow", 16);
        SmartDashboard.putNumber("Arm/Setpoint/GroundAlgaeIntake", 29.4);
        SmartDashboard.putNumber("Arm/Setpoint/L1Coral", 25.2);
        SmartDashboard.putNumber("Arm/Setpoint/GroundCoralIntake", 83.3);
        SmartDashboard.putNumber("Arm/Setpoint/HumanPlayerIntake", -8.8);
    }

    @Override
    public void periodic() {
        System.out.println(getPosition().getDegrees() + " " + setpoint.getDegrees());
    }
    public Rotation2d getPosition(){

        return Rotation2d.fromRotations(armMotor.getPosition().getValueAsDouble());

    }

    public Command commandMoveSpeed(CommandXboxController controller) {
        return Commands.run(

                () -> {
                    double value = -(controller.getLeftY()/3);
                    armMotor.setVoltage(value*12);
                    armMotor1.setVoltage(-value*12);
                    //System.out.println(value);
                }
                
        ).finallyDo(() -> {
            armMotor.setVoltage(0);
            armMotor1.setVoltage(0);
        });
    }
    public Command stopCommand(){
        return Commands.run(

                () -> {
                    double value = (0);
                    armMotor.setVoltage(value*12);
                    armMotor1.setVoltage(-value*12);
                    //System.out.println(value);
                }
                
        );
    }

    public void setSetpoint(Rotation2d motorPosition) {
        setpoint = motorPosition;
        // armMotor.setControl(positionCtrlReq.withPosition(motorPosition.getRotations()));
    }

    public Command commandToSetpoint(Rotation2d location) {

        return Commands.runOnce(
                ()->setSetpoint(location),
                this
    );
        //   .andThen(Commands.waitUntil(()-> (Math.abs(armMotor.getPosition().refresh().getValueAsDouble() - location.getRotations())<=0.001)));
    }

    public Command commandToSetpoint(ArmState armState){

        return commandToSetpoint(Rotation2d.fromDegrees(armState.positionDegrees.getAsDouble()));

    }

    public Command commandSetToZero(){
        //offSet = armMotor.getPosition().refresh().getValueAsDouble();
        return Commands.run(
                ()->armMotor.setPosition(0));
    }
    public Command goToL2(){
        return commandToSetpoint(Rotation2d.fromDegrees(13.5));
    }
    public Command goToL3(){
        return commandToSetpoint(Rotation2d.fromDegrees(-1.14));
    }
    
}