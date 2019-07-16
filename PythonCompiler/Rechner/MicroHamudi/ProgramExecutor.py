from MicroHamudi.Visitors.Interpreter import Interpreter
from MicroHamudi.Visitors.Formatter import Formatter
from MicroHamudi.Programs.single_instr import instrutions_set as instructions
from MicroHamudi.Functions.Functions import write, format_hexstring_instr

PATH = "E:\\Informatik Python\\New\\Rechner\\MicroHamudi\\Outputs\\"
filename = "program"

SECOND_PATH = "E:\\Software\\LogiSim\\Projects\\Computer\\Programs\\Compiler Programs\\"


class ProgramExecutor:
    """Class that represents a program executor"""

    def __init__(self, instr):
        """Initialize a compiler with a set of instructions (program)"""
        self.instr = instr
        self.interpreter = Interpreter(self.instr)
        self.formatter = Formatter(self.instr)

    def compile(self, write_enabled=True):
        """Execute each Visitor of the compiler and write the output of the interpreter if @write_enabled"""

        hex_program = self.interpreter.execute()

        if write_enabled:
            write(hex_program, PATH, filename)
            write(hex_program, SECOND_PATH, filename)

        print(format_hexstring_instr(hex_program, 3))

        print("\n" + self.formatter.parse())


compiler = ProgramExecutor(instructions)
compiler.compile(False)
