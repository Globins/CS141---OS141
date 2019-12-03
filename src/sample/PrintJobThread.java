package sample;

import java.io.*;

public class PrintJobThread extends Thread
{
	private StringBuffer fileName;

	public PrintJobThread(StringBuffer fileName)
	{
		this.fileName = fileName;
	}
	public void run()
	{
		try
		{
			FileInfo info = OS141.diskManager.getFileInfo(fileName);
			if(info != null)
			{
				Disk disk = OS141.disks[info.diskNumber];
				int printerIndex = OS141.printerManager.request();
				if(OS141.gui_on)
				{
					OS141.printerManager.PrinterGUIUsed(printerIndex, fileName);
				}
				Printer printer = OS141.printers[printerIndex];
			
				StringBuffer sector = new StringBuffer();
				for(int i = 0; i < info.fileLength; i++)
				{
					sector.setLength(0);
					disk.read(info.startingSector+i, sector);
					printer.print(sector);
				}
				OS141.printerManager.release(printerIndex);
				if(OS141.gui_on)
				{
					OS141.printerManager.printerGUIFree(printerIndex);
				}
			}
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}
