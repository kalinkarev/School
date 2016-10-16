package bg.elsys.ip.socket.echo;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EchoClient {

	private static final String HOST_NAME = "localhost";
	private static final int PORT = 3004;
	
	public static void main(String[] args) throws UnknownHostException, IOException {

		try(Socket echoSocket = new Socket(HOST_NAME, PORT);
				PrintStream out = new PrintStream(echoSocket.getOutputStream());
				Scanner in = new Scanner(echoSocket.getInputStream());
				Scanner stdIn = new Scanner(System.in)) {
			String userInput;
			while ((userInput = stdIn.nextLine()) != null) { // dokato pishem ne6to
				if (userInput.equals("/time")) {
					Calendar cal = Calendar.getInstance();
			        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			        System.out.println( sdf.format(cal.getTime()) );
					//System.out.println("Nice to meet you");
				} else if (userInput.equals("/date")) {
					Date date = new Date();
					LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
					int year  = localDate.getYear();
					int month = localDate.getMonthValue();
					int day   = localDate.getDayOfMonth();
				
					System.out.println(day + "/" + month + "/" + year);
				} else if (userInput.equals("/myip")) {
					String command = null;
			        if(System.getProperty("os.name").equals("Linux"))
			            command = "ifconfig";
			        else
			            command = "ipconfig";
			        Runtime r = Runtime.getRuntime();
			        Process p = r.exec(command);
			        Scanner s = new Scanner(p.getInputStream());

			        StringBuilder sb = new StringBuilder("");
			        while(s.hasNext())
			            sb.append(s.next());
			        String ipconfig = sb.toString();
			        Pattern pt = Pattern.compile("192\\.168\\.[0-9]{1,3}\\.[0-9]{1,3}");
			        Matcher mt = pt.matcher(ipconfig);
			        mt.find();
			        String ip = mt.group();

			        System.out.println(ip);
				} else if (userInput.equals("/help")) {
					System.out.println("/time, /date, /myip, /help");
				} else {
					out.println(userInput);
					System.out.println("Echo: " + in.nextLine());
				}
			}
		}	
	}
}