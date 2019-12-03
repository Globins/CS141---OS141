package sample;

import javafx.application.Platform;
import javafx.scene.paint.Color;

public class PrinterManager extends ResourceManager
{
	public PrinterManager(int num_printers)
	{
		super(num_printers);
	}
	synchronized void printerGUIFree(int index)
	{
		Platform.setImplicitExit(false);
		Platform.runLater(new Runnable()
		{
			@Override
			public void run()
			{
				OS141GUI.printerGUI[index].setFill(Color.BLUEVIOLET);
				OS141GUI.printStatus[index].setText("Status: Free");
			}
		});
	}
	synchronized void PrinterGUIUsed(int index, StringBuffer fileName)
	{
		Platform.setImplicitExit(false);
		Platform.runLater(new Runnable()
		{
			@Override
			public void run()
			{
				OS141GUI.printerGUI[index].setFill(Color.RED);
				OS141GUI.printStatus[index].setText("Status: Printing file:\n" + fileName);
			}
		});
	}
}
