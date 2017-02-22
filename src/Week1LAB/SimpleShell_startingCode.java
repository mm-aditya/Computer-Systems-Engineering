package Week1LAB;

import java.io.*;
import java.util.Arrays;

class SimpleShell {
	public static void main(String[] args) throws java.io.IOException {
		String commandLine;
		File currentWorkingdir = null;
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

			switch(commands[0]) {
                default:// TODO: creating the external process and executing the command in that process
                            try {
                                ProcessBuilder pb = new ProcessBuilder();
                                pb.command(commands);
                                pb.directory(currentWorkingdir);
                                Process p = pb.start();
                                BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
                                String coutput;
                                System.out.println("Working directory: "+pb.directory()+"\n");
                                while ((coutput = br.readLine()) != null) {
                                    System.out.println(coutput);
                                }
                            } catch (IOException E) {
                                System.out.println("Your command is not recognized! Enter again!");
                            }
                            break;

                case "cd":// TODO: modifying the shell to allow changing directories
                    try {
                        //System.out.println("CHANGING DIRECTORY\n");
                        ProcessBuilder pb = new ProcessBuilder();
                        pb.command(commands);
                        switch(commands[1]){
                            case "..":
                                    //System.out.println("Coming out\n");
                                    String temp = currentWorkingdir.toString();
                                    int ctr = temp.length()-1;
                                    //System.out.println("Ctr is: "+temp);
                                    while(ctr>0){
                                        if(temp.charAt(ctr)==File.separator.charAt(0)){
                                            temp = temp.substring(0,ctr);
                                            System.out.println("Sub string to change to: "+temp);
                                            ctr=-1;
                                        }
                                        else
                                        {
                                            //System.out.print(temp.charAt(ctr));
                                            ctr--;
                                        }
                                    }
                                    if (ctr==0) {
                                        //System.out.println("Cannot go further back");
                                        currentWorkingdir = null;
                                    }
                                    else{
                                        currentWorkingdir= new File(temp);
                                    }
                                    break;
                            default:
                                    //System.out.println("Going deeper\n");
                                    String name="";
                                    if(currentWorkingdir!=null) {
                                        name = currentWorkingdir.toString()+File.separator;
                                    }
                                    File tmp = new File(name+commands[1]);
                                    if(tmp.exists())
                                     currentWorkingdir = new File(name+commands[1]);
                                    else
                                        System.out.println("This directory does not exist!");
                                    break;
                        }
                        pb.directory(currentWorkingdir);
                        Process p = pb.start();
                        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
                        String coutput;
                        while ((coutput = br.readLine()) != null) {
                            System.out.println(coutput);
                        }
                    } catch (IOException E) {
                        System.out.println("Your command is not recognized! Enter again!");
                    }
                    break;

                case "history":

                    break;
            }




		}
	}

}