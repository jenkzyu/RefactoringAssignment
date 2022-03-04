import java.util.ArrayList;

import javax.swing.JFrame;

public interface IResponses {
	
	public JFrame frame(String name);
	public void noCustomersFound(JFrame notFound);
	public void noAccounts(JFrame noAccounts);
	public int onFailure(String success, String message);
	

}
