package basic.shapes.interfaces;

import basic.shapes.Polyline;

/**
 * Интерфейс объектов, которые могут предоставить полилинию
 */
public interface Polylineable extends Shape {
    Polyline getPolyline();
}
