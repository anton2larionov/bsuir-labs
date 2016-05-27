package basic.shapes.properties;

/**
 * Свойства линии рисования
 */
public class LineProperty {
    private Color lineColor;
    private LineType lineType;

    public LineProperty() {
        this.lineColor = Color.DEFAULT;
        this.lineType = LineType.DEFAULT;
    }

    public LineProperty(final LineType lineType, final Color lineColor) {
        this.lineColor = lineColor;
        this.lineType = lineType;
    }

    public LineType getLineType() {
        return lineType;
    }

    public void setLineType(LineType lineType) {
        this.lineType = lineType;
    }

    public Color getLineColor() {
        return lineColor;
    }

    public void setLineColor(Color lineColor) {
        this.lineColor = lineColor;
    }

    @Override
    public String toString() {
        return lineType + " line type, "
                + lineColor + " line color";
    }
}
