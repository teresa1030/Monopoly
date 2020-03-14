package checkpoint;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;

public class A1063343_Checkpoint6 {
	protected static ArrayList<Character> CharacterArray = new ArrayList<Character>();
	protected static ArrayList<Land> LandArray = new ArrayList<Land>();
	protected static String[] Round, TurnRole;
	protected static String LandComment;
	protected static int i = 0, j, k;

	public static void main(String[] args) throws IOException {

		//// TODO: Announce your GUI object to make the GUI ////
		//// TODO: For this work if you make every Character object to Random(), then
		//// you should plus the Round one and plus every Character.status one.
		//// Hint: How to put the image
		//// Hint: first you should announce a ImageIcon e.g. ImageIcon image = new
		//// ImageIcon(Imagepath);
		//// Hint: then put it into layout object what you want to display on. e.g.
		//// JButton btn = new JButton(image); or JLabel label = new JLabel(image);

		A1063343_GUI.Start();
		//QueryDB();
	
	}

	

	public static void Load(String filename, String filename2) throws IOException {
		//// TODO: You should load the variables from the file. ////
		CharacterArray.clear();
		LandArray.clear();
		BufferedReader file = new BufferedReader(new FileReader(filename));
		String f, r;
		String[] RoundTurn;
		r = file.readLine();
		RoundTurn = r.split(",");
		Round = RoundTurn[0].split(":");
		TurnRole = RoundTurn[1].split(":");
		k = Integer.parseInt(Round[1]);

		int start = 0;
		while ((f = file.readLine()) != null) {
			String[] fProp = f.split(",");
			Character character = new Character(Integer.parseInt(fProp[0]), Integer.parseInt(fProp[1]),
					Integer.parseInt(fProp[2]), Integer.parseInt(fProp[3]), fProp[4]);
			CharacterArray.add(character);
			if (Integer.parseInt(TurnRole[1]) == Integer.parseInt(fProp[1])) {
				i = start;
				// System.out.println(fProp[1]+"i:"+i+",start"+start);
			}
			start++;
		}

		BufferedReader file2 = new BufferedReader(new FileReader(filename2));
		LandComment = file2.readLine();
		String f2;
		while ((f2 = file2.readLine()) != null) {
			String[] f2Prop = f2.split(",");
	//		System.out.println(f2Prop[0]);
			Land land = new Land(Integer.parseInt(f2Prop[0]), Integer.parseInt(f2Prop[1]), 0, 0);
			LandArray.add(land);
		}
		file.close();
		file2.close();
	//	QueryDB();    
		download();
	}

	public static void Save(String filename, String filename2) throws IOException {
		//// TODO: You should save the changed variables into original data (filename).
		//// ////
		BufferedWriter bfw = new BufferedWriter(new FileWriter(filename));
		String RoundandTurn = ("Round:" + k + ",Turn:" + CharacterArray.get(i).CHARACTER_NUMBER);
		// TurnRole[1]=Integer.toString(CharacterArray.get(i).CHARACTER_NUMBER);
		bfw.write(RoundandTurn + "\n");
		for (Character character : CharacterArray) {
			String str = String.join(",", Integer.toString(character.location),
					Integer.toString(character.CHARACTER_NUMBER), Integer.toString(character.money),
					Integer.toString(character.status), character.IMAGE_FILENAME);
			bfw.write(str + "\n");
		}
		bfw.flush();
		
		BufferedWriter bfw2 = new BufferedWriter(new FileWriter(filename2));
		bfw2.write(LandComment+ "\n");
		for (Land land : LandArray) {
			String str2 = String.join(",", Integer.toString(land.PLACE_NUMBER),Integer.toString(land.owner));
			bfw2.write(str2 + "\n");
		}
		bfw2.flush();
		bfw.close();
		bfw2.close();

	}

	public static void Random() {
		//// TODO: while calling the Random function, Character.location should plus the
		//// random value, and Character.status should minus one.
		//// TODO: While Character.status more than zero(not include zero), Character
		//// can move(plus the random value).
		Random ran = new Random();
		j = (ran.nextInt(6) + 1);
		if (CharacterArray.get(i).status > 0) {
			CharacterArray.get(i).location = (CharacterArray.get(i).location + j) % 20;
			CharacterArray.get(i).status -= 1;
		}
	}
	public static void download() {
		int[] Land_number = {1,2,3,4,6,7,8,9,11,12,13,14,16,17,18,19};
	    int[] Land_price = {300,400,500,350,450,650,450,750,800,700,1100,1300,950,350,450,1150};
	    int[] Land_tolls = {50,100,30,20,50,80,150,35,60,70,40,50,20,40,50,200};
	    for(int n=0; n < LandArray.size();n++) {
	    	for(int index=0; index<16;index++) {
				if(LandArray.get(n).PLACE_NUMBER==Land_number[index]) {
		    		LandArray.get(n).LAND_PRICE = Land_price[index];
					LandArray.get(n).TOLLS = Land_tolls[index];
		    	}
	    	}   	
	    }	
	}
    
	public static void QueryDB() {
		String driver = "com.mysql.cj.jdbc.Driver";
		String protocol = "jdbc:mysql:";
		try {
			Class.forName(driver);
		} catch (Exception err) {
			err.printStackTrace(System.err);
			System.exit(0);
		}
		String url = "//140.127.220.220/CHECKPOINT?serverTimezone=GMT";
		String username = "checkpoint";
		String password = "ckppwd";
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(protocol + url, username, password);
			Statement s = conn.createStatement();
			ResultSet rs = null;

			rs = s.executeQuery("SELECT PLACE_NUMBER,LAND_PRICE,TOLLS FROM LAND;");
			while (rs.next()) {
				serach(rs);
			}

			rs.close();
			conn.close();

		} catch (SQLException err) {
			err.printStackTrace(System.err);
			System.exit(0);
		}

	}

	private static void serach(ResultSet rs) throws SQLException {
		int PLACE_NUMBER = rs.getInt("PLACE_NUMBER");
		int LAND_PRICE = rs.getInt("LAND_PRICE");
		int TOLLS = rs.getInt("TOLLS");
	//	 System.out.println("number: "+PLACE_NUMBER+" price: "+LAND_PRICE+" fee: "+TOLLS);
		 
		for (int i = 0; i < LandArray.size(); i++) {
			if (LandArray.get(i).PLACE_NUMBER == PLACE_NUMBER) {
				LandArray.get(i).LAND_PRICE = LAND_PRICE;
				LandArray.get(i).TOLLS = TOLLS;
		//		System.out.println("number: " + LandArray.get(i).PLACE_NUMBER + "  owner: " + LandArray.get(i).owner
			//			+ "  price: " + LandArray.get(i).LAND_PRICE + " fee: " + LandArray.get(i).TOLLS);
			}
		}

	}
}
