from MicroHamudi.Enums.Maps import Opcodes
from MicroHamudi.Functions.Functions import handle_immediate, handle_registry, format_hex
from MicroHamudi.OPs.Instruction import Instruction


class MOV_RA_imm(Instruction):
    """Class that represents 'MOV RA, imm'-Instruction"""

    def __init__(self, regA, imm):
        super().__init__()

        # Format immediate
        self.imm = handle_immediate(imm)

        # Format regA
        self.regA = handle_registry(regA)

    def __str__(self):
        """String representation of 'MOV RA, imm'-instruction as 'xxAx cccc'"""
        return Opcodes.MOV_RA_imm.__str__() + self.regA + "0" + " " + format_hex(self.imm, 4)
