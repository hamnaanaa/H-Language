from MicroHamudi.Enums.Maps import Opcodes
from MicroHamudi.OPs.Instruction import Instruction


class PUSH_EBP(Instruction):
    """Class that represents 'PUSH EBP'-Instruction"""

    def __init__(self):
        super().__init__()

    def __str__(self):
        """String representation of 'PUSH EBP'-instruction as 'xx00'"""
        return Opcodes.PUSH_EBP.__str__() + "00"
