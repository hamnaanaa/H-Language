package Assembly.AssemblyConstants;

public enum Opcodes {
    IFETCH("ifetch", "0x00"),
    INC_RA("inc_ra", "0x01"),
    INC_RB("inc_rb", "0x02"),
    JMP_imm("jmp_imm", "0x03");
// TODO : all enums (with space or underline?)
//      MOV_RB_RA("mov rb ra", )


    private String OPName;
    private String hexValue;

    private Opcodes(String OPName, String hexValue) {
        this.OPName = OPName;
        this.hexValue = hexValue;
    }
}
