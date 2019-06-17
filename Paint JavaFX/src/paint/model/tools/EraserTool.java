package paint.model.tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import paint.model.PointerSizeHolder;

/**
 *
 * @author Anderson
 */
public class EraserTool implements CanvasTool {

    private final PointerSizeHolder sizeHolder;

    public EraserTool(PointerSizeHolder sizeHolder) {
        this.sizeHolder = sizeHolder;
    }

    @Override
    public void onMousePressed(GraphicsContext gc, MouseEvent e) {
    }

    @Override
    public void onMouseReleased(GraphicsContext gc, MouseEvent e) {
    }

    @Override
    public void onMouseDragged(GraphicsContext gc, MouseEvent e) {
        double x = e.getX();
        double y = e.getY();
        double size = sizeHolder.getPointerSize();

        gc.setStroke(Color.WHITE);
        gc.setFill(Color.WHITE);
        gc.fillRect(x, y, size, size);
    }

}
