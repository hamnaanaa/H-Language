import sys

from MicroHamudi.Exceptions.CompilerExceptions import *


def format_hex(hex_str, length):
    """Return a hex-string with fixed length (filled with '0')"""
    while len(hex_str) < length:
        hex_str = "0" + hex_str
    return hex_str


def handle_int(int_imm):
    """Format the given immediate as int and return it's string representation"""
    # if bigger than 2^15 - 1 or smaller than -2^15 (16-Bit signed int limits) raise an exception
    if int_imm > (2 ** 15 - 1) or int_imm < -(2 ** 15):
        raise WrongImmediateException("\nThe number must be representable with signed 16-Bit."
                                      " Given number: '" + str(int_imm) + "' cannot be represented"
                                                                          " as signed 16-Bit Integer")
    else:
        # flip the number if negative due to it's hex representation (ffff for -1)
        if int_imm < 0:
            int_imm = 2 ** 16 + int_imm

        # return the string representation of the hex-number without '0x'
        return str(hex(int_imm))[2:]


def handle_float(float_imm):
    """Format the given immediate as float and return it's string representation"""
    # try to round the number and message the wrong format
    print("Floating-point numbers are not supported! Rounding the number to an int...")
    float_imm = int(float_imm)
    return handle_int(float_imm)


def handle_string(str_imm):
    """Take the immediate as string and return it's formatted string representation"""
    try:
        if '0x' in str_imm:
            # hex-str
            str_imm = str_imm[2:]
            str_imm = format_hex(str_imm, 4)

            int_imm = int(str_imm, 16)

            if str_imm[0].upper() in "89ABCDEF":
                int_imm -= 2 ** 16

            str_imm = handle_int(int_imm)
        else:
            # dec-str
            str_imm = handle_int(int(str_imm))

        return str_imm
    except ValueError:
        print("ValueError: Wrong immediate-format: for hex please use '0x'!")
        sys.exit(-1)


def handle_registry(reg):
    """Format the given registry as string (or make it a string) and return formatted registry string as hex"""
    try:
        # Check what instance the given registry is and format it accordingly
        if isinstance(reg, int):
            if reg < 0:
                raise ValueError
            reg = str(hex(reg))[2:]
        elif isinstance(reg, float):
            if int(reg) < 0:
                raise ValueError
            reg = str(hex(int(reg)))[2:]
        elif isinstance(reg, str):
            if '0x' in reg or 'x' in reg:
                reg = reg[2:]
            else:
                reg = str(hex(int(reg)))[2:]

        if len(reg) > 1:
            raise WrongRegistryIndexException("\nNo Registry with index '"
                                              + str(hex(int(reg, 16))).upper().replace('X', 'x')
                                              + "' is available!")
        else:
            return reg

    except ValueError:
        print("ValueError: Non-existing index '" + str(reg).upper() + "'! If hex, please use 0x.")
        sys.exit(-1)


def handle_immediate(imm):
    """Format the given immediate depending on it's data type and return it as formatted string"""
    # Handle the immediate
    if isinstance(imm, int):
        imm = handle_int(imm)
    elif isinstance(imm, float):
        imm = handle_float(imm)
    elif isinstance(imm, str):
        imm = handle_string(imm)

    # Return the formatted immediate
    return imm


def format_classname(classname, instr_hex, str_to_write=''):
    """Extract all data needed from hex-instructions to format the ASM-instruction"""
    if '_' in classname:
        classname = classname.replace("_", " ")
    if 'RA' in classname:
        # Extract from hex-instruction 0xXXAB A-registry 'A'
        classname = classname.replace("RA", "RA:" + instr_hex[4])
    if 'RB' in classname:
        # Extract from hex-instruction 0xXXAB B-registry 'B'
        classname = classname.replace('RB', "RB:" + instr_hex[5])
    if 'imm' in classname:
        # Extract from hex-instruction 0xXXAB CCCC immediate 'CCCC'
        classname = classname.replace('imm', str(int(instr_hex[-4:], 16)))
    if 'EBP p' in classname:
        classname = classname.replace('EBP p ', '[EBP + ').replace(classname[-4:], classname[-4:] + "]")
    if 'str' in classname:
        classname = classname.replace('str', "'" + str_to_write + "'").replace('\n', '\\n')

    return classname


def calculate_indentation(indent_size, instr_counter):
    """Calculates the indentation needed and returns it as string"""
    return " " * (len(str(indent_size)) - len(str(instr_counter)))


def write(str_input, path, filename):
    """Write the input into the @filename"""
    with open(path + filename, "w") as file:
        file.write(str_input)


def format_hexstring_instr(long_str, newlines_num=6):
    """Print a long string creating @newlines_num lines (default 6 lines)"""
    instr_list = split_with_space(long_str)

    output_str = ""
    instr_num = len(instr_list)
    instr_per_line = instr_num // newlines_num

    for num, value in enumerate(instr_list):
        if num < 2:
            output_str += value + " "
        else:
            current_instr = num - 2
            if current_instr % instr_per_line == 0:
                output_str += "\n" + value + " "
            else:
                output_str += value + " "

    return output_str


def split_with_space(input_str):
    """Split @input_str with space and format 'v2.0 raw\n'"""
    output_str = input_str.split(" ")

    for num, element in enumerate(output_str):
        if 'raw\n' in element:
            n_elements = element.split("\n")
            del output_str[num]
            for key, obj in enumerate(n_elements):
                output_str.insert(num + key, obj)

    return output_str


def convert_chars_hex(char):
    """Convert char to ASCII-hex-value of it without '0x' """
    hex_string = str(hex(ord(char))[2:])
    while len(hex_string) < 4:
        hex_string = "0" + hex_string
    return hex_string
