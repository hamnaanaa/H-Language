from MicroHamudi.Functions.Functions import handle_registry
from MicroHamudi.OPs.Instruction import Instruction
from MicroHamudi.Enums.Maps import Opcodes


class INC_RA(Instruction):
    """Class that represents 'INC RA'-Instruction"""

    def __init__(self, regA):
        super().__init__()

        # Format regA
        self.regA = handle_registry(regA)

    def __str__(self):
        """String representation of 'INC RA'-instruction as 'xxAx'"""
        return Opcodes.INC_RA.__str__() + self.regA + "0"
