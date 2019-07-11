from MicroHamudi.Enums.Maps import Opcodes
from MicroHamudi.OPs.Instruction import Instruction


class MOV_ESP_EBP(Instruction):
    """Class that represents MOV ESP EBP-Instruction"""

    def __init__(self):
        super().__init__()

    def __str__(self):
        """String representation of 'MOV ESP EBP'-instruction as 'xx00'"""
        return Opcodes.MOV_ESP_EBP.__str__() + "00"
