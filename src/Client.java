import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Client {
    
	RequestsInterface ri;
	
    public Client() {
    	try {
    		ri = (RequestsInterface) Naming.lookup("rmi://" + "127.0.0.1" + "/Request");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
    }
    
    public String getListOfVoteResult(String user_name) {
    	try {
			return ri.getListOfVoteResult(user_name);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
    }

    public String login(String user_name) {
    	try {
			return ri.login(user_name);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    public String register_user(String user_name) {
    	try {
			return ri.register_user(user_name);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
    }

    public String recordVote(String candidate, String user_name) {
    	try {
			return ri.recordVote(candidate, user_name);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
    	return null;
    }

    public String[] getCandidates(String user_name) {
    	try {
			return ri.getCandidates(user_name);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
    	return null;
    }
}
