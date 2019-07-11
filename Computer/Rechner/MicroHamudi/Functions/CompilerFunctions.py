# Compiler Constants
from string import ascii_letters, digits, whitespace, hexdigits

from MicroHamudi.Exceptions.CompilerExceptions import *

AVAILABLE_SECTIONS = ["'$$ include:'",
                      "'.. bss:'",
                      "'.. data:'",
                      "'.. variable:'",
                      "'.. text:'"]
AVAILABLE_REGISTRY = ['a', 'b']
AVAILABLE_REGISTRY_INDEX = [str(index) for index in range(16)]
ALLOWED_CHARS = ascii_letters + digits + '_'
ALLOWED_DIGITS = hexdigits + '+' + '-' + '/' + '*' + '(' + ')' + "%"
RESERVED_CHARS = '..' + '$$' + '%%' + '%' + ':' + '::'
STRING_SEPARATOR = ["'", '"']
STRING_ESCAPE_SEQUENCE = '\\'
ARRAY_OPENBRACKET_SEPARATOR = '['
ARRAY_CLOSEBRACKET_SEPARATOR = ']'
EXPRESSION_LEFT_PARENTHESIS = '('
EXPRESSION_RIGHT_PARENTHESIS = ')'
LONG_COMMENT_OPEN = '{'
LONG_COMMENT_CLOSE = '}'
SINGLE_LINE_COMMENT = '#'
WHITESPACE = ['\n', ' ', '\t', '\r', '\x0b', '\x0c']


def get_available_sections():
    """Get the list of all available sections in H-Language

        :return:
            a list of all defined by compiler sections for source code
        :type:
            List
    """
    return AVAILABLE_SECTIONS


def get_available_registry_index():
    """Get the list of all available registry index in H-Language

        :return:
            a list of all defined by compiler registry indices for source code
        :type:
            List
    """
    return AVAILABLE_REGISTRY_INDEX


def get_available_registry():
    """Get the list of all available registry in H-Language

        :return:
            a list of all defined by compiler registry for source code
        :type:
            List
    """
    return AVAILABLE_REGISTRY


def remove_comments_and_whitespace(lines):
    """Remove all whitespace and comments defined by standards:
        1.  { ... } -> long comment over multiple lines
        2.  # ... -> single-line comment

        :return:
            list of instructions (code) without comments
        :type:
            List
    """
    # Flag for long comment
    is_longcomment = False
    # Formatted code for output
    code_without_comments = []

    for line in lines:
        # Iterate over each line
        # if long comment flag is true -> handle long comment's next line
        # else handle normal next line
        if is_longcomment:
            is_longcomment = handle_long_comment(code_without_comments, line, True)
        else:
            # check if this line contains long comment
            if LONG_COMMENT_OPEN in line:
                # handle new long comment
                is_longcomment = handle_long_comment(code_without_comments, line, False)
                continue
            # check if this line contains single-line comment
            elif SINGLE_LINE_COMMENT in line:
                # remove comment part after '#' and whitespace
                line = line[:line.find(SINGLE_LINE_COMMENT)]
                line = tokenize_and_refactor(line)
                # add if not an empty line
                if line:
                    if LONG_COMMENT_CLOSE in line:
                        raise WrongCommentSectionException("\nWrong amount of closing '" + LONG_COMMENT_CLOSE
                                                           + "' comment separators")
                    else:
                        code_without_comments.append(line)
            elif LONG_COMMENT_CLOSE in line:
                raise WrongCommentSectionException("\nWrong amount of closing '" + LONG_COMMENT_CLOSE
                                                   + "' comment separators")
            else:
                line = tokenize_and_refactor(line)
                # add if not an empty line
                if line:
                    code_without_comments.append(line)

    return code_without_comments


