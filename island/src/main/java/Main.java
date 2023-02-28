import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Main {
    
    public static void main(String[] args) throws ParseException {
        
        Options options = new Options();
        options.addOption("i", true, "mesh input");
        options.addOption("o", true, "mesh output");
        options.addOption("m", "mode", true, "island generation mode");
        options.addOption("h", "help", false, "print this message");
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        if (cmd.hasOption("h") || !cmd.hasOption("i") || !cmd.hasOption("o")) {
            System.out.println("usage: island -i <file path> -o <file path>\n-h,--help           print this message\n-i <file path>      mesh input\n-m,--mode <arg>     island generation mode\n-o <file path>      mesh output");
            return;
        }
        

    }
}
