package sample;

public class OS141
{
	public static int NUM_USERS = 4, NUM_DISKS = 2, NUM_PRINTERS = 3;
	public static String userFileNames[];
	public static UserThread users[];
	public static Disk disks[];
	public static Printer printers[];
	public static DiskManager diskManager;
	public static PrinterManager printerManager;
	public static boolean gui_on = true;
	
	public static void configure(String args[])
	{
		int argIndex = 0;
		NUM_USERS = Integer.parseInt(args[argIndex].substring(1));
		argIndex++;
		userFileNames = new String[NUM_USERS];
		for(int i = 0; i < NUM_USERS; i++)
		{
			userFileNames[i] = args[argIndex];
			argIndex++;
		}	
		NUM_DISKS = Integer.parseInt(args[argIndex].substring(1));
		argIndex++;
		NUM_PRINTERS = Integer.parseInt(args[argIndex].substring(1));
		if(args[args.length-1].substring(1).equals("ng"))
		{
			gui_on = false;
		}
		users = new UserThread[NUM_USERS];
		disks = new Disk[NUM_DISKS];
		printers = new Printer[NUM_PRINTERS];
		diskManager = new DiskManager(NUM_DISKS);
		printerManager = new PrinterManager(NUM_PRINTERS);
	}
	public static void main(String args[])
	{
		configure(args);
		if(gui_on)
		{
			OS141GUI.launcher(args);
		}
		else
		{
			for(int i = 0; i < NUM_DISKS; i++)
			{
				disks[i] = new Disk(NUM_DISKS, i+1);
			}
			for(int i = 0; i < NUM_PRINTERS; i++)
			{
				printers[i] = new Printer(i+1);
			}
			for(int i = 0; i < NUM_USERS; i++)
			{
				users[i] = new UserThread(userFileNames[i], i);
				users[i].start();
			}
		}
	}
}
