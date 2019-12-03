package sample;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.io.*;

public class UserThread extends Thread
{
	private String fileName;
	private StringBuffer currentLine;
	private StringBuffer currentFile;
	private int currentDisk = 0;
	private int index;

	private Line currentLineGUI;

	public UserThread(String fileName, int index)
	{
		this.fileName = fileName;
		this.index = index;
	}
	public void run()
	{
		try
		{
			File inputFile = new File("inputs/"+fileName);
			BufferedReader br = new BufferedReader(new FileReader(inputFile));
			String line = br.readLine();
			while(line != null)
			{
				currentLine = new StringBuffer(line);
				if(currentLine.toString().startsWith(".save"))
				{
					currentFile = new StringBuffer(currentLine.substring(6));
					currentDisk = OS141.diskManager.request();
					if(OS141.gui_on)
					{
						OS141.diskManager.diskGUIUsed(currentDisk,currentFile);
						Platform.setImplicitExit(false);
						Platform.runLater(new Runnable()
						{
							@Override
							public void run()
							{
								currentLineGUI = new Line(OS141GUI.userGUI[index].getX()+25, OS141GUI.userGUI[index].getY()+25, OS141GUI.diskGUI[currentDisk].getCenterX(), OS141GUI.diskGUI[currentDisk].getCenterY());
								OS141GUI.root.getChildren().add(currentLineGUI);
								OS141GUI.window.show();
							}
						});
					}
					line = br.readLine();
					currentLine = new StringBuffer(line);
					while(!currentLine.toString().startsWith(".end"))
					{
						Disk disk = OS141.disks[currentDisk];
						FileInfo info = OS141.diskManager.getFileInfo(currentFile);
						if(info == null)
						{
							info = new FileInfo();
							info.diskNumber = currentDisk;
							info.fileLength = 1;
							info.startingSector = OS141.diskManager.getAndIncrementNextFreeSector(currentDisk);
							OS141.diskManager.createFile(currentFile, info);
							disk.write(info.startingSector, currentLine);
						}
						else
						{
							int nextFreeSector = OS141.diskManager.getAndIncrementNextFreeSector(currentDisk);
							info.fileLength++;
							disk.write(nextFreeSector, currentLine);
						}
						line = br.readLine();
						currentLine = new StringBuffer(line);
					}
					if(OS141.gui_on)
					{
						Platform.setImplicitExit(false);
						Platform.runLater(new Runnable()
						{
							@Override
							public void run()
							{
								OS141GUI.root.getChildren().remove(currentLineGUI);
								OS141GUI.window.show();
							}
						});
						OS141.diskManager.diskGUIFree(currentDisk);
					}
					OS141.diskManager.getFileInfo(currentFile);
					currentFile = null;
					OS141.diskManager.release(currentDisk);
				}
				else if(currentLine.toString().startsWith(".print"))
				{
					new PrintJobThread(new StringBuffer(currentLine.substring(7))).start();
				}
				line = br.readLine();
			}
			if(OS141.gui_on)
			{
				OS141GUI.userGUI[index].setFill(Color.GREEN);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
