package seedu.address.commons.core;

import java.awt.Point;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Serializable class that contains the GUI settings.
 * Guarantees: immutable.
 */
public class GuiSettings implements Serializable {

    private static final double DEFAULT_HEIGHT = 600;
    private static final double DEFAULT_WIDTH = 740;
    private static final double DEFAULT_SPLIT_RATIO = 0.5;

    private final double windowWidth;
    private final double windowHeight;
    private final Point windowCoordinates;
    private final double splitViewRatio;

    /**
     * Constructs a {@code GuiSettings} with the default height, width and position.
     */
    public GuiSettings() {
        windowWidth = DEFAULT_WIDTH;
        windowHeight = DEFAULT_HEIGHT;
        windowCoordinates = null; // null represent no coordinates
        splitViewRatio = DEFAULT_SPLIT_RATIO;
    }

    /**
     * Constructs a {@code GuiSettings} with the specified height, width and position.
     */
    public GuiSettings(double windowWidth, double windowHeight, int xPosition,
                       int yPosition, double splitViewRatio) {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        windowCoordinates = new Point(xPosition, yPosition);
        this.splitViewRatio = splitViewRatio;
    }

    public double getWindowWidth() {
        return windowWidth;
    }

    public double getWindowHeight() {
        return windowHeight;
    }

    public Point getWindowCoordinates() {
        return windowCoordinates != null ? new Point(windowCoordinates) : null;
    }

    public double getSplitViewRatio() {
        return splitViewRatio;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof GuiSettings)) { //this handles null as well.
            return false;
        }

        GuiSettings o = (GuiSettings) other;

        return windowWidth == o.windowWidth
                && windowHeight == o.windowHeight
                && splitViewRatio == o.splitViewRatio
                && Objects.equals(windowCoordinates, o.windowCoordinates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(windowWidth, windowHeight, splitViewRatio, windowCoordinates);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Width : " + windowWidth + ", ");
        sb.append("Height : " + windowHeight + "\n");
        sb.append("Position : " + windowCoordinates);
        sb.append("Split Ratio : " + splitViewRatio);
        return sb.toString();
    }
}
