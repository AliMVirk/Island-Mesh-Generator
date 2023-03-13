package island.configuration;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Configuration {
    
    private CommandLine cmd;

    public Configuration(String[] args) throws ParseException {
        CommandLineParser parser = new DefaultParser();
        cmd = parser.parse(initializeOptions(), args);
        if (cmd.hasOption("h") || !cmd.hasOption("i") || !cmd.hasOption("o")) {
            printHelpMessage();
        }
    }

    private void printHelpMessage() {
        System.out.println("usage: island -i <file path> -o <file path>\n-h,--help               print this message\n-i <file path>          mesh input\n-mode,--mode <arg>      island generation mode\n-o <file path>          mesh output\n-shape,--shape <shape>  island shape");
        System.exit(0);
    }

    public String export(String optionKey) {
        return cmd.getOptionValue(optionKey);
    }

    private Options initializeOptions() {
        Options options = new Options();
        options.addOption("i", true, "mesh input");
        options.addOption("o", true, "mesh output");
        options.addOption("mode", "mode", true, "island generation mode");
        options.addOption("h", "help", false, "print this message");
        options.addOption("shape", "shape", true, "island shape");
        return options;
    }

}
