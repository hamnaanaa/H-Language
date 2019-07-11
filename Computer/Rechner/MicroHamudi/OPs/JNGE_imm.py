from MicroHamudi.Functions.Functions import handle_immediate
from MicroHamudi.OPs.JL_imm import JL_imm


class JNGE_imm(JL_imm):
    """Class that represents 'JNGE imm'-Instruction"""

    def __init__(self, imm):
        super().__init__(imm)

        # Format immediate
        self.imm = handle_immediate(imm)

    def __str__(self):
        """String representation of 'JNGE imm'-instruction as 'xxxx cccc'"""
        return super().__str__()
