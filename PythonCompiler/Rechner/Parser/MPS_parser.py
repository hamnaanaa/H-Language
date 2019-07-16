file_name = 'MPS.txt'
header = 'v2.0 raw\n'
PATH = "E:\\Software\\LogiSim\\Projects\\Computer\\"
bin_file_name = 'Binary Instructions.txt'
files_to_write = ['MPS_parsed_0', 'MPS_parsed_1', 'MPS_parsed_2']
mapping_prom_filename = 'mapping_PROM'


def instr_print(instructions, instruction_names):
    """Print the binary instructions in format: 'BINARY : INSTR_NAME' """
    bin_instrs = []
    for line in range(len(instructions)):
        bin_instrs.append(add_dots(instructions[line], 4)
                          + " |::| " + add_dots(parse_bin_hex(instructions[line], 24), 2)
                          + " |::| " + instruction_names[line])

    with open(PATH + bin_file_name, "w") as file:
        for instr in bin_instrs:
            file.write(instr + "\n")
            print(instr)


def add_dots(string, size=4):
    """Add dots every for every 4th bit as 'bbbb.bbbb' and two dots for every 32th bit as 'bbbb .. bbbb' """
    formatted_string = ''
    for char in range(len(string)):
        if char % size == 0 and char != 0:
            if char % (2 * size ** 2) == 0:
                formatted_string += " .. " + string[char]
            else:
                formatted_string += "." + string[char]
        else:
            formatted_string += string[char]
    return formatted_string


def parse_bin_hex(bin_string, size=8):
    """Parse binary string to decimal int and then into hex-number. Return string of this hex-number without '0x' """
    hex_string = str(hex(int(bin_string, 2)))[2:]
    while len(hex_string) < size:
        hex_string = "0" + hex_string
    return hex_string


def parse_instr_bin_list(lines, instructions, instruction_names):
    """Create from the lines a list of formatted binary instructions and a list of their names"""
    for line_num in range(len(lines)):
        if line_num % 2 == 1:
            # format the binary instruction (remove spaces and expend if neeeded to 96 bits)
            instruction_to_add = lines[line_num].replace("\t", "").rstrip()[6:]
            while len(instruction_to_add) < 96:
                instruction_to_add += "0"
            instructions.append(instruction_to_add)
        else:
            instruction_names.append(lines[line_num].partition("\t")[0])


def write_mps(instructions):
    """Convert instructions to HEX and write in three MPS for LogiSim"""
    for file_num in range(len(files_to_write)):
        with open(PATH + files_to_write[file_num], "w") as file:
            file.write(header)
            for instruction in instructions:
                parsed_instr = parse_bin_hex(instruction[0 + 32 * file_num: 32 + 32 * file_num]) + " "
                file.write(parsed_instr)


def write_mapping_prom(instruction_names):
    """Function that writes the instructions' start addresses into Mapping-PROM"""
    addresses = []
    for index, instr in enumerate(instruction_names):
        if ' I ' in instr:
            addresses.append(index)

    with open(PATH + mapping_prom_filename, 'w') as prom_file:
        prom_file.write(header)

        for address in addresses:
            prom_file.write(parse_bin_hex(bin(int(address)), 4) + " ")


def decode():
    """Decode the instructions from binary and write in three files in LogiSim's ROM format"""
    # Open the file with binary instructions
    with open(file_name) as file:
        lines = file.readlines()
        with open(PATH + file_name, "w") as file_write:
            for line in lines:
                file_write.write(line + "\n")

    # Read the instructions
    instructions, instruction_names = [], []
    parse_instr_bin_list(lines, instructions, instruction_names)

    # Print formatted binary instructions and their names
    instr_print(instructions, instruction_names)

    # Write to each of MPS-Files parsed hex-instructions
    write_mps(instructions)

    # Write to Mapping-PROM linked addresses
    write_mapping_prom(instruction_names)


decode()
