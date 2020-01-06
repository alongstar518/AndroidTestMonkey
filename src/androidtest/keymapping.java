package androidtest;

import java.util.Dictionary;
import java.util.Hashtable;

public class keymapping {
	public Dictionary<String, Integer> keymappinglist = new Hashtable<String,Integer>();
	
	public keymapping()
	{
		keymappinglist.put("up", 19);
		keymappinglist.put("down", 20);
		keymappinglist.put("left", 21);
		keymappinglist.put("right", 22);
		keymappinglist.put("menu", 256);
		keymappinglist.put("guide", 172);
		keymappinglist.put("back", 111);
		keymappinglist.put("ok", 66);
		keymappinglist.put("channelup", 92);
		keymappinglist.put("channeldown", 93);
		keymappinglist.put("1", 8);
		keymappinglist.put("2", 9);
		keymappinglist.put("3", 10);
		keymappinglist.put("4", 11);
		keymappinglist.put("5", 12);
		keymappinglist.put("6", 13);
		keymappinglist.put("7", 14);
		keymappinglist.put("8", 15);
		keymappinglist.put("9", 16);
		keymappinglist.put("0", 7);
		keymappinglist.put("androidhome", 3);
		keymappinglist.put("fwd", 90);
		keymappinglist.put("pause", 127);
		keymappinglist.put("play", 126);		
		keymappinglist.put("rwd", 89);
	}
	
	public int getKeyCode(String key)
	{
		try
		{
			return keymappinglist.get(key);
		}
		catch(Exception e)
		{
			utility.Log("Can not find key" + key + "Exception： " + e.getMessage());
		}
		return 0;
	}
}
