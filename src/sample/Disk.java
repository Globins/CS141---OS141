package sample;

public class Disk
{
	static final int NUM_SECTORS = 1024;
	StringBuffer sectors[] = new StringBuffer[NUM_SECTORS];
	private int id;
	private int sectorSize;


	public Disk(int capacity, int id)
	{
		this.id = id;
		sectorSize = capacity / NUM_SECTORS;
		for(int i = 0; i < sectors.length; i++)
		{
			sectors[i] = new StringBuffer(sectorSize);
		}
	}
	public void write(int sector, StringBuffer data) throws InterruptedException 
	{
		if(OS141.gui_on)
		{
			Thread.sleep((long) (200.0f/OS141GUI.currentSpeedMultiplier));
		}
		else
		{
			Thread.sleep(200);
		}

		sectors[sector].setLength(0);
		sectors[sector].append(data);
	}
	public void read(int sector, StringBuffer data) throws InterruptedException
	{
		if(OS141.gui_on)
		{
			Thread.sleep((long) (200.0f/OS141GUI.currentSpeedMultiplier));
		}
		else
		{
			Thread.sleep(200);
		}
		data.append(sectors[sector]);
	}
}
