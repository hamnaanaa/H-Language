from MicroHamudi.Exceptions.CompilerExceptions import WrongInstructionException


def all_instr_names_set():
    """Return all instruction names' set formatted as 'instr' (lowercase)"""
    return set(instr.split('_')[0].lower() for instr in Opcodes.opcodes_mapping.keys())


class Opcodes:
    """Class that represents map from OP's name to it's hex value"""

    opcodes_mapping = {
        'IFETCH': '0x00',
        'INC_RA': '0x01',
        'INC_RB': '0x02',
        'JMP_imm': '0x03',
        'MOV_RB_RA': '0x04',
        'MOV_RA_RB': '0x05',
        'ADD_RA_imm': '0x06',
        'ADD_RB_imm': '0x07',
        'PRINT_imm': '0x08',
        'PUSH_imm': '0x09',
        'POP_RA': '0x0a',
        'POP_RB': '0x0b',
        'MOV_RA_EBP_p_imm': '0x0c',
        'AND_RA_RB': '0x0d',
        'AND_RB_RA': '0x0e',
        'MUL_RA_RB': '0x0f',
        'MUL_RB_RA': '0x10',
        'SUBR_RA_RB': '0x11',
        'SUBL_RA_RB': '0x12',
        'SUBR_RB_RA': '0x13',
        'SUBL_RB_RA': '0x14',
        'ADD_RA_RB': '0x15',
        'ADD_RB_RA': "0x16",
        'JZ_imm': "0x17",
        'CMP_RA_RB': "0x18",
        'OR_RA_RB': "0x19",
        'OR_RB_RA': "0x1a",
        'NOT_RA': "0x1b",
        'NOT_RB': "0x1c",
        'MOV_RA_imm': "0x1d",
        'MOV_RB_imm': "0x1e",
        'DIVR_RA_RB': "0x1f",
        'DIVL_RA_RB': "0x20",
        'DIVR_RB_RA': "0x21",
        'DIVL_RB_RA': "0x22",
        'PUSH_RA': "0x23",
        'PUSH_RB': "0x24",
        'PRINT_RA': "0x25",
        'CALL_imm': "0x26",
        'RET': "0x27",
        'PRINT_RB': "0x28",
        'MOV_ESP_EBP': "0x29",
        'MOV_EBP_ESP': "0x2a",
        'PUSH_EBP': "0x2b",
        'POP_EBP': "0x2c",
        'ADD_ESP_imm': "0x2d",
        'SUBR_ESP_imm': "0x2e",
        'JNZ_imm': "0x2f",
        'JOVF_imm': "0x30",
        'JLE_imm': "0x31",
        'JL_imm': "0x32",
        'CMP_RA_imm': "0x33",
        'CMP_RB_imm': "0x34",
        'MUL_RA_imm': "0x35",
        'MUL_RB_imm': "0x36",
        'XCHG_RA_RB': "0x37",
        'DIVR_RA_imm': "0x38",
        'DIVR_RB_imm': "0x39",

        # EXTENDED
        'EQU': "macro",
        'JE_imm': "0x17",
        'JNE_imm': "0x2f",
        'JNG_imm': "0x31",
        'JNGE_imm': "0x32",
        'PRINTSTR_imm': "macro",
        'NEWLINE': "macro"
    }

    def __str__(self):
        """Return hex-opcode of the instruction"""
        return str(Opcodes.opcodes_mapping)


class Operands:
    """Class that represents map from OP's name to its amount of operands"""

    operands_mapping = {
        'IFETCH': 0,
        'NEWLINE': 0,
        'RET': 0,

        'NOT': 1,
        'INC': 1,

        'JMP': 1,
        'JNZ': 1,
        'JOVF': 1,
        'JLE': 1,
        'JL': 1,
        'JE': 1,
        'JZ': 1,
        'JNE': 1,
        'JNG': 1,
        'JNGE': 1,

        'PUSH': 1,
        'POP': 1,
        'CALL': 1,

        'PRINT': 1,
        'PRINTSTR': 1,

        'MOV': 2,
        'CMP': 2,
        'XCHG': 2,
        'EQU': 2,

        'ADD': 2,
        'MUL': 2,
        'SUBR': 2,
        'SUBL': 2,
        'DIVR': 2,
        'DIVL': 2,

        'AND': 2,
        'OR': 2
    }


def num_operands(str_op, line):
    """Give the number of operands @str_op requires

        :raise:
            -> WrongInstructionException:
                if the whole @operands_mapping does not contain given instruction's OP
        :return:
            number of operands the given OP requires
        :type:
            Int
    """
    for key, value in Operands.operands_mapping.items():
        if str_op.lower() == key.lower():
            return value

    raise WrongInstructionException("\nNon-valid instruction: No instruction with given OP '" + str_op + "' was found"
                                    + "\nGiven instruction: '" + line + "'")
