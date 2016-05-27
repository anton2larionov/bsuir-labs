package basic.shapes.interfaces;

/**
 * Обобщенный строитель геометрической фигуры
 */
public interface ShapeBuilder<T extends Shape> {
    T buildShape();
}
