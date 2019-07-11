package Assembly.AssemblyConstants;

import Assembly.AssemblyExceptions.FunctionalExceptions.WrongOperatorNameException;

import java.util.stream.Stream;

import static Assembly.AssemblyConstants.AssemblyConstants.*;

public enum Operators {
    /**
     * NOP-Operators
     */
    NOP("nop", TEXT_FLAG),
    IFETCH("ifetch", TEXT_FLAG),

    /**
     * REGISTRY-Operands
     */
    MOV("mov", TEXT_FLAG),
    XCHG("xchg", TEXT_FLAG),

    /**
     * OUTPUT-Operators
     */
    NEWLINE("newline", TEXT_FLAG),
    PRINT("print", TEXT_FLAG),
    PRINTSTR("printstr", TEXT_FLAG),

    /**
     * STACK-Operators
     */
    PUSH("push", TEXT_FLAG),
    POP("pop", TEXT_FLAG),

    /**
     * ARITHMETIC-Operators
     */
    INC("inc", TEXT_FLAG),
    ADD("add", TEXT_FLAG),
    MUL("mul", TEXT_FLAG),
    SUBR("subr", TEXT_FLAG),
    SUBL("subl", TEXT_FLAG),
    DIVR("divr", TEXT_FLAG),
    DIVL("divl", TEXT_FLAG),
    SAR("sar", TEXT_FLAG),

    /**
     * LOGIC-Operators
     */
    NOT("not", TEXT_FLAG),
    AND("and", TEXT_FLAG),
    OR("or", TEXT_FLAG),
    XOR("xor", TEXT_FLAG),
    CMP("cmp", TEXT_FLAG),

    /**
     * CALL-operator
     */
    CALL("call", TEXT_FLAG),

    /**
     * JUMP-Operators
     */
    JMP("jmp", TEXT_FLAG),
    JZ("jz", TEXT_FLAG),
    JE("je", TEXT_FLAG),
    JNZ("jnz", TEXT_FLAG),
    JNE("jne", TEXT_FLAG),
    JOVF("jovf", TEXT_FLAG),
    JLE("jle", TEXT_FLAG),
    JNG("jng", TEXT_FLAG),
    JL("jl", TEXT_FLAG),
    JNGE("jnge", TEXT_FLAG),

    /**
     * SECTION-Operators
     */
    EQU("equ", VAR_FLAG),
    DEF("def", DATA_FLAG),
    RES("res", BSS_FLAG);

    // TODO : new Operators
    //      MOD("mod", TEXT_FLAG)
    //      ...


    private String operatorMEMO;
    private int sectionFlag;

    Operators(String operatorMEMO, int sectionFlag) {
        this.operatorMEMO = operatorMEMO;
        this.sectionFlag = sectionFlag;
    }

    @Override
    public String toString() {
        return operatorMEMO;
    }

    public String toExtendedString() {
        return operatorMEMO + ":" + sectionFlag;
    }

    public static Operators getOperator(String OP) {
        for (Operators operator : Operators.values())
            if (operator.operatorMEMO.equals(OP))
                return operator;

        throw new WrongOperatorNameException("No operator with MEMO '" + OP + "' found");
    }
}
