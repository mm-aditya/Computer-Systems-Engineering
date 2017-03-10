package LAB4;

/**
 * Created by aditya on 2/3/2017.
 */


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

class FileOperation {

    private static File currentDirectory = new File(System.getProperty("user.dir") + "\\src");
    private static int parentDepth;
    public static void main(String[] args) throws Exception {

        String curdir = System.getProperty("user.dir") + "\\src";

        String commandLine;

        BufferedReader console = new BufferedReader
                (new InputStreamReader(System.in));

        while (true) {
            // read what the user entered
            System.out.print("jsh>");
            commandLine = console.readLine();

            // clear the space before and after the command line
            commandLine = commandLine.trim();

            // if the user entered a return, just loop again
            if (commandLine.equals("")) {
                continue;
            }
            // if exit or quit
            else if (commandLine.equalsIgnoreCase("exit") | commandLine.equalsIgnoreCase("quit")) {
                System.exit(0);
            }

            // check the command line, separate the words
            String[] commandStr = commandLine.split(" ");
            ArrayList<String> command = new ArrayList<String>();
            for (int i = 0; i < commandStr.length; i++) {
                command.add(commandStr[i]);
            }
            command.add(null);
            command.add(null);
            //System.out.println(command);
            switch(command.get(0)) {
                case "create": // TODO: implement code to handle create here
                    if (command.get(1)==null)
                        System.out.println("NO FILE NAME SPECIFIED");
                    Java_create(currentDirectory, command.get(1));
                    break;

                case "delete":// TODO: implement code to handle delete here
                    if (command.get(1)==null)
                        System.out.println("NO FILE NAME SPECIFIED");
                    Java_delete(currentDirectory, command.get(1));
                    break;

                case "display":// TODO: implement code to handle display here
                    if (command.get(1)==null)
                        System.out.println("NO FILE NAME SPECIFIED");
                    else {
                        Java_cat(currentDirectory, command.get(1));
                    }
                    break;

                case "list":// TODO: implement code to handle list here
                    Java_ls(currentDirectory, command.get(1),command.get(2));
                    break;

                case "find":// TODO: implement code to handle find here
                    System.out.println("Results of search: ");
                    Java_find(currentDirectory,command.get(1));
                    break;

                case "tree":// TODO: implement code to handle tree here
                    int depth;
                    if(command.get(1) == null)
                        depth = 1;
                    else
                        depth = Integer.parseInt(command.get(1));
                    if(command.get(1) != null)
                        parentDepth =  Integer.parseInt(command.get(1));
                    else
                        parentDepth = 1;
                    Java_tree(currentDirectory, depth,command.get(2));
                    break;

                // other commands

            }
            currentDirectory = new File(curdir);
            ProcessBuilder pBuilder = new ProcessBuilder(command);
            pBuilder.directory(currentDirectory);


//            try{
//                Process process = pBuilder.start();
//                // obtain the input stream
//                InputStream is = process.getInputStream();
//                InputStreamReader isr = new InputStreamReader(is);
//                BufferedReader br = new BufferedReader(isr);
//
//                // read what is returned by the command
//                String line;
//                while ( (line = br.readLine()) != null)
//                    System.out.println(line);
//
//                // close BufferedReader
//                br.close();
//            }
//            // catch the IOexception and resume waiting for commands
//            catch (IOException ex){
//                System.out.println(ex);
//                continue;
//            }


        }
    }

    /**
     * Create a file
     // @param dir - current working directory
     // @param command - name of the file to be created
     */
    private static void Java_create(File dir, String name) throws IOException{
        // TODO: create a file
        try {
            File file = new File(dir, name);
            file.createNewFile();
        }catch (IOException E){
            System.out.println(E);
        }
    }

    /**
     * Delete a file
     * @param dir - current working directory
     * @param name - name of the file to be deleted
     */
    private static void Java_delete(File dir, String name) {
        // TODO: delete a file
        File file = new File(dir, name);
        file.delete();
    }

    /**
     * Display the file
     * @param dir - current working directory
     * @param name - name of the file to be displayed
     */
    private static void Java_cat(File dir, String name) throws Exception{
        // TODO: display a file
        File file = new File(dir, name);
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader in = new BufferedReader(fileReader);
            String line;

            System.out.println("File "+name+" contains:");
            while((line = in.readLine())!=null)
                System.out.println(line);
            in.close();
        }catch(Exception E){
            System.out.println("FILE NOT FOUND");
        }

    }

    /**
     * Function to sort the file list
     * @param list - file list to be sorted
     * @param sort_method - control the sort type
     * @return sorted list - the sorted file list
     */
    private static File[] sortFileList(File[] list, String sort_method) {
        // sort the file list based on sort_method
        // if sort based on name
        if (sort_method.equalsIgnoreCase("name")) {
            Arrays.sort(list, new Comparator<File>() {
                public int compare(File f1, File f2) {
                    return (f1.getName()).compareTo(f2.getName());
                }
            });
        }
        else if (sort_method.equalsIgnoreCase("size")) {
            Arrays.sort(list, new Comparator<File>() {
                public int compare(File f1, File f2) {
                    return Long.valueOf(f1.length()).compareTo(f2.length());
                }
            });
        }
        else if (sort_method.equalsIgnoreCase("time")) {
            Arrays.sort(list, new Comparator<File>() {
                public int compare(File f1, File f2) {
                    return Long.valueOf(f1.lastModified()).compareTo(f2.lastModified());
                }
            });
        }
        return list;
    }

    /**
     * List the files under directory
     * @param dir - current directory
     * @param display_method - control the list type
     * @param sort_method - control the sort type
     */
    private static void Java_ls(File dir, String display_method, String sort_method) {
        // TODO: list files
        File[] list	= dir.listFiles();

        if(sort_method!=null)
            list = sortFileList(list,sort_method);

        if (display_method == null) {
            for	(File file: list)
            {	System.out.println(file.getName());}

        }
        else{
            for	(File file: list) {
                Date date = new Date(file.lastModified());
                System.out.println(String.format("%-20s\tSize: %-10d\tLast Modified: %s",file.getName(),file.length(),date.toString()));
            }
        }

    }

    /**
     * Find files based on input string
     * @param dir - current working directory
     * @param name - input string to find in file's name
     * @return flag - whether the input string is found in this directory and its subdirectories
     */
    private static boolean Java_find(File dir, String name) {
        boolean flag = false;
        // TODO: find files
        if(!dir.isDirectory() && dir.getName().contains(name)) {
            System.out.println(dir.getAbsolutePath());
            flag = true;
        }
        else if (dir.isDirectory()){
            File[] filelist = dir.listFiles();
            for(File file: filelist)
                Java_find(file,name);
        }
        return flag;
    }

    /**
     * Print file structure under current directory in a tree structure
     * @param dir - current working directory
     * @param depth - maximum sub-level file to be displayed
     * @param sort_method - control the sort type
     */
    private static void Java_tree(File dir, int depth, String sort_method) {
        // TODO: print file tree
        File[] list = dir.listFiles();
        String op;
        if(sort_method!=null)
            list = sortFileList(list,sort_method);

        for(File file: list){
            if(!file.isDirectory()) {
                op = spaceMaker(depth);
                System.out.println(op+file.getName());
            }
            else{
                op = spaceMaker(depth);
                System.out.println(op+file.getName());
                Java_tree(file,depth-1,sort_method);
            }
        }
    }

    // TODO: define other functions if necessary for the above functions
    private static String spaceMaker(int n){
        String op = "";
        for(int i =parentDepth; i > n;i--)
            op = op+" ";
        return op+"|-";
    }
}
