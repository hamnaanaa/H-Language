package Assembly;

import Assembly.AssemblyConstants.AssemblyConstants;
import Assembly.AssemblyExceptions.FunctionalExceptions.UndefinedSectionException;
import Assembly.AssemblyExceptions.FunctionalExceptions.WrongFilePathException;
import Assembly.AssemblyExceptions.FunctionalExceptions.WrongSectionException;
import Assembly.AssemblyFunctionality.Pair;

import java.io.IOException;
import java.util.ArrayList;

public class Preprocessor {
    private ArrayList<String> formattedCode;

    private ArrayList<String> includeSection;
    private ArrayList<Pair<String, Integer>> bssSection;
//    private ArrayList<> TODO : varSection (Map, Wrapper or Integer?)
//    private ArrayList<Map<>> TODO : dataSection (maybe Data-class that encapsulates the values with size?)
//    private ArrayList<String> TODO : textSection (maybe Map instead?)

    public Preprocessor(String filePath) throws IOException {
        if (filePath == null)
            throw new WrongFilePathException("\nNon-valid path for a file");

        this.formattedCode = new CodeFormatter(filePath).getFormattedCode();
    }

    private void precompile() {
        int flag = -1;

        for (String line : formattedCode) {
            if (line.startsWith(AssemblyConstants.INCLUDE_HEADER)) {
                if (!line.equals(AssemblyConstants.INCLUDE_HEADER))
                    throw new UndefinedSectionException("\nUndefined section '" + line +
                            "'\nNo other instructions are allowed within section definition");

                flag = AssemblyConstants.INCLUDE_FLAG;

            } else if (line.startsWith(AssemblyConstants.BSS_HEADER)) {
                if (!line.equals(AssemblyConstants.BSS_HEADER))
                    throw new UndefinedSectionException("\nUndefined section '" + line +
                            "'\nNo other instructions are allowed within section definition");

                flag = AssemblyConstants.BSS_FLAG;

            } else if (line.startsWith(AssemblyConstants.DATA_HEADER)) {
                if (!line.equals(AssemblyConstants.DATA_HEADER))
                    throw new UndefinedSectionException("\nUndefined section '" + line +
                            "'\nNo other instructions are allowed within section definition");

                flag = AssemblyConstants.DATA_FLAG;

            } else if (line.startsWith(AssemblyConstants.VAR_HEADER)) {
                if (!line.equals(AssemblyConstants.VAR_HEADER))
                    throw new UndefinedSectionException("\nUndefined section '" + line +
                            "'\nNo other instructions are allowed within section definition");

                flag = AssemblyConstants.VAR_FLAG;

            } else if (line.startsWith(AssemblyConstants.TEXT_HEADER)) {
                if (!line.equals(AssemblyConstants.TEXT_HEADER))
                    throw new UndefinedSectionException("\nUndefined section '" + line +
                            "'\nNo other instructions are allowed within section definition");

                flag = AssemblyConstants.TEXT_FLAG;

            } else if (line.startsWith(AssemblyConstants.INCLUDE_SEPARATOR)
                    || line.startsWith(AssemblyConstants.SECTION_SEPARATOR)) {
                throw new UndefinedSectionException("\nUndefined section '" + line +
                        "'\nAvailable sections: " + AssemblyConstants.getAvailableSections());

            } else if (flag == AssemblyConstants.INCLUDE_FLAG) {
                // TODO : handle include section
                continue;

            } else if (flag == AssemblyConstants.BSS_FLAG) {
                handleBSS(line);
                continue;

            } else if (flag == AssemblyConstants.DATA_FLAG) {
                // TODO : handle data section
                continue;

            } else if (flag == AssemblyConstants.VAR_FLAG) {
                // TODO : handle var section
                continue;

            } else if (flag == AssemblyConstants.TEXT_FLAG) {
                // TODO : handle text section
                continue;

            } else
                throw new UndefinedSectionException("\nUndefined section '" + line +
                        "'\nEmpty statement found");
        }
    }

    private void handleBSS(String line) throws WrongSectionException {
        if (line.startsWith(AssemblyConstants.INCLUDE_STATEMENT_SEPARATOR))
            throw new WrongSectionException("\nInclude statement separator found in BSS-Section: '" + line + "'");


    }
}
