package Assembly.AssemblyConstants;

import Assembly.AssemblyExceptions.FunctionalExceptions.FunctionalTokenExceptions.WrongOperatorNameException;

import static Assembly.AssemblyConstants.AssemblyConstants.*;

public enum Operators {
    /**
     * NOP-Operators
     */
    NOP("nop", 0, TEXT_FLAG),
    IFETCH("ifetch", 0, TEXT_FLAG),

    /**
     * REGISTRY-Operands
     */
    MOV("mov", 2, TEXT_FLAG),
    XCHG("xchg", 2, TEXT_FLAG),

    /**
     * OUTPUT-Operators
     */
    NEWLINE("newline", 0, TEXT_FLAG),
    PRINT("print", 1, TEXT_FLAG),
    PRINTSTR("printstr", 1, TEXT_FLAG),

    /**
     * STACK-Operators
     */
    PUSH("push", 1, TEXT_FLAG),
    POP("pop", 1, TEXT_FLAG),

    /**
     * ARITHMETIC-Operators
     */
    INC("inc", 1, TEXT_FLAG),
    ADD("add", 2, TEXT_FLAG),
    MUL("mul", 2, TEXT_FLAG),
    SUBR("subr", 2, TEXT_FLAG),
    SUBL("subl", 2, TEXT_FLAG),
    DIVR("divr", 2, TEXT_FLAG),
    DIVL("divl", 2, TEXT_FLAG),
    SAR("sar", 2, TEXT_FLAG),
    SHR("shr", 2, TEXT_FLAG),
    SAL("sal", 2, TEXT_FLAG),
    SHL("shl", 2, TEXT_FLAG),

    /**
     * LOGIC-Operators
     */
    NOT("not", 1, TEXT_FLAG),
    AND("and", 2, TEXT_FLAG),
    OR("or", 2, TEXT_FLAG),
    XOR("xor", 2, TEXT_FLAG),
    CMP("cmp", 2, TEXT_FLAG),

    /**
     * CALL-operator
     */
    CALL("call", 1, TEXT_FLAG),

    /**
     * JUMP-Operators
     */
    JMP("jmp", 1, TEXT_FLAG),
    JZ("jz", 1, TEXT_FLAG),
    JE("je", 1, TEXT_FLAG),
    JNZ("jnz", 1, TEXT_FLAG),
    JNE("jne", 1, TEXT_FLAG),
    JOVF("jovf", 1, TEXT_FLAG),
    JLE("jle", 1, TEXT_FLAG),
    JNG("jng", 1, TEXT_FLAG),
    JL("jl", 1, TEXT_FLAG),
    JNGE("jnge", 1, TEXT_FLAG),

    /**
     * SECTION-Operators
     */
    EQU("equ", 2, VAR_FLAG),
    DEF("def", 2, DATA_FLAG),
    RES("res", 2, BSS_FLAG);

    // TODO : new Operators
    //      MOD("mod", TEXT_FLAG)
    //      ...


    private String operatorMEMO;
    private int arity;
    private int sectionFlag;

    public int getArity() {
        return arity;
    }

    Operators(String operatorMEMO, int arity, int sectionFlag) {
        this.operatorMEMO = operatorMEMO;
        this.arity = arity;
        this.sectionFlag = sectionFlag;
    }

    public boolean equals(Operators operator) {
        return operator != null && this.operatorMEMO.equals(operator.operatorMEMO);
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
