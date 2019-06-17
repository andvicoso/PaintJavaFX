package paint.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import paint.model.FontSizeHolder;
import paint.model.PointerSizeHolder;
import paint.model.RecentFiles;
import paint.model.tools.BucketTool;
import paint.model.tools.CanvasTool;
import paint.model.tools.CircleTool;
import paint.model.tools.EraserTool;
import paint.model.tools.FreeDrawTool;
import paint.model.tools.LineTool;
import paint.model.tools.RectangleTool;
import paint.model.tools.TextTool;
import paint.utils.Utils;

public class MainFXMLController implements Initializable, PointerSizeHolder, FontSizeHolder {

    public static final int DEFAULT_WIDTH = 800;
    public static final int DEFAULT_HEIGHT = 600;

    @FXML
    private ColorPicker colorpicker;
    @FXML
    private Canvas canvas;
    @FXML
    private Canvas canvas2;
    @FXML
    private Label showSize;
    @FXML
    private Label showFontSize;
    @FXML
    private Menu mnuRecents;
    @FXML
    private Button btnRedo;
    @FXML
    private Button btnUndo;

    private final RecentFiles recent = new RecentFiles();

    // Initial brush size
    private double pointerSize = 10;
    private double fontSize = 12;

    // Control booleans about what button was pressed
    private CanvasTool selectedTool; // Handles for menu

    // Initializes
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Showing initial brush size
        showSize.setText("" + pointerSize);

        setMouseActionsOnCanvas();

        Platform.runLater(() -> {
            canvas.getScene().getWindow().setOnCloseRequest(e -> Platform.exit());
        });

        //set as default action
        freeDrawAction(null);
        //set default color "black"
        colorpicker.setValue(Color.BLACK);
        //load recent opened files
        mnuRecents.getParentMenu().setOnShowing(event -> {
            loadRecents();
        });
        //loadRecents(null);
    }

    private void setMouseActionsOnCanvas() {
        canvas.setOnMousePressed(e -> {
            toBufferCanvas();
            selectedTool.onMousePressed(canvas.getGraphicsContext2D(), e);
        });

        canvas.setOnMouseDragged(e -> {
            selectedTool.onMouseDragged(canvas.getGraphicsContext2D(), e);
        });

        canvas.setOnMouseReleased(e -> {
            selectedTool.onMouseReleased(canvas.getGraphicsContext2D(), e);
            btnUndo.setDisable(false);
        });

        canvas.getGraphicsContext2D().setFill(Color.WHITE);
        canvas.getGraphicsContext2D().fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    @Override
    public double getPointerSize() {
        return pointerSize;
    }

    @Override
    public double getFontSize() {
        return fontSize;
    }

    private void toBufferCanvas() {
        WritableImage image = Utils.getCanvasSnapshot(canvas);
        canvas2.getGraphicsContext2D().drawImage(image, 0, 0);
    }

    @FXML
    private void handleActionOpen(ActionEvent event) {
        FileChooser openFile = new FileChooser();
        openFile.setTitle("Open Image");

        File file = openFile.showOpenDialog(canvas.getScene().getWindow());
        if (file != null) {
            try {
                openImage(file);
                // Copy file path in a .txt
                recent.write(file.toString());
            } catch (IOException ex) {
                System.err.println("Error opening image!");
            }
        }
    }

    private void openImage(File file) throws FileNotFoundException {
        // Draw image
        InputStream io = new FileInputStream(file);
        Image img = new Image(io);
        canvas.getGraphicsContext2D().drawImage(img, 0, 0);
    }

    @FXML
    private void handleActionSave(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Saving Image");
        //TODO: Fix extension filters
        for (String format : new String[]{"png", "jpg", "jpeg", "bmp"}) {
            String ext = "*." + format;
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter(format.toUpperCase() + " (" + ext + ")", format));
        }

        fc.setSelectedExtensionFilter(fc.getExtensionFilters().get(0));

        File file = fc.showSaveDialog(canvas.getScene().getWindow());
        if (file != null) {
            try {
                String ext = fc.getSelectedExtensionFilter().getExtensions().get(0);
                WritableImage writableImage = Utils.getCanvasSnapshot(canvas);
                file = file.getAbsolutePath().contains(".") ? file : new File(file.getAbsolutePath() + "." + ext);
                boolean saved = ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null), ext, file);
                if (!saved) {
                    System.err.println("Error saving image!");
                }
            } catch (IOException ex) {
                System.err.println("Error saving image!");
            } catch (IllegalArgumentException il) {
                System.err.println("Parameters error!");
            }
        }
    }

    protected void loadRecents() {
        mnuRecents.getItems().clear();
        try {
            for (String file : recent.read()) {
                MenuItem mi = new MenuItem(file);
                mi.setOnAction(e -> {
                    try {
                        openImage(new File(file));
                    } catch (FileNotFoundException ex) {
                        System.err.println("File not found!");
                    }
                });
                mnuRecents.getItems().add(mi);
            }
        } catch (IOException ex) {
            Logger.getLogger(MainFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void changeCanvas() {
        WritableImage image = Utils.getCanvasSnapshot(canvas);
        WritableImage image2 = Utils.getCanvasSnapshot(canvas2);

        canvas.getGraphicsContext2D().drawImage(image2, 0, 0);
        canvas2.getGraphicsContext2D().drawImage(image, 0, 0);
    }

    @FXML
    private void handleActionUndo(ActionEvent event) {
        changeCanvas();

        btnRedo.setDisable(false);
        btnUndo.setDisable(true);
    }

    @FXML
    private void handleActionRedo(ActionEvent event) {
        changeCanvas();

        btnUndo.setDisable(false);
        btnRedo.setDisable(true);
    }

    @FXML
    private void freeDrawAction(ActionEvent e) {
        selectedTool = new FreeDrawTool(colorpicker, this);
    }

    @FXML
    private void deleteAction(ActionEvent e) {
        selectedTool = new EraserTool(this);
    }

    @FXML
    private void circleAction(ActionEvent e) {
        selectedTool = new CircleTool(colorpicker);
    }

    @FXML
    private void rectangleAction(ActionEvent e) {
        selectedTool = new RectangleTool(colorpicker);
    }

    @FXML
    private void lineAction(ActionEvent e) {
        selectedTool = new LineTool(colorpicker);
    }

    @FXML
    private void textAction(ActionEvent e) {
        selectedTool = new TextTool(colorpicker, this);
    }

    @FXML
    private void paintingAction(ActionEvent e) {
        selectedTool = new BucketTool(colorpicker);
    }

    // Brush size controls - increase or decrease in 10
    @FXML
    private void increaseAction(ActionEvent e) {
        pointerSize += 1;
        changeBrush();
    }

    @FXML
    private void decreaseAction(ActionEvent e) {
        pointerSize -= 1;
        changeBrush();
    }

    // Brush size controls - increase or decrease in 10
    @FXML
    private void increaseFontAction(ActionEvent e) {
        fontSize += 1;
        changeFont();
    }

    @FXML
    private void decreaseFontAction(ActionEvent e) {
        fontSize -= 1;
        changeFont();
    }

    private void changeBrush() {
        if (pointerSize < 10) {
            pointerSize = 10;
        }
        showSize.setText("" + pointerSize);
    }

    private void changeFont() {
        if (fontSize < 1) {
            fontSize = 1;
        }
        showFontSize.setText("" + fontSize);
    }

    // Leave the application
    @FXML
    private void handleActionOut(ActionEvent event) {
        Platform.exit();
    }

}
