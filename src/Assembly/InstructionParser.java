package Assembly;

import Assembly.AssemblyConstants.AssemblyConstants;
import Assembly.AssemblyExceptions.FunctionalExceptions.FunctionalTokenExceptions.WrongAssemblyLineException;
import Assembly.AssemblyExceptions.FunctionalExceptions.NonValidAssemblyInstructionException;
import Assembly.AssemblyExceptions.FunctionalExceptions.UndefinedSectionException;
import Assembly.AssemblyExceptions.FunctionalExceptions.WrongFilePathException;
import Assembly.AssemblyFunctionality.AssemblyFunctions;
import Assembly.AssemblyInstructions.Instruction;
import Assembly.AssemblyInstructions.TEXT_Instruction;
import Assembly.AssemblyTokens.EntryLabelToken;
import Assembly.AssemblyTokens.Token;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// TODO : javadoc
public class InstructionParser {
    private List<String> formattedCode;
    private List<Instruction> instructions;
    private int stateFlag;

    private Map<EntryLabelToken, Instruction> entryLabelToInstructionMap;

    // TODO : javadoc (method checks for unique keys and adds to the map if everything is fine)
    private void addEntryLabelToInstructionMap(EntryLabelToken entryLabel, TEXT_Instruction instruction) {
        for (Map.Entry<EntryLabelToken, Instruction> entry : entryLabelToInstructionMap.entrySet())
            if (entry.getKey().equals(entryLabel))
                throw new NonValidAssemblyInstructionException("\n'" + entryLabel + "' was already defined!");

        entryLabelToInstructionMap.put(entryLabel, instruction);
    }

    public InstructionParser(String filePath) throws IOException {
        if (filePath == null)
            throw new WrongFilePathException("\nNon-valid path for a file");

        this.formattedCode = new CodeFormatter(filePath).getFormattedCode();
        this.instructions = new ArrayList<>();
        this.entryLabelToInstructionMap = new HashMap<>();
        this.stateFlag = -1;

        precompile();
    }

    private void precompile() throws UndefinedSectionException {
        for (String line : formattedCode) {
            if (line.startsWith(AssemblyConstants.INCLUDE_HEADER)) {
                if (!line.equals(AssemblyConstants.INCLUDE_HEADER))
                    throw new UndefinedSectionException("\nUndefined section '" + line +
                            "'\nNo other instructions are allowed within section definition");

                stateFlag = AssemblyConstants.INCLUDE_FLAG;

            } else if (line.startsWith(AssemblyConstants.BSS_HEADER)) {
                if (!line.equals(AssemblyConstants.BSS_HEADER))
                    throw new UndefinedSectionException("\nUndefined section '" + line +
                            "'\nNo other instructions are allowed within section definition");

                stateFlag = AssemblyConstants.BSS_FLAG;

            } else if (line.startsWith(AssemblyConstants.DATA_HEADER)) {
                if (!line.equals(AssemblyConstants.DATA_HEADER))
                    throw new UndefinedSectionException("\nUndefined section '" + line +
                            "'\nNo other instructions are allowed within section definition");

                stateFlag = AssemblyConstants.DATA_FLAG;

            } else if (line.startsWith(AssemblyConstants.VAR_HEADER)) {
                if (!line.equals(AssemblyConstants.VAR_HEADER))
                    throw new UndefinedSectionException("\nUndefined section '" + line +
                            "'\nNo other instructions are allowed within section definition");

                stateFlag = AssemblyConstants.VAR_FLAG;

            } else if (line.startsWith(AssemblyConstants.TEXT_HEADER)) {
                if (!line.equals(AssemblyConstants.TEXT_HEADER))
                    throw new UndefinedSectionException("\nUndefined section '" + line +
                            "'\nNo other instructions are allowed within section definition");

                stateFlag = AssemblyConstants.TEXT_FLAG;

            } else if (line.startsWith(AssemblyConstants.INCLUDE_SEPARATOR)
                    || line.startsWith(AssemblyConstants.SECTION_SEPARATOR)) {
                throw new UndefinedSectionException("\nUndefined section '" + line +
                        "'\nAvailable sections: " + AssemblyConstants.getAvailableSections());

            } else if (stateFlag == AssemblyConstants.INCLUDE_FLAG) {
                // TODO : handle include section
                continue;

            } else if (stateFlag == AssemblyConstants.BSS_FLAG) {
                // TODO : handle bss section
                continue;

            } else if (stateFlag == AssemblyConstants.DATA_FLAG) {
                // TODO : handle data section
                continue;

            } else if (stateFlag == AssemblyConstants.VAR_FLAG) {
                // TODO : handle var section
                continue;

            } else if (stateFlag == AssemblyConstants.TEXT_FLAG) {
                // TODO : handle text section
                handleTEXT(line);
                continue;

            } else
                throw new UndefinedSectionException("\nUndefined section '" + line +
                        "'\nEmpty or undefined statement found");
        }
    }

    private Token[] tokenize(String line) {
        return AssemblyFunctions.tokenize(line);
    }

    private void handleTEXT(String line) {
        Token[] tokens = tokenize(line);
        instructions.add(new TEXT_Instruction(tokens, this));
    }

    // TODO : notify that there is a special token and save it
    public void notify(EntryLabelToken entryLabelToken, TEXT_Instruction container) {
        entryLabelToInstructionMap.put(entryLabelToken, container);
    }

    public static void main(String[] args) throws IOException {
        String path = "/home/hamudi/Developing/Repositories/H-Language/H-Language/IO/NumberPrinterAssembly.hlan";
        InstructionParser ip = new InstructionParser(path);
        for (Instruction instruction : ip.instructions)
            System.out.println(instruction);
    }
}
