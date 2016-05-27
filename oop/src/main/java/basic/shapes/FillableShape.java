package basic.shapes;

import basic.shapes.properties.Color;

/**
 * Фигура, имеющая внутреннее замкнутое пространство
 */
public abstract class FillableShape extends AbstractShape {

    /**
     * Цвет заливки
     */
    private Color color = Color.DEFAULT;

    /**
     * @param color цвет заливки
     */
    public void setFillColor(Color color) {
        this.color = color;
    }

    /**
     * @return цвет заливки
     */
    public Color getFillColor() {
        return color;
    }

    /**
     * @return площадь
     */
    public abstract double area();

    @Override
    public String toString() {
        return String.format("%s with %s fill color",
                super.toString(), getFillColor());
    }

}
