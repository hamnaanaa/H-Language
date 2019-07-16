from MicroHamudi.Enums.Maps import Opcodes
from MicroHamudi.OPs.Instruction import Instruction
from MicroHamudi.Functions.Functions import handle_registry, handle_immediate, format_hex


class MUL_RA_imm(Instruction):
    """Class that represents 'MUL RA, imm'-Instruction"""

    def __init__(self, regA, imm):
        super().__init__()

        # Format immediate
        self.imm = handle_immediate(imm)

        # Format regA
        self.regA = handle_registry(regA)

    def __str__(self):
        """String representation of 'MUL RA, imm'-instruction as 'xxAx cccc'"""
        return Opcodes.MUL_RA_imm.__str__() + self.regA + "0" + " " + format_hex(self.imm, 4)
