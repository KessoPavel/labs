library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
LIBRARY work;

entity D_aRS_Trigger_TB is
end D_aRS_Trigger_TB;

architecture Behavioral of D_aRS_Trigger_TB is    

component D_Trigger_aRS
	Port ( D : in STD_LOGIC;
           C : in STD_LOGIC;
           notR : in STD_LOGIC;
           notS : in STD_LOGIC;
           Q : out STD_LOGIC;
           notQ : out STD_LOGIC);
end component;

signal C : STD_LOGIC := '0';
signal D : STD_LOGIC := '0';
signal notR : STD_LOGIC := '1';
signal notS : STD_LOGIC := '1';
signal Q : STD_LOGIC;
signal nQ : STD_LOGIC;

constant clk_period : time := 10ns;

begin
uut : D_Trigger_aRS port map(
		D => D,
		C => C,
		notR => notR,
		notS => notS,
		Q => Q,
		notQ => nQ);

clk_process :process
  begin
	C <= '0';
  wait for clk_period/2;
	C <= '1';
  wait for clk_period/2;
end process;
  
stim_proc: process
  begin  
	D <= '0';
  wait for clk_period + clk_period/3;
	D <= '1';  
	wait for clk_period/2;
	D <= '0';
	wait for clk_period;
	D <= '1';
	wait for 2 * clk_period;
	D <= '0';
	wait for clk_period + clk_period/2;
	D <= '1';
	wait for 2 * clk_period;
	D <= '0';
	wait for 2 * clk_period;
	notS <= '0';
	wait for clk_period + clk_period/2;
	notS <= '1';
	wait for clk_period;
	D <= '1';
	wait for clk_period/2;
	notR <= '0';
	wait for clk_period + clk_period/2;
	notR <= '1';
	wait for clk_period/2;
	D <= '0';
	wait for clk_period + clk_period/2;
  end process;  

end Behavioral;
