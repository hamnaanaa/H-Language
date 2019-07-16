from MicroHamudi.Enums.Maps import Opcodes
from MicroHamudi.OPs.Instruction import Instruction


class RET(Instruction):
    """Class that represents 'RET'-Instruction"""

    def __init__(self):
        super().__init__()

    def __str__(self):
        """String representation of 'RET'-instruction as 'xxAx'"""
        return Opcodes.RET.__str__() + "00"
