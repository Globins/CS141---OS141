package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static sample.OS141.*;

public class OS141GUI extends Application
{
    public static double currentSpeedMultiplier = 1;
    public static Stage window;
    public static AnchorPane root;
    public static Circle diskGUI[];
    public static Text diskStatus[];
    public static Circle printerGUI[];
    public static Text printStatus[];
    public static Rectangle userGUI[];

    public static void launcher(String args[])
    {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception
    {
        window = stage;
        root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        window.setTitle("OS141 Simulation");
        Scene scene = new Scene(root, 600, 500);
        diskGUI = new Circle[NUM_DISKS];
        diskStatus = new Text[NUM_DISKS];
        printerGUI = new Circle[NUM_PRINTERS];
        printStatus = new Text[NUM_PRINTERS];
        userGUI = new Rectangle[NUM_USERS];

        for(int i = 0; i < NUM_DISKS; i++)
        {
            Text diskText = new Text(45+150*i, scene.getHeight()-250, "Disk " + (i+1));
            root.getChildren().add(diskText);
            diskStatus[i] = new Text(30+150*i, scene.getHeight()-350, "Status: Free");
            root.getChildren().add(diskStatus[i]);
            diskGUI[i] = new Circle(60+150*i, scene.getHeight()-300, 25);
            diskGUI[i].setStroke(Color.BLACK);
            diskGUI[i].setFill(Color.BLUE);
            root.getChildren().add(diskGUI[i]);
            disks[i] = new Disk(NUM_DISKS, i+1);
        }
        for(int i = 0; i < NUM_PRINTERS; i++)
        {
            Text printerText = new Text(37.5+150*i, scene.getHeight()-375, "Printer " + (i+1));
            root.getChildren().add(printerText);
            printStatus[i] = new Text(30+150*i, scene.getHeight()-460, "Status: Free");
            root.getChildren().add(printStatus[i]);
            printerGUI[i] = new Circle(60+150*i, 85, 25);
            printerGUI[i].setStroke(Color.BLACK);
            printerGUI[i].setFill(Color.BLUEVIOLET);
            root.getChildren().add(printerGUI[i]);
            printers[i] = new Printer(i+1);
        }
        for(int i = 0; i < NUM_USERS; i++)
        {
            Text userText = new Text(25+90*i, scene.getHeight()-75, "UserThread" + (i+1));
            root.getChildren().add(userText);
            userGUI[i] = new Rectangle(35+90*i, scene.getHeight()-150, 50, 50);
            userGUI[i].setStroke(Color.BLACK);
            userGUI[i].setFill(Color.BLUE);
            root.getChildren().add(userGUI[i]);
            users[i] = new UserThread(userFileNames[i], i);
            users[i].start();
        }
        window.setScene(scene);
        window.show();
    }
}
