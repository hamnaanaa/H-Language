import sys

from MicroHamudi.Exceptions.WrongImmediateException import WrongImmediateException
from MicroHamudi.Functions.Functions import convert_chars_hex
from MicroHamudi.OPs.Instruction import Instruction
from MicroHamudi.OPs.PRINT_imm import PRINT_imm


class PRINTSTR_str(Instruction):
    """Class that represents 'PRINTSTR str'-Instruction"""

    def __init__(self, string_to_write):
        super().__init__()

        try:
            if not isinstance(string_to_write, str):
                raise WrongImmediateException

            self.str = string_to_write

            self.instructions = []
            for key, letter in enumerate(string_to_write):
                hex_letter = "0x" + convert_chars_hex(letter)
                self.instructions.append(PRINT_imm(hex_letter))

        except WrongImmediateException:
            print("Only strings are allowed in PRINTSTR str-instructions!")
            sys.exit(-1)

    def __str__(self):
        """String representation of 'PRINTSTR str'-instruction as a set of single PRINT imm instructions"""
        output_str = ""
        for instr in self.instructions:
            output_str += instr.__str__() + " "

        output_str = output_str.rstrip()

        return output_str
