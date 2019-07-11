from MicroHamudi.Functions.Functions import handle_immediate
from MicroHamudi.OPs.JNZ_imm import JNZ_imm


class JNE_imm(JNZ_imm):
    """Class that represents 'JNE imm'-Instruction"""

    def __init__(self, imm):
        super().__init__(imm)

        # Format immediate
        self.imm = handle_immediate(imm)

    def __str__(self):
        """String representation of 'JNE imm'-instruction as 'xxxx cccc'"""
        return super().__str__()
