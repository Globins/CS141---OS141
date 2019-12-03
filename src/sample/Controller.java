package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Slider;

public class Controller
{
    @FXML
    private Slider speedControl;

    @FXML
    public void onSliderChanged()
    {
        int sliderValue = (int) speedControl.getValue();
        OS141GUI.currentSpeedMultiplier = sliderValue;
    }
}
