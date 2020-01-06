package androidtest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class testresult {
	public long Dur;
	public int Iter;
	public int CIT;
	public Boolean P = false;
	public int Del;
	public int loop;
	public int TIter;
	public int CIter;
	public String RID;
	public int CID;
	public String Name;
	public String TI = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
	public int Flags = 0;
	public int Steps;
	public String DID;
	public String ErrorVerifyToken;
	public String Error;
	public List<diagnostic> stepDiagnostic = new ArrayList<diagnostic>();
}
