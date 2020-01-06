package androidtest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.SystemClock;

import android.util.Base64;
import android.util.Log;
import android.util.Xml;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;


public class clienttestmonkey extends UiAutomatorTestCase{


	private smservice sm;
	private verifyservice verService;
	private clientinfo ci;
	private List<testcase> loopingtestcaselist = new ArrayList<testcase>();
	private List<testcase> runoncetestcaselist = new ArrayList<testcase>();
	private Boolean Running = false;
	private int testcasecount = 0;
	private int CIT = 1;
	private int testLoops = 0;
	private keymapping kmap = new keymapping();
	private int maxCaseDelay = 120000;
	private String[] appPackageNames = {"com.ericsson.MediaFirstUC.tv"};
	private String screenCapLocation = "/sdcard/screencap.png";
	private String windowDumpLocation = "/sdcard/window_dump.xml";
	private String windowDumpFindTarget = "android.webkit.WebView";
	private String Service = "preprod";
		
	private String getStartRunData()
	{
		try
		{
			XmlSerializer xmlSerializer = Xml.newSerializer();
			StringWriter writer = new StringWriter();
			
			xmlSerializer.setOutput(writer);
			
			xmlSerializer.startTag(null,"Data");
			
			xmlSerializer.startTag(null, "RID");
			xmlSerializer.text(ci.RID);
			xmlSerializer.endTag(null, "RID");
			
			xmlSerializer.startTag(null, "DID");
			xmlSerializer.text(ci.DID);
			xmlSerializer.endTag(null, "DID");
			
			xmlSerializer.startTag(null, "BID");
			xmlSerializer.text(ci.BID);
			xmlSerializer.endTag(null, "BID");
			
			xmlSerializer.startTag(null, "Script");
			xmlSerializer.text(ci.Script);
			xmlSerializer.endTag(null, "Script");
			
			xmlSerializer.startTag(null, "IO");
			xmlSerializer.text(ci.IO);
			xmlSerializer.endTag(null, "IO");
			
			xmlSerializer.startTag(null, "SMode");
			xmlSerializer.text(ci.SMode);
			xmlSerializer.endTag(null, "SMode");
			
			xmlSerializer.startTag(null, "DoGC");
			xmlSerializer.text(ci.DoGC);
			xmlSerializer.endTag(null, "DoGC");
			
			xmlSerializer.startTag(null, "TType");
			xmlSerializer.text(ci.TType);
			xmlSerializer.endTag(null, "TType");
			
			xmlSerializer.startTag(null, "TI");
			xmlSerializer.text(ci.TI);
			xmlSerializer.endTag(null, "TI");
			
			xmlSerializer.startTag(null, "XData");
			
			xmlSerializer.startTag(null, "Platform");
			xmlSerializer.text(ci.Platform);
			xmlSerializer.endTag(null, "Platform");
			
			xmlSerializer.startTag(null, "Flavor");
			xmlSerializer.text(ci.Flavor);
			xmlSerializer.endTag(null, "Flavor");
			
			xmlSerializer.startTag(null, "Build");
			xmlSerializer.text(ci.Build);
			xmlSerializer.endTag(null, "Build");
			
			xmlSerializer.startTag(null, "IP");
			xmlSerializer.text(ci.IP);
			xmlSerializer.endTag(null, "IP");

			xmlSerializer.startTag(null, "Port");
			xmlSerializer.text(ci.Port);
			xmlSerializer.endTag(null, "Port");

			xmlSerializer.startTag(null, "OS");
			xmlSerializer.text(ci.OS);
			xmlSerializer.endTag(null, "OS");
			
			xmlSerializer.startTag(null, "Svc");
			xmlSerializer.text(ci.Svc);
			xmlSerializer.endTag(null, "Svc");
			
			xmlSerializer.startTag(null, "HD");
			xmlSerializer.text(ci.HD);
			xmlSerializer.endTag(null, "HD");
			
			xmlSerializer.startTag(null, "DVR");
			xmlSerializer.text(ci.DVR);
			xmlSerializer.endTag(null, "DVR");
			
			xmlSerializer.startTag(null, "HighDef");
			xmlSerializer.text(ci.HighDef);
			xmlSerializer.endTag(null, "HighDef");
			
			xmlSerializer.startTag(null, "PAL");
			xmlSerializer.text(ci.PAL);
			xmlSerializer.endTag(null, "PAL");
			
			xmlSerializer.startTag(null, "Version");
			xmlSerializer.text(ci.Version);
			xmlSerializer.endTag(null, "Version");
			
			xmlSerializer.startTag(null, "AsmConfig");
			xmlSerializer.text(ci.AsmConfig);
			xmlSerializer.endTag(null, "AsmConfig");
			
			xmlSerializer.startTag(null, "Vendor");
			xmlSerializer.text(ci.Vendor);
			xmlSerializer.endTag(null, "Vendor");
			
			xmlSerializer.startTag(null, "Feature");
			xmlSerializer.text(ci.Feature);
			xmlSerializer.endTag(null, "Feature");
			
			xmlSerializer.endTag(null, "XData");
			xmlSerializer.endTag(null, "Data");
			
			xmlSerializer.flush();
			
			return writer.toString();
			
		}
		catch(Exception e)
		{
			utility.Log(String.format("Exception When try to GetStartRunData: %s",Log.getStackTraceString(e)));
		}
		return null;
	}
    
