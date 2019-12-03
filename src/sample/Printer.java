package sample;
import java.io.*;

public class Printer
{	
	private File print_file;
	public Printer(int printer_id)
	{
		print_file = new File("PRINTER"+printer_id);
	}
	void print(StringBuffer data) throws InterruptedException, IOException
	{
		if(OS141.gui_on)
		{
			Thread.sleep((long) (2750.0f/OS141GUI.currentSpeedMultiplier));
		}
		else
		{
			Thread.sleep(2750);
		}
		if(!print_file.exists())
		{
			print_file.createNewFile();	
		}
		FileWriter fileWriter = new FileWriter(print_file, true);
		BufferedWriter writer = new BufferedWriter(fileWriter);
		writer.write(data.toString());
		writer.newLine();
		writer.flush();
	}
}
