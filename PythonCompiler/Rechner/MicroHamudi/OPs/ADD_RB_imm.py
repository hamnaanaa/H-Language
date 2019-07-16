from MicroHamudi.Enums.Maps import Opcodes
from MicroHamudi.OPs.Instruction import Instruction
from MicroHamudi.Functions.Functions import handle_registry, handle_immediate, format_hex


class ADD_RB_imm(Instruction):
    """Class that represents 'ADD RB, imm'-Instruction"""

    def __init__(self, regB, imm):
        super().__init__()

        # Format immediate
        self.imm = handle_immediate(imm)

        # Format regB
        self.regB = handle_registry(regB)

    def __str__(self):
        """String representation of 'ADD RB, imm'-instruction as 'xxxB cccc'"""
        return Opcodes.ADD_RB_imm.__str__() + "0" + self.regB + " " + format_hex(self.imm, 4)
