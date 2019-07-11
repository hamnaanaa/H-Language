from MicroHamudi.Enums.Maps import Opcodes
from MicroHamudi.OPs.Instruction import Instruction
from MicroHamudi.Functions.Functions import handle_registry, handle_immediate, format_hex


class DIVR_RB_imm(Instruction):
    """Class that represents 'DIVR RB, imm'-Instruction"""

    def __init__(self, regB, imm):
        super().__init__()

        # Format immediate
        self.imm = handle_immediate(imm)

        # Format regB
        self.regB = handle_registry(regB)

    def __str__(self):
        """String representation of 'DIVR RB, imm'-instruction as 'xxxB cccc'"""
        return Opcodes.DIVR_RB_imm.__str__() + "0" + self.regB + " " + format_hex(self.imm, 4)
