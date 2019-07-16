from MicroHamudi.OPs.Instruction import Instruction
from MicroHamudi.Enums.Maps import Opcodes


class IFETCH(Instruction):
    """Class that represents 'IFETCH'-Instruction"""

    def __init__(self):
        super().__init__()

    def __str__(self):
        """String representation of 'IFETCH'-instruction as '0000'"""
        return Opcodes.IFETCH.__str__() + "00"
