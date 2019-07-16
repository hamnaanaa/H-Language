file_name = 'Display Output.txt'
header = 'v2.0 raw\n'
PATH = "E:\\Software\\LogiSim\\Projects\\Computer\\"

string_to_write = 'Hello Hamudi\n'


def convert_chars_hex(char):
    """Convert char to ASCII-hex-value of it without '0x' """
    hex_string = str(hex(ord(char))[2:])
    while len(hex_string) < 4:
        hex_string = "0" + hex_string
    return hex_string


def write_letters():
    """Convert string_to_write in 16-Bit Computer's RAM instructions for output on the display"""
    with open(PATH + file_name, "w") as file:
        # Header
        file.write(header)

        # Chars converted to HEX for display
        for letter in string_to_write:
            file.write("0800 " + convert_chars_hex(letter) + " ")

        # loop operation
        file.write("0300 0000")


write_letters()
