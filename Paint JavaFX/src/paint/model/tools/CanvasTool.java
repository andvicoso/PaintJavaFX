package paint.model.tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Anderson
 */
public interface CanvasTool {

    void onMousePressed(GraphicsContext gc, MouseEvent e);

    void onMouseReleased(GraphicsContext gc, MouseEvent e);

    void onMouseDragged(GraphicsContext gc, MouseEvent e);
}
