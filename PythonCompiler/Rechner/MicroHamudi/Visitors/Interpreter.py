from MicroHamudi.Visitors.AsmVisitor import AsmVisitor


class Interpreter(AsmVisitor):
    """Class that represents an Interpreter for LogiSim"""

    def __init__(self, instr):
        super().__init__()

        self.program = instr
        self.string_output = "v2.0 raw\n"

    def execute(self):
        """Execute each instruction of the program sequentially and calculate hex value as string"""
        for instruction in self.program:
            instruction.accept(self)

        return self.string_output

    def visit(self, instr):
        self.string_output += instr.__str__().replace('0x', '') + " "
