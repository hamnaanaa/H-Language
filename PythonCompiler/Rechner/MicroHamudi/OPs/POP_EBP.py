from MicroHamudi.Enums.Maps import Opcodes
from MicroHamudi.OPs.Instruction import Instruction


class POP_EBP(Instruction):
    """Class that represents 'POP EBP'-Instruction"""

    def __init__(self):
        super().__init__()

    def __str__(self):
        """String representation of 'POP EBP'-instruction as 'xx00'"""
        return Opcodes.POP_EBP.__str__() + "00"
