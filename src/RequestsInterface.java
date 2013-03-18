import java.rmi.Remote;
import java.rmi.RemoteException;


public interface RequestsInterface extends Remote {
	public String getListOfVoteResult(String user_name) throws RemoteException;
	public String login(String user_name) throws RemoteException;
	public String register_user(String user_name) throws RemoteException;
	public String recordVote(String candidate, String user_name) throws RemoteException;
	public String[] getCandidates(String user_name) throws RemoteException;
}
