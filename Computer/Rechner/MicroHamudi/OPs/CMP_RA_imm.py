from MicroHamudi.Enums.Maps import Opcodes
from MicroHamudi.Functions.Functions import handle_immediate, handle_registry, format_hex
from MicroHamudi.OPs.Instruction import Instruction


class CMP_RA_imm(Instruction):
    """Class that represents 'CMP RA, imm'-Instruction"""

    def __init__(self, regA, imm):
        super().__init__()

        # Format immediate
        self.imm = handle_immediate(imm)

        # Format regA
        self.regA = handle_registry(regA)

    def __str__(self):
        """String representation of 'CMP RA, imm'-instruction as 'xxAx cccc'"""
        return Opcodes.CMP_RA_imm.__str__() + self.regA + "0" + " " + format_hex(self.imm, 4)
