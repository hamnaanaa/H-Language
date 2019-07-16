from MicroHamudi.Functions.Functions import handle_immediate
from MicroHamudi.OPs.JZ_imm import JZ_imm


class JE_imm(JZ_imm):
    """Class that represents 'JE imm'-Instruction"""

    def __init__(self, imm):
        super().__init__(imm)

        # Format immediate
        self.imm = handle_immediate(imm)

    def __str__(self):
        """String representation of 'JE imm'-instruction as 'xxxx cccc'"""
        return super().__str__()
