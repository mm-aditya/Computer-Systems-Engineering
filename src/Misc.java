
import java.io.File;

/**
 * Created by aditya on 26/2/2017.
 */
public class Misc {
    public static void main(String[] args) {
        File currentDirectory = new File(System.getProperty("user.dir"));
        File file1 = new File(currentDirectory+"\\src");
        File[] list	=file1.listFiles();
        for	(File file: list)
        {	System.out.println(file.getName());}

        System.out.println(currentDirectory.getAbsolutePath());
    }
}
