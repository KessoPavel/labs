library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.NUMERIC_STD.ALL;
use IEEE.std_logic_textio.ALL;
library STD;
use std.textio.ALL;

entity ShiftRegister_TB is
  file test_file : text;
  signal OutVect : std_logic_vector(4 downto 0);
end ShiftRegister_TB;

architecture Behavioral of ShiftRegister_TB is    

component ShiftRegister
  Port (   nG : in STD_LOGIC;
		       nSRCLR : in STD_LOGIC;
           nSRCKEN : in STD_LOGIC;
           SRCK : in STD_LOGIC;
           nSRLOAD : in STD_LOGIC;
           DS : in STD_LOGIC;
		       SER0 : in STD_LOGIC;
           SER1 : in STD_LOGIC;
           RCK : in STD_LOGIC;
           
           QH_out : out STD_LOGIC;
           
           QA : inout STD_LOGIC;
           QB : inout STD_LOGIC;
           QC : inout STD_LOGIC;
           QD : inout STD_LOGIC;
           QE : inout STD_LOGIC;
           QF : inout STD_LOGIC;
           QG : inout STD_LOGIC;
           QH : inout STD_LOGIC);
end component;

signal nG : STD_LOGIC := '0';
signal nSRCLR : STD_LOGIC := '0';
signal nSRCKEN : STD_LOGIC := '0';
signal SRCK : STD_LOGIC := '0';
signal nSRLOAD : STD_LOGIC := '0';
signal DS : STD_LOGIC := '0';
signal SER0 : STD_LOGIC := '0';
signal SER1 : STD_LOGIC := '0';
signal RCK : STD_LOGIC := '0';

signal QH_out : STD_LOGIC;

signal QA : STD_LOGIC;
signal QB : STD_LOGIC;
signal QC : STD_LOGIC;
signal QD : STD_LOGIC;
signal QE : STD_LOGIC;
signal QF : STD_LOGIC;
signal QG : STD_LOGIC; 
signal QH : STD_LOGIC; 

constant clk_period : time := 10ns;

begin
uut : ShiftRegister port map(
    nG => nG,
    nSRCLR => nSRCLR,
    nSRCKEN => nSRCKEN,
    SRCK => SRCK,
    nSRLOAD => nSRLOAD,
    DS =>DS, 
	  SER0 => SER0, 
    SER1 => SER1,
    RCK => RCK,
    QH_out => QH_out, 
    QA => QA, 
    QB => QB, 
    QC => QC, 
    QD => QD, 
    QE => QE, 
    QF => QF, 
    QG => QG, 
    QH => QH);

clk_process :process
  begin
  SRCK <= '0';
  wait for clk_period/2;
  SRCK <= '1';
  wait for clk_period/2;
end process;
  
stim_proc: process
    variable file_status : file_open_status;
    variable current_line : line;
    --1
    begin  
    nSRCKEN <= '1';
    nSRLOAD <= '1';
    wait for clk_period;
    --2
    nG <= '1';
    
    QA <= '1'; 
    QB <= '0';
    QC <= '1';
    QD <= '0';
    QE <= '1';
    QF <= '0';
    QG <= '1';
    QH <= '1';
    nSRCLR <= '1';
    wait for clk_period/2;
    --2.5
    RCK <= '1';
    wait for clk_period/2;
    --3
    RCK <= '0';
    
    nG <= '0';
    nSRLOAD <= '0';
    
    QA <= 'Z';
    QB <= 'Z';
    QC <= 'Z';
    QD <= 'Z';
    QE <= 'Z';
    QF <= 'Z';
    QG <= 'Z';
    QH <= 'Z';
    wait for clk_period;
    --4
    nSRCKEN <= '0';
    nSRLOAD <= '1';
    DS <= '0';
    SER0 <= '0';
    SER1 <= '0';
    wait for clk_period;
    --5  
    SER1 <= '1';
    wait for clk_period;
    --6
    SER1 <= '0';
    SER0 <= '1';
    wait for clk_period; 
    --7
    SER1 <= '1'; 
    wait for clk_period; 
    --8
    DS <= '1';
    SER0 <= '0';
    SER1 <= '0';
    wait for clk_period; 
    --9
    SER1 <= '1';
    wait for clk_period; 
    --10  
    SER0 <= '1';
    wait for clk_period; 
    --11  
    nSRCKEN <= '1';
    DS <= '0';
    SER0 <= '0';
    SER1 <= '0';
    wait for clk_period/2;
    --11.5
    RCK <= '0';
    wait for clk_period/2;
    --12  
    RCK <= '1';
    nSRCLR <= '0';
    wait for clk_period; 
    end process;  

end Behavioral;