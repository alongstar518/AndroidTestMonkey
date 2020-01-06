package androidtest;

import java.util.Map;
import java.util.Map.Entry;
import java.util.HashMap;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.util.Log;

public class verifyservice {
	private final String soapaction_verifyfocus = "http://tempuri.org/VerifyFocus";
	private final String soapaction_verifyfreeze = "http://tempuri.org/VerifyFreeze";
	
	private final String namespace = "http://tempuri.org/";
	
	private String verifyserviceIp="service_api_url";
	private final String Url = String.format("http://%s/MfImageService/MfCtmVerify.asmx", verifyserviceIp);
	
	private final String method_verifyfocus = "VerifyFocus";
	private final String method_verifyfreeze = "VerifyFreeze";
	 
    public verifyservice(String verifyServerIp)
    {
    	verifyserviceIp = verifyServerIp;
    }

    public String VerifyFocus(String picBase64Data, String runId, String deviceId, String verificationCategory, String verificationValue, String serviceName)
    {
    	Map<String,Object> param = new HashMap<String,Object>();
    	param.put("base64ImgStr", picBase64Data);
    	param.put("runId", runId);
    	param.put("deviceId", deviceId);
    	param.put("verificationCategory", verificationCategory);
    	param.put("verificationValue", verificationValue);
    	param.put("serviceName", serviceName);
    	return CallWebService(param, Url, soapaction_verifyfocus,method_verifyfocus,true);	
    }
    
    public String VerifyFreeze(String runId, String boxId)
    {
    	Map<String,Object> param = new HashMap<String,Object>();
    	param.put("runId", runId);
    	param.put("boxId", boxId);
    	return CallWebService(param, Url, soapaction_verifyfreeze,method_verifyfreeze,true);
    }
    
    private String CallWebService(Map<String,Object> param, String Url, String soapAction,String methodName,Boolean needResponse)
    {
    	try
    	{
	    	SoapObject request = new SoapObject(namespace, methodName);
	    	for(Entry<String, Object> e : param.entrySet())
	    	{
	    		request.addProperty(e.getKey(),e.getValue());
	    	}
	    	
    	
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
    		//utility.Log("Paramvalue = ");
    	}
    	return null;
    }
}