def handle_long_comment(list_to_add, line, begins_as_comment):
    """Handle long comment { ... } : the line may:
        - begin with a comment separator
            (:param begins_as_comment -> False)
        - be a next comment line
            (:param begins_as_comment -> True)
        see test_cases.txt for all possible tests

        :raise:
            -> WrongCommentSectionException:
                if the comment separation of the line is not valid
        :return:
             does this line end as a comment?
        :type:
            Boolean
    """
    # if the line begins as a comment, there was already one opening comment separator
    if begins_as_comment:
        open_comment_counter = 1
    else:
        open_comment_counter = 0

    # if the line is a next comment line
    if begins_as_comment:
        # if there is no closing comment separator -> the whole line is a comment
        if LONG_COMMENT_CLOSE not in line:
            return True
        else:
            # check if the separation of comments is valid (e.g. no '{comment}}'
            check_comment_separators(line, begins_as_comment)

            instructions_between = []
            while LONG_COMMENT_CLOSE in line:
                # cut from the next close comment separator
                line = line[line.find(LONG_COMMENT_CLOSE) + 1:]
                # and decrement the counter
                open_comment_counter -= 1
                # if there is an open comment separator add everything between as an instruction (e.g. ..c} instr {..
                if line.find(LONG_COMMENT_OPEN) != -1:
                    instructions_between.append(line[:line.find(LONG_COMMENT_OPEN)])
                    # and increment the counter
                    open_comment_counter += 1

            # if there is something left at the end and the counter is 0 -> add it
            if line and open_comment_counter == 0:
                instructions_between.append(line)

            # divide instructions into different lines if there are separated between different comments
            # e.g. ..} instr_1 {..} instr_2 {..
            output_line = ''
            for instr in instructions_between:
                instr = tokenize_and_refactor(instr)
                if instr:
                    # '||' is only a placeholder for division
                    output_line += instr + '||'

            # divide using the placeholder '||'
            formatted_lines = tokenize_and_refactor(output_line).split('||')

            # add each instruction found in between
            for formatted_line in formatted_lines:
                if SINGLE_LINE_COMMENT in formatted_line:
                    formatted_line = formatted_line.split(SINGLE_LINE_COMMENT)[0]
                if LONG_COMMENT_OPEN in formatted_line or LONG_COMMENT_CLOSE in formatted_line:
                    raise WrongCommentSectionException("\nWrong amount of closing '" + LONG_COMMENT_CLOSE
                                                       + "' comment separators")
                if formatted_line:
                    list_to_add.append(formatted_line)

            # return if the line ends as a comment
            return open_comment_counter != 0

    else:
        # similar to the first case
        check_comment_separators(line, begins_as_comment)

        instructions_between = []
        while LONG_COMMENT_OPEN in line:
            instructions_between.append(line[:line.find(LONG_COMMENT_OPEN)])
            line = line[line.find(LONG_COMMENT_OPEN) + 1:]
            open_comment_counter += 1

            if line.find(LONG_COMMENT_CLOSE) != -1:
                line = line[line.find(LONG_COMMENT_CLOSE) + 1:]
                open_comment_counter -= 1

        if line and open_comment_counter == 0:
            instructions_between.append(line)

        output_line = ''
        for instr in instructions_between:
            instr = tokenize_and_refactor(instr)
            if instr:
                output_line += instr + '||'

        formatted_lines = tokenize_and_refactor(output_line).split('||')
        for formatted_line in formatted_lines:
            if SINGLE_LINE_COMMENT in formatted_line:
                formatted_line = formatted_line.split(SINGLE_LINE_COMMENT)[0]
            if LONG_COMMENT_OPEN in formatted_line or LONG_COMMENT_CLOSE in formatted_line:
                raise WrongCommentSectionException("\nWrong amount of closing '" + LONG_COMMENT_CLOSE
                                                   + "' comment separators")
            if formatted_line:
                list_to_add.append(formatted_line)

        return open_comment_counter != 0


