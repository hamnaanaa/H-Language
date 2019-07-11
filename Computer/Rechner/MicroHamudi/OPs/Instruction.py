class Instruction:
    """Class that represents an ASM-Instruction"""

    def __init__(self):
        """Initialize a general instruction"""
        self.regA = None
        self.regB = None
        self.imm = None

    def accept(self, visitor):
        """Visitor Pattern"""
        visitor.visit(self)
