from MicroHamudi.Enums.Maps import Opcodes
from MicroHamudi.Functions.Functions import handle_registry
from MicroHamudi.OPs.Instruction import Instruction


class INC_RB(Instruction):
    """Class that represents 'INC RB'-Instruction"""

    def __init__(self, regB):
        super().__init__()

        # Format regB
        self.regB = handle_registry(regB)

    def __str__(self):
        """String representation of 'INC RB'-instruction as 'xxxB'"""
        return Opcodes.INC_RB.__str__() + "0" + self.regB
