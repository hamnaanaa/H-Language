from MicroHamudi.Enums.Maps import Opcodes
from MicroHamudi.OPs.Instruction import Instruction
from MicroHamudi.Functions.Functions import format_hex, handle_immediate


class JMP_imm(Instruction):
    """Class that represents 'JMP imm'-Instruction"""

    def __init__(self, imm):
        super().__init__()

        # Format immediate
        self.imm = handle_immediate(imm)

    def __str__(self):
        """String representation of 'JMP_imm'-instruction as 'xxxx cccc'"""
        return Opcodes.JMP_imm.__str__() + "00" + " " + format_hex(self.imm, 4)
