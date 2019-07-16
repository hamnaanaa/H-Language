from MicroHamudi.Enums.Maps import Opcodes
from MicroHamudi.Functions.Functions import handle_registry
from MicroHamudi.OPs.Instruction import Instruction


class PUSH_RA(Instruction):
    """Class that represents 'PUSH RA'-Instruction"""

    def __init__(self, regA):
        super().__init__()

        # Format regA
        self.regA = handle_registry(regA)

    def __str__(self):
        """String representation of 'PUSH RA'-instruction as 'xxAx'"""
        return Opcodes.PUSH_RA.__str__() + self.regA + "0"
