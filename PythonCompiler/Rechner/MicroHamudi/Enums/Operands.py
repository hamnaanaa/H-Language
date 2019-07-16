from MicroHamudi.Functions.CompilerFunctions import AVAILABLE_REGISTRY, AVAILABLE_REGISTRY_INDEX
from MicroHamudi.Exceptions.CompilerExceptions import *


class Operand:
    def __init__(self, type, value, length):
        self.type = type
        self.value = value
        self.length = length


class ArrayAccessOperand(Operand):
    def __init__(self, reg_name, reg_index):
        super().__init__("registry", check_registry_access(reg_name, reg_index), 1)


class StringOperand(Operand):
    def __init__(self, string):
        super().__init__("string", string, len(string))


def check_registry_access(reg_name, reg_index):
    """Function to check if given :param reg_num and :param reg_index are valid for registry access

        :raise:
            -> WrongRegistryAccessException:
                if registry name isn't allowed or valid
                OR registry index isn't allowed or valid
        :return:
            Tuple
    """
    if reg_name not in AVAILABLE_REGISTRY:
        raise WrongRegistryAccessException("\nNon-valid registry name '" + reg_name + "'"
                                           + "\nAvailable registry: "
                                           + str(AVAILABLE_REGISTRY).replace('[', '').replace(']', ''))
    if reg_index not in AVAILABLE_REGISTRY_INDEX:
        raise WrongRegistryAccessException("\nNon-valid registry index '" + reg_index + "'"
                                           + "\nAvailable registry index: "
                                           + str(AVAILABLE_REGISTRY_INDEX).replace('[', "'").replace(']', "'"))

# TODO : Assembly section
