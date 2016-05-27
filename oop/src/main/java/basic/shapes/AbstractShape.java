package basic.shapes;

import basic.shapes.interfaces.Shape;
import basic.shapes.properties.LineProperty;

/**
 * Абстрактная геометрическая фигура с базовыми атрибутами
 */
public abstract class AbstractShape implements Shape {

    private final LineProperty lineProperty = new LineProperty();

    public LineProperty getLineProperty() {
        return lineProperty;
    }

    @Override
    public void draw() {
        System.out.println(toString());
    }

    @Override
    public String toString() {
        return "Drawing a " + getName() +
                " with properties (" + getLineProperty() + ")";
    }

}
