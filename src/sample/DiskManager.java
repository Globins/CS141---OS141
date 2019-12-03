package sample;

import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class DiskManager extends ResourceManager
{
	private DirectoryManager directoryManager;
	private int[] nextFreeSectors;	

	public DiskManager(int num_disks)
	{
		super(num_disks);
		directoryManager = new DirectoryManager();
		nextFreeSectors = new int[num_disks];	
	}
	public synchronized FileInfo getFileInfo(StringBuffer data)
	{
		return directoryManager.lookup(data);
	}
	public synchronized void createFile(StringBuffer fileName, FileInfo info)
	{
		directoryManager.enter(fileName, info);
	}
	public int getAndIncrementNextFreeSector(int index)
	{
        int next = nextFreeSectors[index];
        nextFreeSectors[index] = nextFreeSectors[index]+1;
        return next;
    }
	synchronized void diskGUIFree(int index)
	{
		Platform.setImplicitExit(false);
		Platform.runLater(new Runnable()
		{
			@Override
			public void run()
			{
				OS141GUI.diskGUI[index].setFill(Color.BLUE);
				OS141GUI.diskStatus[index].setText("Status: Free");
			}
		});
	}
	synchronized void diskGUIUsed(int index, StringBuffer fileName)
	{
		Platform.setImplicitExit(false);
		Platform.runLater(new Runnable()
		{
			@Override
			public void run()
			{
				OS141GUI.diskGUI[index].setFill(Color.RED);
				OS141GUI.diskStatus[index].setText("Status: Writing to file:\n" + fileName);
			}
		});
	}
}
