from MicroHamudi.Enums.Maps import Opcodes
from MicroHamudi.Functions.Functions import handle_registry
from MicroHamudi.OPs.Instruction import Instruction


class DIVR_RB_RA(Instruction):
    """Class that represents 'DIVR RB, RA'-Instruction"""

    def __init__(self, regB, regA):
        super().__init__()

        # Format regA and regB
        self.regA = handle_registry(regA)
        self.regB = handle_registry(regB)

    def __str__(self):
        """String representation of 'DIVR RB, RA'-instruction as 'xxAB'"""
        return Opcodes.DIVR_RB_RA.__str__() + self.regA + self.regB