def check_comment_separators(line, begins_as_comment):
    """Check if :param:line's comment separation is valid

        :raise:
            -> WrongCommentSectionException:
                if the comment separation of the line is not valid
        :return:
            nothing
        :type:
            None
    """
    if begins_as_comment:
        counter = 1
    else:
        counter = 0

    for char in line:
        if char == LONG_COMMENT_OPEN:
            counter += 1
        elif char == LONG_COMMENT_CLOSE:
            counter -= 1

        if counter < 0:
            raise WrongCommentSectionException("\nWrong amount of closing '" + LONG_COMMENT_CLOSE
                                               + "' comment separators")


def count_tokens(line):
    """Count each token ignoring whitespace (Strings and expressions with parenthesis allow using whitespaces)

        :return:
            number of tokens
        :type:
            Int
    """
    counter = 0
    for element in split_instruction_with_spaces(line):
        # if not null after removing all whitespace
        if tokenize_and_refactor(element):
            counter += 1

    return counter


def extract_label_name(line, isLabel=True):
    """Extract lable name from '%% label_name:'
        or 'JCC %label_name:' depending of flag @isLabel

        :return:
            lable name with no whitespace and in lowercase
        :type:
            String

    """
    if isLabel:
        return tokenize_and_refactor(line[line.find('%') + 2:line.find(':')])
    else:
        return tokenize_and_refactor(line[line.find('%') + 1:line.find(':')])


def add_to_collection(collection, label, isLabel=True, value=None):
    """Check if the label was already in this collection:
        -> add it if not and add the given value if the collection is a dictionary
        -> raise WrongLabelException if yes due to overlapping labels and it's a lable and not JCC-instruction

        :raise:
            -> WrongLabelException:
                if label is already in the set due to overlapping of labels and it's not a JCC-Instruction
        :return:
            None
        :type:
            None

    """
    if isinstance(collection, set):
        if label in collection and isLabel:
            raise WrongLabelException("\nThe label '" + label + "' was already defined")
        else:
            collection.add(label)

    elif isinstance(collection, dict):
        if label in collection.keys() and isLabel:
            raise WrongLabelException("\nThe label '" + label + "' was already defined")
        else:
            collection[label] = value


def is_allowed_variable_name(variable_name, all_instructions, line=None):
    """Check if @variable_name contains any syntax error

        :raise:
            -> WrongSyntaxException: (if line given, print the line in which the exception happened
                if the variable name contains any forbidden character
                OR if the variable name begins with a number
                OR the variable name is any reserved word
        :return:
            True
        :type:
            Boolean
    """

    if not variable_name:
        raise WrongSyntaxException("\nEmpty variable names are not allowed. Found an empty name in line: '"
                                   + line + "'")

    # check if the first char is a number
    if variable_name[0] in digits:
        if line:
            raise WrongSyntaxException(
                "\nThe variable name is not allowed to begin with a number: '" + variable_name + "'"
                + "\nGiven instruction: '" + line + "'.")
        else:
            raise WrongSyntaxException(
                "\nThe variable name is not allowed to begin with a number: '" + variable_name + "'")

    # check if the variable name is an instruction (reserved word)
    if variable_name in all_instructions:
        if line:
            raise WrongSyntaxException("\n'" + variable_name + "' is a reserved word for an existing instruction"
                                       + "\nGiven instruction: '" + line + "'")
        else:
            raise WrongSyntaxException("\n'" + variable_name + "' is a reserved word for an existing instruction")

    # check if the variable name contains any forbidden chars
    for char in variable_name:
        if char not in ALLOWED_CHARS:
            if line:
                raise WrongSyntaxException(
                    "\n'" + char + "' is not allowed as variable name in '" + variable_name + "'"
                    + "\nGiven instruction: '" + line + "'")
            else:
                raise WrongSyntaxException(
                    "\n'" + char + "' is not allowed as variable name in '" + variable_name + "'")

    return True


