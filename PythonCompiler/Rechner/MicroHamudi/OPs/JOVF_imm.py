from MicroHamudi.Enums.Maps import Opcodes
from MicroHamudi.Functions.Functions import handle_immediate, format_hex
from MicroHamudi.OPs.Instruction import Instruction


class JOVF_imm(Instruction):
    """Class that represents 'JOVF imm'-Instruction"""

    def __init__(self, imm):
        super().__init__()

        # Format immediate
        self.imm = handle_immediate(imm)

    def __str__(self):
        """String representation of 'JOVF imm'-instruction as 'xxxx cccc'"""
        return Opcodes.JOVF_imm.__str__() + "00" + " " + format_hex(self.imm, 4)