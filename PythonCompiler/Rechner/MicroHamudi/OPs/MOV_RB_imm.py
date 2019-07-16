from MicroHamudi.Enums.Maps import Opcodes
from MicroHamudi.Functions.Functions import handle_immediate, handle_registry, format_hex
from MicroHamudi.OPs.Instruction import Instruction


class MOV_RB_imm(Instruction):
    """Class that represents 'MOV RB, imm'-Instruction"""

    def __init__(self, regB, imm):
        super().__init__()

        # Format immediate
        self.imm = handle_immediate(imm)

        # Format regB
        self.regB = handle_registry(regB)

    def __str__(self):
        """String representation of 'MOV RB, imm'-instruction as 'xxxB cccc'"""
        return Opcodes.MOV_RB_imm.__str__() + "0" + self.regB + " " + format_hex(self.imm, 4)
