from MicroHamudi.Enums.Maps import Opcodes
from MicroHamudi.Functions.Functions import handle_registry
from MicroHamudi.OPs.Instruction import Instruction


class SUBR_RA_RB(Instruction):
    """Class that represents 'SUBR RA, RB'-Instruction"""

    def __init__(self, regA, regB):
        super().__init__()

        # Format regA and regB
        self.regA = handle_registry(regA)
        self.regB = handle_registry(regB)

    def __str__(self):
        """String representation of 'SUBR RA, RB'-instruction as 'xxAB'"""
        return Opcodes.SUBR_RA_RB.__str__() + self.regA + self.regB
