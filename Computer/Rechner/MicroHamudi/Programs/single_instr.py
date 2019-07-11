from MicroHamudi.OPs.ADD_ESP_imm import ADD_ESP_imm
from MicroHamudi.OPs.CALL_imm import CALL_imm
from MicroHamudi.OPs.CMP_RA_imm import CMP_RA_imm
from MicroHamudi.OPs.CMP_RB_imm import CMP_RB_imm
from MicroHamudi.OPs.DIVR_RA_imm import DIVR_RA_imm
from MicroHamudi.OPs.DIVR_RB_imm import DIVR_RB_imm
from MicroHamudi.OPs.JE_imm import JE_imm
from MicroHamudi.OPs.JLE_imm import JLE_imm
from MicroHamudi.OPs.JL_imm import JL_imm
from MicroHamudi.OPs.JNE_imm import JNE_imm
from MicroHamudi.OPs.JNGE_imm import JNGE_imm
from MicroHamudi.OPs.JNG_imm import JNG_imm
from MicroHamudi.OPs.JNZ_imm import JNZ_imm
from MicroHamudi.OPs.JOVF_imm import JOVF_imm
from MicroHamudi.OPs.MOV_EBP_ESP import MOV_EBP_ESP
from MicroHamudi.OPs.MOV_ESP_EBP import MOV_ESP_EBP
from MicroHamudi.OPs.MUL_RA_imm import MUL_RA_imm
from MicroHamudi.OPs.MUL_RB_RA import MUL_RB_RA
from MicroHamudi.OPs.MUL_RB_imm import MUL_RB_imm
from MicroHamudi.OPs.POP_EBP import POP_EBP
from MicroHamudi.OPs.PRINTSTR_str import PRINTSTR_str
from MicroHamudi.OPs.PRINT_RA import PRINT_RA
from MicroHamudi.OPs.PRINT_RB import PRINT_RB
from MicroHamudi.OPs.PUSH_EBP import PUSH_EBP
from MicroHamudi.OPs.RET import RET
from MicroHamudi.OPs.ADD_RA_RB import ADD_RA_RB
from MicroHamudi.OPs.ADD_RB_RA import ADD_RB_RA
from MicroHamudi.OPs.AND_RA_RB import AND_RA_RB
from MicroHamudi.OPs.AND_RB_RA import AND_RB_RA
from MicroHamudi.OPs.CMP_RA_RB import CMP_RA_RB
from MicroHamudi.OPs.DIVL_RA_RB import DIVL_RA_RB
from MicroHamudi.OPs.DIVR_RA_RB import DIVR_RA_RB
from MicroHamudi.OPs.DIVR_RB_RA import DIVR_RB_RA
from MicroHamudi.OPs.JZ_imm import JZ_imm
from MicroHamudi.OPs.MOV_RA_EBP_p_imm import MOV_RA_EBP_p_imm
from MicroHamudi.OPs.MOV_RA_imm import MOV_RA_imm
from MicroHamudi.OPs.MOV_RB_imm import MOV_RB_imm
from MicroHamudi.OPs.NOT_RA import NOT_RA
from MicroHamudi.OPs.NOT_RB import NOT_RB
from MicroHamudi.OPs.OR_RA_RB import OR_RA_RB
from MicroHamudi.OPs.OR_RB_RA import OR_RB_RA
from MicroHamudi.OPs.POP_RB import POP_RB
from MicroHamudi.OPs.PUSH_RA import PUSH_RA
from MicroHamudi.OPs.PUSH_RB import PUSH_RB
from MicroHamudi.OPs.SUBL_RA_RB import SUBL_RA_RB
from MicroHamudi.OPs.SUBL_RB_RA import SUBL_RB_RA
from MicroHamudi.OPs.SUBR_ESP_imm import SUBR_ESP_imm
from MicroHamudi.OPs.SUBR_RA_RB import SUBR_RA_RB
from MicroHamudi.OPs.SUBR_RB_RA import SUBR_RB_RA
from MicroHamudi.OPs.ADD_RA_imm import ADD_RA_imm
from MicroHamudi.OPs.ADD_RB_imm import ADD_RB_imm
from MicroHamudi.OPs.IFETCH import IFETCH
from MicroHamudi.OPs.INC_RA import INC_RA
from MicroHamudi.OPs.INC_RB import INC_RB
from MicroHamudi.OPs.JMP_imm import JMP_imm
from MicroHamudi.OPs.MOV_RA_RB import MOV_RA_RB
from MicroHamudi.OPs.POP_RA import POP_RA
from MicroHamudi.OPs.PRINT_imm import PRINT_imm
from MicroHamudi.OPs.PUSH_imm import PUSH_imm
from MicroHamudi.OPs.MOV_RB_RA import MOV_RB_RA
from MicroHamudi.OPs.MUL_RA_RB import MUL_RA_RB
from MicroHamudi.OPs.XCHG_RA_RB import XCHG_RA_RB

value = 1587
i = 10_000
BOOL = 1
NULL = 0
dummie = 555

instrutions_set = [
    # INITIALIZE:
    MOV_RA_imm(5, value),           # INPUT: a5
    MOV_RB_imm(13, NULL),           # b13 saves 0 as const
    MOV_RA_imm(14, BOOL),           # a14 saves 1 as boolean
    MOV_RB_imm(14, i),              # b14 saves 10_000

    # FUNC:
    CMP_RA_imm(5, NULL),               # check a5 ? 0
    JL_imm(41),                     # goto NEGATIVE

    # LOOP:
    CMP_RB_imm(14, NULL),           # check if already all digits were outputed
    JLE_imm(63),                    # goto DONE

    # ZERO_DIGIT:
    CMP_RA_imm(14, NULL),           # check if boolean 'is_leading' true
    JE_imm(32),                     # goto PRINT

    PUSH_RA(5),                     # save old a5 for check
    DIVR_RA_RB(5, 14),              # a5 = a5 / i
    JNE_imm(29),                    # goto NOTZERO
    POP_RA(5),                      # return old a5 value
    DIVR_RB_imm(14, 10),            # i = i / 10
    JMP_imm(12),                   # goto LOOP

    # NOTZERO:
    POP_RA(5),                      # return old a5 value
    MOV_RA_imm(14, NULL),           # set bool 'is_leading' to false

    # PRINT:
    DIVR_RA_RB(5, 14),              # a5 = a5 / i
    ADD_RA_imm(5, 0x30),            # a5 = a5 + 0x30 for ASCII-digit
    PRINT_RA(5),                    # PRINTDIGIT RA
    MOV_RA_RB(5, 0),                # a5 = a5 % i
    DIVR_RB_imm(14, 10),            # i = i / 10
    JMP_imm(12),                 # goto LOOP

    # NEGATIVE:
    CMP_RA_imm(5, -32768),          # compare to min value
    JE_imm(51),                     # goto MINVALUE
    PRINTSTR_str('-'),              # print '-'
    MUL_RA_imm(5, -1),              # a5 = -a5
    JMP_imm(12),                 # goto LOOP

    # MINVALUE:
    PRINTSTR_str("-32768"),         # print explicitly INT_MINVALUE

    # DONE:
    PRINTSTR_str('\n')
]