	private String getBeginCase(int CaseId)
	{
		try
		{
			XmlSerializer xmlSerializer = Xml.newSerializer();
			StringWriter writer = new StringWriter();
			
			xmlSerializer.setOutput(writer);
						
			xmlSerializer.startTag(null,"Data");
			xmlSerializer.attribute(null,"Iter", Integer.toString(testcasecount));
			
			xmlSerializer.startTag(null, "DID");
			xmlSerializer.text(ci.DID);
			xmlSerializer.endTag(null, "DID");
			
			xmlSerializer.startTag(null, "RID");
			xmlSerializer.text(ci.RID);
			xmlSerializer.endTag(null, "RID");
			
			xmlSerializer.startTag(null, "CID");
			xmlSerializer.text(Integer.toString(CaseId));
			xmlSerializer.endTag(null, "CID");
			
			xmlSerializer.startTag(null, "CIT");
			xmlSerializer.text(Integer.toString(CIT));
			xmlSerializer.endTag(null, "CIT");
			
			xmlSerializer.startTag(null,"Loop");
			xmlSerializer.text(Integer.toString(testLoops));
			xmlSerializer.endTag(null, "Loop");
						
			xmlSerializer.startTag(null,"TI");
			xmlSerializer.text(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()));
			xmlSerializer.endTag(null, "TI");
			
			xmlSerializer.endTag(null, "Data");
			
			xmlSerializer.flush();
			
			return writer.toString();
		}
		catch(Exception e)
		{
			utility.Log("Error when get Begin case for tc: "+ Integer.toString(CaseId) + "Exception: " + Log.getStackTraceString(e));
		}
		return null;
		
	}
	
	private void updateTcList(testcase tc, Boolean runonce)
	{
		try
		{
			for(int i = 0; i < tc.Interation; i++)
			{
				testcase _tc = new testcase();
				_tc.Id = tc.Id;
				_tc.TestSteps = tc.TestSteps;
				_tc.Interation = tc.Interation;
				_tc.TCName = tc.TCName;
				_tc.Flag = tc.Flag;
				if(runonce)
				   runoncetestcaselist.add(_tc);
				else
				   loopingtestcaselist.add(_tc);
			}
		}
		catch(Exception e)
		{
			utility.Log("Can not Add TC:" + tc.TCName);
		}
	}
	
	private void getAndParseTestScript()
	{
		try
		{
		    String testScript = sm.BeginRun(getStartRunData());
		    if(testScript.length() <= 0)
		    {
		    	utility.Log("Error when get test script for begin run, exit now");
		    	return;
		    }
			utility.Log(testScript);   
		    InputStream stream = new ByteArrayInputStream(testScript.getBytes());
		    
		    XmlPullParser parser = Xml.newPullParser();
		    
		    parser.setInput(stream, null);
		    
		    testcase tc = null;
		    teststep ts = null;
		    int laststepcount = 1;
		    while(parser.getEventType() != XmlPullParser.END_DOCUMENT)
		    {
		    	parser.next();
		    	if(parser.getEventType() != XmlPullParser.START_TAG)
		    		continue;
		    	
		    	String name = parser.getName();
		    	
		    	if(name.equals("Case"))
		    	{
		    		utility.Log("--Find a case node");
		    		if(tc != null)
		    		{
		    			utility.Log("Adding TC: " + Integer.toString(tc.Id));
		    			laststepcount = 1;
			    		if(tc.Id == 0)
			    			//runoncetestcaselist.add(tc);
			    			updateTcList(tc, true);
			    		else
			    			//loopingtestcaselist.add(tc);
			    			updateTcList(tc, false);
			    		testcasecount++;
		    		}
		    		tc = new testcase();
		    		tc.Flag = Integer.parseInt(parser.getAttributeValue(null, "Flags"));
		    		tc.Id = Integer.parseInt(parser.getAttributeValue(null, "Id"));
		    		tc.Interation = Integer.parseInt(parser.getAttributeValue(null, "Iteration"));
		    		tc.TCName = parser.getAttributeValue(null, "Name");	    		
		    	}
		    	if(name.equals("Step"))
		    	{
		    		utility.Log("--Find Step Node");
		    		ts = new teststep();
		    		if(parser.getAttributeCount() == 0)
		    		{
		    			continue;
		    		}
		    		else
		    		{
		    			ts.Event = parser.getAttributeValue(null, "Event");
		    			ts.PostStepDelay = Integer.parseInt(parser.getAttributeValue(null, "PostStepDelay"));
		    		}
		    		tc.TestSteps.put(laststepcount, ts);
		    		laststepcount++;
		    	}
		    	if(name.equals("Verify"))
		    	{
		    		utility.Log("--Find verify Node");
		    		ts = new teststep();

	    			ts.VerificationStep = true;
	    			ts.Token = parser.getAttributeValue(null, "Token");
	    			ts.VerifyValue = parser.getAttributeValue(null, "Value");

		    		tc.TestSteps.put(laststepcount, ts);
		    		laststepcount++;
		    	}
		    	
		    }
		    utility.Log("Adding TC: " + Integer.toString(tc.Id));
    		if(tc.Id == 0)
    			//runoncetestcaselist.add(tc);
    			updateTcList(tc, true);
    		else
    			//loopingtestcaselist.add(tc);
    			updateTcList(tc, false);
		    utility.Log("TC count:" + Integer.toString(loopingtestcaselist.size()));
		    Running = true;
		}
		catch(Exception e)
		{
			utility.Log("Error when run test case: exception " + Log.getStackTraceString(e));
		}
		
	}
	
	private String VerifyPage(String pageNameToVerify)
	{
		try
		{
			utility.Log("Verifying Page..");
			
			UiDevice device = getUiDevice();
			
			device.dumpWindowHierarchy(windowDumpLocation);
			
		    InputStream stream = new BufferedInputStream(new FileInputStream(windowDumpLocation));
		    
		    XmlPullParser parser = Xml.newPullParser();
		    
		    parser.setInput(stream, null);
		    
		    while(parser.getEventType() != XmlPullParser.END_DOCUMENT)
		    {
		    	parser.next();
		    	if(parser.getEventType() != XmlPullParser.START_TAG)
		    		continue;
		    	
		    	String name = parser.getName();
		    	
		    	if(name.equals("node"))
		    	{
		    		if(parser.getAttributeValue(null, "class") == windowDumpFindTarget)
		    		{
		    			String pageName = parser.getAttributeValue(null, "content-desc");
		    			if(pageNameToVerify == pageName)
		    				return "Success|";
		    			else
		    				return "Failed|Expected=" + pageNameToVerify + " Actual=" + pageName;
		    		}
		    	}
		    }
		
		}
		catch(Exception e)
		{
			utility.Log("Error When verifying page, Error: " + Log.getStackTraceString(e) );
		}
		return "Failed|Can Not Find WebView Class";
	}
	
	private Boolean isAppCrash(String[] packageNames)
	{
		try
		{
			utility.Log("Checking App Crash...");
			Runtime rt = Runtime.getRuntime();
			String[] commands = {"ps"};
			Process proc = rt.exec(commands);
		
			BufferedReader stdInput = new BufferedReader(new 
			     InputStreamReader(proc.getInputStream()));
				
			String s = null;
			while ((s = stdInput.readLine()) != null) {
			    for(String p : packageNames)
			    {
			    	if(s.contains(p))
			    		return false;
			    }
			}
		
		}
		catch(Exception e)
		{
			utility.Log("Error When checkProcess Error: " + Log.getStackTraceString(e) );
		}
		return true;
	}
	
	private void logResult(testresult result)
	{
		try
		{
			XmlSerializer xmlSerializer = Xml.newSerializer();
			StringWriter writer = new StringWriter();
			
			xmlSerializer.setOutput(writer);
						
			xmlSerializer.startTag(null,"Data");
			xmlSerializer.attribute(null, "Steps", Integer.toString(result.Steps));
			xmlSerializer.attribute(null,"Iter", Integer.toString(testcasecount));
			
			xmlSerializer.startTag(null, "CIT");
			xmlSerializer.text(Integer.toString(result.CIT));
			xmlSerializer.endTag(null, "CIT");
			
			xmlSerializer.startTag(null, "P");
			xmlSerializer.text(Boolean.toString(result.P));
			xmlSerializer.endTag(null, "P");
			
			xmlSerializer.startTag(null, "Dur");
			xmlSerializer.text(Long.toString(result.Dur));
			xmlSerializer.endTag(null, "Dur");
			
			xmlSerializer.startTag(null, "Del");
			xmlSerializer.text(Integer.toString(result.Del));
			xmlSerializer.endTag(null, "Del");
			
			xmlSerializer.startTag(null,"Loop");
			xmlSerializer.text(Integer.toString(result.loop));
			xmlSerializer.endTag(null, "Loop");
			
			xmlSerializer.startTag(null,"TIter");
			xmlSerializer.text(Integer.toString(result.TIter));
			xmlSerializer.endTag(null, "TIter");
			
			xmlSerializer.startTag(null,"CIter");
			xmlSerializer.text(Integer.toString(result.CIter));
			xmlSerializer.endTag(null, "CIter");
			
			xmlSerializer.startTag(null,"RID");
			xmlSerializer.text(result.RID);
			xmlSerializer.endTag(null, "RID");
			
			xmlSerializer.startTag(null,"DID");
			xmlSerializer.text(result.DID);
			xmlSerializer.endTag(null, "DID");
			
			xmlSerializer.startTag(null,"CID");
			xmlSerializer.text(Integer.toString(result.CID));
			xmlSerializer.endTag(null, "CID");
			
			xmlSerializer.startTag(null,"TI");
			xmlSerializer.text(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()));
			xmlSerializer.endTag(null, "TI");
			if(!result.P) {
				xmlSerializer.startTag(null, "F");
				xmlSerializer.attribute(null, "T", result.ErrorVerifyToken);
				xmlSerializer.attribute(null, "V", result.Error);
				xmlSerializer.attribute(null, "O", Long.toString(android.os.SystemClock.uptimeMillis()));
				xmlSerializer.endTag(null, "F");
			}
			xmlSerializer.startTag(null,"Flags");
			xmlSerializer.text(Integer.toString(result.Flags));
			xmlSerializer.endTag(null, "Flags");
			
			xmlSerializer.startTag(null,"XData");
			
				xmlSerializer.startTag(null, "Test");
					xmlSerializer.startTag(null, "Steps");
						for(diagnostic d : result.stepDiagnostic)
						{
							xmlSerializer.startTag(null, "Step");
								xmlSerializer.startTag(null, "PartialStep");
									xmlSerializer.attribute(null, "nLastCpuUsage", d.nLastCpuUsage);
									xmlSerializer.attribute(null, "nAvgCpuUsage", d.nAvgCpuUsage);
									xmlSerializer.attribute(null, "pMemoryLoad", d.pMemoryLoad);
									xmlSerializer.attribute(null, "kbPhyMemory", d.kbPhyMemory);
									xmlSerializer.attribute(null, "kbAvailablePhyMemory", d.kbAvailablePhyMemory);
									xmlSerializer.attribute(null, "kbAvailableVirMemory", d.kbAvailableVirMemory);
									xmlSerializer.attribute(null, "GCMemory", d.GCMemory);
									xmlSerializer.text(d.StepName);
							    xmlSerializer.endTag(null, "PartialStep");
							    xmlSerializer.startTag(null, "duration");
							    	xmlSerializer.text(d.StepDuration);
							    xmlSerializer.endTag(null, "duration");
							xmlSerializer.endTag(null, "Step");
						}
					xmlSerializer.endTag(null, "Steps");
				xmlSerializer.endTag(null, "Test");
				
			xmlSerializer.endTag(null, "XData");			
			
			
			xmlSerializer.endTag(null, "Data");
			
			xmlSerializer.flush();
			
			utility.Log("Result Xml = "+writer.toString());
			
			sm.EndCase(writer.toString());
		}
		catch(Exception e)
		{
			utility.Log("Error when log result for tc: "+ result.Name + "Exception: " + Log.getStackTraceString(e));
		}		
	}
	
	private void takeScreenCapture()
	{
		UiDevice device = getUiDevice();
		File capFile = new File(screenCapLocation);
		device.takeScreenshot(capFile);
	}
		
	private String encodeImage(String path)
    {
        File imagefile = new File(path);
        FileInputStream fis = null;
        String encImage = null;
        try
        {
            fis = new FileInputStream(imagefile);
	        Bitmap bm = BitmapFactory.decodeStream(fis);
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        bm.compress(Bitmap.CompressFormat.PNG,100,baos);
	        byte[] b = baos.toByteArray();
	        encImage = Base64.encodeToString(b, Base64.DEFAULT);
        }
        catch(FileNotFoundException e)
        {
            utility.Log("Error when try to take screen capture for tc: " + Log.getStackTraceString(e));
        }
        return encImage;
    }
	
	private void runTestCase(testcase tc, int delay)
	{
		try
		{
			utility.Log("In case delay: "+ Integer.toString(delay) +"ms");
			Thread.sleep(delay);
		}
		catch(Exception e)
		{
		}
		for (int i = 0; i<3; i++)
		{
			try
			{
				String xml = getBeginCase(tc.Id);
				utility.Log("getbegincase"+xml);
				sm.BeginCase(xml);
				break;
			}
			catch(Exception e)
			{
				utility.Log("Error when start test case: " + Integer.toString(tc.Id) + " Exception:" + e.getMessage());
			}
		}
		testresult result = new testresult();
		result.Iter = testcasecount;
		result.CID = tc.Id;
		result.CIT = CIT;
		result.CIter = CIT;
		result.loop = testLoops;
		result.RID = ci.RID;
		result.DID = ci.DID;
		result.Name = tc.TCName;
		result.Del = delay;
		long dur_testStart = SystemClock.uptimeMillis();
		try
		{
			utility.Log("RunningTest: " + tc.TCName);
			
			for(int i = 1; i <= tc.TestSteps.size() ; i++)
			{
			    teststep t = tc.TestSteps.get(i);
			    if(t.VerificationStep)
			    {
			    	utility.Log("Verifying - " + t.Token);
			    	String resultReturn = "Failed|NullInfo,Either VerifyFocs or Verify Page performed.";
			    	
	    			diagnostic resultDiag = utility.GetDiagnostic(t.Token,Long.toString(0));
	    			if(resultDiag != null)
	    			{
		    			result.stepDiagnostic.add(resultDiag);
	    			}
	    			
			    	if(t.Token.toLowerCase().startsWith("---"))
			    	{
			    		resultReturn = VerifyPage(t.VerifyValue);
			    	}
			    	else
			    	{
			    		if(!t.Token.toLowerCase().contains("diags"))
			    		{
					    	takeScreenCapture();
					    	String picString = encodeImage(screenCapLocation);
					    	resultReturn = verService.VerifyFocus(picString, ci.RID, ci.DID, t.Token, t.VerifyValue, Service);
			    		}
			    		else
			    		{
			    			resultReturn = "Success|";
			    		}
			    	}
			    	if(resultReturn.contains("Success"))
			    		result.P = true;
			    	else
			    	{
			    		result.P = false;
			    		result.Error = resultReturn;
			    		result.ErrorVerifyToken = t.Token;
			    		if(resultReturn.contains("Crashed"))
			    		{
			    			Running = false;
			    		}
			    		break;
			    	}
			    }
			    else
			    {
			    	long dur_stepStart = SystemClock.uptimeMillis();
			    	Boolean sendkeyResult = sendRemoteKey(t.Event, false);
			    	long stepDur = SystemClock.uptimeMillis() - dur_stepStart;
    				if(!sendkeyResult)
    				{
    					result.Error = "Key press Failed for " + t.Event;
    					result.P = false;
    					return;
    				}
	    			diagnostic resultDiag = utility.GetDiagnostic(t.Event,Long.toString(stepDur));
	    			if(resultDiag != null)
	    			{
		    			result.stepDiagnostic.add(resultDiag);
	    			}
	    			result.P=true;
			    }
    			Thread.sleep(t.PostStepDelay);
		    }
		}
		catch(Exception e)
		{
			utility.Log("Error Accour, test failed. Error Message:" + Log.getStackTraceString(e));
			result.P = false;
			result.Dur = SystemClock.uptimeMillis() - dur_testStart;
			if(result.Error == null)
				result.Error = Log.getStackTraceString(e);
		}
		CIT++;
		result.Dur = SystemClock.uptimeMillis() - dur_testStart;
		logResult(result);
	}
	
	public Boolean isAppHalt()
	{
		try
		{
			utility.Log("Checking App halt..");
			return verService.VerifyFreeze(ci.RID, ci.DID).contains("true");
		}
		catch(Exception e)
		{
			utility.Log("Error when verify Freeze: Exception: " + Log.getStackTraceString(e));
		}
		return false;
	}
	
	private void runScript(List<testcase> caselist)
	{
		for(testcase tc : caselist)
		{
			if(isAppCrash(appPackageNames) || isAppHalt())
			{
				Running = false;
				break;
			}
			Random r = new Random();
			runTestCase(tc,r.nextInt(maxCaseDelay));
		}
	}
	
	private Boolean sendRemoteKey(String Key,Boolean textinput)
	{
		UiDevice device = getUiDevice();
		if(textinput)
		{
	        for (int i = 0; i < Key.length(); i++) {
	            char c = Key.charAt(i);
	            if (c >= 48 && c <= 57) // 0~9
	                return device.pressKeyCode(c - 41);
	            else if (c >= 65 && c <= 90) // A~Z
	                return device.pressKeyCode(c - 36, 1);
	            else if (c >= 97 && c < 122) // a~z
	                return device.pressKeyCode(c - 68);
	        }
		}
	    utility.Log("Sending Key:" + Key);
	    return device.pressKeyCode(kmap.getKeyCode(Key));
	}
	
	private void getPackageNames(String packageNameStr)
	{
		try
		{
			appPackageNames = packageNameStr.split(",");
		}
		catch(Exception e)
		{
			utility.Log("Error when get appPackageNames from package name string. Error: " + Log.getStackTraceString(e));
		}
	}
		
	public void test() throws UiObjectNotFoundException
	{
		String IP = getParams().getString("IP");
		String RID = getParams().getString("RID");
		String DID = getParams().getString("DID");
		String smIp = getParams().getString("SMIp");
		String verIp = getParams().getString("verIp");
		String script = getParams().getString("ScriptName");
		String packageNames = getParams().getString("PackageNames");
		Service = getParams().getString("Service");
		getPackageNames(packageNames);
		smIp = smIp.isEmpty() ? "tvst-db-01.mr.ericsson.se" : smIp ;
		verIp = verIp.isEmpty() ? "tvst-db-01.mr.ericsson.se" : verIp ;
		sm = new smservice(smIp);
		verService = new verifyservice(verIp);
		ci = new clientinfo();
		ci.IP = IP;
		ci.RID = RID;
		ci.DID = DID;
		ci.Script = script;
		ci.Build = utility.getAppVersion(getUiDevice().getCurrentPackageName());
		ci.BID = ci.Build;
		utility.Log("StartRun Build:" + ci.Build);
		getAndParseTestScript();
		if(runoncetestcaselist.size() > 0)
		     runScript(runoncetestcaselist);
		
		while(Running)
		{
			testLoops++;
			Collections.shuffle(loopingtestcaselist);
			utility.Log("TC total runs:" + Integer.toString(loopingtestcaselist.size()));
			runScript(loopingtestcaselist);
	    
		}
		utility.Log("Run finsihed, stop.");
	}
}
