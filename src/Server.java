import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class Server extends UnicastRemoteObject implements RequestsInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Server() throws RemoteException {
		super();
	}

	static ConcurrentHashMap<String, Boolean> registered_users;
	static ConcurrentHashMap<String, Integer> votes;

	public static void init() throws FileNotFoundException {
		registered_users = new ConcurrentHashMap<String, Boolean>();
		votes = new ConcurrentHashMap<String, Integer>();

		Scanner r = new Scanner(new FileReader("candidates.in"));

		int users = r.nextInt();
		for (int i = 0; i < users; i++)
			registered_users.put(r.next(), r.nextBoolean());

		int candidates = r.nextInt();
		for (int i = 0; i < candidates; i++) {
			String s = r.next();
			int vote = r.nextInt();
			votes.put(s, vote);
		}
	}

	public static void main(String[] args) throws FileNotFoundException {
		init();
		
		Server server = null;
		try {
			server = new Server();
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		try {
			Naming.rebind("Request", server);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		System.out.println("Server is ready!");

	}

	@Override
	public String getListOfVoteResult(String user_name) throws RemoteException {
		String res = "";
		// view results.
		Boolean vote_before = registered_users.get(user_name);
		if (vote_before) {
			int total = 0;
			for (String s : votes.keySet()) {
				int vote = votes.get(s);
				total += vote;
			}
			for (String s : votes.keySet()) {
				int vote = votes.get(s);
				res += String.format("%s: %d %.2f", s, vote, vote * 100.
						/ total);
				res += "%\n";

			}
		} else {
			res += "404 NOT FOUND";
		}

		return res;

	}

	@Override
	public String login(String user_name) throws RemoteException {
		String res = "";
		// login
		if (!registered_users.containsKey(user_name)) {
			res += "404 NOT FOUND";
		} else {
			res += "200 OK";
		}
		return res;
	}

	@Override
	public String register_user(String user_name) throws RemoteException {
		String res = "";
		// register
		if (registered_users.containsKey(user_name)) {
			res += "404 NOT FOUND";
		} else {
			registered_users.put(user_name, false);
			res += "200 OK";
		}
		return res;
	}

	@Override
	public String recordVote(String candidate, String user_name)
			throws RemoteException {
		String res = "";

		// vote
		Boolean vote_before = registered_users.get(user_name);
		if (!vote_before) {
			if (votes.containsKey(candidate)) {
				votes.put(candidate, votes.get(candidate) + 1);
			}
			registered_users.put(user_name, true);
			res += "200 OK";
		} else {
			res += "404 NOT FOUND";
		}
		return res;

	}

	@Override
	public String[] getCandidates(String user_name) {
		String[] candidates = new String[votes.size()];
		// get candidate
		int cnt = 0;
		for (String candidate : votes.keySet()) {
			candidates[cnt++] = candidate;
		}
		return candidates;

	}
}
