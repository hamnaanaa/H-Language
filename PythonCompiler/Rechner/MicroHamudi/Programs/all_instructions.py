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

instructions = [
    IFETCH(),
    INC_RA(5),
    INC_RB(2),
    JMP_imm(7554),
    MOV_RB_RA(5, 2),
    MOV_RA_RB(5, 2),
    ADD_RA_imm(5, 7554),
    ADD_RB_imm(5, 7554),
    PRINT_imm("0x1d82"),
    PUSH_imm("0x1d83"),
    POP_RA(0xf),
    POP_RB(14),
    MOV_RA_EBP_p_imm(4, "487"),
    AND_RA_RB(5, "0xc"),
    AND_RB_RA(5, "0xc"),
    MUL_RA_RB(15, "0xe"),
    MUL_RB_RA(0xe, 10),
    SUBR_RA_RB(15, "0xc"),
    SUBL_RA_RB(0xb, "8"),
    SUBR_RB_RA(0xa, "4"),
    SUBL_RB_RA(0xb, "1"),
    ADD_RA_RB(15, 0x4),
    ADD_RB_RA(15, 0x4),
    JZ_imm("0x1d82"),
    CMP_RA_RB(4, 0xf),
    OR_RA_RB(4, 0xb),
    OR_RB_RA(4, 0xb),
    NOT_RA(0xf),
    NOT_RB(0xa),
    MOV_RA_imm(0x6, "0x1d82"),
    MOV_RB_imm(0x6, "0x1d82"),
    DIVR_RA_RB(0x6, 0x7),
    DIVL_RA_RB(0x6, 0x7),
    DIVR_RB_RA(0xb, 0x2),
    PUSH_RA(15),
    PUSH_RB(0xc),
    PRINT_RA(6),
    CALL_imm("0x1d82"),
    RET(),
    PRINT_RB(0xb),
    PRINTSTR_str("abcd"),
    MOV_ESP_EBP(),
    MOV_EBP_ESP(),
    PUSH_EBP(),
    POP_EBP(),
    ADD_ESP_imm(0x1d82),
    SUBR_ESP_imm(0x1d82),
    JNZ_imm("0xabcd"),
    JE_imm("0x1d82"),
    JNE_imm("0x1111"),
    JOVF_imm("0x2222"),
    JLE_imm("0x3333"),
    JNG_imm("0x3333"),
    JL_imm("0x4444"),
    JNGE_imm(0x4444),
    CMP_RA_imm(5, 15),
    CMP_RB_imm(5, 15),
    MUL_RA_imm(5, "0xffff"),
    MUL_RB_imm(6, "0xaaaa"),
    XCHG_RA_RB(5, 8),
    DIVR_RA_imm(5, 15),
    DIVR_RB_imm(4, 7)
]
