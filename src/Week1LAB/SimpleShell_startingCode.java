package Week1LAB;

import java.io.*;
import java.util.Arrays;

class SimpleShell {
	public static void main(String[] args) throws java.io.IOException {
		String commandLine;
		BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
		
		while (true) {
			// read what the user entered
			System.out.print("jsh>");
			commandLine = console.readLine();
			String[] commands = commandLine.split(" ");

            // TODO: adding a history feature

			// if the user entered a return, just loop again
			if (commandLine.equals("")) {
				continue;
			}
			
			// TODO: creating the external process and executing the command in that process
            //try {
                ProcessBuilder pb = new ProcessBuilder();
                pb.command(commands);
                Process p = pb.start();
                BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String coutput;
                while((coutput = br.readLine())!=null){
                    System.out.println(coutput);
                }
            //}catch (IOException E){
            //    System.out.println("Your command is not recognized! Enter again!");
            //}

			// TODO: modifying the shell to allow changing directories
		}
	}

}