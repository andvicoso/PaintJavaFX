package paint.model.tools;

import java.util.Optional;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.StageStyle;
import paint.model.FontSizeHolder;

/**
 *
 * @author Anderson
 */
public class TextTool extends ColloredTool {

    private final FontSizeHolder sizeHolder;

    public TextTool(ColorPicker colorPicker, FontSizeHolder sizeHolder) {
        super(colorPicker);
        this.sizeHolder = sizeHolder;
    }

    @Override
    public void onMousePressed(GraphicsContext gc, MouseEvent e) {
        super.onMousePressed(gc, e);
    }

    @Override
    public void onMouseReleased(GraphicsContext gc, MouseEvent e) {
        double size = sizeHolder.getFontSize();

        TextInputDialog dialog = new TextInputDialog("Texto");
        dialog.initStyle(StageStyle.UNDECORATED);
        dialog.setHeaderText(null);
        dialog.setGraphic(null);
        dialog.setX(e.getScreenX());
        dialog.setY(e.getScreenY());

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(text -> {
            gc.setFont(Font.font(size));
            gc.setFill(colorPicker.getValue());
            gc.fillText(text, e.getX(), e.getY());
        });

    }

    @Override
    public void onMouseDragged(GraphicsContext gc, MouseEvent e) {
    }

}
