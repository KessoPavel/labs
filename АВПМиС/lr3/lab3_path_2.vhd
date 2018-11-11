library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.NUMERIC_STD.ALL;
use IEEE.std_logic_textio.ALL;
library STD;
use std.textio.ALL;

entity lab3_path_2 is
    file test_file : text;
    signal OutVect : std_logic_vector(4 downto 0);
end lab3_path_2;

architecture Behavioral of lab3_path_2 is
    Component CarryGeneratorSerial
    Port ( nP3 : in STD_LOGIC;
              nG3 : in STD_LOGIC;
              nP2 : in STD_LOGIC;
              nG2 : in STD_LOGIC;
              nP1 : in STD_LOGIC;
              nG1 : in STD_LOGIC;
              nP0 : in STD_LOGIC;
              nG0 : in STD_LOGIC;
              Cn : in STD_LOGIC;
              nP : out STD_LOGIC;
              nG : out STD_LOGIC;
              Cn_z : out STD_LOGIC;
              Cn_y : out STD_LOGIC;
              Cn_x : out STD_LOGIC);
    END Component;
   
    Component CarryGeneratorParallel
        Port ( nP3 : in STD_LOGIC;
              nG3 : in STD_LOGIC;
              nP2 : in STD_LOGIC;
              nG2 : in STD_LOGIC;
              nP1 : in STD_LOGIC;
              nG1 : in STD_LOGIC;
              nP0 : in STD_LOGIC;
              nG0 : in STD_LOGIC;
              Cn : in STD_LOGIC;
              nP : out STD_LOGIC;
              nG : out STD_LOGIC;
              Cn_z : out STD_LOGIC;
              Cn_y : out STD_LOGIC;
              Cn_x : out STD_LOGIC);
    END Component;
      
    --Input
    signal nP3 : STD_LOGIC := '0';
    signal nG3 : STD_LOGIC := '0';
    signal nP2 : STD_LOGIC := '0';
    signal nG2 : STD_LOGIC := '0';
    signal nP1 : STD_LOGIC := '0';
    signal nG1 : STD_LOGIC := '0';
    signal nP0 : STD_LOGIC := '0';
    signal nG0 : STD_LOGIC := '0';
    signal Cn : STD_LOGIC := '0';          
   
    --Output
    signal nP_s : STD_LOGIC;
    signal nG_s : STD_LOGIC;
    signal Cn_z_s : STD_LOGIC;
    signal Cn_y_s : STD_LOGIC;
    signal Cn_x_s : STD_LOGIC; 
	
    signal nP_p : STD_LOGIC;
    signal nG_p : STD_LOGIC;
    signal Cn_z_p : STD_LOGIC;
    signal Cn_y_p : STD_LOGIC;
    signal Cn_x_p : STD_LOGIC; 
    
	
     
    --
begin
mapping_serial: CarryGeneratorSerial PORT MAP(
    nP3 => nP3,
    nG3 => nG3,
    nP2 => nP2,
    nG2 => nG2,
    nP1 => nP1,
    nG1 => nG1,
    nP0 => nP0,
    nG0 => nG0,
    Cn => Cn,
    nP => nP_s,
    nG => nG_s,
    Cn_z => Cn_z_s,
    Cn_y => Cn_y_s,
    Cn_x => Cn_x_s
    );
    
mapping_parallel: CarryGeneratorParallel PORT MAP(
        nP3 => nP3,
        nG3 => nG3,
        nP2 => nP2,
        nG2 => nG2,
        nP1 => nP1,
        nG1 => nG1,
        nP0 => nP0,
        nG0 => nG0,
        Cn => Cn,
        nP => nP_p,
        nG => nG_p,
        Cn_z => Cn_z_p,
        Cn_y => Cn_y_p,
        Cn_x => Cn_x_p
        );
    

file_process :process  
    variable file_status : file_open_status;
    variable inputs : line;
    variable outputs : line;
    variable SigVect : std_logic_vector(8 downto 0);
    variable SigOutVect : std_logic_vector(4 downto 0);
    begin
      file_open(file_status, test_file, "test.txt", read_mode);
      
      while not endfile(test_file) loop
        readline(test_file, inputs);
        readline(test_file, outputs);
        read(inputs, SigVect);  
        read(outputs, SigOutVect);      
      
        nP3 <= SigVect(8);
        nP2 <= SigVect(7);
        nP1 <= SigVect(6);
        nP0 <= SigVect(5);
        nG3 <= SigVect(4);
        nG2 <= SigVect(3);
        nG1 <= SigVect(2);
        nG0 <= SigVect(1);
        Cn <= SigVect(0);
                  
        assert Cn_x_s = Cn_x_p and Cn_y_s = Cn_y_p and Cn_z_s = Cn_z_p and nG_s = nG_p and nP_s = nP_p report "TEST ERROR" severity error;

      wait for 20 ns;
      end loop;
    end process;
end Behavioral;