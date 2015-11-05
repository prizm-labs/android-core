using UnityEngine;
using System.Collections;
using UnityEngine.UI;

public class BroadcastReceiver : MonoBehaviour
{
	public Text msg;
	AndroidJavaClass jc;
	string javaMessage = "";
	
	void Start()
	{
		// Acces the android java receiver we made
		jc = new AndroidJavaClass("com.prizm.testPlugin.MyReceiver");
		// We call our java class function to create our MyReceiver java object
		jc.CallStatic("createInstance");       
	}
	
	void Update()
	{              
		// We get the text property of our receiver
		javaMessage = jc.GetStatic<string>("text");
		msg.text = javaMessage;
	}
}