from MicroHamudi.Functions.CompilerFunctions import *
from MicroHamudi.Enums import Maps
from MicroHamudi.Exceptions.CompilerExceptions import *

# Preprocessor Section Constants
INCLUDE_FLAG = 0
BSS_FLAG = 1
DATA_FLAG = 2
VARIABLES_FLAG = 3
TEXT_FLAG = 4


class Preprocessor:
    """Class that represents a preprocessor (I. Stage of Compiling)"""

    def __init__(self, full_file_name, all_instructions,
                 all_available_sections, all_available_registry, all_available_registry_index,
                 variables_dict, pointers_res_dict, pointers_def_dict, labels_dict, jumps_dict):
        self.file_name = full_file_name
        self.all_instructions = all_instructions
        self.available_sections = all_available_sections
        self.available_registry = all_available_registry
        self.available_registry_index = all_available_registry_index
        self.variables = variables_dict
        self.variables_to_convert = set()
        self.pointers_res = pointers_res_dict
        self.pointers_def = pointers_def_dict
        self.labels = labels_dict
        self.jumps = jumps_dict

    def extract_sections(self):
        """Read the lines from @self.file_name and extract the sections"""
        # Read the file
        with open(self.file_name) as file:
            lines = file.readlines()

        # get code without comments and whitespace and only lowercase as a list of lines
        # TODO : test_cases.txt
        code = remove_comments_and_whitespace(lines)

        variables_section, text_section = self.precompile(code)

        return text_section

    def precompile(self, lines):
        flag = -1
        variables_section = []
        text_section = []
        for key, line in enumerate(lines):
            if '$$ include:' in line:
                if line != '$$ include:':
                    raise UndefinedSectionException("\nUndefined Section '" + line
                                                    + "'.\nInstructions are not allowed within section definition.")
                flag = INCLUDE_FLAG
                continue
            elif '.. bss:' in line:
                if line != '.. bss:':
                    raise UndefinedSectionException("\nUndefined Section '" + line
                                                    + "'.\nInstructions are not allowed within section definition.")
                flag = BSS_FLAG
                continue
            elif '.. data:' in line:
                if line != '.. data:':
                    raise UndefinedSectionException("\nUndefined Section '" + line
                                                    + "'.\nInstructions are not allowed within section definition.")
                flag = DATA_FLAG
                continue
            elif '.. variables:' in line:
                if line != '.. variables:':
                    raise UndefinedSectionException("\nUndefined Section '" + line
                                                    + "'.\nInstructions are not allowed within section definition.")
                flag = VARIABLES_FLAG
                continue
            elif '.. text:' in line:
                if line != '.. text:':
                    raise UndefinedSectionException("\nUndefined Section '" + line
                                                    + "'.\nInstructions are not allowed within section definition.")
                flag = TEXT_FLAG
                continue
            elif line.startswith('..') or line.startswith('$$'):
                raise UndefinedSectionException("\nUndefined Section '" + line
                                                + "'. Available sections:\n" + self.available_sections)
            elif flag == INCLUDE_FLAG:
                # TODO : $include section
                continue
            elif flag == BSS_FLAG:
                self.handle_bss(line)
                continue
            elif flag == DATA_FLAG:
                self.handle_data(line)
                continue
            elif flag == VARIABLES_FLAG:
                next_variable = self.extract_variable_assignment(line)
                variables_section.append(next_variable)
                continue
            elif flag == TEXT_FLAG:
                # TODO : .text section
                next_instruction = self.extract_instruction(line)
                text_section.append(next_instruction)
                continue
            else:
                raise WrongSectionException("\nUndefined Section for instruction '" + line
                                            + "'. Available sections:\n" + str(self.available_sections))

        return variables_section, text_section

    def handle_bss(self, line):
        """Check if line is a correct bss-segment instruction
         and add the label with its size of place to reserve to @self.pointers_res. Correct instruction:
            - label starts with '>' and ends with '<:'
            - there are exactly three tokens '>label<:', 'res:', '(expression)*'
            - second token must be 'res:'
            - label must be a valid variable name
            - expression must be evaluated as int (float WIP) and be greater than zero

            :raise:
                -> WrongSyntaxException:
                    if number of tokens != 3 (>label<: res: expression)
                    OR label doesn't start with '>'
                    OR label doesn't end wih '<:'
                    OR wrong reserved word (not 'res:') found
                    OR the variable name contains any forbidden character
                    OR if the variable name begins with a number
                    OR the variable name is any reserved word

                -> WrongStringFormatException:
                    if string's begin separator and end separator aren't equal

                -> WrongImmediateException:
                    if the expression given in the assignment is not a valid math expression
                    OR in general if the expression cannot be evaluated
                    OR the expression's value is equal to zero

                -> NonImplementedFunctionality:
                    if the expression was evaluated as float

                -> WrongExpressionFormatException:
                    if the expression isn't a String or an Integer
                    OR the expression is a String (strings are not supported in BSS-Segments)

                -> SegmentationFault:
                    if the expression's value is less than zero
            :return:
                add correct bss-segment instruction as {label: value} to @self.pointers_res
            :type:
                Void
        """
        instruction_tokens = split_instruction_with_spaces(line)

        # special cases for exceptions
        if len(instruction_tokens) != 3:
            raise WrongSyntaxException("\nBSS-Segment instruction must have the structure:"
                                       + "\n'>label<:', 'res:', '(expression)*'"
                                       + "\nGiven instruction: '" + line + "' consists of more or less than 3 tokens")
        if (not instruction_tokens[0].endswith('<:')) or (not instruction_tokens[0].startswith('>')):
            raise WrongSyntaxException("\nBSS-Segment instruction must have the structure:"
                                       + "\n'>label<:', 'res:', '(expression)*'"
                                       + "\nWrong label separation '" + instruction_tokens[0]
                                       + " in given instruction: '" + line + "'")
        if not instruction_tokens[1] == 'res:':
            raise WrongSyntaxException("\nBSS-Segment instruction must have the structure:"
                                       + "\n'>label<:', 'res:', '(expression)*'"
                                       + "\nWrong reserved word separation '" + instruction_tokens[1]
                                       + "in given instruction: '" + line + "'")

        # make sure label name is allowed
        label_name = ''
        if is_allowed_variable_name(instruction_tokens[0][1:-2], self.all_instructions, line):
            label_name = instruction_tokens[0][1:-2]

        # evaluate given expression (return a list with all expressions evaluated)
        expression = is_allowed_value(instruction_tokens[2], line)

        # multiple expressions or non-ints are not allowed in BSS-Segments
        if (len(expression) > 1) or (not isinstance(expression[0], int)):
            raise WrongExpressionFormatException(
                "\nOnly expressions that can be evaluated as int are allowed to be used in"
                " .bss-sections"
                + "\nNon-valid expression '" + str(expression)
                + "' found in line: '" + line + "'")

        # raise SegmentationFault if trying to initiate negative amount of cells
        if expression[0] < 0:
            raise SegmentationFault("\nNegative numbers are not allowed for reservation"
                                    + "\nGiven expression '" + instruction_tokens[2] + "' was evaluated as '"
                                    + str(expression) + "'")
        # raise WrongImmediateException if trying to initiate zero cells (makes no sense)
        if expression[0] == 0:
            raise WrongImmediateException("\nReservation of zero memory cell is not allowed:"
                                          + "\nGiven expression '" + instruction_tokens[2] + "' was evaluated as '"
                                          + str(expression) + "'")

        # add formatted bss-segment instruction to @self.pointers_res dictionary
        self.pointers_res[label_name] = expression

    def handle_data(self, line):
        """Check if line is a correct data-segment instruction
         and add the label with its size of place to reserve to @self.pointers_def. Correct instruction:
            - label starts with '>' and ends with '<:'
            - there are exactly three tokens '>label<:', 'def:', '(expression)*'
            - second token must be 'def:'
            - label must be a valid variable name
            - expression must be evaluated as:
             -- int (float WIP) and be greater than zero
             -- array (complex arrays are allowed)
             -- string (special case of an array)

             :raise:
                -> WrongSyntaxException:
                    if number of tokens != 3 (>label<: def: expression)
                    OR label doesn't start with '>'
                    OR label doesn't end wih '<:'
                    OR wrong reserved word (not 'def:') found
                    OR the variable name contains any forbidden character
                    OR if the variable name begins with a number
                    OR the variable name is any reserved word

                -> WrongStringFormatException:
                if string's begin separator and end separator aren't equal
                OR string wasn't closed correctly

                -> WrongImmediateException:
                    if the expression given in the assignment is not a valid math expression
                    OR in general if the expression cannot be evaluated

                -> NonImplementedFunctionality:
                    if the expression was evaluated as float

                -> WrongExpressionFormatException:
                    if the expression isn't a String, Integer or Array
                    OR the expression is empty

                -> WrongArrayFormatException:
                    if the array wasn't closed correctly
             :return:
                add correct data-segment instruction as {label: value} to @self.pointers_def
             :type:
                Void
        """
        instruction_tokens = split_instruction_with_spaces(line)

        # special cases for exceptions
        if len(instruction_tokens) != 3:
            raise WrongSyntaxException("\nDATA-Segment instruction must have the structure:"
                                       + "\n'>label<:', 'def:', '(expression)*'"
                                       + "\nGiven instruction: '" + line + "' consists of more or less than 3 tokens")
        if (not instruction_tokens[0].endswith('<:')) or (not instruction_tokens[0].startswith('>')):
            raise WrongSyntaxException("\nDATA-Segment instruction must have the structure:"
                                       + "\n'>label<:', 'def:', '(expression)*'"
                                       + "\nWrong label separation '" + instruction_tokens[0]
                                       + " in given instruction: '" + line + "'")
        if not instruction_tokens[1] == 'def:':
            raise WrongSyntaxException("\nDATA-Segment instruction must have the structure:"
                                       + "\n'>label<:', 'def:', '(expression)*'"
                                       + "\nWrong reserved word separation '" + instruction_tokens[1]
                                       + "in given instruction: '" + line + "'")

        # make sure label name is allowed
        label_name = ''
        if is_allowed_variable_name(instruction_tokens[0][1:-2], self.all_instructions, line):
            label_name = instruction_tokens[0][1:-2]

        # evaluate given expression (return a list of all expressions found and evaluated)
        expression = is_allowed_value(instruction_tokens[2], line, True)

        # add formatted data-segment instruction to @self.pointers_def dictionary
        self.pointers_def[label_name] = expression

    def extract_variable_assignment(self, line):
        """Extract next EQU-Instruction and add the variable name to @self.variables set

            :raise:
                -> WrongVariableAssignmentException:
                    if non-EQU instruction or whitespace found in a line
                    OR the instruction is not in the correct form 'var EQU value'

                -> WrongSectionException:
                    if any token from other section found in a line

                -> WrongSyntaxException:
                    if variable name is wrong
                    OR variable value isn't an instance of int
                    OR multiple equ assignments within one line
                    OR unknown instruction

                -> WrongImmediateException:
                    if given value cannot be evaluated as int or float

                -> NonImplementedFunctionalityException:
                    if given value is evaluated as float

                -> WrongLabelException:
                    if label is already in the set due to overlapping of labels and it's not a JCC-Instruction
            :return:
                EQU-Instruction as String within the code in '.. variables:' section
            :type:
                String
        """
        # check if include statement
        if line.startswith('::'):
            raise WrongSectionException("\nInclude Statement '" + line
                                        + "' must be defined within '$$ include:' section")
        # check if bss statement
        elif line.startswith('res:'):
            raise WrongSectionException("\nInstruction '" + line
                                        + "' must be defined within '.. bss:' section")
        # check if data statement
        elif line.startswith('def:'):
            raise WrongSectionException("\nInstruction '" + line
                                        + "' must be defined within '.. data:' section")

        # check if the line consists of three tokens (due to var(0) EQU(1) value(2))
        # make sure if using '(e*)' expressions that there is no space in between
        if count_tokens(line) == 3:
            # check if the given instruction is an 'EQU'-instruction
            line_tokens = split_instruction_with_spaces(line)
            if line_tokens[1] == 'equ':
                # check if a given variable name is a valid name
                if is_allowed_variable_name(line_tokens[0], self.all_instructions, line):
                    # check if a given value is a valid expression (try to assign)
                    line_tokens[2] = is_allowed_value(line_tokens[2], line)

                    # try to add the variable's name to @self.variables-dictionary with value and return the line
                    add_to_collection(self.variables, line_tokens[0], True, line_tokens[2])

                    line = ''
                    for token in line_tokens:
                        line += str(token) + ' '
                    return line.rstrip()

            else:
                # Wrong instruction found
                raise WrongVariableAssignmentException("\nDeclaration must have the form 'var equ (value)*'."
                                                       "\nGiven instruction: '" + line + "'")
        else:
            if line.count('equ') > 1:
                raise WrongSyntaxException("\nMultiple declarations within one line are not allowed."
                                           "\nGiven instruction: '" + line + "'")
            else:
                raise WrongSyntaxException("\nDeclaration must have the form 'var equ (value)*'."
                                           "\nGiven instruction: '" + line + "'")

    def extract_instruction(self, line):
        """Extract next Assembly-Instruction and replace all macros found with their actual value

            :raise:
                -> WrongSectionException:
                    if line starts with include statement '::'
                    OR line starts with reserve statement 'res:'
                    OR line starts with definition statement 'def:'
                    OR any of line tokens is 'equ'
                -> WrongLabelException:
                    SEE :handle_label_declaration
                    AND :handle_jump_instr
                -> WrongSyntaxException:
                    if missing comma between operands found
                    OR a reserved separator '%%' was used as op
                -> WrongInstructionException:
                    if unknown operands found in given instruction
                    OR wrong parenthesis or braces usage
                    OR wrong amount of parameters (tokens) in given instruction
            :return:
                Assembly-Instruction as String within the code in '.. text:' section
            :type:
                String
        """

        # split into tokens
        instruction_tokens = split_instruction_with_spaces(line)
        op = instruction_tokens[0]

        # check if include statement
        if line.startswith('::'):
            raise WrongSectionException("\nInclude Statement '" + line
                                        + "' must be defined within '$$ include:' section")
        # check if bss statement
        elif line.startswith('res:'):
            raise WrongSectionException("\nInstruction '" + line
                                        + "' must be defined within '.. bss:' section")
        # check if data statement
        elif line.startswith('def:'):
            raise WrongSectionException("\nInstruction '" + line
                                        + "' must be defined within '.. data:' section")
        # check if variable assignment
        elif 'equ' in instruction_tokens:
            raise WrongSectionException("\nInstruction '" + line
                                        + "' must be defined within '.. variables:' section")

        # if the line is a label check if it starts with '%%', ends with ':' and consists of only two tokens
        if op == '%%':
            self.handle_label_declaration(line)
            return line
        # if number of tokens equals the number of expected operands for a given op + 1 (OP is a token as well)
        elif len(instruction_tokens) == Maps.num_operands(op, line) + 1:
            if self.is_jump_instr(op):
                return op + " " + self.handle_jump_instr(instruction_tokens[1], line)
            elif self.is_valid_op(op):
                # Number of operands + OP
                operand_num = Maps.num_operands(op, line)
                formatted_output = op + " "
                for operand_index in range(operand_num):

                    if operand_index + 1 < operand_num:
                        # [0] index in instruction tokens is an op
                        # check if all comments except the last one are divided with a comma
                        if not instruction_tokens[operand_index + 1].endswith(','):
                            raise WrongSyntaxException("\nEach operand must be divided with a comma"
                                                       + "\nNo comma found between '"
                                                       + instruction_tokens[operand_index + 1] + "' and '"
                                                       + instruction_tokens[operand_index + 2]
                                                       + "'\nGiven instruction: '" + line + "'")
                        formatted_output += self.handle_operand(instruction_tokens[operand_index + 1][:-1], line) + " "
                    else:
                        formatted_output += self.handle_operand(instruction_tokens[operand_index + 1], line) + " "

                return formatted_output.rstrip()

            else:
                if '%%' in op:
                    raise WrongSyntaxException("\n'%%' is a reserved separator and cannot be a part of instruction"
                                               + "\nGiven instruction: '" + line + "'")
                else:
                    raise WrongInstructionException("\nUnkown operands in given instruction: '" + line + "'")
        else:
            all_tokens = ''
            for token in instruction_tokens:
                all_tokens += "'" + token + "' "
            if ('[' in line or ']' in line) or ('(' in line or ')' in line):
                raise WrongInstructionException("\nInstruction '" + line + "' must contain three or less tokens"
                                                + "\nGiven tokens: " + all_tokens.rstrip() + " -> "
                                                + str(len(instruction_tokens)) + " tokens with "
                                                + str(line.count(',')) + ' comma'
                                                + "\nNon-valid parenthesis or braces usage: Make sure there is no space"
                                                  " in between")
            else:
                raise WrongInstructionException("\nInstruction '" + line + "' must contain "
                                                + str(Maps.num_operands(op, line) + 1) + " tokens"
                                                + "\nGiven tokens: " + all_tokens.rstrip() + " -> "
                                                + str(len(instruction_tokens)) + " tokens with "
                                                + str(line.count(',')) + ' comma')

    def handle_label_declaration(self, line):
        """Handle label declaration in @line by splitting the line with spaces and adding the label_name. Check:
            - if the declaration consists of exactly two tokens ('%%' and 'label_name:')
            - if label_name ends with ':'
            - if label_name is a valid name and neither empty nor a reserved word

            :raise:
                -> WrongLabelException:
                    if the label_name is not valid
                    OR the declaration does not end with ':'
                    OR the label_name is empty or a reserved word
                    OR more than two tokens were found (e.g. due to other instruction or whitespace in label_name
                    OR if label is already in the set due to overlapping of labels (see :add_to_collection for more)
            :return:
                nothing (label_name is added to the @self.labels)
            :type:
                None
        """
        instruction_tokens = split_instruction_with_spaces(line)
        # check if the line consists of only two tokens
        if len(instruction_tokens) == 2 and instruction_tokens[0] == '%%':
            # check if label declaration ends with ':'
            if instruction_tokens[1].endswith(':'):
                label_name = instruction_tokens[1][:-1]
                # if not an empty label and valid label name
                if is_allowed_variable_name(label_name, self.all_instructions, line):
                    add_to_collection(self.labels, label_name, True, -1)
                else:
                    raise WrongLabelException("\nNon-valid label"
                                              + "\nGiven label declaration: '" + line + "'")
            else:
                raise WrongLabelException("\nEach label must end with ':'"
                                          "\nGiven Label: '" + instruction_tokens[1] + "'")
        else:
            # extract all tokens
            all_tokens = ''
            for token in instruction_tokens:
                all_tokens += "'" + token + "' "
            raise WrongLabelException("\nOnly two tokens are allowed as label: '%%' and 'label_name' without "
                                      "spaces" + "\nGiven tokens: " + all_tokens.rstrip() + " -> "
                                      + str(len(instruction_tokens)) + " tokens in instruction '"
                                      + line + "'")

    def handle_jump_instr(self, jmp_label, line):
        """Handle given jmp_label and return the label if:
            - label starts with '%'
            - label ends with ':'
            - label is an expression and can be evaluated

            :raise:
                -> WrongLabelException:
                    if string label doesn't start with '%'
                    OR doesn't end with ':'
                    OR given label is an expression that cannot be evaluated
            :return:
                jump label if correct formatted
            :type:
                String (evaluated expression if expression given)
        """
        # if the label starts with '%' -> string label
        if jmp_label.startswith('%'):
            # check if ends with ':'
            if not jmp_label.endswith(':'):
                raise WrongLabelException("\nJump label must end with ':' separator"
                                          + "Given label: '" + jmp_label + "' in line: '" + line + "'")
            if not is_allowed_variable_name(jmp_label[1:-1], self.all_instructions, line):
                raise WrongLabelException("\nWrong jump label found: '" + jmp_label + "'"
                                          + "\nGiven line: '" + line + "'")

            # try to add to the list (multiple labels are allowed because these are only jumps
            add_to_collection(self.jumps, jmp_label[1:-1], False, -1)
            return jmp_label
        else:
            # if not a string label -> try to evaluate as a constant address
            try:
                if not isinstance(is_allowed_value(jmp_label), int):
                    raise WrongSyntaxException("\nLabel must have the form '%...:' or be an expression that can be"
                                               " evaluated as int"
                                               + "\nFound label '" + str(jmp_label) + "' in line: '" + line + "'")
                return str(is_allowed_value(jmp_label))
            except (NameError, SyntaxError, NonImplementedFunctionalityException):
                # if expression evaluation failed
                raise WrongLabelException("\nJump label must start with '%' separator or be an int expression")

    def handle_operand(self, operand, line):
        """Handle given operand and return an accordingly formatted operand

            :raise:
                -> WrongOperandException:
                    if operand contains ':'-separator for registry access but consists of more than two tokens
            :return:
                formatted operand
            :type:
                String
        """
        if operand.startswith('%%'):
            raise WrongSyntaxException("\n'%%' is a reserved separator and cannot be a part of instruction"
                                       + "\nGiven instruction: '" + line + "'")
        elif operand.startswith('%'):
            raise WrongOperandException("\nLabels as operands are only allowed with JCC-instructions"
                                        + "\nGiven operand: '" + operand + "'"
                                        + " in line: '" + line + "'")

            # check if include statement
        elif operand.startswith('::'):
            raise WrongSectionException("\nInclude Statement '" + line
                                        + "' must be defined within '$$ include:' section")
            # check if bss statement
        elif operand.startswith('res:'):
            raise WrongSectionException("\nInstruction '" + line
                                        + "' must be defined within '.. bss:' section")
            # check if data statement
        elif operand.startswith('def:'):
            raise WrongSectionException("\nInstruction '" + line
                                        + "' must be defined within '.. data:' section")

        # check if a valid string as an operand
        if operand.startswith('"') or operand.startswith("'"):
            return is_valid_string(operand, line)

        # TODO : if '>..< operand -> handle a pointer

        # else check if a colon in a variable (might me a registry access (reg:index)
        elif ':' in operand:
            if not (operand.endswith('"') or operand.endswith("'")):
                # then split the operand
                colon_tokens = operand.split(':')
                # if not two tokens -> raise an exception
                if len(colon_tokens) != 2:
                    raise WrongOperandException("\nWrong operand '" + operand + "' given"
                                                + "\nRegistry access must be in form 'reg:index'"
                                                + "\nGiven instruction: '" + line + "'")
                else:
                    # else extract two tokens
                    registry_name, registry_index = colon_tokens[0], colon_tokens[1]
                    # check if a valid registry name (see @AVAILABLE_REGISTRY in CompilerFunctions)
                    if registry_name not in self.available_registry:
                        raise WrongRegistryAccessException("\nNon-valid registry name '" + registry_name + "'"
                                                           + " in line '" + line + "'\nAvailable registry: "
                                                           + str(self.available_registry)
                                                           .replace('[', '').replace(']', ''))
                    # check if a valid registry index (see @AVAILABLE_REGISTRY_INDEX in CompilerFunctions)
                    elif registry_index not in self.available_registry_index:
                        raise WrongRegistryAccessException("\nNon-valid registry index '" + registry_index + "'"
                                                           + ". Available registry index: "
                                                           + str(self.available_registry_index)
                                                           .replace('[', "'").replace(']', "'"))
                    else:
                        return operand
            else:
                raise WrongStringFormatException("\nOnly a string can end with a string separator"
                                                 + "\nGiven operand >" + operand + "< in line: >"
                                                 + line + "<")

        try:
            # try to evaluate given operand as an expression
            # TODO : make sure evaluation with strings is ok
            if is_allowed_value(operand, line):
                return str(is_allowed_value(operand))
        except WrongImmediateException:
            # if not possible -> save it as a variable for later check
            if is_allowed_variable_name(operand, self.all_instructions, line):
                add_to_collection(self.variables_to_convert, operand, False)
                return operand

        raise WrongInstructionException("\nUndefined operand '" + operand + "'"
                                        + "\nGiven instruction: '" + line + "'")

    def is_valid_op(self, op):
        """Check if given op is a valid op (is in @self.all_instructions

            :return:
                True if valid op
            :type:
                Boolean
        """
        return op in self.all_instructions

    def is_jump_instr(self, op):
        """Check if given op is a jump instruction (starts with J.. and is in all instructions)

            :return:
                True if jump instr
            :type:
                Boolean
        """
        return op.lower().startswith('j') and self.is_valid_op(op)