def is_valid_string(string, line=None):
    """Check if given @string in form "str" or 'str' is a defined string for a variable and check all escape sequences

            :raise:
                -> WrongStringFormatException:
                    if given string begins and ends not with string separator
                    OR with two different separators
                    OR if the string separator found within the string without an escape sequence
            :return:
                string if a valid one
            :type:
                String
        """
    if not ((string.startswith('"') and string.endswith('"')) or (string.startswith("'") and string.endswith("'"))):
        if line:
            raise WrongStringFormatException("\nA string must begin and end with same string separators: (\' or \")"
                                             + "\nGiven operand >" + string + "< in line: '" + line + "'")
        else:
            raise WrongStringFormatException("\nA string must begin and end with same string separators: (\' or \")"
                                             + "\nGiven operand >" + string + "<")
    # remove separators
    string_separator = string[0]
    string = string[1:-1]

    if string[-1] == '\\':
        raise WrongStringFormatException("\nString separator wasn't found without escape sequence: >" + string
                                         + "<\nGiven instruction: >" + line + "<")

    # check if separator used within the string without an escape sequence
    for key, char in enumerate(string):
        if char == string_separator:
            # if previous char was not an escape sequence or it was the first char
            if key - 1 < 0 or string[key - 1] != '\\':
                raise WrongStringFormatException("\nString separator found without escape sequence: >" + string
                                                 + "<\nGiven instruction: >" + line + "<")

    return '"' + string + '"'


# noinspection PyDefaultArgument
def is_allowed_value(value, line=None, arrays_allowed=False, recursive=False, value_list=[]):
    """Check if given @value is a defined value for a variable

        :raise:
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
            list of all evaluated expressions if valid math expressions (bin/oct/dec/hex), strings or arrays found
        :type:
            List
    """
    if not value:
        raise WrongExpressionFormatException("\nEmpty expression found > " + str(value) + " <"
                                             + "\nGiven line: '" + line + "'")

    if not recursive:
        value_list = []

    if value[0] in STRING_SEPARATOR:
        if value[-1] in STRING_SEPARATOR:
            if value[0] == value[-1]:
                # add each character in string to a list to return
                # ALTERNATIVE: STRINGS AS CHAR ARRAYS: for char in value[1:-1]:
                value_list.append(value[1:-1])

                if not recursive:
                    return value_list
            else:
                raise WrongStringFormatException("\nDifferent string separators found: >" + str(value) + "<"
                                                 + "\nGiven line: '" + line + "'")
        else:
            raise WrongStringFormatException("\nString was opened but not closed correctly: >" + str(value) + "<"
                                             + "\nGiven line: '" + line + "'")

    elif arrays_allowed and value[0] == ARRAY_OPENBRACKET_SEPARATOR:
        if value[-1] == ARRAY_CLOSEBRACKET_SEPARATOR:
            array_values = split_instruction_with_spaces(value[1:-1], ',')
            for expression in array_values:
                # evaluate each element in given array recursively (arrays in arrays allowed for testing purposes)
                is_allowed_value(tokenize_and_refactor(expression), line, True, True, value_list)

            if not recursive:
                return value_list

        else:
            raise WrongArrayFormatException("\nArray was opened but not closed correctly: >" + str(value) + "<"
                                            + "\nGiven line: '" + line + "'")

    else:
        try:
            # make sure no whitespace is in-between
            value = value.replace('_', '')
            value = evaluate_number(value)

            if not isinstance(value, int):
                if isinstance(value, float):
                    if line:
                        raise NonImplementedFunctionalityException("\nGiven assignment: '" + line + "'"
                                                                   + "\nThe value must be an instance of int"
                                                                   + "\nFloating Point is still WIP. "
                                                                   + "Thanks for waiting!")
                    else:
                        raise NonImplementedFunctionalityException("\nThe value must be an instance of int."
                                                                   "\nFloating Point is still WIP. Thanks for waiting!")
                else:
                    if line:
                        raise WrongExpressionFormatException("\nThe value must be an instance of int."
                                                             "\nWrong value given: '" + str(value)
                                                             + "' in assignment '" + line + "'.")
                    else:
                        raise WrongExpressionFormatException("\nThe value must be an instance of int."
                                                             "\nWrong value given: '" + str(value) + "'.")

            value_list.append(value)
            if not recursive:
                return value_list
        except (NameError, SyntaxError):
            if line:
                raise WrongImmediateException(
                    "\nOnly numbers (bin/oct/dec/hex), math expressions and strings are allowed as values for"
                    " variables."
                    "\nGiven value: '" + str(value) + "' in assignment '" + line + "'.")
            else:
                raise WrongImmediateException(
                    "\nOnly numbers (bin/oct/dec/hex), math expressions and strings are allowed as values for"
                    " variables."
                    "\nGiven value: '" + str(value) + "'.")


