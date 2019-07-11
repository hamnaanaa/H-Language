from MicroHamudi.Functions.Functions import handle_immediate
from MicroHamudi.OPs.JLE_imm import JLE_imm


class JNG_imm(JLE_imm):
    """Class that represents 'JNG imm'-Instruction"""

    def __init__(self, imm):
        super().__init__(imm)

        # Format immediate
        self.imm = handle_immediate(imm)

    def __str__(self):
        """String representation of 'JNG imm'-instruction as 'xxxx cccc'"""
        return super().__str__()
