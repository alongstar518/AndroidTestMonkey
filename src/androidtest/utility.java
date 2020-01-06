package androidtest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;

import android.util.Log;

public class utility {

	public static void Log(String log)
	{
		try
		{
		    android.util.Log.d("com.testmonkey", log);
		}
		catch(Exception e)
		{
		}
	}
    
	public static int GetCpuUsage() {
		try {
		    RandomAccessFile reader = new RandomAccessFile("/proc/stat", "r");
		    String load = reader.readLine();

		    String[] toks = load.split(" ");

		    long idle1 = Long.parseLong(toks[5]);
		    long cpu1 = Long.parseLong(toks[2]) + Long.parseLong(toks[3]) + Long.parseLong(toks[4])
		          + Long.parseLong(toks[6]) + Long.parseLong(toks[7]) + Long.parseLong(toks[8]);

		    try {
		        Thread.sleep(360);
		    } catch (Exception e) {}

		    reader.seek(0);
		    load = reader.readLine();
		    reader.close();

		    toks = load.split(" ");

		    long idle2 = Long.parseLong(toks[5]);
		    long cpu2 = Long.parseLong(toks[2]) + Long.parseLong(toks[3]) + Long.parseLong(toks[4])
		        + Long.parseLong(toks[6]) + Long.parseLong(toks[7]) + Long.parseLong(toks[8]);

		    return (int) ((cpu2 - cpu1) * 100 / ((cpu2 + idle2) - (cpu1 + idle1)));

		} catch (IOException ex) {
		    ex.printStackTrace();
		   }

		    return 0;
	} 
	
	private static String[] GetMemUsage()
	{
		String[] ret = new String[3];
		try
		{
			
		    RandomAccessFile reader = new RandomAccessFile("/proc/meminfo", "r");
		    for(int i =0 ; i < 3; i++)
		    {
			    String load = reader.readLine();
			    //Log("Diags: Mem Log: " + load);
			    String[] toks = load.split(" +");
			    ret[i] = toks[1];
		    }
		    reader.close();
		}
		catch(Exception e)
		{
			
		}
		return ret;
	}
	
	public static diagnostic GetDiagnostic(String Step, String StepDuration)
	{
		try
		{
			String[] memInfo = GetMemUsage();
			diagnostic ret = new diagnostic();
			ret.kbAvailablePhyMemory = memInfo[2];
			ret.kbAvailableVirMemory = "0";
			ret.kbPhyMemory = memInfo[0];
			ret.nLastCpuUsage =Integer.toString(GetCpuUsage());
			ret.pMemoryLoad = Long.toString(100 - (Long.parseLong(memInfo[2]) * 100 / Long.parseLong(memInfo[0])));
			ret.StepDuration = StepDuration;
			ret.StepName = Step;
			return ret;
		}
		catch(Exception e)
		{
			Log("Error when get Diagnostic Error:" + Log.getStackTraceString(e));
		}
		return null;
	}
	
	public static String getAppVersion(String PackageName)
	{
		String ret ="0";
		try
		{
			Runtime rt = Runtime.getRuntime();
			String[] cmd = {"dumpsys" , "package" , PackageName};
			Process proc = rt.exec(cmd);

			BufferedReader stdInput = new BufferedReader(new 
			     InputStreamReader(proc.getInputStream()));
				
			String s = null;
			while((s = stdInput.readLine()) != null) 
			{
				if(s.contains("versionName"))
				{
					Log("Find Version Name String: " + s);
					String result = s.split("=")[1];
					ret = result.substring(result.length()-6);
				}
			}
		}
		catch(Exception e)
		{
			Log("Error when get App Version Error:" + Log.getStackTraceString(e));
		}
		return ret.replace(".", "");
	}
}
