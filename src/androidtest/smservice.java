package androidtest;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
//import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.util.Log;;

public class smservice {

	private final String soapaction_beginrun = "soapBeginRun";
	private final String soapaction_begincase = "soapBeginCase";
	private final String soapaction_endcase =  "soapEndCase";
	
	private final String namespace = "clienttestmonkey";
	
	private String SmServiceIp="test.server.url";
	private final String Url = String.format("http://%s/test/sm.asmx", SmServiceIp);
	
	private final String method_beginrun = "BeginRun";
	private final String method_begincase = "BeginCase";
	private final String method_endcase = "EndCase";
     
    public smservice(String smServerIp)
    {
	    SmServiceIp = smServerIp;
    }

    public String BeginRun(String beginRunData)
    {
    	return CallWebService(beginRunData, Url, soapaction_beginrun,method_beginrun,true);	
    }
    
    public String BeginCase(String beginCase)
    {
    	return CallWebService(beginCase, Url, soapaction_begincase,method_begincase,false);
    }
    
    public String EndCase(String data)
    {
    	return CallWebService(data, Url, soapaction_endcase,method_endcase,false);
    }
    
    
    private String CallWebService(String parametervalue, String Url, String soapAction,String methodName,Boolean needResponse)
    {
    	try
    	{
	    	SoapObject request = new SoapObject(namespace, methodName);
	    	request.addProperty("strXData", parametervalue);
    	
	        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	        soapEnvelope.dotNet = true;
	        soapEnvelope.setOutputSoapObject(request);
	
	        HttpTransportSE transport = new HttpTransportSE(Url);
	
	        transport.call(soapAction, soapEnvelope);
	        if(needResponse)
	        	return soapEnvelope.getResponse().toString();
	        else
	        	return null;
    	}
    	catch(Exception e)
    	{
    		utility.Log("Error when do action"+soapAction+",exception: " + Log.getStackTraceString(e));
    		utility.Log("Paramvalue = " + parametervalue);
    	}
    	return null;
    }
}
