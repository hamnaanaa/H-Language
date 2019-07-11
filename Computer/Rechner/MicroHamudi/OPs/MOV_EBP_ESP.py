from MicroHamudi.Enums.Maps import Opcodes
from MicroHamudi.OPs.Instruction import Instruction


class MOV_EBP_ESP(Instruction):
    """Class that represents 'MOV EBP, ESP'-Instruction"""

    def __init__(self):
        super().__init__()

    def __str__(self):
        """String representation of 'MOV EBP, ESP'-instruction as 'xx00'"""
        return Opcodes.MOV_EBP_ESP.__str__() + "00"