def evaluate_number(str_input):
    """Evaluate raw input @str_input as a math expression and return the result

        _raise:
            -> WrongImmediateException:
                if the evaluated @str_input is not an instance of int or float (is not a valid value)
        :return:
            evaluated expression
        :type:
            Int
    """
    # TODO : number evaluation (shunting-yard-algorithm with reverse polish notation over stack)
    value = eval(str_input)
    if isinstance(value, int) or isinstance(value, float):
        return value
    else:
        raise WrongImmediateException("\nGiven value '" + str_input + "' cannot be evaluated.")


def tokenize_and_refactor(line):
    """Tokenize the line, remove whitespace and make @line lowercase

        :return:
            line with no whitespace and all tokens are divided with only one ' '-space
        :type:
            String
    """
    formatted_line = ''
    tokens = split_instruction_with_spaces(line)
    for token in tokens:
        if token not in whitespace:
            token = strip_whitespace(token)
            formatted_line += token + " "

    return formatted_line.rstrip().lower()


def strip_whitespace(str_input):
    """Strip all whitespace characters ' \t\n\r\x0b\x0c'

        :return:
            string_input with no whitespace from both sides
        :type:
            String
    """
    if not str_input:
        return ''

    while str_input[0] in WHITESPACE:
        str_input = str_input[1:]

    while str_input[-1] in WHITESPACE:
        str_input = str_input[:-1]

    return str_input


def split_instruction_with_spaces(line, split_char=' '):
    """Split given line with space (' ') and return a list of tokens.
     Make sure a string containing spaces will not be split into different tokens

        :return:
            list of tokens
        :type:
            List
    """
    formatted_line_tokens = []
    buffer = ''
    prev_char = '\0'
    is_string = False
    opened_parenthesis = 0
    opened_brackets = 0

    for char in line:
        if char != split_char:
            buffer += char
            if char in STRING_SEPARATOR:
                if prev_char != STRING_ESCAPE_SEQUENCE:
                    is_string = not is_string
            elif char == EXPRESSION_LEFT_PARENTHESIS:
                opened_parenthesis += 1
            elif char == ARRAY_OPENBRACKET_SEPARATOR:
                opened_brackets += 1
            elif char == EXPRESSION_RIGHT_PARENTHESIS:
                opened_parenthesis -= 1
            elif char == ARRAY_CLOSEBRACKET_SEPARATOR:
                opened_brackets -= 1
        else:
            if is_string or opened_parenthesis > 0 or opened_brackets > 0:
                buffer += char
            else:
                formatted_line_tokens.append(buffer)
                buffer = ''

        prev_char = char

        if opened_brackets < 0:
            raise WrongArrayFormatException("\nToo many close brackets found in array in line: '"
                                            + strip_whitespace(line) + "'")
        if opened_parenthesis < 0:
            raise WrongExpressionFormatException("\nToo many close parenthesis found in expression in line: '" +
                                                 strip_whitespace(line) + "'")

    if buffer:
        formatted_line_tokens.append(buffer)

    if opened_brackets > 0:
        raise WrongArrayFormatException("\nToo many open brackets found in array in line: '"
                                        + strip_whitespace(line) + "'")
    if opened_parenthesis > 0:
        print(strip_whitespace(line))
        raise WrongExpressionFormatException("\nToo many open parenthesis found in expression in line: '" +
                                             strip_whitespace(line) + "'")

    return formatted_line_tokens
