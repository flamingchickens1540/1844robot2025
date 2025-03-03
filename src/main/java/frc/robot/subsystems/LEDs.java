package frc.robot.subsystems;

import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.LEDPattern;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.MetersPerSecond;

public class LEDs extends SubsystemBase {
    AddressableLED lEDs = new AddressableLED(0);
    AddressableLEDBuffer buffer = new AddressableLEDBuffer(19);
    LEDPattern purple = LEDPattern.solid(new Color("#5f03a1"));
    public LEDs(){
        lEDs.setLength(buffer.getLength());
        lEDs.setData(buffer);

        purple.applyTo(buffer);
        lEDs.setData(buffer);
        lEDs.start();

    }
    private void setToGreen(){
        LEDPattern green = LEDPattern.solid(Color.kGreen);
        green.applyTo(buffer);
        lEDs.setData(buffer);

    }
    private void setToRed(){
        LEDPattern red = LEDPattern.solid(Color.kRed);
        red.applyTo(buffer);
        lEDs.setData(buffer);
    }
    private void setToRainbow(){
        LEDPattern m_rainbow = LEDPattern.rainbow(255, 256);
        Distance kLedSpacing = Meters.of(1 / 12.0);
        LEDPattern m_scrollingRainbow =
                m_rainbow.scrollAtAbsoluteSpeed(MetersPerSecond.of(1), kLedSpacing);
        m_scrollingRainbow.applyTo(buffer);
        lEDs.setData(buffer);
    }
    private void setToPurple(){
        purple.applyTo(buffer);
        lEDs.setData(buffer);
    }
    public Command commandSetToRed(){
        return Commands.run(this::setToRed);
    }
    public Command commandSetToGreen(){
        return Commands.run(this::setToGreen);
    }
    public Command commandSetToRainbow(){
        return Commands.run(this::setToRainbow);
    }
    public Command commandSetToPurple(){
        return Commands.run(this::setToPurple);
    }

}
