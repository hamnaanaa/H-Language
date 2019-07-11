from MicroHamudi.Functions.Functions import format_classname, calculate_indentation
from MicroHamudi.Visitors.AsmVisitor import AsmVisitor


class Formatter(AsmVisitor):
    """Class that represents a Formatter for ASM-Istructions"""

    def __init__(self, instr):
        super().__init__()

        self.program = instr
        self.instr_counter = 0
        self.indent_size = len(self.program)
        self.string_output = ""

    def parse(self):
        """Parse the program sequentially"""
        for instruction in self.program:
            instruction.accept(self)
            self.instr_counter += 1

        return self.string_output

    def visit(self, instr):
        if 'str' in instr.__class__.__name__:
            instr_str = format_classname(instr.__class__.__name__, instr.__str__(), instr.str)
        else:
            instr_str = format_classname(instr.__class__.__name__, instr.__str__())

        indentation = calculate_indentation(self.indent_size, self.instr_counter)
        self.string_output += indentation + str(self.instr_counter) + ": " + instr_str + "\n"
