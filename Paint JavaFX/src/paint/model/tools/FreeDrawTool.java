package paint.model.tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseEvent;
import paint.model.PointerSizeHolder;

/**
 *
 * @author Anderson
 */
public class FreeDrawTool extends ColloredTool {

    private final PointerSizeHolder sizeHolder;
    private double x;
    private double y;

    public FreeDrawTool(ColorPicker colorPicker, PointerSizeHolder sizeHolder) {
        super(colorPicker);
        this.sizeHolder = sizeHolder;
    }

    @Override
    public void onMousePressed(GraphicsContext gc, MouseEvent e) {
        super.onMousePressed(gc, e);
        x = e.getX();
        y = e.getY();
        gc.moveTo(x, y);
        gc.setLineWidth(sizeHolder.getPointerSize());
    }

    @Override
    public void onMouseReleased(GraphicsContext gc, MouseEvent e) {
    }

    @Override
    public void onMouseDragged(GraphicsContext gc, MouseEvent e) {
        gc.lineTo(e.getX(), e.getY());
        gc.stroke();
        gc.closePath();
        gc.beginPath();
        gc.moveTo(e.getX(), e.getY());
    }
}
