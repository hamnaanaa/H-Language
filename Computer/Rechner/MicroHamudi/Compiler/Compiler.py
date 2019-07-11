from MicroHamudi.Functions.CompilerFunctions import get_available_sections, get_available_registry, \
    get_available_registry_index
from MicroHamudi.Compiler.Preprocessor import Preprocessor
from MicroHamudi.Enums.Maps import all_instr_names_set

PATH = "/home/hamudi/Developing/LogiSim Projects/Computer/Rechner/MicroHamudi/Inputs/"
file_to_read = "NumberPrinterAssembly.hlan"


class Compiler:
    """Class that represents a compiler"""

    def __init__(self, file_name):
        # name of the file
        self.file_name = file_name
        # set of all defined Assembly instructions
        self.all_instructions = all_instr_names_set()
        # list of all available sections
        self.available_sections = get_available_sections()
        # list of all available registry
        self.available_registry = get_available_registry()
        # list of all available registry index
        self.available_registry_index = get_available_registry_index()
        # set of all labels in form '%% label:'
        self.labels = dict()
        # set of all jump labels in form 'jcc %label:'
        self.jumps = dict()
        # dictionary of all variables within 'equ' assignments with their values
        self.variables = dict()
        # dictionary of all pointers to reserved memory
        self.pointers_res = dict()
        # dictionary of all pointers to memory with defined constants
        self.pointers_def = dict()

        # Preprocessor
        self.preprocessor = Preprocessor(PATH + self.file_name,
                                         self.all_instructions, self.available_sections, self.available_registry,
                                         self.available_registry_index, self.variables, self.pointers_res,
                                         self.pointers_def, self.labels, self.jumps)
        # I. Stage of Compiler: Remove all comments and extract all sections
        self.code = self.preprocessor.extract_sections()

        # Compiler


compiler = Compiler(file_to_read)
# for instr in compiler.lines:
for liner in compiler.code:
    print(liner)

# all reservations in @compiler.pointer_res
print("Reservations: " + str(compiler.pointers_res))

# all declarations in @compiler.pointer_def
print("Declarations: " + str(compiler.pointers_def))

# all variables in @compiler.variables-dictionary
print(compiler.variables)

# all labels in @compiler.labels-set
print("labels: " + str(compiler.labels))

# all jump_labels in @compiler.jumps-set
print("Jumps: " + str(compiler.jumps))
